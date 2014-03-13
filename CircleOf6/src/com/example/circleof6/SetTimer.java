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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

@SuppressLint("NewApi")
public class SetTimer extends Activity implements LoginDialogFragment.NoticeDialogListener {
	private static TextView tvDisplayTime;
	private static TextView timerView;
	private static TextView timeView;
	private Button btnChangeTime;
	int n;

	TimerTask timerTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_timer);
		tvDisplayTime = (TextView) findViewById(R.id.tvDisplayTime);
		timerView = (TextView) findViewById(R.id.timerView);
		timeView = (TextView) findViewById(R.id.timeView);
		setCurrentTimeOnView(7, 7);
		n = 0;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_timer, menu);
		return true;

	}

	@SuppressLint("NewApi")
	public void showTimePickerDialog(View v) {
		DialogFragment newFragment = new TimePickerFragment();
		newFragment.show(getFragmentManager(), "timePicker");
	}

	// display current time
	public static void setCurrentTimeOnView(int hour, int minute) {
		// set current time into textview
		tvDisplayTime.setText(new StringBuilder().append(padding_str(hour))
				.append(":").append(padding_str(minute)));

	}

	public void onClickSetTimer(View v) {
		
		stopTimer();
		startTimer();
		n=10;
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
						if(n==0)
						{
							timerTask.cancel();
							timerView.setText("Timer Finished");
							Toast.makeText(getApplicationContext(), "Timer Finished!",
									   Toast.LENGTH_LONG).show();
							showNoticeDialog();
							
							
						}
						timeView.setText("Time left "+ n + " Seconds");
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
	 public void showNoticeDialog() {
	        // Create an instance of the dialog fragment and show it
		 LoginDialogFragment dialog = new LoginDialogFragment();
	        dialog.show(getFragmentManager(), "Password Fragment");
	    }

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "Password Correct",
				   Toast.LENGTH_LONG).show();	
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "No password entered",
				   Toast.LENGTH_LONG).show();
		
	}

	public void onWrongPassowrdClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "Wrong password",
				   Toast.LENGTH_LONG).show();
		
	}

	    // The dialog fragment receives a reference to this Activity through the
	    // Fragment.onAttach() callback, which it uses to call the following methods
	    // defined by the NoticeDialogFragment.NoticeDialogListener interface

	


}

class TimePickerFragment extends DialogFragment implements
		TimePickerDialog.OnTimeSetListener {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current time as the default values for the picker
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		// Create a new instance of TimePickerDialog and return it
		return new TimePickerDialog(getActivity(), this, hour, minute,
				DateFormat.is24HourFormat(getActivity()));
	}

	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		// Do something with the time chosen by the user
		SetTimer.setCurrentTimeOnView(hourOfDay, minute);
	}
}

class LoginDialogFragment extends DialogFragment {
	 /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
        public void onWrongPassowrdClick(DialogFragment dialog);
    }
    
    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;
    
    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
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
	    View promptsView = inflater.inflate(R.layout.activity_ask_for_password, null);
        final EditText username = (EditText) promptsView
                .findViewById(R.id.username);

	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	   // builder.setView(inflater.inflate(R.layout.activity_ask_for_password, null))
        builder.setView(promptsView)
	    // Add action buttons
	           .setPositiveButton("Sign In", new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	                   // sign in the user ...
	            	   String text = username.getText().toString();
	            	   if(username.getText().toString().equals("jatin"))
	            	   {
	            		   mListener.onDialogPositiveClick(LoginDialogFragment.this);
	            	   }
	            	   else{
	            		   mListener.onWrongPassowrdClick(LoginDialogFragment.this);
	            		   
	            	   }
	               }
	           })
	           .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	            	   mListener.onDialogNegativeClick(LoginDialogFragment.this);
	               }
	           });      
	    return builder.create();
	}	
}

