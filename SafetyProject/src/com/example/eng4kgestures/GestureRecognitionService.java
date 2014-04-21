package com.example.eng4kgestures;

import java.util.ArrayList;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

public class GestureRecognitionService extends Service implements
		SensorEventListener {

	private Looper mServiceLooper;
	private ServiceHandler mServiceHandler;
	private SensorManager sensorManager;
	private Sensor accelerometerSensor;
	private ArrayList<Acceleration> accelerationList;
	Acceleration acceletationObject;
	private float accelX, accelY, accelZ;
	private static CountDownTimer timer;
	int foundSame = 0, count = 0;
	double minDistance = -1;
	boolean buttonPressed = false;
	private GestureDataBase gestureDataBase;
	ArrayList<Gesture> savedGestures;
	AudioManager am;
	ComponentName mediaReceiverComponent;
	// Binder given to clients
    private final IBinder mBinder = new LocalBinder();

	@Override
	public void onCreate() {
		// Initialize all sensors
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		accelerometerSensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		sensorManager.registerListener(this, accelerometerSensor,
				SensorManager.SENSOR_DELAY_FASTEST);

		// setting up database for acceleration recording
		gestureDataBase = new GestureDataBase(this);
		gestureDataBase.openReadable();

		//
		am = (AudioManager) getApplicationContext().getSystemService(
				Context.AUDIO_SERVICE);
		mediaReceiverComponent = new ComponentName(getPackageName(), MediaButtonReceiver.class.getName());

		// Array list to hold sensor data
		accelerationList = new ArrayList<Acceleration>();

		timer = new CountDownTimer(10000, 20) {
			public void onTick(long millisUntilFinished) {
				acceletationObject = new Acceleration(accelX, accelY, accelZ);
				accelerationList.add(acceletationObject);
			}

			public void onFinish() {
				processData(14.0);
				foundSame = 0;
				// Clear the old readings from the list
				accelerationList.clear();
			}
		};

		// Start up the thread running the service. Note that we create a
		// separate thread because the service normally runs in the process's
		// main thread, which we don't want to block. We also make it
		// background priority so CPU-intensive work will not disrupt our UI.
		HandlerThread thread = new HandlerThread("ServiceStartArguments",
				Process.THREAD_PRIORITY_BACKGROUND);
		thread.start();

		// Get the HandlerThread's Looper and use it for our Handler
		mServiceLooper = thread.getLooper();
		mServiceHandler = new ServiceHandler(mServiceLooper);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

		// For each start request, send a message to start a job and deliver the
		// start ID so we know which request we're stopping when we finish the
		// job
		Message msg = mServiceHandler.obtainMessage();
		msg.arg1 = startId;
		mServiceHandler.sendMessage(msg);

		// If we get killed, after returning from here, restart
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// Start listening for button presses
		am.registerMediaButtonEventReceiver(mediaReceiverComponent);
		return mBinder;
	}

	@Override
	public void onDestroy() {
		Log.d("SafetyFirst", "Service was finished through onDestroy Method!");
		Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
		sensorManager.unregisterListener(this);
		gestureDataBase.close();
		// Stop listening for button presses
		am.unregisterMediaButtonEventReceiver(mediaReceiverComponent);
	}

	// Handler that receives messages from the thread
	private static final class ServiceHandler extends Handler {
		public ServiceHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			// Handle the gesture testing feature here...
		}
	}
	
	/**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        GestureRecognitionService getService() {
            // Return this instance of service so clients can call public methods
            return GestureRecognitionService.this;
        }
    }

	protected void processData(double d) {
		// Process only when there is new data recorded to test
		if (!accelerationList.isEmpty()) {
			boolean thresholdMet = false;
			accelerationList.size();
			for (int i = 0; (i < accelerationList.size()) && (!thresholdMet);) {
				float value = (float) Math
						.pow((Math.pow(accelerationList.get(i)
								.getAccelerationX(), 2)
								+ Math.pow(accelerationList.get(i)
										.getAccelerationY(), 2) + Math.pow(
								accelerationList.get(i).getAccelerationZ(), 2)),
								0.5);
				if (value < d) {
					accelerationList.remove(i);
				} else {
					thresholdMet = true;
				}

			}
			accelerationList.size();
			if (!accelerationList.isEmpty()) {
				int j = Math.min(50, accelerationList.size());
				for (; j < accelerationList.size();) {
					accelerationList.remove(j);
				}
			}

			if (!accelerationList.isEmpty()) {

				// Get the list of all the gestures stored in Database
				savedGestures = gestureDataBase.getAllGestures();

				// Create the gesture object for newly recorded gesture
				String name = "Test Gesture";
				Gesture testGesture = createGesureObject(name, accelerationList);
				try {
					Acceleration[] modifiedTestGesture;
					modifiedTestGesture = (Acceleration[]) TemporalCompressionAverage
							.calculateAverage(testGesture);
					modifiedTestGesture = NormalizeArray
							.normalizeArray(modifiedTestGesture);
					testGesture.setAccelerationArray(modifiedTestGesture);

					minDistance = 10000;
					String selectedGestureName = "None Selected";
					// Compare the newly gesture object with all saved gestures
					for (int i = 0; i < savedGestures.size(); i++) {
						Acceleration[] modifiedSavedGesture;
						modifiedSavedGesture = (Acceleration[]) TemporalCompressionAverage
								.calculateAverage(savedGestures.get(i));
						modifiedSavedGesture = NormalizeArray
								.normalizeArray(modifiedSavedGesture);
						savedGestures.get(i).setAccelerationArray(
								modifiedSavedGesture);
						if (minDistance != Math.min(minDistance,
								(double) DynamicTimeWarping.calcDistance(
										savedGestures.get(i), testGesture))) {
							minDistance = Math.min(minDistance,
									(double) DynamicTimeWarping.calcDistance(
											savedGestures.get(i), testGesture));
							selectedGestureName = savedGestures.get(i)
									.getName();
						}
					}
					Toast.makeText(getBaseContext(),
							"Gesture Selected is: " + selectedGestureName,
							Toast.LENGTH_LONG).show();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public Gesture createGesureObject(String name,
			ArrayList<Acceleration> accelerationList) {
		int size = accelerationList.size();
		Acceleration[] accelerationArray = new Acceleration[size];
		for (int i = 0; i < size; i++) {
			accelerationArray[i] = accelerationList.get(i);
		}

		Gesture gestureObject = new Gesture(name, accelerationArray);
		return gestureObject;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
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
	}
}
