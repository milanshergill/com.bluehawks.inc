package com.example.safetyproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

/*
 * login dialog
 */
public class TimePickerDialogFragment extends DialogFragment {
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
