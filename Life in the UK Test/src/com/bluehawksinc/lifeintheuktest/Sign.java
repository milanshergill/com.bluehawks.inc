package com.bluehawksinc.lifeintheuktest;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;



public class Sign extends Activity

{
    /** Called when the activity is first created. */
    private TextView Op1;
    private TextView quizQuestion;
    public static final int END = 0;
    public static final int SHOW = 1;
    private int index;
    public static int signCount;
   public int start,end;
    DataBaseHelper3 db = new DataBaseHelper3(this);
   
  //  NotesDbAdapter mdb = new NotesDbAdapter(this);
    Cursor c1;
    Cursor c3;
     private SoundManager mSoundManager;
    
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
       
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.sign);
       getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titleset);
    
        mSoundManager = new SoundManager();
        mSoundManager.initSounds(getBaseContext());
        mSoundManager.addSound(1, R.raw.magic);
        mSoundManager.addSound(2, R.raw.button9);
        
        final ImageView Sound_Button = (ImageView)findViewById(R.id.home);
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
        
       // showDialog(1);
     
        try 
        {
            db.createDataBase();
        } 
        catch (IOException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
        switch(0)
        {
        case 0:
        	start = 60;
        	end = 64;
        	break;
        	
        case 1:
        	start = 1;
        	end = 5;
        	break;
        	
        case 2:
        	start = 6;
        	end = 10;
        	break;
        	
        case 3:
        	start = 11;
        	end = 15;
        	break;
        	
        case 4:
        	start = 16;
        	end = 20;
        	break;
        	
        case 5:
        	start = 21;
        	end = 25;
        	break;
        	
        case 6:
        	start = 26;
        	end = 27;
        	break;
        	
        case 7:
        	start = 28;
        	end = 32;
        	break;
        	
        case 8:
        	start = 33;
        	end = 34;
        	break;
        	
        case 9:
        	start = 35;
        	end = 39;
        	break;
        	
        case 10:
        	start = 40;
        	end = 44;
        	break;
        	
        case 11:
        	start = 45;
        	end = 49;
        	break;
        	
        case 12:
        	start = 50;
        	end = 54;
        	break;
        	
        case 13:
        	start = 55;
        	end = 59;
        	break;
        }
        
        signCount = (start - 1);
        if (savedInstanceState != null)
        {
     	   signCount = savedInstanceState.getInt("MyInt");
     	   start = savedInstanceState.getInt("MyStart");
     	   end = savedInstanceState.getInt("MyEnd");

        }
        quizQuestion = (TextView) findViewById(R.id.question);
        
        //Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.hyperspace_out);
        //quizQuestion.startAnimation(hyperspaceJumpAnimation);
       // Animation hyperspaceJumpAnimation6 = AnimationUtils.loadAnimation(this, R.anim.slide_left);
       // radioGroup.startAnimation(hyperspaceJumpAnimation6);
        Op1 = (TextView) findViewById(R.id.Op1);
        
        displayQuestion();      
        
        /*Displays the next options and sets listener on next button*/
        Button btnNext = (Button) findViewById(R.id.btnNext);
      //  Animation hyperspaceJumpAnimation2 = AnimationUtils.loadAnimation(this, R.anim.slide_right);
       // btnNext.startAnimation(hyperspaceJumpAnimation2);
        btnNext.setOnClickListener(Listener);
        
        
        /*Saves the selected values in the database on the End button*/
       ImageView btnEnd = (ImageView) findViewById(R.id.btnEnd);
        btnEnd.setOnClickListener(Listener);
        Button btnPrev = (Button) findViewById(R.id.btnPrev);
        btnPrev.setOnClickListener(Listener);
        
    }
        
    protected Dialog onCreateDialog(int id)
    {
        Dialog dialog = null;
        switch(id) 
        {
        case END:
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to exit?")
                  .setCancelable(true)
                  .setPositiveButton("Yes", new DialogInterface.OnClickListener() 
                  {
                      public void onClick(DialogInterface dialog, int id)
                      {
                     
                      //mSoundManager.playSound(1);
                      // Add your save code here !!!!
                     
                      Intent i1 = new Intent(Sign.this,Main_Screen.class);
                      startActivity(i1);
                      finish();
                      }
                  })
                  .setNegativeButton("No", new DialogInterface.OnClickListener()
                  {
                      public void onClick(DialogInterface dialog, int id) 
                      {
                      mSoundManager.playSound(2);
                           dialog.cancel();
                      }
                  });
            AlertDialog alert = builder.create();
            dialog = alert;
               break;
           
           case SHOW:
               AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
               builder1.setMessage("You successfully completed all the required questions!!")
                     .setCancelable(false)
                     .setPositiveButton("Restart", new DialogInterface.OnClickListener() 
                     {
                         public void onClick(DialogInterface dialog, int id)
                         {
                        	
                         //mSoundManager.playSound(1);
                         Intent i1 = new Intent(Sign.this,SpecialInfo.class);
                         startActivity(i1);
                         finish();
                         }
                     })
                     .setNegativeButton("Menu", new DialogInterface.OnClickListener()
                     {
                         public void onClick(DialogInterface dialog, int id) 
                         {
                        	 
                         //mSoundManager.playSound(1);
                             Intent i2 = new Intent(Sign.this,Main_Screen.class);
                     startActivity(i2);
                     finish();

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
    /*Called when button is clicked*/
   private View.OnClickListener Listener = new View.OnClickListener() 
    {
        
        @Override
        public void onClick(View v) 
        {
            switch (v.getId())
            {
            case(R.id.btnNext):
                mSoundManager.playSound(2);
                   displayQuestion();
                   break;
                   
            case(R.id.btnPrev):
                mSoundManager.playSound(2);
                   prevQuestion();
                   break;
                   
            case(R.id.btnEnd):
                mSoundManager.playSound(2);
                showDialog(END);        
                break;
                
            default:
                mSoundManager.playSound(2);
                Intent i2 = new Intent(Sign.this,Main_Screen.class);
                startActivity(i2);
                finish();            
            
            }
        }
        
     };
    
    
    void displayQuestion()
    {
        if(signCount<end)
        {
        //Fetching data quiz data and incrementing on each click
        try 
        {
            ++signCount;
        //Random random = new Random();
        index = signCount;
        
        c1 = db.getMock2_ques(index);   
        c3 = db.getMock2_Corr(index);
        
        
        
      //  byte[] bb = c1.getBlob(0);
        
        quizQuestion.setText(c1.getString(0));
        Op1.setText("	"+c3.getString(0));
        }
        
        catch (Exception ex)
        {
            displayQuestion();
        }
       }
        else 
        {
            showDialog(SHOW);
        }
    }
     void prevQuestion()
        {
            if(signCount<=end && signCount>start)
            {
            //Fetching data quiz data and incrementing on each click
            try 
            {
            --signCount;
            index = signCount;
            
            c1 = db.getMock2_ques(index);  
            c3 = db.getMock2_Corr(index);
            
            //signCount++;
            
           // byte[] bb = c1.getBlob(0);
            //ByteArrayInputStream b = new  ByteArrayInputStream(bb);
            //Drawable drw = Drawable.createFromStream(b, "image");
            //quizQuestion.setImageDrawable(drw);
            quizQuestion.setText(c1.getString(0));
            Op1.setText("	"+c3.getString(0));
            }
            
            catch (Exception ex)
            {
                displayQuestion();
            }
           }
        }
     @Override
     public void onSaveInstanceState(Bundle savedInstanceState) {
       // Save UI state changes to the savedInstanceState.
       // This bundle will be passed to onCreate if the process is
       // killed and restarted.
       savedInstanceState.putInt("MyInt", signCount-1);
       savedInstanceState.putInt("MyStart", start);
       savedInstanceState.putInt("MyEnd", end);
      
       // etc.
       super.onSaveInstanceState(savedInstanceState);
     }
     }
