package com.example.safetyproject;

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
		Intent addInformation = new Intent(this, LocationActivity.class);
		startActivity(addInformation);
	}

}
