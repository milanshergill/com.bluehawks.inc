package com.bluehawksinc.lifeintheuktest;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Select extends Activity 
{
	/** Called when the activity is first created. */

	 public static SoundManager mSoundManager;
	 
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.select);
       getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
       final ImageView Sound_Button = (ImageView)findViewById(R.id.mute);
       //checkLicense(); 
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

        mSoundManager = new SoundManager();
        mSoundManager.initSounds(getBaseContext());
        mSoundManager.addSound(1, R.raw.button9);
        mSoundManager.addSound(2, R.raw.magic);
        
        Button rule = (Button)findViewById(R.id.rule);
      //  Button sign = (Button)findViewById(R.id.sign);
        Button mock = (Button)findViewById(R.id.mock);
        Button important = (Button)findViewById(R.id.important);
        
        rule.setOnClickListener(new View.OnClickListener() 
        {
			
			public void onClick(View v)
			{
				mSoundManager.playSound(1);
				//mSoundManager.playSound(2);
				Intent i1 = new Intent(Select.this,QuizInfo.class);
				startActivity(i1);
				finish();
			}
		});
        
      /*  sign.setOnClickListener(new View.OnClickListener() 
        {
			
			public void onClick(View v)
			{
				mSoundManager.playSound(1);
				//mSoundManager.playSound(2);
				Intent i1 = new Intent(Select.this,SignInfo.class);
				startActivity(i1);
				finish();
			}
		});*/
        
        mock.setOnClickListener(new View.OnClickListener() 
        {
			
			public void onClick(View v)
			{
				mSoundManager.playSound(1);
				//mSoundManager.playSound(2);
				Intent i1 = new Intent(Select.this,MockInfo.class);
				startActivity(i1);
				finish();
			}
		});
        
        important.setOnClickListener(new View.OnClickListener() 
        {
			
			public void onClick(View v)
			{
				mSoundManager.playSound(1);
				//mSoundManager.playSound(2);
				Intent i1 = new Intent(Select.this,SpecialInfo.class);
				startActivity(i1);
				finish();
			}
		});
        
        findViewById(R.id.home).setOnClickListener(new View.OnClickListener() 
        {
			public void onClick(View v) 
			{
				mSoundManager.playSound(1);
				Intent i1 = new Intent(Select.this,Main_Screen.class);
				startActivity(i1);
				finish();
			}
		});
      
        
    
    }
}


        