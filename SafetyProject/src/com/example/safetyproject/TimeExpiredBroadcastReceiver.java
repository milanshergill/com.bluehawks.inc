package com.example.safetyproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.widget.Toast;

public class TimeExpiredBroadcastReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
//		Toast.makeText(context, "Don't panik but your time is up!!!!.",
//				Toast.LENGTH_LONG).show();
		// Vibrate the mobile phone
		Vibrator vibrator = (Vibrator) context
				.getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(2000);
		Intent buddyGuardIntent = new Intent(context, IAmHereActivity.class);
		// Let the activity know that timer expired
		buddyGuardIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		buddyGuardIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		buddyGuardIntent.putExtra("timer_expired", "Yes");
	    context.startActivity(buddyGuardIntent);
	}
}