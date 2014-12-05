package com.bluehawksinc.lifeintheuktest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class Logo extends Activity 
{
	/** Called when the activity is first created. */
	
	public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       
	
	Handler handler1 = new Handler();
	handler1.postDelayed(new Runnable()
	{
	    public void run() 
	    {
	    	Intent i = new Intent(Logo.this,Main_Screen.class);
	    	startActivity(i);
	    	finish();
	    }
	}, 3000);  /*hello dude */
	
    
    }
	
}