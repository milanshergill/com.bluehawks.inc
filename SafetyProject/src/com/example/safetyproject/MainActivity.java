package com.example.safetyproject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.eng4kgestures.RecordGestures;
import com.parse.ParseAnalytics;

public class MainActivity extends Activity {

	// Delta time to check new location's validity
	private static final int TWO_MINUTES = 1000 * 60 * 2;
	public static Location currentKnownLocation;
	LocationManager locationManager;
	LocationListener locationListener;
	String locationProvider;
	boolean keepLocationServiceRunning = false;
	ToggleButton gestureToggleButton;
	ServiceConnection conn;

	private MyAdapter listAdapter;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;

	/* Variables for Navigation Drawer */
	String userName = "Milandeep Shergill";
	String feature_circle = "Emergency Friends";
	String feature_gesture = "Record Gestures";
	String feature_profile = "Profile";
	String feature_highAlert = "High Alert Mode";

	String feature_circle_info = "Add your friends";
	String feature_gesture_info = "Change your recorded gestures";
	String feature_profile_info = "Change your profile";
	String feature_highAlert_info = "Enter high alert mode";

	/* Alert/Information Server Details */
	private static String url_alert = "http://107.170.96.216:8888/start";
	private static String url_info = "http://107.170.96.216:8888/info";
	private static String url_highAlert = "http://107.170.96.216:8888/highAlert";
	private static final String TAG_NAME = "name";
	private static final String TAG_PHONE = "phone";
	private static final String TAG_INFO = "info";
	private static final String TAG_HEALTH = "health";

	private static final String TAG_LATITUDE = "latitude";
	private static final String TAG_LONGITUDE = "longitude";

