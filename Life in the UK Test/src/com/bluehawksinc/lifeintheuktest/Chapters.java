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

public class  Chapters extends Activity 
{
	/** Called when the activity is first created. */

	 public static SoundManager mSoundManager;
	 public static String selected;
	 public static int prov;
	 NotesDbAdapter mdb = new NotesDbAdapter(this);
	
	//MediaPlayer music = MediaPlayer.create(Main_Screen.this, R.raw.twist);
	
	 DataBaseHelper db = new DataBaseHelper(this);
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.chapters);
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
    
     

       
       final ListView lv = (ListView)findViewById(R.id.list1);
       String[] from = new String[] {"ans","ques_image","progress","completed"};
       int[] to = new int[] {R.id.ans, R.id.ques_image,R.id.progress,R.id.percent};
       mdb.open();
       int Ch2Count = mdb.getChapter2();
       int Ch3Count = mdb.getChapter3();
       int Ch4Count = mdb.getChapter4();
       int Ch5Count = mdb.getChapter5();
       int Ch6Count = mdb.getChapter6();
       mdb.close();
        
               // prepare the list of all records
       List<Map<String, Object>> fillMaps = new ArrayList<Map<String, Object>>();
       
       Map<String,Object> map0 = new HashMap<String, Object>();  
       Bitmap government = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress0);
       String percent = "0%";
      
       
		if(Ch2Count < 11)
		{
			 government = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress0);
			 percent = "0%";
		}
		else if(Ch2Count >= 11 && Ch2Count <= 22)
		{
			 government = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress1);
			 percent = "8%";
		} 
		else if(Ch2Count > 22 && Ch2Count <= 34)
		{
			government = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress2);
			percent = "16%";
		}
		else if(Ch2Count > 34 && Ch2Count <=45)
		{
			 government = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress3);
			 percent = "25%";
		}
		else if(Ch2Count > 45 && Ch2Count <=56)
		{
			 government = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress4);
			 percent = "33%";
		}
		else if(Ch2Count > 56 && Ch2Count <=68)
		{
			 government = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress5);
			 percent = "41%";
		}
		else if(Ch2Count > 68 && Ch2Count <=79)
		{
			 government = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress6);
			 percent = "50%";
		}
		else if(Ch2Count > 79 && Ch2Count <=90)
		{
			 government = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress7);
			 percent = "58%";
		}
		else if(Ch2Count > 90 && Ch2Count <=101)
		{
			 government = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress8);
			 percent = "66%";
		}
		else if(Ch2Count > 101 && Ch2Count <=112)
		{
			 government = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress9);
			 percent = "75%";
		}
		else if(Ch2Count > 112 && Ch2Count <=123)
		{
			 government = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress10);
			 percent = "87%";
		}
		else if(Ch2Count > 123 && Ch2Count <=135)
		{
			 government = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress11);
			 percent = "91%";
		}
		else if(Ch2Count > 135)
		{
			 government = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress12);
			 percent = "100%";
		}
		String name0 = "Chapter 2"; 
		Bitmap arrow = BitmapFactory.decodeResource(this.getResources(),R.drawable.arrow);
        map0.put("progress",government);
        map0.put("ans",name0);
        map0.put("ques_image", arrow);
        map0.put("completed", percent);
        fillMaps.add(map0);
       		
       		Map<String,Object> map = new HashMap<String, Object>();       
    		Bitmap alberta = BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.arrow);
    		String name1 = "Chapter 3";   
    		Bitmap progress1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress0);
    		String percent1 = "0%";

    		if(Ch3Count < 10)
    		{
    			 progress1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress0);
    			 percent1 = "0%";
    		}
    		else if(Ch3Count >= 10 && Ch3Count <= 21)
    		{
    			 progress1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress1);
    			 percent1 = "8%";
    		} 
    		else if(Ch3Count > 21 && Ch3Count <= 32)
    		{
    			progress1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress2);
    			percent1 = "16%";
    		}
    		else if(Ch3Count > 32 && Ch3Count <=43)
    		{
    			 progress1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress3);
    			 percent1 = "25%";
    		}
    		else if(Ch3Count > 43 && Ch3Count <=54)
    		{
    			 progress1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress4);
    			 percent1 = "33%";
    		}
    		else if(Ch3Count > 54 && Ch3Count <=65)
    		{
    			 progress1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress5);
    			 percent1 = "41%";
    		}
    		else if(Ch3Count > 65 && Ch3Count <=72)
    		{
    			 progress1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress6);
    			 percent1 = "50%";
    		}
    		else if(Ch3Count > 72 && Ch3Count <=84)
    		{
    			 progress1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress7);
    			 percent1 = "58%";
    		}
    		else if(Ch3Count > 84 && Ch3Count <=93)
    		{
    			 progress1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress8);
    			 percent1 = "66%";
    		}
    		else if(Ch3Count > 93 && Ch3Count <=105)
    		{
    			 progress1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress9);
    			 percent1 = "75%";
    		}
    		else if(Ch3Count > 105 && Ch3Count <=115)
    		{
    			 progress1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress10);
    			 percent1 = "87%";
    		}
    		else if(Ch3Count > 115 && Ch3Count <=128)
    		{
    			 progress1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress11);
    			 percent1 = "91%";
    		}
    		else if(Ch3Count > 128)
    		{
    			 progress1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress12);
    			 percent1 = "100%";
    		}
    		map.put("completed", percent1);
            map.put("progress", progress1);     	 
            map.put("ques_image",alberta);
            map.put("ans",name1);
            fillMaps.add(map);
            
            Map<String,Object> map1 = new HashMap<String, Object>();     
            Bitmap british = BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.arrow);
    		String name2 = "Chapter 4";  
    		Bitmap progress2 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress0);
    		String percent2 = "0%";
    		if(Ch4Count< 18)
    		{
    			 progress2 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress0);
    			 percent2 = "0%";
    		}
    		else if(Ch4Count>= 18 && Ch4Count<= 36)
    		{
    			 progress2 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress1);
    			 percent2 = "8%";
    		} 
    		else if(Ch4Count> 36 && Ch4Count<= 54)
    		{
    			progress2 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress2);
    			percent2 = "16%";
    		}
    		else if(Ch4Count> 54 && Ch4Count<=73)
    		{
    			 progress2 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress3);
    			 percent2 = "25%";
    		}
    		else if(Ch4Count> 73 && Ch4Count<=91)
    		{
    			 progress2 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress4);
    			 percent2 = "33%";
    		}
    		else if(Ch4Count> 91 && Ch4Count<=109)
    		{
    			 progress2 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress5);
    			 percent2 = "41%";
    		}
    		else if(Ch4Count> 109 && Ch4Count<=127)
    		{
    			 progress2 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress6);
    			 percent2 = "50%";
    		}
    		else if(Ch4Count> 127 && Ch4Count<=146)
    		{
    			 progress2 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress7);
    			 percent2 = "58%";
    		}
    		else if(Ch4Count> 146 && Ch4Count<=164)
    		{
    			 progress2 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress8);
    			 percent2 = "66%";
    		}
    		else if(Ch4Count> 164 && Ch4Count<=182)
    		{
    			 progress2 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress9);
    			 percent2 = "75%";
    		}
    		else if(Ch4Count> 182 && Ch4Count<=200)
    		{
    			 progress2 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress10);
    			 percent2 = "87%";
    		}
    		else if(Ch4Count> 200 && Ch4Count<=217)
    		{
    			 progress2 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress11);
    			 percent2 = "91%";
    		}
    		else if(Ch4Count> 217)
    		{
    			 progress2 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress12);
    			 percent2 = "100%";
    		}
    		map1.put("completed", percent2);
            map1.put("progress", progress2); 
            map1.put("ques_image",british);
            map1.put("ans",name2);
            fillMaps.add(map1);
            
            Map<String,Object> map2 = new HashMap<String, Object>();
            Bitmap manitoba = BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.arrow);
    		String name3 = "Chapter 5";  
    		Bitmap progress3 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress0);
    		String percent3 = "0%";
    		if(Ch5Count< 18)
    		{
    			 progress3 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress0);
    			 percent3 = "0%";
    		}
    		else if(Ch5Count>= 18 && Ch5Count<= 35)
    		{
    			 progress3 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress1);
    			 percent3 = "8%";
    		} 
    		else if(Ch5Count> 35 && Ch5Count<= 53)
    		{
    			progress3 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress2);
    			percent3 = "16%";
    		}
    		else if(Ch5Count> 53 && Ch5Count<=71)
    		{
    			 progress3 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress3);
    			 percent3 = "25%";
    		}
    		else if(Ch5Count> 71 && Ch5Count<=89)
    		{
    			 progress3 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress4);
    			 percent3 = "33%";
    		}
    		else if(Ch5Count> 89 && Ch5Count<=107)
    		{
    			 progress3 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress5);
    			 percent3 = "41%";
    		}
    		else if(Ch5Count> 107 && Ch5Count<=124)
    		{
    			 progress3 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress6);
    			 percent3 = "50%";
    		}
    		else if(Ch5Count> 124 && Ch5Count<=142)
    		{
    			 progress3 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress7);
    			 percent3 = "58%";
    		}
    		else if(Ch5Count> 142 && Ch5Count<=160)
    		{
    			 progress3 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress8);
    			 percent3 = "66%";
    		}
    		else if(Ch5Count> 160 && Ch5Count<=178)
    		{
    			 progress3 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress9);
    			 percent3 = "75%";
    		}
    		else if(Ch5Count> 178 && Ch5Count<=196)
    		{
    			 progress3 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress10);
    			 percent3 = "87%";
    		}
    		else if(Ch5Count> 196 && Ch5Count<=212)
    		{
    			 progress3 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress11);
    			 percent3 = "91%";
    		}
    		else if(Ch5Count> 212)
    		{
    			 progress3 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress12);
    			 percent3 = "100%";
    		}
    		map2.put("completed", percent3);
            map2.put("progress", progress3); 
            map2.put("ques_image",manitoba);
            map2.put("ans",name3);
            fillMaps.add(map2);
            
            Map<String,Object> map3 = new HashMap<String, Object>();
            Bitmap brunswick = BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.arrow);
    		String name4 = "Chapter 6";
    		Bitmap progress4 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress0);
    		String percent4 = "0%";
    		if(Ch6Count< 10)
    		{
    			 progress4 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress0);
    			 percent4 = "0%";
    		}
    		else if(Ch6Count>= 10 && Ch6Count<= 20)
    		{
    			 progress4 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress1);
    			 percent4 = "8%";
    		} 
    		else if(Ch6Count> 20 && Ch6Count<= 30)
    		{
    			progress4 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress2);
    			percent4 = "16%";
    		}
    		else if(Ch6Count> 30 && Ch6Count<=40)
    		{
    			 progress4 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress4);
    			 percent4 = "25%";
    		}
    		else if(Ch6Count> 40 && Ch6Count<=50)
    		{
    			 progress4 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress4);
    			 percent4 = "33%";
    		}
    		else if(Ch6Count> 50 && Ch6Count<=61)
    		{
    			 progress4 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress5);
    			 percent4 = "41%";
    		}
    		else if(Ch6Count> 61 && Ch6Count<=71)
    		{
    			 progress4 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress6);
    			 percent4 = "50%";
    		}
    		else if(Ch6Count> 71 && Ch6Count<=81)
    		{
    			 progress4 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress7);
    			 percent4 = "58%";
    		}
    		else if(Ch6Count> 81 && Ch6Count<=91)
    		{
    			 progress4 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress8);
    			 percent4 = "66%";
    		}
    		else if(Ch6Count> 91 && Ch6Count<=101)
    		{
    			 progress4 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress9);
    			 percent4 = "75%";
    		}
    		else if(Ch6Count> 101 && Ch6Count<=111)
    		{
    			 progress4 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress10);
    			 percent4 = "87%";
    		}
    		else if(Ch6Count> 111 && Ch6Count<=120)
    		{
    			 progress4 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress11);
    			 percent4 = "91%";
    		}
    		else if(Ch6Count> 120)
    		{
    			 progress4 = BitmapFactory.decodeResource(this.getResources(),R.drawable.progress12);
    			 percent4 = "100%";
    		}
    		map3.put("completed", percent4);
            map3.put("progress", progress4); 
            map3.put("ques_image",brunswick);
            map3.put("ans",name4);
            fillMaps.add(map3);
            
                
        SimpleAdapter adapter1 = new SimpleAdapter(this, fillMaps, R.layout.item3, from, to);
        adapter1.setViewBinder(new MyViewBinder());
        lv.setAdapter(adapter1);
       // spinner.setAdapter(adapter1);
       
       
	lv.setOnItemClickListener(List);
	
       
       
        db.close();
        
        findViewById(R.id.home).setOnClickListener(new View.OnClickListener() 
        {
    		public void onClick(View v) 
    		{
    			mSoundManager.playSound(1);
    			Intent i1 = new Intent(Chapters.this,Main_Screen.class);
    			startActivity(i1);
    			finish();
    		}
    	});
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() 
        {
    		public void onClick(View v) 
    		{
    			mSoundManager.playSound(1);
    			Intent i1 = new Intent(Chapters.this,QuizInfo.class);
    			startActivity(i1);
    			finish();
    		}
    	});
        
        
    }
   public AdapterView.OnItemClickListener List = new AdapterView.OnItemClickListener()  
	{
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,long id)                               
          {                                                                                                                                                                           
                if(position == 0)                                                       
                {                                                                                                           
                   Intent i = new Intent(Chapters.this, Chapter2.class);                                                                                        
                   startActivity(i);  
                   finish();
                }
                
                else if(position == 1)
                {
                	Intent i = new Intent(Chapters.this, Chapter3.class);                                                                                        
                    startActivity(i);
                    finish();
                }
                else if(position == 2)
                {
                	Intent i = new Intent(Chapters.this, Chapter4.class);                                                                                        
                    startActivity(i);
                    finish();
                }
                else if(position == 3)
                {
                	Intent i = new Intent(Chapters.this, Chapter5.class);                                                                                        
                    startActivity(i);
                    finish();
                }
                else if(position == 4)
                {
                	Intent i = new Intent(Chapters.this, Chapter6.class);                                                                                        
                    startActivity(i);
                    finish();
                }
             }
       };
    
  /*  public  OnItemSelectedListener List = new OnItemSelectedListener() 
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
    }; */

}

     