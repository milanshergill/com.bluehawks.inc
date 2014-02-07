package com.example.eng4kgestures;

import java.util.ArrayList;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RecordGestures extends Activity implements SensorEventListener {
	SensorManager sensorManager;
	Sensor accelerometerSensor;
	int count = 0;
	private ArrayList<Acceleration> accelerationList;
	private float accelX, accelY, accelZ;
	CountDownTimer timer;
	boolean startRecording = false, buttonPressed = false;
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
	    gestureDataBase.openWriteable();	    
	    
		//initialize the sensor
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	    accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);	
	    
	    //Array list to hold sensor data
	    accelerationList = new ArrayList<Acceleration>();
	    
	    
	    //timer to start recording accelerometer data
	    timer = new CountDownTimer(10000, 20) {
	    	 public void onTick(long millisUntilFinished) {
	    		 count++;
		    	if(startRecording){
		    		acceletationObject =  new Acceleration(accelX,accelY,accelZ);
		    		accelerationList.add(acceletationObject);
		    		recordingStatus.setText("Recording Data");
		    	}
	    	 }
	    	 public void onFinish() {
	    		 processData(14.0);
	    		 startRecording = false; 
//	    		 recordingStatus.setText("Stopped");
	    		 // Clear the old readings from the list
	    		 accelerationList.clear();
	    		 count = 0;
	    	 }
	    };
    }
	
	protected void processData(double d) {
		// Add this reading to database only if there is something recorded
		if(!accelerationList.isEmpty()) {
			String name = gestureName.getText().toString();
			boolean thresholdMet = false;
			int size = accelerationList.size();
			for(int i =0; (i< accelerationList.size() )&&(!thresholdMet);)
			{
				float value = (float) Math.pow((Math.pow(accelerationList.get(i).getAccelerationX(), 2)
							+Math.pow(accelerationList.get(i).getAccelerationY(), 2)
							+Math.pow(accelerationList.get(i).getAccelerationZ(), 2)), 0.5);
				if(value < d)
				{
					accelerationList.remove(i);
				}
				else
				{
					thresholdMet = true;
				}
				
			}
			int k = accelerationList.size();
			if(!accelerationList.isEmpty()) {
				int j = Math.min(50,accelerationList.size());
			for(;j<accelerationList.size();)
			{
				accelerationList.remove(j);
			}
			
			Gesture gesture = createGesureObject(name, accelerationList);
			gestureDataBase.insertGesture(gesture);
			}
			recordingStatus.setText("Gesture saved, initial size was " +size+ " after cleanup size was "+k+ " the size of accel array is " + accelerationList.size() );
		}
//		else
//			recordingStatus.setText("Nothing recorded, press Start to record data.");
	}
	
	//Service methods for the accelerometer initialization
    protected void onResume() {
	    super.onResume();
	    gestureDataBase.openWriteable();
	    sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_FASTEST);
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
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			accelX = event.values[0];
			accelY = event.values[1];
			accelZ = event.values[2];
		}
	}
	
	public void onClickStartRecording(View v) {
//		recordingStatus.setText("Waiting for data to be recorded...");
		startRecording = true;
		accelerationList.clear();
		timer.start();
	}
	
	public void onClickStopRecording(View v) {
		startRecording = false;
		if (timer != null) {
			timer.cancel();
			timer.onFinish();
		}
//		recordingStatus.setText("Stopped, The Size of Array is " + accelerationList.size());
	}
	
	public Gesture createGesureObject(String name, ArrayList<Acceleration> accelerationList) {
		if(name.isEmpty())
			name = "Default Name";
		int size =  accelerationList.size();
		Acceleration [] accelerationArray = new Acceleration[size] ;
		for (int i = 0; i < size; i++) {
			accelerationArray[i] = accelerationList.get(i);
		}
		 
		Gesture gestureObject =  new  Gesture(name, accelerationArray);
		return gestureObject;
	}
	
	
	
	
	public boolean dispatchKeyEvent(KeyEvent event) {
	    int action = event.getAction();
	    int keyCode = event.getKeyCode();
        switch (keyCode) {
        case KeyEvent.KEYCODE_VOLUME_UP:
            if (action == KeyEvent.ACTION_DOWN) {
            	if(!buttonPressed) {
            		buttonPressed = true;
            		startRecording = true;
            		accelerationList.clear();
            		timer.start();
            	}
            }
            if (action == KeyEvent.ACTION_UP) {
            	startRecording = false;
        		if (timer != null) {
        			timer.cancel();
        			timer.onFinish();
        		}
				buttonPressed = false;
            }
            return true;
        case KeyEvent.KEYCODE_VOLUME_DOWN:
            if (action == KeyEvent.ACTION_DOWN) {
            	if(!buttonPressed) {
            		buttonPressed = true;
            		startRecording = true;
            		accelerationList.clear();
            		timer.start();
            	}
            }
            if (action == KeyEvent.ACTION_UP) {
            	startRecording = false;
        		if (timer != null) {
        			timer.cancel();
        			timer.onFinish();
        		}
				buttonPressed = false;
            }
            return true;
        default:
            return super.dispatchKeyEvent(event);
        }
	}
	
}
	
	
