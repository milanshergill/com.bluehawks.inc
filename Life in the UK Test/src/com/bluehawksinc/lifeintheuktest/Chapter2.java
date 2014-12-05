package com.bluehawksinc.lifeintheuktest;

import java.io.IOException;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Chapter2 extends Activity {
	
	private AdView mAdView;
	/** Called when the activity is first created. */
	private RadioGroup radioGroup;
	private RadioButton Op1;
	private RadioButton Op2;
	private RadioButton Op3;
	private RadioButton Op4;
	private TextView quizQuestion;
	private int index;
	public static int Ch2Count = -1;
	public static final int END = 0;
	public static final int SHOW = 1;

	private int ans;
	private int check;
	/* change */
	DataBaseHelper2 db = new DataBaseHelper2(this);

	NotesDbAdapter mdb = new NotesDbAdapter(this);
	// NotesDbAdapter mdb = new NotesDbAdapter(this);
	Cursor c1;
	Cursor c21;
	Cursor c22;
	Cursor c23;
	Cursor c24;
	Cursor c3;
	private SoundManager mSoundManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.start);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.titlechp);
        mAdView = (AdView) findViewById(R.id.adView);
//        mAdView.setAdListener(new ToastAdListener(this));
        mAdView.loadAd(new AdRequest.Builder().build());
        
		mdb.open();
		Ch2Count = mdb.getChapter2();
		--Ch2Count;
		mdb.close();
		if (savedInstanceState != null) {
			Ch2Count = savedInstanceState.getInt("MyInt");

		}

		mSoundManager = new SoundManager();
		mSoundManager.initSounds(getBaseContext());
		mSoundManager.addSound(1, R.raw.magic);
		mSoundManager.addSound(2, R.raw.button9);

		final ImageView Sound_Button = (ImageView) findViewById(R.id.home);
		if (Setting.Sound == true) {
			Sound_Button.setImageResource(R.drawable.sound);
		} else {
			Sound_Button.setImageResource(R.drawable.mute);
		}
		Sound_Button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (Setting.Sound == true) {
					Sound_Button.setImageResource(R.drawable.mute);
					Setting.Sound = false;

				} else {
					Sound_Button.setImageResource(R.drawable.sound);
					Setting.Sound = true;
				}
			}
		});

		// showDialog(1);
		// mSoundManager.addSound(3, R.raw.end);
		try {
			db.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		quizQuestion = (TextView) findViewById(R.id.question);
		// Animation hyperspaceJumpAnimation =
		// AnimationUtils.loadAnimation(this, R.anim.hyperspace_out);
		// quizQuestion.startAnimation(hyperspaceJumpAnimation);
		radioGroup = (RadioGroup) findViewById(R.id.group);
		radioGroup.setOnCheckedChangeListener(Listener2);
		// Animation hyperspaceJumpAnimation6 =
		// AnimationUtils.loadAnimation(this, R.anim.slide_left);
		// radioGroup.startAnimation(hyperspaceJumpAnimation6);
		Op1 = (RadioButton) findViewById(R.id.Op1);
		Op2 = (RadioButton) findViewById(R.id.Op2);
		Op3 = (RadioButton) findViewById(R.id.Op3);
		Op4 = (RadioButton) findViewById(R.id.Op4);

		displayQuestion();

		/* Displays the next options and sets listener on next button */
		Button btnNext = (Button) findViewById(R.id.btnNext);
		// Animation hyperspaceJumpAnimation2 =
		// AnimationUtils.loadAnimation(this, R.anim.slide_right);
		// btnNext.startAnimation(hyperspaceJumpAnimation2);
		btnNext.setOnClickListener(Listener);

		/* Saves the selected values in the database on the End button */
		Button finish = (Button) findViewById(R.id.btnEnd);
		finish.setOnClickListener(Listener);
		Button btnPrev = (Button) findViewById(R.id.btnPrev);
		btnPrev.setOnClickListener(Listener);
		// Animation hyperspaceJumpAnimation3 =
		// AnimationUtils.loadAnimation(this, R.anim.push_left_out);
		// finish.startAnimation(hyperspaceJumpAnimation3);
		findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mSoundManager.playSound(2);
				Intent i1 = new Intent(Chapter2.this, Chapters.class);
				startActivity(i1);
				finish();
			}
		});
	}

	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch (id) {
		case END:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Are you sure you want to exit?")
					.setCancelable(true)
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {

									// mSoundManager.playSound(1);
									// mdb.open();
									// mdb.updateChapter2(Ch2Count);
									// mdb.close();

									// Add your save code here !!!!
									Intent i1 = new Intent(Chapter2.this,
											Main_Screen.class);
									startActivity(i1);
									finish();
								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									mSoundManager.playSound(2);
									dialog.cancel();
								}
							});
			AlertDialog alert = builder.create();
			dialog = alert;
			break;

		case SHOW:
			AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
			builder1.setMessage(
					"You successfully completed all the required questions!!")
					.setCancelable(false)
					.setPositiveButton("Restart",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {

									// mSoundManager.playSound(1);
									mdb.open();
									mdb.updateChapter2(-1);
									mdb.close();
									Intent i1 = new Intent(Chapter2.this,
											QuizInfo.class);
									startActivity(i1);
									finish();
								}
							})
					.setNegativeButton("Menu",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// mSoundManager.playSound(1);
									Intent i2 = new Intent(Chapter2.this,
											Main_Screen.class);
									startActivity(i2);
									finish();

								}
							});
			AlertDialog alert1 = builder1.create();
			dialog = alert1;
			break;

		default:
			dialog = null;
		}
		return dialog;
	}

	/* Called when button is clicked */
	private View.OnClickListener Listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case (R.id.btnNext):
				mSoundManager.playSound(2);
				displayQuestion();
				break;
			case (R.id.btnPrev):
				mSoundManager.playSound(2);
				if (Ch2Count > 0)
					prevQuestion();
				break;

			case (R.id.btnEnd):
				mSoundManager.playSound(2);
				showDialog(END);
				// Intent i1 = new Intent(Quiz1.this,End.class);
				// startActivity(i1);
				break;
			default:
				mSoundManager.playSound(2);
				Intent i2 = new Intent(Chapter2.this, Main_Screen.class);
				startActivity(i2);
				finish();
			}
		}
	};

	RadioGroup.OnCheckedChangeListener Listener2 = new RadioGroup.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if (check == 1) {
				Op1.setBackgroundColor(-16711936);
				Op2.setBackgroundColor(0);
				Op3.setBackgroundColor(0);
				Op4.setBackgroundColor(0);
				if (ans == checkedId) {

				} else {
					/* Op1.setBackgroundColor(-65536); */
					switch (checkedId) {
					case (R.id.Op2):
						Op2.setBackgroundColor(-65536);
						break;
					case (R.id.Op3):
						Op3.setBackgroundColor(-65536);
						break;
					case (R.id.Op4):
						Op4.setBackgroundColor(-65536);
						break;
					default:
					}
				}
			}

			else if (check == 2) {
				Op2.setBackgroundColor(-16711936);
				Op1.setBackgroundColor(0);
				Op3.setBackgroundColor(0);
				Op4.setBackgroundColor(0);
				if (ans == checkedId) {

				} else {
					/* Op2.setBackgroundColor(-65536); */
					switch (checkedId) {
					case (R.id.Op1):
						Op1.setBackgroundColor(-65536);
						break;
					case (R.id.Op3):
						Op3.setBackgroundColor(-65536);
						break;
					case (R.id.Op4):
						Op4.setBackgroundColor(-65536);
						break;
					default:
					}
				}
			}

			else if (check == 3) {
				Op3.setBackgroundColor(-16711936);
				Op2.setBackgroundColor(0);
				Op1.setBackgroundColor(0);
				Op4.setBackgroundColor(0);
				if (ans == checkedId) {

				} else {
					/* Op3.setBackgroundColor(-65536); */
					switch (checkedId) {
					case (R.id.Op2):
						Op2.setBackgroundColor(-65536);
						break;
					case (R.id.Op1):
						Op1.setBackgroundColor(-65536);
						break;
					case (R.id.Op4):
						Op4.setBackgroundColor(-65536);
						break;
					default:
					}
				}
			}

			else if (check == 4) {
				Op4.setBackgroundColor(-16711936);
				Op2.setBackgroundColor(0);
				Op3.setBackgroundColor(0);
				Op1.setBackgroundColor(0);
				if (ans == checkedId) {

				}

				else {
					/* Op4.setBackgroundColor(-65536); */
					switch (checkedId) {
					case (R.id.Op2):
						Op2.setBackgroundColor(-65536);
						break;
					case (R.id.Op3):
						Op3.setBackgroundColor(-65536);
						break;
					case (R.id.Op1):
						Op1.setBackgroundColor(-65536);
						break;
					default:
					}
				}
			}

		}
	};

	int[] numbers = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16,
			17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33,
			34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50,
			51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67,
			68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84,
			85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100,
			101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113,
			114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126,
			127, 128, 129, 130, 131, 132, 133, 134, 135, 136 };

	void displayQuestion() {
		check = 0;
		Op1.setChecked(false);
		Op1.setBackgroundColor(0);
		Op2.setChecked(false);
		Op2.setBackgroundColor(0);
		Op3.setChecked(false);
		Op3.setBackgroundColor(0);
		Op4.setChecked(false);
		Op4.setBackgroundColor(0);
		if (Ch2Count < (numbers.length - 1)) {
			// Fetching data quiz data and incrementing on each click
			try {
				++Ch2Count;
				mdb.open();
				mdb.updateChapter2(Ch2Count);
				mdb.close();
				// Random random = new Random();
				index = numbers[Ch2Count];

				c1 = db.getQues(index);
				c21 = db.getOption1(index);
				c22 = db.getOption2(index);
				c23 = db.getOption3(index);
				c24 = db.getOption4(index);
				c3 = db.getCorrAns(index);

				quizQuestion.setText(c1.getString(0));
				Op1.setText(c21.getString(0));
				Op2.setText(c22.getString(0));
				Op3.setText(c23.getString(0));
				Op4.setText(c24.getString(0));
				String answer = c3.getString(0);
				if (answer.equals(Op1.getText())) {
					ans = Op1.getId();
					check = 1;
				} else if (answer.equals(Op2.getText())) {
					ans = Op2.getId();
					check = 2;
				} else if (answer.equals(Op3.getText())) {
					ans = Op3.getId();
					check = 3;
				} else {
					ans = Op4.getId();
					check = 4;
				}
			}

			catch (Exception ex) {
				displayQuestion();
			}
		} else {
			showDialog(SHOW);
		}
	}

	void prevQuestion() {
		Op1.setChecked(false);
		Op1.setBackgroundColor(0);
		Op2.setChecked(false);
		Op2.setBackgroundColor(0);
		Op3.setChecked(false);
		Op3.setBackgroundColor(0);
		Op4.setChecked(false);
		Op4.setBackgroundColor(0);
		if (Ch2Count < numbers.length && Ch2Count > 0) {
			// Fetching data quiz data and incrementing on each click
			try {
				--Ch2Count;
				index = numbers[Ch2Count];

				c1 = db.getQues(index);
				c21 = db.getOption1(index);
				c22 = db.getOption2(index);
				c23 = db.getOption3(index);
				c24 = db.getOption4(index);
				c3 = db.getCorrAns(index);

				// signCount++;

				// ByteArrayInputStream b = new ByteArrayInputStream(bb);
				// Drawable drw = Drawable.createFromStream(b, "image");
				// quizQuestion.setImageDrawable(drw);
				quizQuestion.setText(c1.getString(0));
				Op1.setText(c21.getString(0));
				Op2.setText(c22.getString(0));
				Op3.setText(c23.getString(0));
				Op4.setText(c24.getString(0));
				String answer = c3.getString(0);
				if (answer.equals(Op1.getText())) {
					ans = Op1.getId();
					check = 1;
				} else if (answer.equals(Op2.getText())) {
					ans = Op2.getId();
					check = 2;
				} else if (answer.equals(Op3.getText())) {
					ans = Op3.getId();
					check = 3;
				} else {
					ans = Op4.getId();
					check = 4;
				}
			}

			catch (Exception ex) {
				prevQuestion();
			}
		}

	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		// Save UI state changes to the savedInstanceState.
		// This bundle will be passed to onCreate if the process is
		// killed and restarted.
		savedInstanceState.putInt("MyInt", Ch2Count - 1);

		// etc.
		super.onSaveInstanceState(savedInstanceState);
	}
}
