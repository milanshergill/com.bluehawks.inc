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
	private ArrayList<Acceleration> accelerationList;
	private float accelX, accelY, accelZ;
	CountDownTimer timer;
	Boolean StartRecording = false;
	TextView Recording_Status;
	private GestureDataBase datasource;
	Acceleration acceletationObject;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record_gestures);
		//textCiew to show the current recording status
		Recording_Status =  (TextView) (findViewById((R.id.Recording_Status)));
		
		//setting up database for acceleration recording
		datasource = new GestureDataBase(this);
	    datasource.open();
	    
//	    List<Acceleration> values = datasource.getAllAccelerations();
	    
	    
		//initialize the sensor
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	    accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);	
	    
	    //Array list to hold sensor data
	    accelerationList = new ArrayList<Acceleration>();
	    
	    
	    //timer to start recording accelerometer data
	    timer = new CountDownTimer(20000, 10) {
	    	 public void onTick(long millisUntilFinished) {
		    	if(StartRecording){
		    		acceletationObject =  new Acceleration(accelX,accelY,accelZ);
		    		accelerationList.add(acceletationObject);
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
	    datasource.open();
	    sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    
  //Service methods for the accelerometer initialization
    protected void onPause() {
	    super.onPause();
	    datasource.close();
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
		accelerationList.clear();
		timer.start();
		}
	
	public void onClickStopRecording(View v) {
		StartRecording = false;
		Recording_Status.setText("Stopped, The Size of Array is " + accelerationList.size());
		}
	
	 public Gesture createGesureObject(String name,ArrayList<Acceleration> accelerationList) {
		 int size =  accelerationList.size();
		 Acceleration [] accelerationArray = new Acceleration[size] ;
		 for (int i = 0; i < size; i++) {
			accelerationArray[i] = accelerationList.get(i);
		 }
		 
		 Gesture gestureObject =  new  Gesture(name,accelerationArray);
		 return gestureObject;
		
		}
}
	
	
