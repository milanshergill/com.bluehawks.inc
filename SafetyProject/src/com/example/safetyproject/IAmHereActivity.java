package com.example.safetyproject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class IAmHereActivity extends Activity implements
		LoginDialogFragment.NoticeLoginDialogListener,
		TimePickerDialogFragment.NoticeTimePickerDialogListener {

	private String START_TIMER = "Start Timer";
	private String CANCEL_TIMER = "Cancel Timer";
	public static String BUDDYGUARD_TIMER = "buddyGuard_timer";
	private String defaultTimerText = "00:00";

	private static final int MAX_SMS_MESSAGE_LENGTH = 160;
	private static final String SMS_SENT = "i.am.here.sentsms";
	private static final String SMS_DELIVERED = "i.am.here.smsdelivered";

	TextView timerText, eventInfoText;
	Button timerButton;
	static long totalTimeCountInMilliseconds = 60 * 1000; // default time of 60
															// secs
	private long timeBlinkInMilliseconds = 10 * 1000; // blinking starts when 10
														// secs left

	private boolean blink; // controls the blinking .. on and off
	CountDownTimer countDownTimer;

	LoginDialogFragment passwordDialog;

	boolean timerActivated = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_iam_here);

		registerReceiver(receiver, new IntentFilter(SMS_SENT)); // SMS_SENT is a
																// constant

		timerText = (TextView) findViewById(R.id.timerText);
		eventInfoText = (TextView) findViewById(R.id.eventText);
		timerButton = (Button) findViewById(R.id.timerButton);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.iam_here, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		// Get passed intent
		Intent intent = getIntent();
		// Get message value from intent
		if (intent != null) {
			String message = intent.getStringExtra("timer_expired");
			if (message != null && message.equals("Yes")) {
				Toast.makeText(this, message, Toast.LENGTH_LONG).show();
			}
		}
	};

	public void onClickedTimerButton(View v) {
		if (timerButton.getText().equals(CANCEL_TIMER)) {
			// Popup password login
			showPasswordDialog();
		} else {
			// Popup Time Selector
			showTimerPickerDialog(v);
		}
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(receiver);
		super.onDestroy();
	}

	private void saveToSharedFile() throws IOException {
		// Save timer status
		SharedPreferences sharedPref = getSharedPreferences(
				getString(R.string.preference_file_key), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putBoolean(BUDDYGUARD_TIMER, timerActivated);
		// Commit Changes
		editor.commit();
	}

	private void loadFromSharedFile() {
		// Load user profile data
		SharedPreferences sharedPref = getSharedPreferences(
				getString(R.string.preference_file_key), Context.MODE_PRIVATE);
		timerActivated = sharedPref.getBoolean(BUDDYGUARD_TIMER, false);
	}

	/******
	 * 
	 * Password Dialog Box Code
	 * 
	 * ******/
	public void showPasswordDialog() {
		// Create an instance of the dialog fragment and show it
		passwordDialog = new LoginDialogFragment();
		passwordDialog.show(getFragmentManager(), "Password Fragment");
	}

	public void showUnCanceleablePasswordDialog() {
		// Dismiss any previous dialog
		if (passwordDialog != null)
			passwordDialog.dismiss();

		// Create an instance of the dialog fragment and show it
		passwordDialog = new LoginDialogFragment();
		passwordDialog.setCancelable(false);
		passwordDialog.startPasswordTimer = true;
		passwordDialog.show(getFragmentManager(), "Password Fragment");
	}

	@Override
	public void onLoginSuccessful(DialogFragment dialog) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(),
				"Password Correct, Timer Cancelled!", Toast.LENGTH_SHORT)
				.show();

		// Cancel the timer and reset the textView
		if (countDownTimer != null)
			countDownTimer.cancel();

		timerText.setText(defaultTimerText);
		timerText
				.setTextAppearance(getApplicationContext(), R.style.normalText);
		timerButton.setText(START_TIMER);
	}

	@Override
	public void onLoginCancelClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
	}

	public void onLoginWrongPassword(DialogFragment dialog) {
		// TODO Auto-generated method stub
	}

	/*****
	 * 
	 * Time Selector Dialog Box Code
	 * 
	 * ******/
	public void showTimerPickerDialog(View v) {
		// Create an instance of the dialog fragment and show it
		TimePickerDialogFragment dialog = new TimePickerDialogFragment();
		dialog.show(getFragmentManager(), "TimePickerFragment");
	}

	@Override
	public void onTimePickerTimeSetClick(DialogFragment dialog, int hour,
			int min) {

		// totalTimeCountInMilliseconds = hour * 3600 * 1000 + min * 60 * 1000;
		totalTimeCountInMilliseconds = 10 * 1000;
		// timeBlinkInMilliseconds = 10 * 1000;
		timeBlinkInMilliseconds = 5 * 1000;

		// startTimer();
		timerButton.setText(CANCEL_TIMER);

		timerActivated = true;
		try {
			saveToSharedFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Run the timer using broadcast receiver and AlarmManager
		Intent intent = new Intent(this, TimeExpiredBroadcastReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(
				this.getApplicationContext(), 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
				+ (10 * 1000), pendingIntent);
		Toast.makeText(this, "Alarm set in " + 5 + " seconds",
				Toast.LENGTH_LONG).show();

	}

	@Override
	public void onTimePickerCancelClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
	}

	private void startTimer() {
		countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds, 500) {

			@Override
			public void onTick(long leftTimeInMilliseconds) {
				long seconds = leftTimeInMilliseconds / 1000;

				if (leftTimeInMilliseconds < timeBlinkInMilliseconds) {
					timerText.setTextAppearance(getApplicationContext(),
							R.style.blinkText);

					if (blink) {
						timerText.setVisibility(View.VISIBLE);
					} else {
						timerText.setVisibility(View.INVISIBLE);
					}

					blink = !blink; // toggle the value of blink
				}

				timerText.setText(String.format("%02d", seconds / 60) + ":"
						+ String.format("%02d", seconds % 60));
			}

			@Override
			public void onFinish() {
				timerText.setText("Time up!");
				timerText.setVisibility(View.VISIBLE);
				showUnCanceleablePasswordDialog();
			}
		}.start();
	}

	// Activate the feature to send messages to all the friends
	public void activateFeature() {
		Toast.makeText(getApplicationContext(),
				"Messages to emergency friends sent.", Toast.LENGTH_SHORT)
				.show();
		String manualMsg = eventInfoText.getText().toString();
		String latitude = "";
		String longitude = "";
		if (MainActivity.currentKnownLocation != null) {
			latitude = "" + MainActivity.currentKnownLocation.getLatitude();
			longitude = "" + MainActivity.currentKnownLocation.getLongitude();
		}
		URL locationLink = null;
		try {
			locationLink = new URL("http://maps.google.com/maps?q=" + latitude
					+ "," + longitude + "&ll=" + latitude + "," + longitude
					+ "&z=17");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (CircleOf6.contactList != null && CircleOf6.buttonIDs != null) {
			for (int i = 0; i < 6; i++) {
				CircleFriend friend = CircleOf6.contactList
						.get(CircleOf6.buttonIDs[i]);
				if (friend != null && friend.getPhoneNumber() != null)
					sendSMS(friend.getPhoneNumber(),
							"This message is from SafetyFirst application."
									+ " BuddyGuard Alert - \"" + manualMsg
									+ "\". Here is the current location: "
									+ locationLink);
			}
		}
	}

	private void sendSMS(String phonenumber, String message) {
		SmsManager manager = SmsManager.getDefault();

		PendingIntent piSend = PendingIntent.getBroadcast(this, 0, new Intent(
				SMS_SENT), 0);
		PendingIntent piDelivered = PendingIntent.getBroadcast(this, 0,
				new Intent(SMS_DELIVERED), 0);

		int length = message.length();

		if (length > MAX_SMS_MESSAGE_LENGTH) {
			ArrayList<String> messagelist = manager.divideMessage(message);

			manager.sendMultipartTextMessage(phonenumber, null, messagelist,
					null, null);
		} else {
			manager.sendTextMessage(phonenumber, null, message, piSend,
					piDelivered);
		}
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String message = null;

			switch (getResultCode()) {
			case Activity.RESULT_OK:
				message = "Message sent!";
				break;
			case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
				message = "Error. Message not sent.";
				break;
			case SmsManager.RESULT_ERROR_NO_SERVICE:
				message = "Error: No service.";
				break;
			case SmsManager.RESULT_ERROR_NULL_PDU:
				message = "Error: Null PDU.";
				break;
			case SmsManager.RESULT_ERROR_RADIO_OFF:
				message = "Error: Radio off.";
				break;
			}

			Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG)
					.show();
		}
	};

	@Override
	public void passwordTimerEnded() {
		// Timer Ended so activate feature
		activateFeature();

		// Change appearance to default state
		timerText.setText(defaultTimerText);
		timerText
				.setTextAppearance(getApplicationContext(), R.style.normalText);
		timerButton.setText(START_TIMER);
	}

	@Override
	public void playAlertSound() {
		// Ring a alert
		Uri notification = RingtoneManager
				.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		Ringtone r = RingtoneManager.getRingtone(getApplicationContext(),
				notification);
		r.play();
	}

	// public boolean dispatchKeyEvent(KeyEvent event) {
	// int action = event.getAction();
	// int keyCode = event.getKeyCode();
	// switch (keyCode) {
	// case KeyEvent.KEYCODE_BACK:
	// if (action == KeyEvent.ACTION_DOWN) {
	// // Nothing
	// }
	// if (action == KeyEvent.ACTION_UP) {
	// // Press finished, switch to Main Activity
	// // Intent mainIntent = new Intent(this, MainActivity.class);
	// // startActivity(mainIntent);
	// }
	// return true;
	// default:
	// return super.dispatchKeyEvent(event);
	// }
	// }
}
