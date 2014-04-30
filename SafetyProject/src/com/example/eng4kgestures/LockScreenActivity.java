package com.example.eng4kgestures;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.example.safetyproject.LoginActivity;
import com.example.safetyproject.MainActivity;
import com.example.safetyproject.R;
import com.example.safetyproject.ServiceHandler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class LockScreenActivity extends Activity implements SensorEventListener {
	
	double MIN_DIS_TO_ACTIVATE_GESTURE = 30;

	Button start, stop;
	TextView results, testingStatus, serverReply;
	EditText serverIP;
	GridView resultsList;

	SensorManager sensorManager;
	Sensor accelerometerSensor;
	private ArrayList<Acceleration> accelerationList;
	Acceleration acceletationObject;
	private float accelX, accelY, accelZ;
	private float oldAccelX, oldAccelY, oldAccelZ = 0;
	private static CountDownTimer timer, udpTimer;
	int foundSame = 0, count = 0;
	double minDistance = -1;
	boolean buttonPressed = false;

	private GestureDataBase gestureDataBase;
	ArrayList<Gesture> savedGestures;
	ArrayList<String> listItems = new ArrayList<String>();
	ArrayAdapter<String> adapter;
	Gesture gestureToSend;

	AsyncTask<String, Void, Void> task = null;

	/* Alert Variables */
	private static String url_alert = "http://107.170.96.216:8888/start";
	private static final String TAG_NAME = "name";
	private static final String TAG_PHONE = "phone";
	private static final String TAG_HEALTH = "health";

	private static final String TAG_LATITUDE = "latitude";
	private static final String TAG_LONGITUDE = "longitude";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lock_screen);

		// Prevent screen from getting locked
		this.getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
						| WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
						| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, listItems);

		start = (Button) findViewById(R.id.start);
		start = (Button) findViewById(R.id.stop);
		results = (TextView) findViewById(R.id.resultsView);
		testingStatus = (TextView) findViewById(R.id.testingStatus);
		serverReply = (TextView) findViewById(R.id.serverReply);
		resultsList = (GridView) findViewById(R.id.resultsList);
		serverIP = (EditText) findViewById(R.id.serverIP);

		resultsList.setAdapter(adapter);
		resultsList.setOnItemLongClickListener(listItemClickListener);

		// setting up database for acceleration recording
		gestureDataBase = new GestureDataBase(this);
		gestureDataBase.openReadable();

		// Array list to hold sensor data
		accelerationList = new ArrayList<Acceleration>();

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		accelerometerSensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		timer = new CountDownTimer(10000, 20) {
			public void onTick(long millisUntilFinished) {
				acceletationObject = new Acceleration(accelX, accelY, accelZ);
				accelerationList.add(acceletationObject);
			}

			public void onFinish() {
				processData(14.0);
				foundSame = 0;
				oldAccelX = 0;
				oldAccelY = 0;
				oldAccelZ = 0;
				// Clear the old readings from the list
				accelerationList.clear();
			}
		};

		udpTimer = new CountDownTimer(12500, 250) {
			public void onTick(long millisUntilFinished) {
				if (count < gestureToSend.getAccelerationArray().length) {
					String data = gestureToSend.getAccelerationArray()[count]
							.getAccelerationX()
							+ ","
							+ gestureToSend.getAccelerationArray()[count]
									.getAccelerationY()
							+ ","
							+ gestureToSend.getAccelerationArray()[count]
									.getAccelerationZ() + ",";
					task = new MyTask().execute(serverIP.getText().toString(),
							data);
					count++;
				}
			}

			public void onFinish() {
				if (task != null)
					task.cancel(true);
				count = 0;
				Toast.makeText(getBaseContext(),
						"Stopped sending gesture " + gestureToSend.getName(),
						Toast.LENGTH_SHORT).show();
			}
		};
	}

	OnItemLongClickListener listItemClickListener = new OnItemLongClickListener() {
		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			// Should click on the name to send the gesture, position start from
			// 0
			if (position % 2 == 0) {
				String name = (String) parent.getItemAtPosition(position);
				for (int i = 0; i < savedGestures.size(); i++) {
					if (savedGestures.get(i).getName().equals(name)) {
						gestureToSend = savedGestures.get(i);
					}
				}
				Toast.makeText(getBaseContext(),
						"Item Clicked " + name + " ID is " + position,
						Toast.LENGTH_SHORT).show();
				AlertDialog diaBox = AskOption(getBaseContext());
				diaBox.show();
				return true;
			} else
				return false;
		}
	};

	protected void processData(double d) {
		// Process only when there is new data recorded to test
		if (!accelerationList.isEmpty()) {
			boolean thresholdMet = false;
			int size = accelerationList.size();
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
			int k = accelerationList.size();
			if (!accelerationList.isEmpty()) {
				int j = Math.min(25, accelerationList.size());
				for (; j < accelerationList.size();) {
					accelerationList.remove(j);
				}
			}

			testingStatus.setText("Gesture saved, initial size was " + size
					+ " after cleanup size was " + k
					+ " the size of accel array is " + accelerationList.size());

			if (!accelerationList.isEmpty()) {

				// Get the list of all the gestures stored in Database
				savedGestures = gestureDataBase.getAllGestures();

				if (savedGestures.size() > 0)
					results.setText("Results");
				else
					results.setText("No saved gestures in database, record gestures first!");

				// Create the gesture object for newly recorded gesture
				String name = "Test Gesture";
				Gesture testGesture = createGesureObject(name, accelerationList);

				/************************************* Start Orginal Code ***********************************************/
				// //Compare the newly gesture object with all saved gestures
				// for (int i = 0; i < savedGestures.size(); i++)
				// {
				// minDistance = (double)
				// DynamicTimeWarping.calcDistance(savedGestures.get(i),
				// testGesture);
				// addItem(savedGestures.get(i).getName());
				// addItem("" + minDistance);
				// }
				/************************************* End Original Code ***********************************************/

				/************************************* Start Temporal Compression ***********************************************/
				// Acceleration[] modifiedTestGesture;
				// modifiedTestGesture = (Acceleration[])
				// TemporalCompresssion.calculateAverage(testGesture);
				// testGesture.setAccelerationArray(modifiedTestGesture);
				// results.setText("check the logcat for results");
				//
				// //Compare the newly gesture object with all saved gestures
				// for (int i = 0; i < savedGestures.size(); i++)
				// {
				// Acceleration[] modifiedSavedGesture;
				// modifiedSavedGesture = (Acceleration[])
				// TemporalCompresssion.calculateAverage(savedGestures.get(i));
				// savedGestures.get(i).setAccelerationArray(modifiedSavedGesture);
				// minDistance = (double)
				// DynamicTimeWarping.calcDistance(savedGestures.get(i),
				// testGesture);
				// addItem(savedGestures.get(i).getName());
				// addItem("" + minDistance);
				// }
				/************************************* Start Temporal Compression ***********************************************/

				/************************************* Start Average Temporal Compression ***********************************************/
				// try {
				// Acceleration[] modifiedTestGesture;
				// modifiedTestGesture = (Acceleration[])
				// TemporalCompressionAverage .calculateAverage(testGesture);
				// testGesture.setAccelerationArray(modifiedTestGesture);
				// results.setText("check the logcat for results");
				//
				// //Compare the newly gesture object with all saved gestures
				// for (int i = 0; i < savedGestures.size(); i++)
				// {
				// Acceleration[] modifiedSavedGesture;
				// modifiedSavedGesture = (Acceleration[])
				// TemporalCompressionAverage
				// .calculateAverage(savedGestures.get(i));
				// savedGestures.get(i).setAccelerationArray(modifiedSavedGesture);
				// minDistance = (double)
				// DynamicTimeWarping.calcDistance(savedGestures.get(i),
				// testGesture);
				// addItem(savedGestures.get(i).getName());
				// addItem("" + minDistance);
				// }
				// } catch (Exception e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				/************************************* End Average Temporal Compression ***********************************************/
				/************************************* Start Normalize Average Temporal Compression ***********************************************/
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
					addItem(selectedGestureName);
					addItem("" + minDistance);
					Toast.makeText(
							getBaseContext(),
							"Gesture Selected is: " + selectedGestureName
									+ " with minimum distance: " + minDistance,
							Toast.LENGTH_LONG).show();
					// Send alert to security
					if (minDistance < MIN_DIS_TO_ACTIVATE_GESTURE) {
						sendInformationToSecurity(url_alert);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/************************************* End Normalize Average Temporal Compression ***********************************************/
			} else
				results.setText("Nothing recorded, press Start to record data.");
		}
		// else
		// results.setText("Nothing recorded, press Start to record data.");
	}

	protected void onResume() {
		super.onResume();
		sensorManager.registerListener(this, accelerometerSensor,
				SensorManager.SENSOR_DELAY_FASTEST);
		gestureDataBase.openReadable();
	}

	protected void onPause() {
		super.onPause();
		sensorManager.unregisterListener(this);
		gestureDataBase.close();
	}

	public void onClickStart(View v) {
		// Start Recording Data & send for Testing
		results.setText("Testing...");
		clearItems();
		timer.start();
	}

	public void onClickStop(View v) {
		// Stop Testing and add results to the resultsList
		if (timer != null) {
			timer.cancel();
			timer.onFinish();
		}
	}

	public void onClickStopUDPTransfer(View v) {
		// Stop transfering gesture via UDP
		if (udpTimer != null) {
			udpTimer.cancel();
			udpTimer.onFinish();
		}
	}

	public void addItem(String value) {
		listItems.add(value);
		adapter.notifyDataSetChanged();
	}

	public void clearItems() {
		listItems.clear();
		adapter.notifyDataSetChanged();
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

	public boolean dispatchKeyEvent(KeyEvent event) {
		int action = event.getAction();
		int keyCode = event.getKeyCode();
		switch (keyCode) {
		case KeyEvent.KEYCODE_VOLUME_UP:
			if (action == KeyEvent.ACTION_DOWN) {
				if (!buttonPressed) {
					buttonPressed = true;
					results.setText("Testing...");
					clearItems();
					timer.start();
				}
			}
			if (action == KeyEvent.ACTION_UP) {
				if (timer != null) {
					timer.cancel();
					timer.onFinish();
				}
				buttonPressed = false;
			}
			return true;
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			if (action == KeyEvent.ACTION_DOWN) {
				if (!buttonPressed) {
					buttonPressed = true;
					results.setText("Testing...");
					clearItems();
					timer.start();
				}
			}
			if (action == KeyEvent.ACTION_UP) {
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

	private AlertDialog AskOption(final Context context) {
		AlertDialog sendGestureDialog = new AlertDialog.Builder(this)
				// set message and title
				.setTitle("Send Gesture via UDP")
				.setMessage("Do you want to send this Gesture?")

				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								udpTimer.start();
								dialog.dismiss();
							}
						})

				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).create();
		return sendGestureDialog;
	}

	private void sendInformationToSecurity(String url) {
		String userName, userPhone, userHealth;

		// Retrieve all the latest user information
		SharedPreferences sharedPref = getSharedPreferences(
				getString(R.string.preference_file_key), Context.MODE_PRIVATE);
		String nameFieldID = Integer.toString(R.id.nameText);
		String phoneFieldID = Integer.toString(R.id.phoneText);
		String healthFieldID = Integer.toString(R.id.healthText);

		String storedName = sharedPref.getString(
				LoginActivity.saveIdForUserName, null);
		String storedPhone = sharedPref.getString(phoneFieldID, null);
		String storedHealth = sharedPref.getString(healthFieldID, null);

		userName = "No Name";
		userPhone = "No Phone";
		userHealth = "No Health Info";

		if (storedName != null)
			userName = storedName;
		if (storedPhone != null)
			userPhone = storedPhone;
		if (storedHealth != null)
			userHealth = storedHealth;
		try {
			String serverReply = new SendDataToServerHelper().execute(userName,
					userPhone, userHealth, url).get();
			if (serverReply != null) {
				if (serverReply.equals("Sucess")) {
					Toast.makeText(getApplicationContext(),
							"Security Alerted!", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(),
							"No Reply from Server!", Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(getApplicationContext(),
						"Server not available, connection refused!",
						Toast.LENGTH_SHORT).show();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Async task class to get json by making HTTP call
	 * */
	private class SendDataToServerHelper extends
			AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... args) {
			// On trust bases the length of input arguments should be 4
			String userName = args[0];
			String userPhone = args[1];
			String userHealth = args[2];
			String url = args[3];

			String latitude, longitude;
			// User Default Location
			latitude = "43.776360";
			longitude = "-79.512405";

			if (MainActivity.currentKnownLocation != null) {
				latitude = "" + MainActivity.currentKnownLocation.getLatitude();
				longitude = ""
						+ MainActivity.currentKnownLocation.getLongitude();
			}

			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(TAG_NAME, userName));
			params.add(new BasicNameValuePair(TAG_PHONE, userPhone));
			params.add(new BasicNameValuePair(TAG_HEALTH, userHealth));
			params.add(new BasicNameValuePair(TAG_LATITUDE, latitude));
			params.add(new BasicNameValuePair(TAG_LONGITUDE, longitude));

			try {
				// Making a request to url and getting response
				String serReply = sh.makeServiceCall(url, ServiceHandler.POST,
						params);
				// serverReply.setText("Server Reply: " + serReply);
				Log.d("SafetyFirst", "Server Reply: " + serReply);
				return serReply;

			} catch (Exception e) {
				// serverReply
				// .setText("Server not responding!\n" + e.getMessage());
				Log.d("SafetyFirst",
						"Server not responding to send information to Server!");
			}

			return null;
		}
	}
}
