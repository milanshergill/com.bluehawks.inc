package com.example.safetyproject;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class SetTimer extends Activity implements
		LoginDialogFragment.NoticeLoginDialogListener,
		TimePickerDialogFragment.NoticeTimePickerDialogListener {
	private static TextView tvDisplayTime;
	private static TextView timerView;
	private static TextView timeView;
	static int n;
	boolean timerSet;
	TimerTask timerTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_timer);
		tvDisplayTime = (TextView) findViewById(R.id.tvDisplayTime);
		timerView = (TextView) findViewById(R.id.timerView);
		timeView = (TextView) findViewById(R.id.timeView);
		setCurrentTimeOnView();
		n = 0;
		timerSet = false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_timer, menu);
		return true;

	}

	// display current time
	public static void setCurrentTimeOnView() {
		tvDisplayTime.setText("Timer set for " + n + " minutes");
	}

	public void onClickSetTimer(View v) {
		if (n != 0) {
			tvDisplayTime.setText("Timer set for " + n + " minutes");
			stopTimer();
			startTimer();
			timerView.setText("Timer has started");
		} else {
			tvDisplayTime.setText("Timer set for " + n + " minutes");
			Toast.makeText(getApplicationContext(), "Set the time first!",
					Toast.LENGTH_LONG).show();
		}
	}

	public void onClickStopTimer(View v) {
		stopTimer();
		timerView.setText("Timer has stopped");
	}

	public void startTimer() {
		final Handler handler = new Handler();

		Timer ourtimer = new Timer();
		timerTask = new TimerTask() {
			public void run() {
				handler.post(new Runnable() {
					public void run() {
						if (n <= 0) {
							timerTask.cancel();
							n = 0;
							timerView.setText("Timer Finished");
							Toast.makeText(getApplicationContext(),
									"Timer Finished!", Toast.LENGTH_LONG)
									.show();
							showPasswordDialog();
							timeView.setText("Time left " + n + " Seconds");
						} else {
							timeView.setText("Time left " + n + " Seconds");
							n--;
						}

					}
				});
			}
		};

		ourtimer.schedule(timerTask, 0, 1000);

	}

	public void stopTimer() {
		if (timerTask != null) {
			timerTask.cancel();
			timerTask = null;
		}
	}

	public void showPasswordDialog() {
		// Create an instance of the dialog fragment and show it
		LoginDialogFragment dialog = new LoginDialogFragment();
		dialog.show(getFragmentManager(), "Password Fragment");

	}

	/*
	 * login pop up intent
	 */
	@Override
	public void onLoginSignInClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "Password Correct",
				Toast.LENGTH_LONG).show();
	}

	@Override
	public void onLoginCancelClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "No password entered",
				Toast.LENGTH_LONG).show();
		showPasswordDialog();

	}

	public void onLoginWrongPasswordClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "Wrong password",
				Toast.LENGTH_LONG).show();
		showPasswordDialog();

	}

	/*
	 * timer picker popup
	 */
	public void showTimerPickerDialog(View v) {
		// Create an instance of the dialog fragment and show it
		TimePickerDialogFragment dialog = new TimePickerDialogFragment();
		dialog.show(getFragmentManager(), "TimePickerFragment");
	}

	@Override
	public void onTimePickerTimeSetClick(DialogFragment dialog, int hour,
			int min) {
		// TODO Auto-generated method stub
		int mins = hour * 60 + min;
		n = mins * 60;
		setCurrentTimeOnView();
		stopTimer();
	}

	@Override
	public void onTimePickerCancelClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "Time Cancelled",
				Toast.LENGTH_LONG).show();
	}

	@Override
	public void passwordTimerEnded() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playAlertSound() {
		// TODO Auto-generated method stub
		
	}
}
