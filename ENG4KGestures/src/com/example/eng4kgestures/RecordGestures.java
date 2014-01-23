package com.example.eng4kgestures;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.View;

public class RecordGestures extends Activity {
	SensorManager sensorManager;
	List<Sensor> sensorList;
	private ArrayList<String> sensorDataList;
	CountDownTimer timer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record_gestures);
		
		//initialize the sensors
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	    sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);

	    //Array list to hold sensor data
	    sensorDataList = new ArrayList<String>();
	    
	    for (Sensor s : sensorList) {
	    	sensorManager.registerListener((SensorEventListener) this, s, SensorManager.SENSOR_DELAY_FASTEST);
	    }
	    
	    timer = new CountDownTimer(20000, 50) {
	    	 public void onTick(long millisUntilFinished) {
	    		 
	    	 }
	    	 public void onFinish() {
	    		 
	    	 }
	    };
	    
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	
}
