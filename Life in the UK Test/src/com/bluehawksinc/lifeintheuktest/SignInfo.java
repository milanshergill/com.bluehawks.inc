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
public class SignInfo extends Activity 
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
        setContentView(R.layout.signinfo);
       getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titleback);
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
				showDialog(0);
			}
		});
        
        findViewById(R.id.home).setOnClickListener(new View.OnClickListener() 
        {
			public void onClick(View v) 
			{
				mSoundManager.playSound(1);
				Intent i1 = new Intent(SignInfo.this,Main_Screen.class);
				startActivity(i1);
				finish();
			}
		});
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() 
        {
    		public void onClick(View v) 
    		{
    			mSoundManager.playSound(1);
    			Intent i1 = new Intent(SignInfo.this,Select.class);
    			startActivity(i1);
    			finish();
    		}
    	});
        
    }
    protected Dialog onCreateDialog(int id)
    {
        Dialog dialog = null;
        switch(id) 
        {
         case SHOW:
        	AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        	builder1.setMessage("Do you want to continue where left or restart again?")
        	       .setCancelable(true)
        	       .setPositiveButton("Continue", new DialogInterface.OnClickListener() 
        	       {
        	           public void onClick(DialogInterface dialog, int id)
        	           {
        	        	   
        	        	   Intent i1 = new Intent(SignInfo.this,Special.class);
        					startActivity(i1);
        					finish();
        	        	  dialog.cancel();
        	        	   
        	           }
        	       })
        	       .setNegativeButton("Restart", new DialogInterface.OnClickListener()
        	       {
        	           public void onClick(DialogInterface dialog, int id) 
        	           {/*
        	        	   mdb.open();
        	        	   mdb.updateSpecial_ques(-1);
        	        	   mdb.close();*/
        	        	   
        	        	   Intent i1 = new Intent(SignInfo.this,Special.class);
        					startActivity(i1);
        					finish();
        	                dialog.cancel();
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
}

        