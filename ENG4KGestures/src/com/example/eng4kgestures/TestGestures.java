package com.example.eng4kgestures;

import java.util.ArrayList;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
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
		// Start Testing
		addItem("Milan");
	}
	
	public void onClickStop(View v) {
		// Stop Testing and add results to the resultsList
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
