package com.bluehawksinc.lifeintheuktest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;


public class Results extends Activity 
{
	/** Called when the activity is first created. */

	 public static SoundManager mSoundManager;
	 NotesDbAdapter mdb = new NotesDbAdapter(this);
	//MediaPlayer music = MediaPlayer.create(Main_Screen.this, R.raw.twist);
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.result);
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
        mdb.open();
       // Button next = (Button)findViewById(R.id.cont);
       ListView lv = (ListView)findViewById(R.id.list);
       String[] from = new String[] {"testname", "score"};
       int[] to = new int[] {R.id.testname, R.id.score};
        
               // prepare the list of all records
        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
       
       	 HashMap<String,String> map1 = new HashMap<String, String>();
       	  map1.put("testname", "Mock Test-1");
       	  map1.put("score", mdb.getSMock1());
       	  fillMaps.add(map1);
       	 
       	HashMap<String,String> map2 = new HashMap<String, String>();
     	  map2.put("testname", "Mock Test-2");
     	  map2.put("score", mdb.getSMock2());
     	  fillMaps.add(map2);
     	  
     	 HashMap<String,String> map3 = new HashMap<String, String>();
      	  map3.put("testname", "Mock Test-3");
      	  map3.put("score", mdb.getSMock3());
      	  fillMaps.add(map3);
      	  
      	HashMap<String,String> map4 = new HashMap<String, String>();
     	  map4.put("testname", "Mock Test-4");
     	  map4.put("score", mdb.getSMock4());
     	  fillMaps.add(map4);
     	 HashMap<String,String> map5 = new HashMap<String, String>();
    	  map5.put("testname", "Mock Test-5");
    	  map5.put("score", mdb.getSMock5());
    	  fillMaps.add(map5);
    	  HashMap<String,String> map6 = new HashMap<String, String>();
    	  map6.put("testname", "Mock Test-6");
    	  map6.put("score", mdb.getSMock6());
    	  fillMaps.add(map6);
    	  HashMap<String,String> map7 = new HashMap<String, String>();
    	  map7.put("testname", "Mock Test-7");
    	  map7.put("score", mdb.getSMock7());
    	  fillMaps.add(map7);
    	  HashMap<String,String> map8 = new HashMap<String, String>();
    	  map8.put("testname", "Mock Test-8");
    	  map8.put("score", mdb.getSMock8());
    	  fillMaps.add(map8);
    	  HashMap<String,String> map9 = new HashMap<String, String>();
    	  map9.put("testname", "Mock Test-9");
    	  map9.put("score", mdb.getSMock9());
    	  fillMaps.add(map9);
    	  HashMap<String,String> map10 = new HashMap<String, String>();
    	  map10.put("testname", "Mock Test-10");
    	  map10.put("score", mdb.getSMock10());
    	  fillMaps.add(map10);
    	  HashMap<String,String> map11 = new HashMap<String, String>();
    	  map11.put("testname", "Mock Test-11");
    	  map11.put("score", mdb.getSMock11());
    	  fillMaps.add(map11);
    	  HashMap<String,String> map12 = new HashMap<String, String>();
    	  map12.put("testname", "Mock Test-12");
    	  map12.put("score", mdb.getSMock12());
    	  fillMaps.add(map12);
    	  HashMap<String,String> map13 = new HashMap<String, String>();
    	  map13.put("testname", "Mock Test-13");
    	  map13.put("score", mdb.getSMock13());
    	  fillMaps.add(map13);
    	  HashMap<String,String> map14 = new HashMap<String, String>();
    	  map14.put("testname", "Mock Test-14");
    	  map14.put("score", mdb.getSMock14());
    	  fillMaps.add(map14);
    	  HashMap<String,String> map15 = new HashMap<String, String>();
    	  map15.put("testname", "Mock Test-15");
    	  map15.put("score", mdb.getSMock15());
    	  fillMaps.add(map15);
        
       SimpleAdapter adapter = new SimpleAdapter(this, fillMaps, R.layout.item, from, to);
       lv.setAdapter(adapter);
        mSoundManager = new SoundManager();
        mSoundManager.initSounds(getBaseContext());
        mSoundManager.addSound(1, R.raw.button9);
        mSoundManager.addSound(2, R.raw.magic);
        mdb.close();
        findViewById(R.id.home).setOnClickListener(new View.OnClickListener() 
        {
    		public void onClick(View v) 
    		{
    			mSoundManager.playSound(1);
    			Intent i1 = new Intent(Results.this,Main_Screen.class);
    			startActivity(i1);
    			finish();
    		}
    	});
        
    }
}

        