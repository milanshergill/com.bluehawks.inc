package com.bluehawksinc.lifeintheuktest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

public class Special_Sign extends Activity 
{
	/** Called when the activity is first created. */

	 public static SoundManager mSoundManager;
	 public static final int SHOW = 0;
	 NotesDbAdapter mdb = new NotesDbAdapter(this);
	//MediaPlayer music = MediaPlayer.create(Main_Screen.this, R.raw.twist);
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.specialinfo);
       getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
       final ImageView Sound_Button = (ImageView)findViewById(R.id.mute);
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
        Button next = (Button)findViewById(R.id.cont);
        mSoundManager = new SoundManager();
        mSoundManager.initSounds(getBaseContext());
        mSoundManager.addSound(1, R.raw.button9);
        mSoundManager.addSound(2, R.raw.magic);
        next.setOnClickListener(new View.OnClickListener() 
        {
			
			public void onClick(View v)
			{
				mSoundManager.playSound(1);
				Intent i1 = new Intent(Special_Sign.this,Sign.class);
				startActivity(i1);
				finish();
				
			}
		});
        
        findViewById(R.id.home).setOnClickListener(new View.OnClickListener() 
        {
			public void onClick(View v) 
			{
				mSoundManager.playSound(1);
				Intent i1 = new Intent(Special_Sign.this,Main_Screen.class);
				startActivity(i1);
				finish();
			}
		});
        
        
    }
    
}

        