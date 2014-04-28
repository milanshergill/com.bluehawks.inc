package com.example.safetyproject;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UserProfileActivity extends Activity {

	EditText nameText, phoneText, healthText;
	String userName, userPhone, userHealthNeeds;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);

		nameText = (EditText) findViewById(R.id.nameText);
		phoneText = (EditText) findViewById(R.id.phoneText);
		healthText = (EditText) findViewById(R.id.healthText);

		// Make name text non-editable
		nameText.setInputType(InputType.TYPE_NULL);
		nameText.setBackgroundColor(0xFFBDBDBD);

		// Enable this to clear user data
		// clearData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_profile, menu);
		return true;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		loadFromSharedFile();
		updateFields();
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
	}

	public void saveUserProfile(View v) {
		if (validateData()) {
			userName = nameText.getText().toString();
			userPhone = phoneText.getText().toString();
			userHealthNeeds = healthText.getText().toString();

			Toast.makeText(getApplicationContext(),
					"Profile saved successfully.", Toast.LENGTH_SHORT).show();

			finish();
		}
	}

	private void updateFields() {
		if (userName != null && !userName.isEmpty())
			nameText.setText(userName);
		if (userPhone != null && !userPhone.isEmpty())
			phoneText.setText(userPhone);
		if (userHealthNeeds != null && !userHealthNeeds.isEmpty())
			healthText.setText(userHealthNeeds);
	}

	private boolean validateData() {
		String name = nameText.getText().toString();
		String phone = phoneText.getText().toString();
		if (name.isEmpty()) {
			nameText.setError("Please provide a valid name!");
			return false;
		} else if (!android.util.Patterns.PHONE.matcher(phone).matches()) {
			phoneText.setError("Enter valid phone number!");
			return false;
		}
		return true;
	}

	private void saveToSharedFile() throws IOException {
		// Save user profile data
		SharedPreferences sharedPref = getSharedPreferences(
				getString(R.string.preference_file_key), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();

		if (userName != null && userPhone != null) {
			// Save user name
			if (!userName.isEmpty()) {
				String nameFieldID = Integer.toString(nameText.getId());
				editor.putString(nameFieldID, userName);
			}

			// Save user email
			if (!userPhone.isEmpty()) {
				String phoneFieldID = Integer.toString(phoneText.getId());
				editor.putString(phoneFieldID, userPhone);
			}

			if (userHealthNeeds != null) {
				// Save user health or emergency needs
				if (!userHealthNeeds.isEmpty()) {
					String healthFieldID = Integer.toString(healthText.getId());
					editor.putString(healthFieldID, userHealthNeeds);
				}
			}
		}

		// Commit Changes
		editor.commit();
	}

	private void loadFromSharedFile() {
		// Load user profile data
		SharedPreferences sharedPref = getSharedPreferences(
				getString(R.string.preference_file_key), Context.MODE_PRIVATE);
		String nameFieldID = Integer.toString(nameText.getId());
		String phoneFieldID = Integer.toString(phoneText.getId());
		String healthFieldID = Integer.toString(healthText.getId());

		String storedName = sharedPref.getString(
				LoginActivity.saveIdForUserName, null);
		String storedPhone = sharedPref.getString(phoneFieldID, null);
		String storedHealth = sharedPref.getString(healthFieldID, null);

		if (storedName != null)
			userName = storedName;
		if (storedPhone != null)
			userPhone = storedPhone;
		if (storedHealth != null)
			userHealthNeeds = storedHealth;
	}

	private void clearData() {
		// Clear all the preferences in the shared preference file
		SharedPreferences sharedPref = getSharedPreferences(
				getString(R.string.preference_file_key), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();

		editor.remove(Integer.toString(nameText.getId()));
		editor.remove(Integer.toString(phoneText.getId()));
		editor.remove(Integer.toString(healthText.getId()));
		editor.commit();
	}

}
