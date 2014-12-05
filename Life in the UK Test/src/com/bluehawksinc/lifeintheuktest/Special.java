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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class  Special extends Activity 
{
	/** Called when the activity is first created. */

	 public static SoundManager mSoundManager;
	 //public static String selected;
	 //public static int prov;
	
	//MediaPlayer music = MediaPlayer.create(Main_Screen.this, R.raw.twist);
	
	 DataBaseHelper db = new DataBaseHelper(this);
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.mock);
       getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titleback);
       try 
       {
       	db.createDataBase();
       } 
       catch (IOException e) 
       {
       	// TODO Auto-generated catch block
       	e.printStackTrace();
       }
       mSoundManager = new SoundManager();
       mSoundManager.initSounds(getBaseContext());
       mSoundManager.addSound(1, R.raw.button9);
       mSoundManager.addSound(2, R.raw.magic);
       
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
    
      // Spinner spinner = (Spinner) findViewById(R.id.spinner);
       
     /*  ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
               this, R.array.spinner_array, android.R.layout.simple_spinner_item);
       adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/
      
       //spinner.setOnItemSelectedListener(List);

       
      final ListView lv = (ListView)findViewById(R.id.list1);
       String[] from = new String[] {"arrow", "mockname"};
       int[] to = new int[] {R.id.arrow, R.id.mockname};
        
               // prepare the list of all records
       List<Map<String, Object>> fillMaps = new ArrayList<Map<String, Object>>();
       
       Map<String,Object> map0 = new HashMap<String, Object>();       
		Bitmap government = BitmapFactory.decodeResource(this.getResources(),
               R.drawable.arrow);
		String name0 = "Spl. Ch-2 Quiz";        	 
       map0.put("arrow",government);
       map0.put("mockname",name0);
       fillMaps.add(map0);
       		
       		Map<String,Object> map = new HashMap<String, Object>();       
    		Bitmap alberta = BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.arrow);
    		String name1 = "Spl. Ch-3 Quiz";        	 
            map.put("arrow",alberta);
            map.put("mockname",name1);
            fillMaps.add(map);
            
            Map<String,Object> map1 = new HashMap<String, Object>();     
            Bitmap british = BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.arrow);
    		String name2 = "Spl. Ch-4 Quiz";        	 
            map1.put("arrow",british);
            map1.put("mockname",name2);
            fillMaps.add(map1);
            
            Map<String,Object> map2 = new HashMap<String, Object>();
            Bitmap manitoba = BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.arrow);
    		String name3 = "Spl. Ch-5 Quiz";        	 
            map2.put("arrow",manitoba);
            map2.put("mockname",name3);
            fillMaps.add(map2);
            
            Map<String,Object> map3 = new HashMap<String, Object>();
            Bitmap brunswick = BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.arrow);
    		String name4 = "Spl. Ch-6 Quiz";        	 
            map3.put("arrow",brunswick);
            map3.put("mockname",name4);
            fillMaps.add(map3);
            
            Map<String,Object> map4 = new HashMap<String, Object>();
            Bitmap newfoundland = BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.arrow);
    		String name5 = "Spl. All Categories Quiz";        	 
            map4.put("arrow",newfoundland);
            map4.put("mockname",name5);
            fillMaps.add(map4);
            
              	
       
       SimpleAdapter adapter1 = new SimpleAdapter(this, fillMaps, R.layout.item4, from, to);
        adapter1.setViewBinder(new MyViewBinder());
       // adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       lv.setAdapter(adapter1);
       // spinner.setAdapter(adapter1);
       
       
	lv.setOnItemClickListener(List);
	
       
       
        db.close();
        
        findViewById(R.id.home).setOnClickListener(new View.OnClickListener() 
        {
    		public void onClick(View v) 
    		{
    			mSoundManager.playSound(1);
    			Intent i1 = new Intent(Special.this,Main_Screen.class);
    			startActivity(i1);
    			finish();
    		}
    	});
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() 
        {
    		public void onClick(View v) 
    		{
    			mSoundManager.playSound(1);
    			Intent i1 = new Intent(Special.this,SpecialInfo.class);
    			startActivity(i1);
    			finish();
    		}
    	});
        
        
    }
    public AdapterView.OnItemClickListener List = new AdapterView.OnItemClickListener()  
	{
		public void onItemClick(AdapterView<?> arg0, View arg1, int pos,long id)                               
          {                                                                                                                                                                           
			/*if(pos == 0)
	          {
				mSoundManager.playSound(1);
    			Intent i1 = new Intent(Special.this, SpCh2.class);
    			startActivity(i1);
    			finish();
	          }
	          else if(pos == 1)
	          {
	        	  mSoundManager.playSound(1);
	    			Intent i1 = new Intent(Special.this, SpCh3.class);
	    			startActivity(i1);
	    			finish();
	          }
	          else if(pos == 2)
	          {
	        	  mSoundManager.playSound(1);
	    			Intent i1 = new Intent(Special.this, SpCh4.class);
	    			startActivity(i1);
	    			finish();
	          }
	          else if(pos == 3)
	          {
	        	  mSoundManager.playSound(1);
	    			Intent i1 = new Intent(Special.this, SpCh5.class);
	    			startActivity(i1);
	    			finish();
	          }
	          else if(pos == 4)
	          {
	        	  mSoundManager.playSound(1);
	    			Intent i1 = new Intent(Special.this, SpCh6.class);
	    			startActivity(i1);
	    			finish();
	          }
	          else if(pos == 5)
	          {
	        	  mSoundManager.playSound(1);
	    			Intent i1 = new Intent(Special.this, SpAll.class);
	    			startActivity(i1);
	    			finish();
	          }*/
          }
       };
    
   /* public  OnItemSelectedListener List = new OnItemSelectedListener() 
    {
    	public void onItemSelected(AdapterView<?> parent,View view, int pos, long id) 
    	{
          selected = parent.getItemAtPosition(pos).toString().trim();
          if(pos == 0)
          {
        	  prov = 0;
          }
          else if(pos == 1)
          {
        	  prov = 1;
          }
          else if(pos == 2)
          {
        	  prov = 2;
          }
          else if(pos == 3)
          {
        	  prov = 3;
          }
          else if(pos == 4)
          {
        	  prov = 4;
          }
          else if(pos == 5)
          {
        	  prov = 5;
          }
          else if(pos == 6)
          {
        	  prov = 6;
          }
          else if(pos == 7)
          {
        	  prov = 7;
          }
          else if(pos == 8)
          {
        	  prov = 8;
          }
          else if(pos == 9)
          {
        	  prov = 9;
          }
          else if(pos == 10)
          {
        	  prov = 10;
          }
          else if(pos == 11)
          {
        	  prov = 11;
          }
          else if(pos == 12)
          {
        	  prov = 12;
          }
          else if(pos == 13)
          {
        	  prov = 13;
          }
        }

        public void onNothingSelected(AdapterView parent) 
        {
          // Do nothing.
        }
    };*/

}

     