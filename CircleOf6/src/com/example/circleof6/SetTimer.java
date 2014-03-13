package com.example.circleof6;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.AlertDialog;
//import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

@SuppressLint("NewApi")
public class SetTimer extends Activity implements
		LoginDialogFragment.NoticeLoginDialogListener,
		TimePickerDialogFragment.NoticeTimePickerDialogListener {
	private static TextView tvDisplayTime;
	private static TextView timerView;
	private static TextView timeView;
	// private Button btnChangeTime;
	int n;

	TimerTask timerTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_timer);
		tvDisplayTime = (TextView) findViewById(R.id.tvDisplayTime);
		timerView = (TextView) findViewById(R.id.timerView);
		timeView = (TextView) findViewById(R.id.timeView);
		setCurrentTimeOnView(0);
		n = 0;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_timer, menu);
		return true;

	}

	// @SuppressLint("NewApi")
	// public void showTimePickerDialog(View v) {
	// DialogFragment newFragment = new TimePickerFragment();
	// newFragment.show(getFragmentManager(), "timePicker");
	// }

	// display current time
	public static void setCurrentTimeOnView(int minute) {

		tvDisplayTime.setText("Timer set for " + minute + " minutes");

	}

	public void onClickSetTimer(View v) {

		stopTimer();
		startTimer();
		timerView.setText("Timer has started");
	}

	public void onClickStopTimer(View v) {
		stopTimer();
		timerView.setText("Timer has stopped");
	}

	private static String padding_str(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	public void startTimer() {
		final Handler handler = new Handler();

		Timer ourtimer = new Timer();
		timerTask = new TimerTask() {
			public void run() {
				handler.post(new Runnable() {
					public void run() {
						if (n == 0) {
							timerTask.cancel();
							timerView.setText("Timer Finished");
							Toast.makeText(getApplicationContext(),
									"Timer Finished!", Toast.LENGTH_LONG)
									.show();
							showPasswordDialog();

						}
						timeView.setText("Time left " + n + " Seconds");
						n--;

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
			n = 0;
		}
	}

	public void showPasswordDialog() {
		// Create an instance of the dialog fragment and show it
		LoginDialogFragment dialog = new LoginDialogFragment();
		dialog.show(getFragmentManager(), "Password Fragment");

	}

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
		n = mins *60;
		setCurrentTimeOnView(mins);
		stopTimer();

	}

	@Override
	public void onTimePickerCancelClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "Time Cancelled",
				Toast.LENGTH_LONG).show();
	}

}

// /*
// * time picker dialog
// */
//
// class TimePickerFragment extends DialogFragment implements
// TimePickerDialog.OnTimeSetListener {
//
// @Override
// public Dialog onCreateDialog(Bundle savedInstanceState) {
// // Use the current time as the default values for the picker
// final Calendar c = Calendar.getInstance();
// int hour = c.get(Calendar.HOUR_OF_DAY);
// int minute = c.get(Calendar.MINUTE);
//
// // Create a new instance of TimePickerDialog and return it
// // return new TimePickerDialog(getActivity(), this, hour, minute,
// // DateFormat.is24HourFormat(getActivity()));
// return new TimePickerDialog(getActivity(), this, hour, minute, true);
// }
//
// public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
// // Do something with the time chosen by the user
// SetTimer.timePickedFromDialog(hourOfDay, minute);
// }
// }

/*
 * login dialog
 */
class LoginDialogFragment extends DialogFragment {
	/*
	 * The activity that creates an instance of this dialog fragment must
	 * implement this interface in order to receive event callbacks. Each method
	 * passes the DialogFragment in case the host needs to query it.
	 */
	public interface NoticeLoginDialogListener {
		public void onLoginSignInClick(DialogFragment dialog);

		public void onLoginCancelClick(DialogFragment dialog);

		public void onLoginWrongPasswordClick(DialogFragment dialog);
	}

	// Use this instance of the interface to deliver action events
	NoticeLoginDialogListener mListener;

	// Override the Fragment.onAttach() method to instantiate the
	// NoticeDialogListener
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// Verify that the host activity implements the callback interface
		try {
			// Instantiate the NoticeDialogListener so we can send events to the
			// host
			mListener = (NoticeLoginDialogListener) activity;
		} catch (ClassCastException e) {
			// The activity doesn't implement the interface, throw exception
			throw new ClassCastException(activity.toString()
					+ " must implement NoticeDialogListener");
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		LayoutInflater inflater = getActivity().getLayoutInflater();
		// View promptsView =
		// inflater.inflate(R.layout.activity_ask_for_password,
		// null);
		View promptsView = inflater.inflate(R.layout.activity_ask_for_password,
				null);
		final EditText username = (EditText) promptsView
				.findViewById(R.id.username);

		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		// builder.setView(inflater.inflate(R.layout.activity_ask_for_password,
		// null))
		builder.setView(promptsView)
				// Add action buttons
				.setPositiveButton("Sign In",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								// sign in the user ...
								String text = username.getText().toString();
								if (username.getText().toString()
										.equals("jatin")) {
									mListener
											.onLoginSignInClick(LoginDialogFragment.this);
								} else {
									mListener
											.onLoginWrongPasswordClick(LoginDialogFragment.this);

								}
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								mListener
										.onLoginCancelClick(LoginDialogFragment.this);
							}
						});
		return builder.create();
	}
}

/*
 * login dialog
 */
class TimePickerDialogFragment extends DialogFragment {
	/*
	 * The activity that creates an instance of this dialog fragment must
	 * implement this interface in order to receive event callbacks. Each method
	 * passes the DialogFragment in case the host needs to query it.
	 */
	public interface NoticeTimePickerDialogListener {
		public void onTimePickerTimeSetClick(DialogFragment dialog, int hour,
				int min);

		public void onTimePickerCancelClick(DialogFragment dialog);

	}

	// Use this instance of the interface to deliver action events
	NoticeTimePickerDialogListener mListener;

	// Override the Fragment.onAttach() method to instantiate the
	// NoticeDialogListener
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// Verify that the host activity implements the callback interface
		try {
			// Instantiate the NoticeDialogListener so we can send events to the
			// host
			mListener = (NoticeTimePickerDialogListener) activity;
		} catch (ClassCastException e) {
			// The activity doesn't implement the interface, throw exception
			throw new ClassCastException(activity.toString()
					+ " must implement NoticeTimePickerDialogListener");
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		LayoutInflater inflater = getActivity().getLayoutInflater();
		View promptsView = inflater.inflate(R.layout.activity_pick_time, null);
		final NumberPicker hourPicker = (NumberPicker) promptsView
				.findViewById(R.id.hourPicker);
		hourPicker.setMinValue(0);
		hourPicker.setMaxValue(20);
		final NumberPicker minPicker = (NumberPicker) promptsView
				.findViewById(R.id.minPicker);
		minPicker.setMinValue(0);
		minPicker.setMaxValue(59);

		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		// builder.setView(inflater.inflate(R.layout.activity_ask_for_password,
		// null))
		builder.setView(promptsView)
				// Add action buttons
				.setPositiveButton("Set Timer",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								// sign in the user ...
								// String text = username.getText().toString();
								mListener.onTimePickerTimeSetClick(
										TimePickerDialogFragment.this,
										hourPicker.getValue(),
										minPicker.getValue());
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								mListener
										.onTimePickerCancelClick(TimePickerDialogFragment.this);
							}
						});
		return builder.create();
	}
}
