package com.myorg;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.ec2.IVpc;
import software.amazon.awscdk.services.ec2.SubnetConfiguration;
import software.amazon.awscdk.services.ec2.SubnetType;
import software.amazon.awscdk.services.ec2.Vpc;

import java.util.Arrays;

public class CdkVpcStack extends Stack
{
	private final IVpc vpc;

	public CdkVpcStack(final Construct scope, final String id)
	{
		this(scope, id, null);
	}

	public CdkVpcStack(final Construct scope, final String id, final StackProps props)
	{
		super(scope, id, props);

		vpc = Vpc.Builder.create(this, "DemoVpc")
				.maxAzs(2)
				.cidr("10.0.0.0/16")
				.subnetConfiguration(Arrays.asList(
						SubnetConfiguration.builder()
								.name("Public")
								.cidrMask(24)
								.subnetType(SubnetType.PUBLIC)
								.build(),
						SubnetConfiguration.builder()
								.name("Private")
								.cidrMask(24)
								.subnetType(SubnetType.PRIVATE)
								.build()
				))
				.build();
	}

	public IVpc getVpc()
	{
		return vpc;
	}
}
