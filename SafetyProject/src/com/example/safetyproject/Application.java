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
		Parse.initialize(this, "aiizf8TiGbMBXOuqChsatoDvaD0MpWyjaz5tuiQs",
				"qVha5rt4cvvxb32060SZfiRF9YfNRvXB8Nz9Bhhl");

		// Specify an Activity to handle all pushes by default.
		PushService.setDefaultPushCallback(this, MainActivity.class);
	}
}