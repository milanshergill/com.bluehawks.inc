package com.example.circleof6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	Button circleOf6;
	Button setTimer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		circleOf6 = (Button) findViewById(R.id.startCirleOf6);
		setTimer = (Button) findViewById(R.id.setTimer);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void startCircleOf6Activity(View v) {
		Intent circleOf6 = new Intent(this, CircleOf6.class);
		startActivity(circleOf6);
	}
	public void startsetTimerActivity(View v) {
		Intent timerActivity = new Intent(this, SetTimer.class);
		startActivity(timerActivity);
	}
	
}
