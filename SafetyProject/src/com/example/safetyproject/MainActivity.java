package com.example.safetyproject;

import com.example.eng4kgestures.GestureActivity;
import com.example.eng4kgestures.TestGestures;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.PushService;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// To track statistics around application
//        ParseAnalytics.trackAppOpened(getIntent());
// 
//        // inform the Parse Cloud that it is ready for notifications
//        PushService.setDefaultPushCallback(this,  MainActivity.class);
//        ParseInstallation.getCurrentInstallation().saveInBackground();
		
		 Parse.initialize(this, "aiizf8TiGbMBXOuqChsatoDvaD0MpWyjaz5tuiQs", "qVha5rt4cvvxb32060SZfiRF9YfNRvXB8Nz9Bhhl");
		 PushService.setDefaultPushCallback(this, MainActivity.class);
		 ParseAnalytics.trackAppOpened(getIntent());
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void startCall911Activity(View v) {
		String phoneCallUri = "tel:647";
		Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);
		phoneCallIntent.setData(Uri.parse(phoneCallUri));
		startActivity(phoneCallIntent);
	}

	public void startAlertLocationActivity(View v) {
		Intent SendDataToServerActivity = new Intent(this, SendDataToServer.class);
		startActivity(SendDataToServerActivity);
	}

	public void startsetTimerActivity(View v) {
		Intent timerActivity = new Intent(this, SetTimer.class);
		startActivity(timerActivity);
	}

	public void startCircleOf6Activity(View v) {
		Intent circleOf6 = new Intent(this, CircleOf6.class);
		startActivity(circleOf6);
	}
	
	public void startAddInformationActivity(View v) {
		Intent addInformation = new Intent(this, UserProfileActivity.class);
		startActivity(addInformation);
	}
	
	public void startGestureRecognition(View v) {
		Intent gestureRecoginition = new Intent(this, GestureActivity.class);
		startActivity(gestureRecoginition);
	}
	
	public void activateGestures(View v) {
		Intent activateGestures = new Intent(this, TestGestures.class);
		startActivity(activateGestures);
	}
}
