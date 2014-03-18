package com.example.safetyproject;

import com.parse.Parse;
import com.parse.ParseACL;

import com.parse.ParseUser;

import android.app.Application;

public class ParseApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		// Add your initialization code here
		 Parse.initialize(this, "aiizf8TiGbMBXOuqChsatoDvaD0MpWyjaz5tuiQs", "qVha5rt4cvvxb32060SZfiRF9YfNRvXB8Nz9Bhhl");


		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();
	    
		// If you would like all objects to be private by default, remove this line.
		defaultACL.setPublicReadAccess(true);
		
		ParseACL.setDefaultACL(defaultACL, true);
	}

}
