package com.example.safetyproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/*
 * login dialog
 */
public class LoginDialogFragment extends DialogFragment {

	CountDownTimer passwordCountDownTimer;
	TextView passwordTimer;
	EditText passwordField;
	boolean playSound = true;
	boolean startPasswordTimer = false;

	/*
	 * The activity that creates an instance of this dialog fragment must
	 * implement this interface in order to receive event callbacks. Each method
	 * passes the DialogFragment in case the host needs to query it.
	 */
	public interface NoticeLoginDialogListener {
		public void onLoginSuccessful(DialogFragment dialog);

		public void onLoginCancelClick(DialogFragment dialog);

		public void onLoginWrongPassword(DialogFragment dialog);

		public void passwordTimerEnded();

		public void playAlertSound();
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
		View promptsView = inflater.inflate(R.layout.activity_ask_for_password,
				null);
		passwordField = (EditText) promptsView.findViewById(R.id.password);

		passwordTimer = (TextView) promptsView.findViewById(R.id.passwordTimer);
		builder.setCancelable(false)
				.setView(promptsView)
				.setNeutralButton("LogIn",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								// Do nothing here but perform actions in
								// over-ridden method
							}
						});
		return builder.create();
	}

	@Override
	public void onStart() {
		super.onStart(); // super.onStart() is where dialog.show() is actually
							// called on the underlying dialog, so we have to do
							// it after this point

		// Start the password timer
		if (startPasswordTimer)
			startPassowrdTimer();

		AlertDialog d = (AlertDialog) getDialog();
		if (d != null) {
			Button neutralButton = (Button) d.getButton(Dialog.BUTTON_NEUTRAL);
			neutralButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// Verify the password
					if (passwordField.getText().toString().equals("0")) {
						mListener.onLoginSuccessful(LoginDialogFragment.this);
						// Cancel the timer
						if (passwordCountDownTimer != null) {
							passwordCountDownTimer.cancel();
						}
						dismiss(); // dismiss the dialog
					} else {
						passwordField.setError("Wrong Password!");
					}
				}
			});
		}
	}

	private void startPassowrdTimer() {

		long totalTimeInMillis = 10 * 1000; // 10 secs

		// make the passtimer field visible
		passwordTimer.setVisibility(View.VISIBLE);
		passwordCountDownTimer = new CountDownTimer(totalTimeInMillis, 1000) {

			@Override
			public void onTick(long leftTimeInMilliseconds) {
				long seconds = leftTimeInMilliseconds / 1000;

				// format the textview to show the easily readable format
				passwordTimer.setText(String.format("%02d", seconds / 60) + ":"
						+ String.format("%02d", seconds % 60));

				if (playSound) {
					mListener.playAlertSound();
				}
				playSound = !playSound; // toggle to play alert every other tick
			}

			@Override
			public void onFinish() {
				// this function will be called when the timecount is finished
				mListener.passwordTimerEnded();
				dismiss(); // dismiss the dialog

			}
		}.start();
	}
}