	/* High Alert Mode Option */
	AnimationDrawable highAlertDrawable;
	boolean highAlertModeON = false;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);
		setContentView(R.layout.drawer_layout);

		// Initialize the Analytics for Parse Push Notifications
		ParseAnalytics.trackAppOpened(getIntent());

		listAdapter = new MyAdapter(getApplicationContext(),
				generateNavigationItems());

		mTitle = mDrawerTitle = getTitle();

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};

		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		// Set the adapter for the list view
		mDrawerList.setAdapter(listAdapter);
		// Set the list's click listener
		mDrawerList.setOnItemClickListener(new NavigationTitleClickListener());

		gestureToggleButton = (ToggleButton) findViewById(R.id.gestureToggleButton);

		// Acquire a reference to the system Location Manager
		locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);

		// Define a listener that responds to location updates
		locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				// Called when a new location is found by the network location
				// provider or GPS provider.
				makeUseOfNewLocation(location);
			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
			}

			public void onProviderEnabled(String provider) {
				// Add the newly enabled provider for location updates
				locationManager.requestLocationUpdates(provider, 0, 0,
						locationListener);
			}

			public void onProviderDisabled(String provider) {
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
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle your other action bar items...

		return super.onOptionsItemSelected(item);
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		mDrawerLayout.isDrawerOpen(mDrawerList);
		// menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	private ArrayList<Model> generateNavigationItems() {
		ArrayList<Model> models = new ArrayList<Model>();
		// Set the title as Name of the user
		models.add(new Model(userName));
		models.add(new Model(R.drawable.add_information_icon, feature_profile,
				feature_profile_info));
		models.add(new Model(R.drawable.ic_action_group, feature_circle,
				feature_circle_info));
		models.add(new Model(R.drawable.gesture_icon, feature_gesture,
				feature_gesture_info));
		models.add(new Model(R.drawable.high_alert_icon, feature_highAlert,
				feature_highAlert_info));

		return models;
	}

	public void startCall911Activity(View v) {
		// String phoneCallUri = "tel:647";
		// Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);
		// phoneCallIntent.setData(Uri.parse(phoneCallUri));
		// startActivity(phoneCallIntent);
		showCallEmergencyDialog();
	}

	public void startAlertLocationActivity(View v) {
		keepLocationServiceRunning = true;
		// showAlertFromUserDialog();
		Intent sendDataToServerActivity = new Intent(this,
				SendDataToServer.class);
		startActivity(sendDataToServerActivity);
	}

	public void startsetTimerActivity(View v) {
		Intent timerActivity = new Intent(this, IAmHereActivity.class);
		startActivity(timerActivity);
	}

	public void startSendInformationActivity(View v) {
		keepLocationServiceRunning = true;
		showInformationFromUserDialog();
	}

	public void activateGestures(View v) {

		if (gestureToggleButton.isChecked()) {
			Log.d("SafetyFirst", "Button was checked now!");

		} else {

		}
	}

	/***********************************************
	 * 
	 * Location Information part starts here
	 * 
	 ************************************************/

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		keepLocationServiceRunning = false;
		loadFromSharedFile();

		if (locationManager != null) {

			// Register the listener with the Location Manager to receive
			// location updates from the Network Provide - Cell Tower or Wifi
			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

			// Register the listener with the Location Manager to receive
			// location updates from GPS provider
			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 0, 0, locationListener);

			locationProvider = LocationManager.GPS_PROVIDER;

			// Try to get the cached gps location for faster response
			currentKnownLocation = locationManager
					.getLastKnownLocation(locationProvider);
		}

		// Display the notification received from Parse to user
		Intent intent = getIntent();
		if (intent != null) {
			Bundle extras = intent.getExtras();
			if (extras != null) {
				String notificationData = extras.getString("com.parse.Data");
				if (notificationData != null) {
					String data = "Sorry, no data found!";
					try {
						JSONObject json = new JSONObject(notificationData);
						data = json.getString("alert");
					} catch (JSONException e) {
						e.printStackTrace();
						Log.d("SafetyFirst",
								"Error reading parse notification!");
					}
					Log.d("SafetyFirst", data);
					showNotificationAlert(data);
				}
			}
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		try {
			saveToSharedFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (locationManager != null && !keepLocationServiceRunning) {
			// Remove the listener you previously added
			locationManager.removeUpdates(locationListener);
		}
	}

	private void saveToSharedFile() throws IOException {
		// Save toggle button status
		SharedPreferences sharedPref = getSharedPreferences(
				getString(R.string.preference_file_key), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();

		if (gestureToggleButton != null) {
			// Save toggle button status
			String buttonID = Integer.toString(gestureToggleButton.getId());
			editor.putBoolean(buttonID, gestureToggleButton.isChecked());
		}

		// Commit Changes
		editor.commit();
	}

	private void loadFromSharedFile() {
		// Load user profile data
		SharedPreferences sharedPref = getSharedPreferences(
				getString(R.string.preference_file_key), Context.MODE_PRIVATE);
		String buttonID = Integer.toString(gestureToggleButton.getId());

		boolean buttonStatus = sharedPref.getBoolean(buttonID, false);
		if (gestureToggleButton != null)
			gestureToggleButton.setChecked(buttonStatus);
	}

	protected void makeUseOfNewLocation(Location newLocation) {
		boolean isBetter = isBetterLocation(newLocation, currentKnownLocation);

		// Save new location as the current known location
		if (isBetter)
			currentKnownLocation = newLocation;
	}

	/**
	 * Determines whether one Location reading is better than the current
	 * Location fix
	 * 
	 * @param location
	 *            The new Location that you want to evaluate
	 * @param currentBestLocation
	 *            The current Location fix, to which you want to compare the new
	 *            one
	 */
	protected boolean isBetterLocation(Location location,
			Location currentBestLocation) {
		if (currentBestLocation == null) {
			// A new location is always better than no location
			return true;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = location.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
		boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location, use
		// the new location
		// because the user has likely moved
		if (isSignificantlyNewer) {
			return true;
			// If the new location is more than two minutes older, it must be
			// worse
		} else if (isSignificantlyOlder) {
			return false;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation
				.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider(location.getProvider(),
				currentBestLocation.getProvider());

		// Determine location quality using a combination of timeliness and
		// accuracy
		if (isMoreAccurate) {
			return true;
		} else if (isNewer && !isLessAccurate) {
			return true;
		} else if (isNewer && !isSignificantlyLessAccurate
				&& isFromSameProvider) {
			return true;
		}
		return false;
	}

	/** Checks whether two providers are the same */
	private boolean isSameProvider(String provider1, String provider2) {
		if (provider1 == null) {
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}

	class NavigationTitleClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// Highlight the selected item, close the drawer and perform action
			mDrawerList.setItemChecked(position, true);
			mDrawerLayout.closeDrawer(mDrawerList);
			Model selectedModel = (Model) mDrawerList
					.getItemAtPosition(position);
			String selectedOptionName = selectedModel.getTitle();
			Log.d("SafetyFirst", selectedOptionName);
			if (selectedOptionName.equals(feature_highAlert)) {
				// Start the Screen Flashing here
				showHighAlertModeDialog();
			} else {
				Class<?> className = null;
				if (selectedOptionName.equals(feature_profile))
					className = UserProfileActivity.class;
				else if (selectedOptionName.equals(feature_circle))
					className = CircleOf6.class;
				else if (selectedOptionName.equals(feature_gesture))
					className = RecordGestures.class;

				if (className != null) {
					Intent featureActivity = new Intent(view.getContext(),
							className);
					startActivity(featureActivity);
				}
			}
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/*
	 * 
	 * Change to high alert mode dialog box
	 */
	private void showCallEmergencyDialog() {
		AlertDialog emergencyDialog = new AlertDialog.Builder(this)
				// set message and title
				.setTitle("Call Emergency")
				.setMessage("Do you want to call 911?")

				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// call emergency number here
								dialog.dismiss();
								String phoneCallUri = "tel:647";
								Intent phoneCallIntent = new Intent(
										Intent.ACTION_CALL);
								phoneCallIntent.setData(Uri.parse(phoneCallUri));
								startActivity(phoneCallIntent);
							}
						})

				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).create();
		emergencyDialog.show();
	}

	/*
	 * 
	 * Change to high alert mode dialog box
	 */
	private void showHighAlertModeDialog() {
		AlertDialog highAlertModeDialog = new AlertDialog.Builder(this)
				// set message and title
				.setTitle("High Alert Mode")
				.setMessage("Choose the option below.")

				.setPositiveButton("Turn On",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								// Change to high Alert mode here
								final RelativeLayout layout = (RelativeLayout) findViewById(R.id.main_layout);
								highAlertDrawable = new AnimationDrawable();
								final Handler handler = new Handler();

								highAlertDrawable.addFrame(new ColorDrawable(
										Color.WHITE), 1000);
								highAlertDrawable.addFrame(new ColorDrawable(
										Color.RED), 1000);
								highAlertDrawable.setOneShot(false);

								layout.setBackgroundDrawable(highAlertDrawable);
								handler.postDelayed(new Runnable() {
									@Override
									public void run() {
										highAlertDrawable.start();
									}
								}, 200);
								highAlertModeON = true;
								enterHighAlertMode();
							}
						})

				.setNegativeButton("Turn Off",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								if (highAlertDrawable != null
										&& highAlertDrawable.isRunning()) {
									highAlertDrawable.stop();
								}
								final RelativeLayout layout = (RelativeLayout) findViewById(R.id.main_layout);
								layout.setBackgroundColor(0xFFFFFF);
								highAlertModeON = false;
							}
						}).create();
		highAlertModeDialog.show();
	}

	protected void enterHighAlertMode() {
		// Periodically send location information to server every 30 seconds
		final ScheduledExecutorService scheduler = Executors
				.newSingleThreadScheduledExecutor();

		scheduler.scheduleAtFixedRate(new Runnable() {
			public void run() {
				if (highAlertModeON) {
					// Send data to server
					new SendHighAlertDataToServerHelper().execute();
				}
				else {
					scheduler.shutdown();
				}
			}
		}, 0, 30, TimeUnit.SECONDS);

	}

	/*
	 * 
	 * Send Witness Information Dialog Box
	 */
	private void showInformationFromUserDialog() {
		LayoutInflater factory = LayoutInflater.from(this);
		final View sendDialogView = factory.inflate(
				R.layout.send_info_dialog_layout, null);
		AlertDialog witnessDialog = new AlertDialog.Builder(this)
				// set message and title
				.setTitle("Send Information to Server")

				.setPositiveButton("Send",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// Send info here
								MultiAutoCompleteTextView infoText = (MultiAutoCompleteTextView) sendDialogView
										.findViewById(R.id.informationText);
								dialog.dismiss();
								Log.d("SafetyFirst", "Message Printed "
										+ infoText.getText().toString());
								sendInformationToSecurity(infoText.getText()
										.toString(), url_info);
							}
						})

				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).create();
		witnessDialog.setView(sendDialogView);
		witnessDialog.show();
	}

	/*
	 * 
	 * Send ALert Information Dialog Box
	 */
	private void showAlertFromUserDialog() {
		AlertDialog alertDialog = new AlertDialog.Builder(this)
				// set message and title
				.setTitle("Send Alert to Security")
				.setMessage("Do you want to inform security?")

				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								// Send alert to security here
								sendInformationToSecurity("Alert Message",
										url_alert);
							}
						})

				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).create();
		alertDialog.show();
	}

	/*
	 * 
	 * Show Parse Notification alert to user
	 */
	private void showNotificationAlert(String data) {
		AlertDialog notificationDialog = new AlertDialog.Builder(this)
		// set message and title
				.setTitle("SafetyFirst Notification").setMessage(data)

				.setNeutralButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).create();
		notificationDialog.show();
	}

	private void sendInformationToSecurity(String infoText, String url) {
		String userName, userPhone, userHealth;

		// Retrieve all the latest user information
		SharedPreferences sharedPref = getSharedPreferences(
				getString(R.string.preference_file_key), Context.MODE_PRIVATE);
		String nameFieldID = Integer.toString(R.id.nameText);
		String phoneFieldID = Integer.toString(R.id.phoneText);
		String healthFieldID = Integer.toString(R.id.healthText);

		String storedName = sharedPref.getString(nameFieldID, null);
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
			userHealth = storedPhone;
		try {
			String serverReply = new SendDataToServerHelper().execute(userName,
					userPhone, userHealth, infoText, url).get();
			if (serverReply != null) {
				if (serverReply.equals("Success")) {
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
			// On trust bases the length of input arguments should be 5
			String userName = args[0];
			String userPhone = args[1];
			String userHealth = args[2];
			String informationText = args[3];
			String url = args[4];

			String latitude, longitude;
			// User Location
			latitude = "No Location Found";
			longitude = "No Location Found";

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
			params.add(new BasicNameValuePair(TAG_INFO, informationText));
			params.add(new BasicNameValuePair(TAG_LATITUDE, latitude));
			params.add(new BasicNameValuePair(TAG_LONGITUDE, longitude));

			try {
				// Making a request to url and getting response
				String serverReply = sh.makeServiceCall(url,
						ServiceHandler.POST, params);
				return serverReply;

			} catch (Exception e) {
				Toast.makeText(getApplicationContext(),
						"Server not responding!\n" + e.getMessage(),
						Toast.LENGTH_SHORT).show();
				Log.d("SafetyFirst",
						"Server not responding to send information to Server!");
			}

			return null;
		}
	}

	/**
	 * Async task class to get json by making HTTP call
	 * */
	private class SendHighAlertDataToServerHelper extends
			AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... args) {

			String latitude, longitude;
			// User Location
			latitude = "No Location Found";
			longitude = "No Location Found";

			if (MainActivity.currentKnownLocation != null) {
				latitude = "" + MainActivity.currentKnownLocation.getLatitude();
				longitude = ""
						+ MainActivity.currentKnownLocation.getLongitude();
			}

			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(TAG_LATITUDE, latitude));
			params.add(new BasicNameValuePair(TAG_LONGITUDE, longitude));

			try {
				Log.d("SafetyFirst", "Trying to send High Alert Data...");

				// Making a request to url and getting response
				String serverReply = sh.makeServiceCall(url_highAlert,
						ServiceHandler.POST, params);
				Log.d("SafetyFirst", serverReply);

			} catch (Exception e) {
				Log.d("SafetyFirst",
						"Server not responding to send information to Server!\n Error: "
								+ e.toString());
			}
			return null;
		}
	}
}
