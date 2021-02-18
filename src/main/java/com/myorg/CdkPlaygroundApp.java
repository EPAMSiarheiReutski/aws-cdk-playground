package com.myorg;

import software.amazon.awscdk.core.App;

public class CdkPlaygroundApp
{
	public static void main(final String[] args)
	{
		App app = new App();

		final CdkVpcStack cdkVpcStack = new CdkVpcStack(app, "CdkVpcStack");
		new CdkEc2Stack(app, "CdkEc2Stack", cdkVpcStack.getVpc());

		app.synth();
	}
}
