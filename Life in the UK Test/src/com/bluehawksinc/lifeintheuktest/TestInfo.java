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
import android.widget.TextView;


public class  TestInfo extends Activity 
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
       TextView heading = (TextView)findViewById(R.id.text);
       ListView lv = (ListView)findViewById(R.id.list1);
       String[] from = new String[] {"infoques", "infoans"};
       int[] to = new int[] {R.id.infoques, R.id.infoans};
        
       heading.setText("Test Information");
               // prepare the list of all records
       List<Map<String, Object>> fillMaps = new ArrayList<Map<String, Object>>();
       
    	 String ques1 = "Ques) "+getString(R.string.note24);
    	 String ans1 ="Ans) "+getString(R.string.note25);
    	 Map<String,Object> map1 = new HashMap<String, Object>();
         map1.put("infoques",ques1);
         map1.put("infoans",ans1);
         fillMaps.add(map1);
         
         String ques2 = "Ques) "+getString(R.string.note14);
    	 String ans2 ="Ans) "+getString(R.string.note15);
    	 Map<String,Object> map2 = new HashMap<String, Object>();
         map2.put("infoques",ques2);
         map2.put("infoans",ans2);
         fillMaps.add(map2);
         
         String ques3 = "Ques) "+getString(R.string.note20);
    	 String ans3 ="Ans) "+getString(R.string.note21);
    	 Map<String,Object> map3 = new HashMap<String, Object>();
         map3.put("infoques",ques3);
         map3.put("infoans",ans3);
         fillMaps.add(map3);
         
         String ques4 = "Ques) "+getString(R.string.note0);
    	 String ans4 ="Ans) "+getString(R.string.note1);
    	 Map<String,Object> map4 = new HashMap<String, Object>();
         map4.put("infoques",ques4);
         map4.put("infoans",ans4);
         fillMaps.add(map4);
         
         String ques5 = "Ques) "+getString(R.string.note2);
    	 String ans5 ="Ans) "+getString(R.string.note3);
    	 Map<String,Object> map5 = new HashMap<String, Object>();
         map5.put("infoques",ques5);
         map5.put("infoans",ans5);
         fillMaps.add(map5);
         
         String ques6 = "Ques) "+getString(R.string.note4);
    	 String ans6 ="Ans) "+getString(R.string.note5);
    	 Map<String,Object> map6 = new HashMap<String, Object>();
         map6.put("infoques",ques6);
         map6.put("infoans",ans6);
         fillMaps.add(map6);
         
         String ques7 = "Ques) "+getString(R.string.note6);
    	 String ans7 ="Ans) "+getString(R.string.note7);
    	 Map<String,Object> map7 = new HashMap<String, Object>();
         map7.put("infoques",ques7);
         map7.put("infoans",ans7);
         fillMaps.add(map7);
         
         String ques8 = "Ques) "+getString(R.string.note8);
    	 String ans8 ="Ans) "+getString(R.string.note9);
    	 Map<String,Object> map8 = new HashMap<String, Object>();
         map8.put("infoques",ques8);
         map8.put("infoans",ans8);
         fillMaps.add(map8);
         
         String ques9 = "Ques) "+getString(R.string.note10);
    	 String ans9 ="Ans) "+getString(R.string.note11);
    	 Map<String,Object> map9 = new HashMap<String, Object>();
         map9.put("infoques",ques9);
         map9.put("infoans",ans9);
         fillMaps.add(map9);
         
         String ques10 = "Ques) "+getString(R.string.note12);
    	 String ans10 ="Ans) "+getString(R.string.note13);
    	 Map<String,Object> map10 = new HashMap<String, Object>();
         map10.put("infoques",ques10);
         map10.put("infoans",ans10);
         fillMaps.add(map10);
         
         String ques11 = "Ques) "+getString(R.string.note16);
    	 String ans11 ="Ans) "+getString(R.string.note17);
    	 Map<String,Object> map11 = new HashMap<String, Object>();
         map11.put("infoques",ques11);
         map11.put("infoans",ans11);
         fillMaps.add(map11);
         
         String ques12 = "Ques) "+getString(R.string.note18);
    	 String ans12 ="Ans) "+getString(R.string.note19);
    	 Map<String,Object> map12 = new HashMap<String, Object>();
         map12.put("infoques",ques12);
         map12.put("infoans",ans12);
         fillMaps.add(map12);
         
         String ques13 = "Ques) "+getString(R.string.note22);
    	 String ans13 ="Ans) "+getString(R.string.note23);
    	 Map<String,Object> map13 = new HashMap<String, Object>();
         map13.put("infoques",ques13);
         map13.put("infoans",ans13);
         fillMaps.add(map13);
         
       SimpleAdapter adapter = new SimpleAdapter(this, fillMaps, R.layout.itemtestinfo, from, to);
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
    			Intent i1 = new Intent(TestInfo.this,Main_Screen.class);
    			startActivity(i1);
    			finish();
    		}
    	});
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() 
        {
    		public void onClick(View v) 
    		{
    			mSoundManager.playSound(1);
    			Intent i1 = new Intent(TestInfo.this,Setting.class);
    			startActivity(i1);
    			finish();
    		}
    	});
    }
}

        