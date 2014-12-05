package com.bluehawksinc.lifeintheuktest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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

public class Mock_random extends Activity
{
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
                	   mdb.open();
                       mdb.updateSpecial(score);
                       mdb.close();
                   
                   Intent i1 = new Intent(Mock_random.this,End_random.class);
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
    Intent i2 = new Intent(Mock_random.this,End_random.class);
    startActivity(i2);
    finish();
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
incorrect.add(numbers.get(count));
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
incorrect.add(numbers.get(count));
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
incorrect.add(numbers.get(count));
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
incorrect.add(numbers.get(count));
mSoundManager.playSound(2);
displayQuestion();
}
break;

default:
displayQuestion();
  }
 
}
};
Integer[] arrayCh2 = {36,70,45,20,74,109,111,127,4,31,104,60,44,59,122,97,29,136,102,134,69,120,100,105,64,125,8,53,66,133,24,82,16,99,23,42,87,94,50,88,9,91,7,76,110,96,57,98,51,56,95,3,2,114,1,28,101,49,115,18,40,75,10,54,63,32,15,72};
ArrayList<Integer> array_Ch2 = new ArrayList<Integer>(Arrays.asList(arrayCh2));
{
  Collections.shuffle(array_Ch2);
}

Integer[] arrayCh3 = {203,225,206,163,161,155,252,193,145,151,249,228,159,250,142,139,226,168,196,158,216,143,186,237,154,246,262,242,189,214,190,147,160,256,254,217,255,232,180,220,156,185,231,148,222,141,140,213,234,248,238,229,149,200,261,266};
ArrayList<Integer> array_Ch3 = new ArrayList<Integer>(Arrays.asList(arrayCh3));
{ Collections.shuffle(array_Ch3);
}

Integer[] arrayCh4 = {370,368,446,387,322,476,475,440,290,332,438,419,458,390,396,292,299,478,409,473,346,468,305,465,333,393,462,410,413,469,425,402,481,453,275,358,405,354,353,350,313,385,365,320,274,287,321,376,400,359,369,407,345,331,325,404,454,304,318,483,456,301,389,285,295,334,367,460,328,459,401,379,276,286,388,466,343,398,294,373,406,430,363,302,452,371,378,463,316,433,444,414,418,372,308,474,375,431,314,279,381,416,360,437,315,485,327,366,310,297,455,267,383,326,450,269,461,364,289,280,399,335,434,277,319,291,347,415,284,451,309,436,323,352,281,329,408,424,448,348,271,339,336,447,312,344};
ArrayList<Integer> array_Ch4 = new ArrayList<Integer>(Arrays.asList(arrayCh4));
{
  Collections.shuffle(array_Ch4);
}

Integer[] arrayCh5 = {696,657,655,570,527,526,681,567,492,536,698,493,500,558,610,494,631,509,632,562,560,597,588,563,598,576,504,528,664,669,564,554,674,568,628,629,646,599,582,530,540,510,684,666,668,541,677,547,490,617,603,671,625,556,566,585,604,658,620,648,645,618,565,688,519,551,652,692,587,613,522,572,608,516,549,627,642,656,623,640,559,647,545,514,593,693,697,616,679,660,665,596,600,513,633,586,489,503,630,673,498,637,615,555,569,622,695,650,694,571,577,520,591,626,539,583,590,550,573,499,663,635,507,594,517,619,544,639,690,521,699,680,543,511,675,506,638,641,496,689,533};
ArrayList<Integer> array_Ch5 = new ArrayList<Integer>(Arrays.asList(arrayCh5));
{
  Collections.shuffle(array_Ch5);
}

Integer[] arrayCh6 = {771,734,763,732,742,811,757,759,740,818,810,702,768,720,785,727,794,787,756,706,739,774,781,762,805,752,718,761,723,755,815,754,792,776,726,751,716,791,796,772,713,721,782,769,820,766,728,746,814};
ArrayList<Integer> array_Ch6 = new ArrayList<Integer>(Arrays.asList(arrayCh6));
{
  Collections.shuffle(array_Ch6);
}



ArrayList<Integer> numbers = new ArrayList<Integer>();
{
  for(int i = 0; i < 24; i++)
  {
	  if(i<4)
	  {
		  numbers.add(array_Ch2.get(i));
	  }
	  else if(i>=4 && i<9)
	  {
		  numbers.add(array_Ch3.get(i));
	  }
	  else if(i>=9 && i<14)
	  {
		  numbers.add(array_Ch4.get(i));
	  }
	  else if(i>=14 && i<19)
	  {
		  numbers.add(array_Ch5.get(i));
	  }
	  else if(i>=19 && i <24)
	  {
		  numbers.add(array_Ch6.get(i));
	  }
    
  }
 // Collections.shuffle(numbers);
}

//int[] numbers = {121,132,123,134,125,136,127,138,129,140,131,122,133,124,135,126,137,128,139,130};
void displayQuestion()
{
if(count<numbers.size()-1)
{
try 
{
++count;
index = numbers.get(count);
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
{ mdb.open();

mdb.updateSpecial(0);
     mdb.updateSpecial(score);
    score=0;
     mdb.close();
  Intent i3 = new Intent(Mock_random.this,End_random.class);
  startActivity(i3);
  finish();
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