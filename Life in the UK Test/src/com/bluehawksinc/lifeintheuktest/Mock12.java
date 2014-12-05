package com.bluehawksinc.lifeintheuktest;

import java.io.IOException;
import java.util.ArrayList;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Mock12 extends Activity
{
	private InterstitialAd mInterstitial;
/** Called when the activity is first created. **/
private RadioGroup radioGroup;
private RadioButton Op1;
private RadioButton Op2;
private RadioButton Op3;
private RadioButton Op4;
//private ImageView quizQuestion;
private TextView ques;
protected static int score=0;
public static final int END = 0;
public static ArrayList<Integer> incorrect = new ArrayList<Integer>();


public int index;
public int count=-1;
final DataBaseHelper db = new DataBaseHelper(this);
NotesDbAdapter mdb = new NotesDbAdapter(this);

Cursor c1;
Cursor c21;
Cursor c22;
Cursor c23;
Cursor c24;
Cursor c3;
private SoundManager mSoundManager;
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
       
    	super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.test);
       getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titleset);
       //incorrect.clear(); 
       final AdView mAdView;
		mAdView = (AdView) findViewById(R.id.adView);
		mAdView.loadAd(new AdRequest.Builder().build());
	       //mInterstitial add stuff
	       mInterstitial = new InterstitialAd(this);
	       mInterstitial.setAdUnitId(getResources().getString(R.string.ad_unit_interstitial_id));
	       mInterstitial.loadAd(new AdRequest.Builder().build());
       if (savedInstanceState != null)
       {
    	   count = savedInstanceState.getInt("MyInt");
    	   score = savedInstanceState.getInt("MyScore");
    	   incorrect = savedInstanceState.getIntegerArrayList("MyArray");

       }
       else
       {
    	   incorrect.clear();
       }
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
        
        //mSoundManager.addSound(3, R.raw.end);
        try 
        {
         db.createDataBase();
        } 
        catch (IOException e) 
        {
         // TODO Auto-generated catch block
         e.printStackTrace();
        }
        
       // quizQuestion = (ImageView) findViewById(R.id.question);
        ques=(TextView)findViewById(R.id.ques);
        
        //Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.hyperspace_out);
        //quizQuestion.startAnimation(hyperspaceJumpAnimation);
        radioGroup = (RadioGroup) findViewById(R.id.group);
        radioGroup.setOnCheckedChangeListener(Listener2);
       // Animation hyperspaceJumpAnimation6 = AnimationUtils.loadAnimation(this, R.anim.slide_left);
       // radioGroup.startAnimation(hyperspaceJumpAnimation6);
        Op1 = (RadioButton) findViewById(R.id.Op1);
        Op2 = (RadioButton) findViewById(R.id.Op2);
        Op3 = (RadioButton) findViewById(R.id.Op3);
        Op4 = (RadioButton) findViewById(R.id.Op4);
        
        
        displayQuestion();      
        
       
        /*Saves the selected values in the database on the End button*/
       ImageView btnEnd = (ImageView) findViewById(R.id.btnEnd);
        btnEnd.setOnClickListener(Listener);
       // Animation hyperspaceJumpAnimation3 = AnimationUtils.loadAnimation(this, R.anim.push_left_out);
       // btnEnd.startAnimation(hyperspaceJumpAnimation3);
    }
    
    
    protected Dialog onCreateDialog(int id)
    {
        Dialog dialog = null;
        switch(id) 
        {
        case END:
         AlertDialog.Builder builder = new AlertDialog.Builder(this);
         builder.setMessage("Are you sure you want to exit?")
               .setCancelable(false)
               .setPositiveButton("Yes", new DialogInterface.OnClickListener() 
               {
                   public void onClick(DialogInterface dialog, int id)
                   {
                   //mSoundManager.playSound(1);
                   mdb.open();
                   mdb.updateMock12(score);
                   mdb.close();
                   Intent i1 = new Intent(Mock12.this,End12.class);
           startActivity(i1);
          finish();if (mInterstitial.isLoaded()) mInterstitial.show();
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
case(R.id.btnEnd):
mSoundManager.playSound(2);
showDialog(END);
break;
   default:
    mSoundManager.playSound(2);
    Intent i2 = new Intent(Mock12.this,End12.class);
    startActivity(i2);
   finish();if (mInterstitial.isLoaded()) mInterstitial.show();
}
}
};
 
 
RadioGroup.OnCheckedChangeListener Listener2 = new RadioGroup.OnCheckedChangeListener() 
{
@Override
public void onCheckedChanged(RadioGroup group, int checkedId) 
{
String answer = c3.getString(0);
switch (checkedId)
{
case(R.id.Op1):
if(answer.equals(Op1.getText()))
{
score++;
Op1.setChecked(false);
mSoundManager.playSound(2);
displayQuestion();
}
else
{
Op1.setChecked(false);
incorrect.add(numbers[count]);
mSoundManager.playSound(2);
displayQuestion();
}
break;
case(R.id.Op2):
if(answer.equals(Op2.getText()))
{
score++;
Op2.setChecked(false);
mSoundManager.playSound(2);
displayQuestion();
}
else
{
Op2.setChecked(false);
incorrect.add(numbers[count]);
mSoundManager.playSound(2);
displayQuestion();
}
break;
case(R.id.Op3):
if(answer.equals(Op3.getText()))
{
score++;
Op3.setChecked(false);
mSoundManager.playSound(2);
displayQuestion();
}
else
{
Op3.setChecked(false);
incorrect.add(numbers[count]);
mSoundManager.playSound(2);
displayQuestion();
}
break;
case(R.id.Op4):
if(answer.equals(Op4.getText()))
{
Op4.setChecked(false);
score++;
mSoundManager.playSound(2);
displayQuestion();
}
else
{
Op4.setChecked(false);
incorrect.add(numbers[count]);
mSoundManager.playSound(2);
displayQuestion();
}
break;

default:
displayQuestion();
  }
 
}
};
/* ArrayList<Integer> numbers = new ArrayList<Integer>();
{
  for(int i = 1; i <= 5; i++)
  {
    numbers.add(i);
  }
  Collections.shuffle(numbers);

}*/
int[] numbers = {25,124,39,52,212,175,265,153,183,403,341,278,384,361,595,609,487,644,614,738,736,808,725,741};
void displayQuestion()
{
if(count<numbers.length-1)
{
try 
{
++count;
index = numbers[count];
c1 = db.getMock2_ques(index);
c21 = db.getMock2_Op1(index);
c22 = db.getMock2_Op2(index);
c23 = db.getMock2_Op3(index);
c24 = db.getMock2_Op4(index);
c3 = db.getMock2_Corr(index);
//quizQuestion.setImageDrawable(null);
ques.setText(c1.getString(0));
Op1.setText(c21.getString(0));
Op2.setText(c22.getString(0));
Op3.setText(c23.getString(0));
Op4.setText(c24.getString(0));
}
catch (Exception ex)
{
displayQuestion();
}
  }
else
{
  mdb.open();
  
  mdb.updateMock12(0);
       mdb.updateMock12(score);
      score=0;
       mdb.close();
  Intent i3 = new Intent(Mock12.this,End12.class);
  startActivity(i3);
 finish();if (mInterstitial.isLoaded()) mInterstitial.show();
}
}
public void onSaveInstanceState(Bundle savedInstanceState) {
	  // Save UI state changes to the savedInstanceState.
	  // This bundle will be passed to onCreate if the process is
	  // killed and restarted.
	  savedInstanceState.putInt("MyInt", count-1);
	  savedInstanceState.putInt("MyScore", score);
	  savedInstanceState.putIntegerArrayList("MyArray", incorrect);
	  // etc.
	  super.onSaveInstanceState(savedInstanceState);
	}
}