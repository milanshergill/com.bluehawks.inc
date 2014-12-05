package com.bluehawksinc.lifeintheuktest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;


public class  ans1 extends Activity 
{
	/** Called when the activity is first created. */

	 public static SoundManager mSoundManager;
	
	//MediaPlayer music = MediaPlayer.create(Main_Screen.this, R.raw.twist);
	
	 DataBaseHelper db = new DataBaseHelper(this);
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.ans1);
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
       
       try 
       {
       	db.createDataBase();
       } 
       catch (IOException e) 
       {
       	// TODO Auto-generated catch block
       	e.printStackTrace();
       }
       
       ListView lv = (ListView)findViewById(R.id.list1);
       String[] from = new String[] {"ques_image", "ques", "ans"};
       int[] to = new int[] {R.id.ques_image, R.id.ques, R.id.ans};
        
               // prepare the list of all records
       List<Map<String, Object>> fillMaps = new ArrayList<Map<String, Object>>();
       
       for(int i = 0; i < Mock1.incorrect.size(); i++)
       {
    	 int value = (int) Mock1.incorrect.get(i);
    	 Cursor c1 = db.getMock2_ques(value);
    	 String ques1 ="Ques"+") "+c1.getString(0);
    	 Cursor c2 = db.getMock2_Corr(value);
    	 String ans1 ="Ans"+") "+c2.getString(0);
    	 Map<String,Object> map = new HashMap<String, Object>();
    	 map.put("ques_image", null);
         map.put("ques",ques1);
         map.put("ans",ans1);
         fillMaps.add(map);
    	 
       }
       SimpleAdapter adapter = new SimpleAdapter(this, fillMaps, R.layout.item1, from, to);
       adapter.setViewBinder(new MyViewBinder());
       lv.setAdapter(adapter);
       
       
       
        mSoundManager = new SoundManager();
        mSoundManager.initSounds(getBaseContext());
        mSoundManager.addSound(1, R.raw.button9);
        mSoundManager.addSound(2, R.raw.magic);
        db.close();
        
        findViewById(R.id.home).setOnClickListener(new View.OnClickListener() 
        {
    		public void onClick(View v) 
    		{
    			mSoundManager.playSound(1);
    			Intent i1 = new Intent(ans1.this,Main_Screen.class);
    			startActivity(i1);
    			finish();
    		}
    	});
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() 
        {
    		public void onClick(View v) 
    		{
    			mSoundManager.playSound(1);
    			Intent i1 = new Intent(ans1.this,End.class);
    			startActivity(i1);
    			finish();
    		}
    	});
        
    }
}

        