package com.example.android.mediafx;

import java.net.DatagramSocket;
import java.util.List;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UDP_Main extends Activity implements SensorEventListener {
	
	public static final int SERVERPORT = 12345;
	public TextView text1; 
	public EditText serverIp;
	public Button btn;
	public Button btn2;
	SensorManager sensorManager;
	List<Sensor> sensorList;
	private float mSensorX;
	private float mSensorY;
	private float mSensorZ;
	private long mSensorTimeStamp;
	private long mCpuTimeStamp;
	private String da;
	DatagramSocket socket;
	
	/** Called when the activity is first created. */ 
	@Override 
	public void onCreate(Bundle savedInstanceState) 
	{ 
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.main);
		
		text1=(TextView)findViewById(R.id.textView1); 
		serverIp=(EditText)findViewById(R.id.editText2); 
		btn = (Button)findViewById(R.id.button1); 
		btn2 = (Button)findViewById(R.id.button2);
		
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	    sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
	    
	    for (Sensor s : sensorList) {
	    	sensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);
	    }
	}
	
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		mSensorX = event.values[0];
		mSensorY = event.values[1];
		mSensorZ = event.values[2];
		mSensorTimeStamp = event.timestamp;
		mCpuTimeStamp = System.nanoTime();
		
		da = mSensorX + "," + mSensorY + "," + mSensorZ + "," + mSensorTimeStamp + ",";
	}
	
	public void onClick_Start(View v) { 
		// TODO Auto-generated method stub 
		text1.append("Started");
		new MyTask().execute(serverIp.getText().toString(), da);
	} 
	
	public void onClick_Stop(View v) { 
		// TODO Auto-generated method stub 
		text1.append("Stopped");
	} 
}
