package com.example.eng4kgestures;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RecordGestures extends Activity implements SensorEventListener {
	SensorManager sensorManager;
	Sensor accelerometerSensor;
	private ArrayList<Float> sensorXDataList;
	private ArrayList<Float> sensorYDataList;
	private ArrayList<Float> sensorZDataList;
	private float accelX, accelY, accelZ;
	CountDownTimer timer;
	Boolean StartRecording = false;
	TextView Recording_Status;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record_gestures);
		
		Recording_Status =  (TextView) (findViewById((R.id.Recording_Status)));
		
		//initialize the sensor
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	    accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);	
	    
	    //Array list to hold sensor data
	    sensorXDataList = new ArrayList<Float>();
	    sensorYDataList = new ArrayList<Float>();
	    sensorZDataList = new ArrayList<Float>();
	    
	    //timer to start recording accelerometer data
	    timer = new CountDownTimer(20000, 10) {
	    	 public void onTick(long millisUntilFinished) {
		    	if(StartRecording){
		    		sensorXDataList.add(accelX);
		    		sensorYDataList.add(accelY);
		    		sensorZDataList.add(accelZ);
		    		Recording_Status.setText("Recording Data");
		    	}
	    	 }
	    	 public void onFinish() {
	    		StartRecording = false; 
	    		Recording_Status.setText("Stopped");
	    	 }
	    };
	    
	    
    }
	
	//Service methods for the accelerometer initialization
    protected void onResume() {
	    super.onResume();
	    sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    
  //Service methods for the accelerometer initialization
    protected void onPause() {
	    super.onPause();
	    sensorManager.unregisterListener(this);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
				if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
					accelX = event.values[0];
					accelY = event.values[1];
					accelZ = event.values[2];
				}
		
	}
	
	public void onClickStartRecording(View v) {
		StartRecording = true;
		sensorXDataList.clear();
		sensorYDataList.clear();
		sensorZDataList.clear();
		timer.start();
		}
	
	public void onClickStopRecording(View v) {
		StartRecording = false;
		Recording_Status.setText("Stopped, The Size of Array is " + sensorXDataList.size());
		}
	
	
}
