package com.myorg.constructs;

import org.jetbrains.annotations.NotNull;
import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.services.ec2.AmazonLinuxImage;
import software.amazon.awscdk.services.ec2.ISecurityGroup;
import software.amazon.awscdk.services.ec2.ISubnet;
import software.amazon.awscdk.services.ec2.IVpc;
import software.amazon.awscdk.services.ec2.Instance;
import software.amazon.awscdk.services.ec2.InstanceClass;
import software.amazon.awscdk.services.ec2.InstanceSize;
import software.amazon.awscdk.services.ec2.InstanceType;
import software.amazon.awscdk.services.ec2.SubnetSelection;

import java.util.Collections;

public class Ec2InstanceConstruct extends Construct
{
	private final Instance instance;

	public Ec2InstanceConstruct(@NotNull Construct scope,
	                            @NotNull String id,
	                            @NotNull IVpc vpc,
	                            @NotNull ISubnet subnet,
	                            @NotNull ISecurityGroup securityGroup)
	{
		super(scope, id);

		instance = Instance.Builder.create(this, id)
				.vpc(vpc)
				.instanceType(InstanceType.of(InstanceClass.BURSTABLE2, InstanceSize.MICRO))
				.machineImage(AmazonLinuxImage.Builder.create().build())
				.vpcSubnets(SubnetSelection.builder()
						.subnets(Collections.singletonList(subnet))
						.build())
				.instanceName(id)
				.securityGroup(securityGroup)
				.keyName("instanceKey")
				.build();
	}

	public Instance getInstance()
	{
		return instance;
	}
}
