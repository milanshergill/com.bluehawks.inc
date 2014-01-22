package com.example.gesture_app;

import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.dfki.ccaal.gestures.Distribution;
import de.dfki.ccaal.gestures.IGestureRecognitionListener;
import de.dfki.ccaal.gestures.IGestureRecognitionService;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.RemoteException;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {

	public static final int SERVERPORT = 12345;
	public static String GESTURES_RECORDED = "gesture recorded";
	public static String MAKE_A_CALL = "make a call";
	private static boolean start = false, dataSent = false, buttonPressed = false, callMade = false;
	public TextView text1, text2, text3; 
	public EditText serverIp;
	public Button btn, btn2, btn3, btn4;
	SensorManager sensorManager;
	List<Sensor> sensorList;
	private float accelX, accelY, accelZ;
	private float gravityX, gravityY, gravityZ;
	private float magneticX, magneticY, magneticZ;
	private float oldAccelX, oldAccelY, oldAccelZ = 0;
	private float gyroX, gyroY, gyroZ;
	private float[] gravity, geomagnetic;
	private float[] accelArray, accelResults;
	private float[] R_original, I, R_inverted;
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
	
	private IGestureRecognitionService recognitionService;
	
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
	    gravity = new float[3];
	    geomagnetic = new float[3];
		accelArray= new float[4];
		accelResults = new float[4];
		R_original = new float[16];
		I = new float[16];
		R_inverted = new float[16];
	    
	    for (Sensor s : sensorList) {
	    	sensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_FASTEST);
	    }
	    
	    Intent gestureBindIntent = new Intent("de.dfki.ccaal.gestures.GESTURE_RECOGNIZER");
	    bindService(gestureBindIntent, gestureConnection, Context.BIND_AUTO_CREATE);
	    
	    timer = new CountDownTimer(500000, 50) {

	        public void onTick(long millisUntilFinished) {
	        	if(start) {
	        		if(accelX != oldAccelX && accelY != oldAccelY && accelZ != oldAccelZ) {
	        			gravity[0] = gravityX;
	        			gravity[1] = gravityY;
	        			gravity[2] = gravityZ;
	        			geomagnetic[0] = magneticX;
	        			geomagnetic[1] = magneticY;
	        			geomagnetic[2] = magneticZ;
	        			accelArray[0] = accelX;
	        			accelArray[1] = accelY;
	        			accelArray[2] = accelZ;
	        			accelArray[3] = 0;
	        			SensorManager.getRotationMatrix(R_original, I, gravity, geomagnetic);
	        			android.opengl.Matrix.invertM(R_inverted, 0, R_original, 0);
	        			android.opengl.Matrix.multiplyMV(accelResults, 0, R_original, 0, accelArray, 0);
		        		da = timeStamp + "," + accelResults[0] + "," + accelResults[1] + "," + accelResults[2] + "," + accelX + "," + accelY + "," + accelZ + ",";
		        		System.out.println(Arrays.toString(R_original));
		        		System.out.println(Arrays.toString(R_inverted));
		        		System.out.println(Arrays.toString(accelArray));
		        		System.out.println(Arrays.toString(accelResults));
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
	     
	     timer_udp = new CountDownTimer(500000, 50) {
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
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			accelX = event.values[0];
			accelY = event.values[1];
			accelZ = event.values[2];
		}
		else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
			gyroX = event.values[0];
			gyroY = event.values[1];
			gyroZ = event.values[2];
		}
		else if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
			gravityX = event.values[0];
			gravityY = event.values[1];
			gravityZ = event.values[2];
		}
		else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
			magneticX = event.values[0];
			magneticY = event.values[1];
			magneticZ = event.values[2];
		}
	}
	
	public void onClick_Start(View v) { 
		// TODO Auto-generated method stub 
//		timer.start();
		text2.setText("waiting to learn");
		try {
			recognitionService.startLearnMode(GESTURES_RECORDED, MAKE_A_CALL);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	
	public void onClick_Stop(View v) { 
		// TODO Auto-generated method stub
//		if (timer != null)
//			timer.cancel();
//		text1.setText("Stopped\n");
//		text3.setText("Same values that was not recorded is " + foundSame + "\n");
//		start = false;
//		foundSame = 0;
//		oldAccelX = 0;
//		oldAccelY = 0;
//		oldAccelZ = 0;
//		timeStamp = 0;
		text2.setText("stopped learning");
		try {
			recognitionService.stopLearnMode();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void onClick_SendData(View v) { 
		// TODO Auto-generated method stub 
//		timer_udp.start();
//		text2.setText("Started\n");
		text2.setText("waiting to recognize");
		try {
			recognitionService.startClassificationMode(GESTURES_RECORDED);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	} 
	
	public void onClick_StopData(View v) { 
		// TODO Auto-generated method stub
//		if (task != null)
//			task.cancel(true);
//		if (timer_udp != null)
//			timer_udp.cancel();
//		counter = 0;
//		dataSent = false;
//		dataList.clear();
//		text2.setText("Stopped and Data Cleared\n");
		text2.setText("stopped recognizing");
		callMade = false;
		try {
			recognitionService.stopClassificationMode();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	IBinder gestureListenerStub = new IGestureRecognitionListener.Stub() {
		@Override 
		public void onGestureLearned(String gestureName) throws RemoteException {
			System.out.println("Learned!");
		}
		   
		@Override 
		public void onGestureRecognized(Distribution distribution) throws RemoteException {
			if(distribution.getBestDistance() <= 5 && !callMade) {
				System.out.println(String.format("%s %f", distribution.getBestMatch(),distribution.getBestDistance()));
				callMade = true;
//				text1.setText("Recognized as " + distribution.getBestMatch());
//				String number = "6479841690";
//				String uri = "tel:" + number.trim() ;
//				Intent intent = new Intent(Intent.ACTION_CALL);
//				intent.setData(Uri.parse(uri));
//				startActivity(intent);
				Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
				startActivity(intent);
			}
			else
				System.out.println("Not Recognized");
		}
		
		@Override
		public void onTrainingSetDeleted(String trainingSet) throws RemoteException {
			System.out.println("Training Set " + trainingSet + " deleted!");
//			text1.setText("Training Set Deleted");
		}
	};

	private final ServiceConnection gestureConnection = new ServiceConnection() {

	   public void onServiceConnected(ComponentName className, IBinder service) {
	      recognitionService = IGestureRecognitionService.Stub.asInterface(service);
	      try {
	    	  text3.setText("registered");
	         recognitionService.registerListener(IGestureRecognitionListener.Stub.asInterface(gestureListenerStub));
	      } catch (RemoteException e) {
	    	  text3.setText("error");
	    	  e.printStackTrace();
	      }
	   }
	   
	   public void onServiceDisconnected(ComponentName className) {
		   text3.setText("disconnected");
	   }
	};
}
