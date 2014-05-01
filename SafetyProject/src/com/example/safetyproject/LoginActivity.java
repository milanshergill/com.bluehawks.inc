package com.example.safetyproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private String[] DUMMY_CODES = { "1234", "5678", "0000", "7777", "5555" };
	public final static String saveIdForUserName = "ThisIsUserIdForUserNameOfSafetyFirst";

	EditText accessCodeField;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		accessCodeField = (EditText) findViewById(R.id.accessCode);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
//		 clearData();
		SharedPreferences pref = getSharedPreferences(
				getString(R.string.preference_file_key), Context.MODE_PRIVATE);
		if (pref.getBoolean("activity_executed", false)) {
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			finish();
		} else if (pref.getBoolean(IAmHereActivity.BUDDYGUARD_TIMER, false)) {
			Intent intent = new Intent(this, IAmHereActivity.class);
			startActivity(intent);
			finish();
		}
	}

	public void clearData() {
		SharedPreferences pref = getSharedPreferences(
				getString(R.string.preference_file_key), Context.MODE_PRIVATE);
		Editor ed = pref.edit();
		ed.putBoolean("activity_executed", false);
		ed.putBoolean(IAmHereActivity.BUDDYGUARD_TIMER, false);
		ed.commit();
	}

	public void onClickGetCode(View v) {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW,
				Uri.parse("https://my.yorku.ca"));
		startActivity(browserIntent);
	}

	public void onClickRegister(View v) {
		String accessCode = accessCodeField.getText().toString();
		if (validateCode(accessCode)) {
			SharedPreferences pref = getSharedPreferences(
					getString(R.string.preference_file_key),
					Context.MODE_PRIVATE);
			Editor ed = pref.edit();
			ed.putBoolean("activity_executed", true);
			String userName = getUserName(Integer.parseInt(accessCode));
			ed.putString(saveIdForUserName, userName);
			ed.commit();

			Toast.makeText(getApplicationContext(), "Registration Successful!",
					Toast.LENGTH_SHORT).show();
			Intent mainActivityIntent = new Intent(getApplicationContext(),
					MainActivity.class);
			startActivity(mainActivityIntent);
		} else {
			accessCodeField.setError("Please provide a valid code.");
		}
	}

	private boolean validateCode(String accessCode) {
		for (int i = 0; i < DUMMY_CODES.length; i++) {
			if (accessCode.equals(DUMMY_CODES[i]))
				return true;
		}
		return false;
	}

	private String getUserName(int code) {
		String userName = null;
		switch (code) {
		case 1234:
			userName = "Milandeep Shergill";
			break;
		case 5678:
			userName = "Jatin Behl";
			break;
		case 0000:
			userName = "Manjeet Kaur";
			break;
		case 7777:
			userName = "Khady Lo Seck";
			break;
		case 5555:
			userName = "Khemesse";
			break;	
		}
		return userName;
	}
}
