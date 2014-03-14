package com.example.safetyproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/*
 * login dialog
 */
public class LoginDialogFragment extends DialogFragment {
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