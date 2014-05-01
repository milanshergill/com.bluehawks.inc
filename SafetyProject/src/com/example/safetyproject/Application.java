package com.example.safetyproject;

import com.parse.Parse;
import com.parse.PushService;

public class Application extends android.app.Application {

	public Application() {
	}

	@Override
	public void onCreate() {
		super.onCreate();

		// Initialize the Parse SDK.
		Parse.initialize(this, "4hr6Vv7ULByMWRiLDE60Egpsb8FLRPeHPCjAbnWs",
				"wMRkYC1P05iZQ8chHTvPVYrQ6OLEMvqspzboUQul");

		// Specify an Activity to handle all pushes by default.
		PushService.setDefaultPushCallback(this, MainActivity.class);
	}
}