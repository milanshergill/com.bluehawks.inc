package com.example.eng4kgestures;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class TestGestures extends Activity {
	
	Button start, stop;
	TextView results;
	ListView resultsList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_gestures);
		
		start = (Button) findViewById(R.id.start);
		start = (Button) findViewById(R.id.stop);
		results = (TextView) findViewById(R.id.resultsView);
		resultsList = (ListView) findViewById(R.id.resultsList);
	}
	
	public void onClickStart(View v) {
		//Start Testing
		results.setVisibility(0);
	}
	
	public void onClickStop(View v) {
		// Stop Testing and add results to the resultsList
		results.setVisibility(1);
	}
}
