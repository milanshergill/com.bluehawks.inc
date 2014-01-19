package com.example.basicgesture;

import java.util.ArrayList;
import java.util.List;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {

	public TextView text1, text2; 
	public Button btn, btn2;
	SensorManager sensorManager;
	List<Sensor> sensorList;
	private float accelX, accelY, accelZ;
	private float gyroX, gyroY, gyroZ;
	private ArrayList<Float> accelXList, accelYList, accelZList;
	private double timeStamp = 0;
	private double timeStep = 0.05;
	private String da;
	CountDownTimer timer;
	int counter = 0;
	int foundSame = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		text1=(TextView)findViewById(R.id.textView1);
		text2=(TextView)findViewById(R.id.textView2);
		btn = (Button)findViewById(R.id.button1); 
		btn2 = (Button)findViewById(R.id.button2);
		
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	    sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
	    
	    
	    accelXList = new ArrayList<Float>();
	    accelYList = new ArrayList<Float>();
	    accelZList = new ArrayList<Float>();
	    
	    for (Sensor s : sensorList) {
	    	sensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_FASTEST);
	    }
	 
	    timer = new CountDownTimer(20000, 50) {

	        public void onTick(long millisUntilFinished) {
        	
    			accelXList.add(Float.valueOf(accelX)); //Contains only acceleration "x" values
    			accelYList.add(Float.valueOf(accelY)); //Contains only acceleration "y" values
    			accelZList.add(Float.valueOf(accelZ)); //Contains only acceleration "z" values
        	
    			timeStamp = timeStamp + timeStep;
	            text2.setText("seconds remaining: " + millisUntilFinished / 1000 +"\n");
	        }

	        public void onFinish() {
	            text1.setText("Recording Finished!\n" + "Same values that was not recorded are " + foundSame + "\n");
        		timeStamp = 0;
	        }
	     };
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
		// TODO Auto-generated method stub
		if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
			accelX = event.values[0];
			accelY = event.values[1];
			accelZ = event.values[2];
		}
	}
	
	public void onClick_Start(View v) { 
		// TODO Auto-generated method stub
		timer.start();
		text1.setText("Recording Started\n");
		
	} 
	
	public void onClick_Stop(View v) { 
		// TODO Auto-generated method stub
		if (timer != null)
			timer.cancel();
		text1.setText("Recording Stoped\n");
		timeStamp = 0;
	}
}
