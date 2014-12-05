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


public class Info extends Activity 
{
	/** Called when the activity is first created. */

	 public static SoundManager mSoundManager;
	 
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.info);
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
        Button back = (Button)findViewById(R.id.home);
        Button otherapps = (Button)findViewById(R.id.otherapps);
        TextView www = (TextView)findViewById(R.id.name3);
       ImageView logo = (ImageView)findViewById(R.id.logo1);
        
        logo.setOnClickListener(new View.OnClickListener ()
        {
        	public void onClick(View v)
        	{
        		//mSoundManager.playSound(2);
        		Uri uri = Uri.parse("https://market.android.com/developer?pub=BlueHawks+Inc.");
    			startActivity( new Intent( Intent.ACTION_VIEW, uri ) );

        	}
        });
        
        mSoundManager = new SoundManager();
        mSoundManager.initSounds(getBaseContext());
        mSoundManager.addSound(1, R.raw.button9);
        
        
        back.setOnClickListener(new View.OnClickListener() 
        {
			
			public void onClick(View v)
			{
				mSoundManager.playSound(1);
				Intent i1 = new Intent(Info.this,Main_Screen.class);
				startActivity(i1);
				finish();
			}
		});
        www.setOnClickListener(new View.OnClickListener() 
        {
			
			public void onClick(View v)
			{
				mSoundManager.playSound(1);
				Uri uri = Uri.parse("https://market.android.com/developer?pub=BlueHawks+Inc.");
    			startActivity( new Intent( Intent.ACTION_VIEW, uri ) );
			}
		});
        otherapps.setOnClickListener(new View.OnClickListener() 
        {
			
			public void onClick(View v)
			{
				mSoundManager.playSound(1);
				Uri uri = Uri.parse("https://market.android.com/developer?pub=BlueHawks+Inc.");
    			startActivity( new Intent( Intent.ACTION_VIEW, uri ) );
			}
		});
        
    }
}


        