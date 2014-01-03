package com.example.keypad_udp;

import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {

	public static final int SERVERPORT = 12345;
	private static boolean start = false, dataSent = false, buttonPressed = false;
	public TextView text1, text2, text3; 
	public EditText serverIp;
	public Button btn, btn2, btn3, btn4;
	SensorManager sensorManager;
	List<Sensor> sensorList;
	private float accelX, accelY, accelZ;
	private float oldAccelX, oldAccelY, oldAccelZ = 0;
	private float gyroX, gyroY, gyroZ;
	private ArrayList<String> dataList;
	private double timeStamp = 0;
	private double timeStep = 0.05;
	private String da;
	DatagramSocket socket;
	AsyncTask<String, Void, Void> task = null;
	CountDownTimer timer;
	CountDownTimer timer_udp;
	int counter = 0;
	int foundSame = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		text1=(TextView)findViewById(R.id.textView1);
		text2=(TextView)findViewById(R.id.textView2);
		text3=(TextView)findViewById(R.id.textView3);
		serverIp=(EditText)findViewById(R.id.editText1); 
		btn = (Button)findViewById(R.id.button1); 
		btn2 = (Button)findViewById(R.id.button2);
		btn3 = (Button)findViewById(R.id.button3);
		btn4 = (Button)findViewById(R.id.button4);
		
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	    sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
	    
	    dataList = new ArrayList<String>();
	    
	    for (Sensor s : sensorList) {
	    	sensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_FASTEST);
	    }
	    
	    timer = new CountDownTimer(10000, 50) {

	        public void onTick(long millisUntilFinished) {
	        	if(start) {
	        		if(accelX != oldAccelX && accelY != oldAccelY && accelZ != oldAccelZ) {
		        		da = timeStamp + "," + accelX + "," + accelY + "," + accelZ + "," + gyroX + "," + gyroY + "," + gyroZ + ",";
		    			dataList.add(da);
	        		}
	        		else
	        			foundSame++;
	    			oldAccelX = accelX;
	        		oldAccelY = accelY;
	        		oldAccelZ = accelZ;
	    			timeStamp = timeStamp + timeStep;
	        	}
	        	if(!start)
	        		start = true;
	        	
	        	if(!dataSent) {
	        		timer_udp.start();
	        		dataSent = true;
	        		text3.setText("Sending Data Timer Started");
	        	}
	            text1.setText("seconds remaining: " + millisUntilFinished / 1000 +"\n");
	        }

	        public void onFinish() {
	            text1.setText("Timer Finished!\n");
	            text3.setText("Same values that was not recorded is " + foundSame + "\n");
	            start = false;
	            foundSame = 0;
	            oldAccelX = 0;
        		oldAccelY = 0;
        		oldAccelZ = 0;
        		timeStamp = 0;
	        }
	     };
	     
	     timer_udp = new CountDownTimer(20000, 150) {
    	 	String dataToSend = "Nothing";
	        public void onTick(long millisUntilFinished) {
	        	if(counter < dataList.size() && !dataList.isEmpty()) {
	        		dataToSend = dataList.get(counter);
	        		task = new MyTask().execute(serverIp.getText().toString(), dataList.get(counter));
	            	counter = counter+1;
	        	}
	        	text2.setText(dataToSend + "\n" + "Data List Size is:" + dataList.size() + "\nCounter is:" + counter + "\nseconds remaining: " + millisUntilFinished / 1000 +"\n");
	        }

	        public void onFinish() {
	        	if (task != null)
	    			task.cancel(true);
	            counter = 0;
	    		dataSent = false;
	    		dataList.clear();
	    		text2.setText("Timer Finished and Data Cleared\n");
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
		else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
			gyroX = event.values[0];
			gyroY = event.values[1];
			gyroZ = event.values[2];
		}
	}
	
	public void onClick_Start(View v) { 
		// TODO Auto-generated method stub 
//		start = true;
		timer.start();
		text1.setText("Started\n");
		
	} 
	
	public void onClick_Stop(View v) { 
		// TODO Auto-generated method stub
		if (timer != null)
			timer.cancel();
		text1.setText("Stopped\n");
        text3.setText("Same values that was not recorded is " + foundSame + "\n");
        start = false;
        foundSame = 0;
        oldAccelX = 0;
		oldAccelY = 0;
		oldAccelZ = 0;
		timeStamp = 0;
	}
	
	public void onClick_SendData(View v) { 
		// TODO Auto-generated method stub 
		timer_udp.start();
		text2.setText("Started\n");
		
	} 
	
	public void onClick_StopData(View v) { 
		// TODO Auto-generated method stub
		if (task != null)
			task.cancel(true);
		if (timer_udp != null)
			timer_udp.cancel();
		counter = 0;
		dataSent = false;
		dataList.clear();
		text2.setText("Stopped and Data Cleared\n");
	}
	
	public boolean dispatchKeyEvent(KeyEvent event) {
	    int action = event.getAction();
	    int keyCode = event.getKeyCode();
        switch (keyCode) {
        case KeyEvent.KEYCODE_VOLUME_UP:
            if (action == KeyEvent.ACTION_DOWN) {
            	if(!buttonPressed) {
            		buttonPressed = true;
	            	timer.start();
	        		text1.setText("Started\n");
            	}
            }
            if (action == KeyEvent.ACTION_UP) {
            	if (timer != null)
        			timer.cancel();
        		text1.setText("Stopped\n");
				text3.setText("Same values that was not recorded is " + foundSame + "\n");
				start = false;
				buttonPressed = false;
				foundSame = 0;
				oldAccelX = 0;
        		oldAccelY = 0;
        		oldAccelZ = 0;
        		timeStamp = 0;
            }
            return true;
        case KeyEvent.KEYCODE_VOLUME_DOWN:
            if (action == KeyEvent.ACTION_DOWN) {
            	if(!buttonPressed) {
            		buttonPressed = true;
	            	timer.start();
	        		text1.setText("Started\n");
            	}
            }
            if (action == KeyEvent.ACTION_UP) {
            	if (timer != null)
        			timer.cancel();
        		text1.setText("Stopped\n");
				text3.setText("Same values that was not recorded is " + foundSame + "\n");
				start = false;
				buttonPressed = false;
				foundSame = 0;
				oldAccelX = 0;
        		oldAccelY = 0;
        		oldAccelZ = 0;
        		timeStamp = 0;
            }
            return true;
        default:
            return super.dispatchKeyEvent(event);
        }
	}
}
