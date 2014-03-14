package com.example.circleof6;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class SendDataToServer extends Activity {
    private ProgressDialog pDialog;
	// URL to get contacts JSON
	private static String url = "http://10.24.2.26:8080/GoogleMap/helpMe";
	private static String url1 = "http://10.24.2.26:8080/GoogleMap/ctrl";
	private static final String TAG_NAME = "name";
	private static final String TAG_AGE = "age";

	JSONArray contacts = null;
	TextView dataStatus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_data_to_server);
		dataStatus = (TextView)findViewById(R.id.dataStatus);
		dataStatus.setText("Initialized");
	}

	public void onClickSendData(View v){
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
            params.add(new BasicNameValuePair("name", "Jatin Behl"));
            params.add(new BasicNameValuePair("age", "24"));
            params.add(new BasicNameValuePair("country", "Canada"));
            
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.POST,params);
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
	       
	        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.send_data_to_server, menu);
		return true;
	}

}
