package com.example.eng4kgestures;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	Button RecordGestures;
	Button TestGestures;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		RecordGestures =  (Button) (findViewById((R.id.RecordGestures))) ;
		TestGestures =  (Button) (findViewById((R.id.TestGestures))) ;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onClickRecordGestures(View v) {
	Intent myIntent = new Intent(MainActivity.this, RecordGestures.class);
	MainActivity.this.startActivity(myIntent);
	}
}
