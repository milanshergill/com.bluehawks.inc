package com.example.eng4kgestures;

import java.util.ArrayList;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class TestGestures extends Activity implements SensorEventListener {
	
	Button start, stop;
	TextView results;
	ListView resultsList;
	
	SensorManager sensorManager;
	Sensor accelerometerSensor;
	private float accelX, accelY, accelZ;
	private float oldAccelX, oldAccelY, oldAccelZ = 0;
	private ArrayList<Float> accelXList, accelYList, accelZList;
	CountDownTimer timer;
	int foundSame = 0;
	double minDistance = -1;
	
    ArrayList<String> listItems = new ArrayList<String>();
    ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_gestures);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
		
		start = (Button) findViewById(R.id.start);
		start = (Button) findViewById(R.id.stop);
		results = (TextView) findViewById(R.id.resultsView);
		resultsList = (ListView) findViewById(R.id.resultsList);
		
		resultsList.setAdapter(adapter);
		
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	    accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	    
	    accelXList = new ArrayList<Float>();
	    accelYList = new ArrayList<Float>();
	    accelZList = new ArrayList<Float>();
	    
	    timer = new CountDownTimer(20000, 50) {

	        public void onTick(long millisUntilFinished) {
        		if(accelX != oldAccelX && accelY != oldAccelY && accelZ != oldAccelZ) {
        			accelXList.add(Float.valueOf(accelX)); //Contains only acceleration "x" values
	    			accelYList.add(Float.valueOf(accelY)); //Contains only acceleration "y" values
	    			accelZList.add(Float.valueOf(accelZ)); //Contains only acceleration "z" values
        		}
        		else
        			foundSame++;
    			oldAccelX = accelX;
        		oldAccelY = accelY;
        		oldAccelZ = accelZ;
    		}

	        public void onFinish() {
	            processData();
	            foundSame = 0;
	            oldAccelX = 0;
        		oldAccelY = 0;
        		oldAccelZ = 0;
	        }
	     };
	}
	
	protected void processData() {
		// TODO Auto-generated method stub
		String name = "Circle Gesture";
		double [][] recordedAccel = new double[3][accelXList.size()];
		for (int i = 0; i < recordedAccel.length; i++) {
			recordedAccel[1][i] = accelX;
			recordedAccel[2][i] = accelY;
			recordedAccel[3][i] = accelZ;
		}
		minDistance = (double) DynamicTimeWarping.calcDistance(recordedAccel, recordedAccel);
		addItem(name + minDistance);
	}

	protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
	
	public void onClickStart(View v) {
		// Start Recording Data & send for Testing
		timer.start();
	}
	
	public void onClickStop(View v) {
		// Stop Testing and add results to the resultsList
		if (timer != null)
			timer.cancel();
	}
	
	public void addItem(String value) {
		listItems.add(value);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
			accelX = event.values[0];
			accelY = event.values[1];
			accelZ = event.values[2];
		}
	}
}
