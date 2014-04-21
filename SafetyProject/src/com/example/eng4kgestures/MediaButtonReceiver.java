package com.example.eng4kgestures;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.widget.Toast;

public class MediaButtonReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		if (Intent.ACTION_MEDIA_BUTTON.equals(intent.getAction())) {
			KeyEvent event = (KeyEvent) intent
					.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
			if (KeyEvent.KEYCODE_VOLUME_DOWN == event.getKeyCode()) {
				Toast.makeText(context, "Event Occurred",
						Toast.LENGTH_SHORT).show();
			}
		}
	}
}