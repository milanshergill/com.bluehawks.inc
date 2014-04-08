package com.example.safetyproject;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UserProfileActivity extends Activity {

	EditText nameText, emailText, healthText;
	String userName, userEmail, userHealthNeeds;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);

		nameText = (EditText) findViewById(R.id.nameText);
		emailText = (EditText) findViewById(R.id.emailText);
		healthText = (EditText) findViewById(R.id.healthText);
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
			userEmail = emailText.getText().toString();
			userHealthNeeds = healthText.getText().toString();

			Toast.makeText(getApplicationContext(),
					"Profile saved successfully.", Toast.LENGTH_SHORT).show();
		}
	}

	private void updateFields() {
		if (userName != null && !userName.isEmpty())
			nameText.setText(userName);
		if (userEmail != null && !userEmail.isEmpty())
			emailText.setText(userEmail);
		if (userHealthNeeds != null && !userHealthNeeds.isEmpty())
			healthText.setText(userHealthNeeds);
	}

	private boolean validateData() {
		String name = nameText.getText().toString();
		String email = emailText.getText().toString();
		// String health = healthText.getText().toString();
		if (name.isEmpty()) {
			nameText.setError("Please provide a valid name!");
			return false;
		} else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email)
				.matches()) {
			emailText
					.setError("Enter valid email address in format example@example.com");
			return false;
		}
		return true;
	}

	private void saveToSharedFile() throws IOException {
		// Save user profile data
		SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();

		// Save user name
		if (!userName.isEmpty()) {
			String nameFieldID = Integer.toString(nameText.getId());
			editor.putString(nameFieldID, userName);
		}

		// Save user email
		if (!userEmail.isEmpty()) {
			String emailFieldID = Integer.toString(emailText.getId());
			editor.putString(emailFieldID, userEmail);
		}

		// Save user health or emergency needs
		if (!userHealthNeeds.isEmpty()) {
			String healthFieldID = Integer.toString(healthText.getId());
			editor.putString(healthFieldID, userHealthNeeds);
		}

		// Commit Changes
		editor.commit();
	}

	private void loadFromSharedFile() {
		// Load user profile data
		SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
		String nameFieldID = Integer.toString(nameText.getId());
		String emailFieldID = Integer.toString(emailText.getId());
		String healthFieldID = Integer.toString(healthText.getId());

		String storedName = sharedPref.getString(nameFieldID, null);
		String storedEmail = sharedPref.getString(emailFieldID, null);
		String storedHealth = sharedPref.getString(healthFieldID, null);

		if (storedName != null)
			userName = storedName;
		if (storedEmail != null)
			userEmail = storedEmail;
		if (storedHealth != null)
			userHealthNeeds = storedHealth;
	}

	private void clearData() {
		// Clear all the preferences in the shared preference file
		SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();

		editor.remove(Integer.toString(nameText.getId()));
		editor.remove(Integer.toString(emailText.getId()));
		editor.remove(Integer.toString(healthText.getId()));
		editor.commit();
	}

}
