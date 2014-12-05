package com.bluehawksinc.lifeintheuktest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class End extends Activity 
{
	/** Called when the activity is first created. */
	private SoundManager mSoundManager;
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.end);
       getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
       final ImageView Sound_Button = (ImageView)findViewById(R.id.mute);
       
       final DataBaseHelper db = new DataBaseHelper(this);
       NotesDbAdapter mdb = new NotesDbAdapter(this);
       
       if(Setting.Sound == true)
		{
			Sound_Button.setImageResource(R.drawable.sound);
			
		}
		else
		{
			Sound_Button.setImageResource(R.drawable.mute);
			
		}
       Sound_Button.setOnClickListener(new View.OnClickListener() 
       {
   		public void onClick(View v) 
   		{
   			if(Setting.Sound == true)
   			{
   				Sound_Button.setImageResource(R.drawable.mute);
   				Setting.Sound = false;
   			}
   			else
   			{
   				Sound_Button.setImageResource(R.drawable.sound);
   				Setting.Sound = true;
   			}
   		}
   	});
        Button next = (Button)findViewById(R.id.menu);
        Button next1 = (Button)findViewById(R.id.another);
        TextView Score = (TextView)findViewById(R.id.score);
        TextView Score0 = (TextView)findViewById(R.id.score0);
        TextView Score01 = (TextView)findViewById(R.id.score01);
        TextView Score02 = (TextView)findViewById(R.id.score02);
        TextView Score1 = (TextView)findViewById(R.id.score1);
        TextView Score2 = (TextView)findViewById(R.id.score2);
        mdb.open();
        int scores = mdb.getMock1();
        mdb.close();
        if(scores < 18)
        {
        	Score.setText("You Scored:	"+scores);
        	Score1.setText("Sorry, you have failed the test! Try harder!"); 
        	Score1.setTextColor(-65536);
        	Score0.setText(" Unfortunately, you did NOT pass at this time.");
        	Score01.setText("According to Exam Format, To PASS, you must answer at least 18 of the 24 questions correctly. ");
        	Score02.setText("We suggest that you re-take this test or take other UK Citizenship Mock tests by clicking 'Take Another Test' below, and keep trying until you have ZERO mistakes.");
        	Score2.setText("Good luck! ");
        }
        else if(scores >= 18 && scores < 24)
          {
        	Score.setText("You Scored: "+scores);
        	Score1.setText("Good performance !! ");
            Score1.setTextColor(-16711936);
        	Score0.setText("But you still had some mistakes."); 
        			
        	Score01.setText("You can try even harder to have zero mistakes");
        	Score02.setText("We suggest that you re-take this test or take other UK Citizenship Mock tests by clicking 'Take Another Test' below, and keep trying until you have ZERO mistakes.");
        	Score2.setText("Good luck! ");
        }
        else if(scores == 24)
        {
        	Score.setText("You Scored: "+scores);
        	Score1.setText("Congratulations !! ");
        	Score1.setTextColor(-16711936);
        	Score02.setText("You aced your Test with zero mistakes ");
        	Score2.setText("Keep it up !! ");
        }
        	
        mSoundManager = new SoundManager();
        mSoundManager.initSounds(getBaseContext());
        mSoundManager.addSound(1, R.raw.magic);
        mSoundManager.addSound(2, R.raw.button9);
        
        
        findViewById(R.id.home).setOnClickListener(new View.OnClickListener() 
        {
    		public void onClick(View v) 
    		{
    			mSoundManager.playSound(2);
    			Intent i1 = new Intent(End.this,Main_Screen.class);
    			startActivity(i1);
    			finish();
    		}
    	});
        next.setOnClickListener(new View.OnClickListener() 
        {
			
			public void onClick(View v)
			{
				mSoundManager.playSound(2);
				//mSoundManager.playSound(1);
				Intent i1 = new Intent(End.this,Main_Screen.class);
				startActivity(i1);
				finish();
			}
		});
        
        next1.setOnClickListener(new View.OnClickListener() 
        {
			
			public void onClick(View v)
			{
				mSoundManager.playSound(2);
				//mSoundManager.playSound(1);
				Intent i1 = new Intent(End.this,Mock.class);
				startActivity(i1);
				finish();
			}
		});
        findViewById(R.id.answer).setOnClickListener(new View.OnClickListener() 
        {
    		public void onClick(View v) 
    		{
    			if(Mock1.incorrect.isEmpty())
    			{
    				AlertDialog.Builder builder = new AlertDialog.Builder(End.this);
    			    builder.setMessage("All the questions you answered were correct. No incorrect answers to display!!")
    			          .setCancelable(true)
    			          
    			          .setNeutralButton("Ok", new DialogInterface.OnClickListener() 
    			          {
    			              public void onClick(DialogInterface dialog, int id)
    			              {         
    			            	  dialog.cancel();
    			              }
    			          });
    			
    			          builder.show();
    			}
    			else
    			{
    				mSoundManager.playSound(2);
    				Intent i1 = new Intent(End.this,ans1.class);
    				startActivity(i1);
    				finish();
    			}
    		}
    	});
        
    }
}
        