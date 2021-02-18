package com.myorg;

import com.myorg.constructs.Ec2InstanceConstruct;
import software.amazon.awscdk.core.CfnParameter;
import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.services.ec2.ISecurityGroup;
import software.amazon.awscdk.services.ec2.IVpc;
import software.amazon.awscdk.services.ec2.Peer;
import software.amazon.awscdk.services.ec2.Port;
import software.amazon.awscdk.services.ec2.SecurityGroup;

import java.util.stream.IntStream;

public class CdkEc2Stack extends Stack
{
	public CdkEc2Stack(Construct scope, String id, IVpc vpc)
	{
		super(scope, id);

		final ISecurityGroup publicInstancesSG = SecurityGroup.Builder.create(this, "PublicInstancesSG")
				.vpc(vpc)
				.securityGroupName("Public")
				.allowAllOutbound(true)
				.build();

		final ISecurityGroup privateInstancesSG = SecurityGroup.Builder.create(this, "PrivateInstancesSG")
				.vpc(vpc)
				.securityGroupName("Private")
				.allowAllOutbound(true)
				.build();

		final CfnParameter myIp = CfnParameter.Builder.create(this, "myIp")
				.build();

		// cdk deploy --all --profile sr_cdk --parameters CdkEc2Stack:myIp=""
		publicInstancesSG.addIngressRule(Peer.ipv4(myIp.getValueAsString()), Port.tcp(22));
		privateInstancesSG.addIngressRule(publicInstancesSG, Port.allTraffic());

		IntStream.range(0, vpc.getPublicSubnets().size())
				.forEach(i -> new Ec2InstanceConstruct(this, "Public" + i, vpc, vpc.getPublicSubnets().get(i), publicInstancesSG));

		IntStream.range(0, vpc.getPrivateSubnets().size())
				.forEach(i -> new Ec2InstanceConstruct(this, "Private" + i, vpc, vpc.getPrivateSubnets().get(i), privateInstancesSG));
	}
}
