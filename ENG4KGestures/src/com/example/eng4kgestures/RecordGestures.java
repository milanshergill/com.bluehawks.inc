package com.example.eng4kgestures;

import java.util.ArrayList;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RecordGestures extends Activity implements SensorEventListener {
	SensorManager sensorManager;
	Sensor accelerometerSensor;
	private ArrayList<Acceleration> accelerationList;
	private float accelX, accelY, accelZ;
	CountDownTimer timer;
	Boolean startRecording = false;
	TextView recordingStatus;
	EditText gestureName;
	private GestureDataBase gestureDataBase;
	Acceleration acceletationObject;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record_gestures);
		
		//EditText having the gesture name
		gestureName = (EditText) findViewById(R.id.gestureName);
		
		//textCiew to show the current recording status
		recordingStatus =  (TextView) (findViewById((R.id.Recording_Status)));
		
		//setting up database for acceleration recording
		gestureDataBase = new GestureDataBase(this);
	    gestureDataBase.open();	    
	    
		//initialize the sensor
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	    accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);	
	    
	    //Array list to hold sensor data
	    accelerationList = new ArrayList<Acceleration>();
	    
	    
	    //timer to start recording accelerometer data
	    timer = new CountDownTimer(20000, 10) {
	    	 public void onTick(long millisUntilFinished) {
		    	if(startRecording){
		    		acceletationObject =  new Acceleration(accelX,accelY,accelZ);
		    		accelerationList.add(acceletationObject);
		    		recordingStatus.setText("Recording Data");
		    	}
	    	 }
	    	 public void onFinish() {
	    		startRecording = false; 
	    		recordingStatus.setText("Stopped");
	    	 }
	    };
    }
	
	protected void processData() {
		// TODO Auto-generated method stub
		String name = gestureName.getText().toString();
		Gesture gesture = createGesureObject(name, accelerationList);
		gestureDataBase.insertGesture(gesture);
	}
	
	//Service methods for the accelerometer initialization
    protected void onResume() {
	    super.onResume();
	    gestureDataBase.open();
	    sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    
  //Service methods for the accelerometer initialization
    protected void onPause() {
	    super.onPause();
	    gestureDataBase.close();
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
		startRecording = true;
		accelerationList.clear();
		timer.start();
	}
	
	public void onClickStopRecording(View v) {
		startRecording = false;
		recordingStatus.setText("Stopped, The Size of Array is " + accelerationList.size());
	}
	
	public Gesture createGesureObject(String name, ArrayList<Acceleration> accelerationList) {
		int size =  accelerationList.size();
		Acceleration [] accelerationArray = new Acceleration[size] ;
		for (int i = 0; i < size; i++) {
			accelerationArray[i] = accelerationList.get(i);
		}
		 
		Gesture gestureObject =  new  Gesture(name, accelerationArray);
		return gestureObject;
	}
}
	
	
