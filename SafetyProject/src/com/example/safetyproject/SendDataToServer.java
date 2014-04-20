package com.example.safetyproject;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class SendDataToServer extends Activity {
	private ProgressDialog pDialog;
	// URL to which to send JSON data
	// private static String url = "http://10.24.2.26:8080/GoogleMap/helpMe";
	private static String url = "http://107.170.96.216:8888/start";
	private static final String TAG_NAME = "name";
	private static final String TAG_PHONE = "phone";
	private static final String TAG_HEALTH = "health";

	private static final String TAG_LATITUDE = "latitude";
	private static final String TAG_LONGITUDE = "longitude";

	String userName, userPhone, userHealthNeeds, latitude, longitude;
	TextView dataStatus, serverResponse, informationSent;
	String jsonStr = "No Reply";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_data_to_server);
		
		this.getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
						| WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);

		dataStatus = (TextView) findViewById(R.id.dataStatus);
		serverResponse = (TextView) findViewById(R.id.serverResponse);
		informationSent = (TextView) findViewById(R.id.infoSent);

		dataStatus.setText("Initialized");
	}

	public void onClickSendData(View v) {
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
		userHealthNeeds = "No Health Information";

		// User Location
		latitude = "No Location Found";
		longitude = "No Location Found";

		if (MainActivity.currentKnownLocation != null) {
			latitude = "" + MainActivity.currentKnownLocation.getLatitude();
			longitude = "" + MainActivity.currentKnownLocation.getLongitude();
		}

		if (storedName != null)
			userName = storedName;
		if (storedPhone != null)
			userPhone = storedPhone;
		if (storedHealth != null)
			userHealthNeeds = storedHealth;
		new SendDataToServerHelper().execute();
	}

	/**
	 * Async task class to get json by making HTTP call
	 * */
	private class SendDataToServerHelper extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			dataStatus.setText("Executing send");
			pDialog = new ProgressDialog(SendDataToServer.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(TAG_NAME, userName));
			params.add(new BasicNameValuePair(TAG_PHONE, userPhone));
			params.add(new BasicNameValuePair(TAG_HEALTH, userHealthNeeds));
			params.add(new BasicNameValuePair(TAG_LATITUDE, latitude));
			params.add(new BasicNameValuePair(TAG_LONGITUDE, longitude));

			try {
				// Making a request to url and getting response
				jsonStr = sh.makeServiceCall(url, ServiceHandler.POST, params);
				// String jsonStr = sh.makeServiceCall(url1,
				// ServiceHandler.GET);
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(),
						"Server not responding!\n" + e.getMessage(),
						Toast.LENGTH_SHORT).show();
				Log.d("SafetyFirst", "Server not responding to send alerts to Server!");
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();
			dataStatus.setText("Data Sent");
			serverResponse.setText(jsonStr);
			informationSent.setText("Name: " + userName + "\nPhone Number: "
					+ userPhone + "\nHealth: " + userHealthNeeds
					+ "\nlatitude: " + latitude + "\nlongitude: " + longitude);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.send_data_to_server, menu);
		return true;
	}

}
