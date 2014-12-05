package com.bluehawksinc.lifeintheuktest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Contacts.Settings;
import android.text.Selection;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Main_Screen extends Activity {
	/** Called when the activity is first created. */

	public static SoundManager mSoundManager;
	public static final int ZERO = 0;

	NotesDbAdapter mdb;

	// MediaPlayer music = MediaPlayer.create(Main_Screen.this, R.raw.twist);
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.first);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.titlemain);
		mdb = new NotesDbAdapter(this);
		mdb.open();
		mdb.createDB();
		// Animation hyperspaceJumpAnimation1 =
		// AnimationUtils.loadAnimation(this, R.anim.wave_scale);
		// wel.startAnimation(hyperspaceJumpAnimation1);
		Button next = (Button) findViewById(R.id.home);
		Button next1 = (Button) findViewById(R.id.start2);
		Button next2 = (Button) findViewById(R.id.start3);
		Button next3 = (Button) findViewById(R.id.start);
		TextView not = (TextView) findViewById(R.id.name1);
		// Button next2 = (Button)findViewById(R.id.train);

		// Animation hyperspaceJumpAnimation =
		// AnimationUtils.loadAnimation(this, R.anim.push_left_in);
		// next.startAnimation(hyperspaceJumpAnimation);

		// mb.stop();
		mSoundManager = new SoundManager();
		mSoundManager.initSounds(getBaseContext());
		mSoundManager.addSound(1, R.raw.button9);
		mSoundManager.addSound(2, R.raw.magic);

		AlertDialog.Builder builder = new AlertDialog.Builder(Main_Screen.this);
		final EditText input = new EditText(this);
		builder.setTitle("Enter Your Name Below (max 16 chars)")
				.setView(input)
				.setPositiveButton("Done",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								String value = input.getText().toString()
										.trim();
								TextView name = (TextView) findViewById(R.id.name);
								if (value.length() == 0) {
									// name.setText("");
								} else if (value.length() >= 16) {
									// name.setText("");
								} else {
									mdb.open();
									mdb.updateName(value);
									mdb.close();
									name.setText(value);
								}

							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								dialog.cancel();
								/* User clicked cancel so do some stuff */
							}
						});
		// builder.show();
		mdb.open();
		String nm = mdb.getSName();
		mdb.close();
		if (nm.equals("player1")) {
			builder.show();
		} else {
			mdb.open();
			String n = mdb.getSName();
			mdb.close();
			TextView name = (TextView) findViewById(R.id.name);
			name.setText(n);
		}
		Eula.show(this);

		next1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mSoundManager.playSound(1);
				// mSoundManager.playSound(2);
				Intent i1 = new Intent(Main_Screen.this, Select.class);
				startActivity(i1);

			}
		});
		next3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mSoundManager.playSound(1);
				Intent i1 = new Intent(Main_Screen.this, Setting.class);
				startActivity(i1);
			}
		});

		next2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mSoundManager.playSound(1);
				// mSoundManager.playSound(2);
				Intent i1 = new Intent(Main_Screen.this, Results.class);
				startActivity(i1);
			}
		});

		next.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mSoundManager.playSound(1);
				// mSoundManager.playSound(2);
				Intent i1 = new Intent(Main_Screen.this, Info.class);
				startActivity(i1);
			}
		});

		not.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// mSoundManager.playSound(1);
				AlertDialog.Builder builder1 = new AlertDialog.Builder(
						Main_Screen.this);
				final EditText input = new EditText(Main_Screen.this);
				builder1.setTitle("Enter Your Name Below")
						.setView(input)
						.setPositiveButton("Done",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										String value = input.getText()
												.toString().trim();
										TextView name = (TextView) findViewById(R.id.name);
										if (value.length() == 0) {
											// name.setText("");
										} else if (value.length() >= 16) {
											// name.setText("");
										} else {
											mdb.open();
											mdb.updateName(value);
											mdb.close();
											name.setText(value);
										}

									}
								})
						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										dialog.cancel();
										/* User clicked cancel so do some stuff */
									}
								});
				builder1.show();
			}
		});

		// Check the license

	}
}