package com.bluehawksinc.lifeintheuktest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;


public class Setting extends Activity 
{
	public static final String Sound_Button = null;
	/** Called when the activity is first created. */
	public static boolean Sound = true;
	 public static SoundManager mSoundManager;
	 NotesDbAdapter mdb = new NotesDbAdapter(this);
	 
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.setting);
       getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlesetting);
      // final ImageView Sound_Button = (ImageView)findViewById(R.id.mute);
       Button back = (Button)findViewById(R.id.home);
       ToggleButton soundButton = (ToggleButton)findViewById(R.id.toggle);
        TextView testInfo = (TextView)findViewById(R.id.testinfo);
        Button reset = (Button)findViewById(R.id.reset);
       TextView findCentre = (TextView)findViewById(R.id.location);
       
       
       if(Sound == true)
		{
			//Sound_Button.setImageResource(R.drawable.sound);
			soundButton.setChecked(true);
			
			
		}
		else
		{
			//Sound_Button.setImageResource(R.drawable.mute);
			soundButton.setChecked(false);
			
		}
     /*  Sound_Button.setOnClickListener(new View.OnClickListener() 
       {
   		public void onClick(View v) 
   		{
   			if(Sound == true)
   			{
   				Sound_Button.setImageResource(R.drawable.mute);
   				Sound = false;
   			}
   			else
   			{
   				Sound_Button.setImageResource(R.drawable.sound);
   				Sound = true;
   			}
   		}
   	});*/
       
       mSoundManager = new SoundManager();
       mSoundManager.initSounds(getBaseContext());
       mSoundManager.addSound(1, R.raw.button9);
       
       testInfo.setOnClickListener(new View.OnClickListener ()
       {
       	public void onClick(View v)
       	{
       		mSoundManager.playSound(1);
       		Intent i1 = new Intent(Setting.this,TestInfo.class);
			startActivity(i1);
			finish();

       	}
       });
        
        findCentre.setOnClickListener(new View.OnClickListener ()
        {
        	public void onClick(View v)
        	{
        		mSoundManager.playSound(1);
        		Uri uri = Uri.parse( "http://classic.multimap.com/clients/places.cgi?client=ufitest" );
    			startActivity( new Intent( Intent.ACTION_VIEW, uri ) );

        	}
        });
        
        
        back.setOnClickListener(new View.OnClickListener() 
        {
			
			public void onClick(View v)
			{
				mSoundManager.playSound(1);
				Intent i1 = new Intent(Setting.this,Main_Screen.class);
				startActivity(i1);
				finish();
			}
		});
        reset.setOnClickListener(new View.OnClickListener() 
        {
			
			public void onClick(View v)
			{
				mSoundManager.playSound(1);
				AlertDialog.Builder builder = new AlertDialog.Builder(Setting.this);
			    builder.setMessage("Which of the following do you want to reset?")
			          .setCancelable(true)
			          .setPositiveButton("Mock Test", new DialogInterface.OnClickListener() 
			          {
			              public void onClick(DialogInterface dialog, int id)
			              {
			             
			              mSoundManager.playSound(1);
			              // Add your save code here !!!!
			              mdb.open();
			              mdb.updateMock1(0);
			              mdb.updateMock2(0);
			              mdb.updateMock3(0);
			              mdb.updateMock4(0);
			              mdb.updateMock5(0);
			              mdb.updateMock6(0);
			              mdb.updateMock7(0);
			              mdb.updateMock8(0);
			              mdb.updateMock9(0);
			              mdb.updateMock10(0);
			              mdb.updateMock11(0);
			              mdb.updateMock12(0);
			              mdb.updateMock13(0);
			              mdb.updateMock14(0);
			              mdb.updateMock15(0);
			              
			              mdb.close();
			              dialog.cancel();            
			              
			              }
			          })
			.setNegativeButton("Study Guide", new DialogInterface.OnClickListener() {
			                public void onClick(DialogInterface dialog, int whichButton) 
			                {
			                	mdb.open();
			                	mdb.updateChapter2(-1);
			                	mdb.updateChapter3(-1);
			                	mdb.updateChapter4(-1);
			                	mdb.updateChapter5(-1);
			                	mdb.updateChapter6(-1);
			                	mdb.close();
			                	
			                	dialog.cancel();
			                    /* User clicked cancel so do some stuff */
			                }
			            })
			    .setNeutralButton("Cancel", new DialogInterface.OnClickListener() 
			    {public void onClick(DialogInterface dialog, int whichButton) 
                {
                	                	
                	dialog.cancel();
                    /* User clicked cancel so do some stuff */
                }
            });
			          builder.show();
			}
			
		});
       soundButton.setOnClickListener(new View.OnClickListener() 
        {  
    	   public void onClick(View v) 
   			{
   				if(Sound == true)
   				{
   					//Sound_Button.setImageResource(R.drawable.mute);
   					Sound = false;
   				}
   				else
   				{
   					//Sound_Button.setImageResource(R.drawable.sound);
   					Sound = true;
   				}
   			}
        });
    }
}


        