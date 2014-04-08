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
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class SendDataToServer extends Activity {
	private ProgressDialog pDialog;
	// URL to which to send JSON data
	// private static String url = "http://10.24.2.26:8080/GoogleMap/helpMe";
	private static String url = "http://107.170.96.216:8888/start";
	private static final String TAG_NAME = "name";
	private static final String TAG_AGE = "age";
	private static final String TAG_HEALTH = "health";

	private static final String TAG_LOCATION = "location";

	String userName, userEmail, userHealthNeeds, userLocation;
	TextView dataStatus, serverResponse, informationSent;
	String jsonStr = "No Reply";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_data_to_server);
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
		String emailFieldID = Integer.toString(R.id.emailText);
		String healthFieldID = Integer.toString(R.id.healthText);

		String storedName = sharedPref.getString(nameFieldID, null);
		String storedEmail = sharedPref.getString(emailFieldID, null);
		String storedHealth = sharedPref.getString(healthFieldID, null);

		userName = "No Name";
		userEmail = "No Email";
		userHealthNeeds = "No Health Information";
		// userLocation = "";

		userLocation = MainActivity.currentKnownLocation.getLatitude() + ":"
				+ MainActivity.currentKnownLocation.getLongitude();

		if (storedName != null)
			userName = storedName;
		if (storedEmail != null)
			userEmail = storedEmail;
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
			params.add(new BasicNameValuePair(TAG_AGE, userEmail));
			params.add(new BasicNameValuePair(TAG_HEALTH, userHealthNeeds));
			params.add(new BasicNameValuePair(TAG_LOCATION, userLocation));

			// Making a request to url and getting response
			jsonStr = sh.makeServiceCall(url, ServiceHandler.POST, params);
			// String jsonStr = sh.makeServiceCall(url1, ServiceHandler.GET);

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
			informationSent.setText("Name: " + userName + "\nEmail: "
					+ userEmail + "\nHealth: " + userHealthNeeds
					+ "\nLocation: " + userLocation);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.send_data_to_server, menu);
		return true;
	}

}
