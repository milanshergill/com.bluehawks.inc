package com.example.testgestureapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	private GestureDataBase gestureDataBase;
	public static String GESTURES_RECORDED = "gesture recorded";
	public static IGestureRecognitionService recognitionService;
	TextView textView1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		textView1 = (TextView) findViewById(R.id.textView1);
		
		//setting up database for acceleration recording
		gestureDataBase = new GestureDataBase(this);
	    gestureDataBase.openWriteable();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
    protected void onResume() {
    	Intent gestureBindIntent = new Intent("com.example.testgestureapp.GESTURE_RECOGNIZER");
	    bindService(gestureBindIntent, gestureConnection, Context.BIND_AUTO_CREATE);
	    gestureDataBase.openWriteable();
	    super.onResume();
    }
    
    protected void onPause() {
    	gestureDataBase.close();
	    super.onPause();
    }

	public void onClickRecordGestures(View v) {
		Intent myIntent = new Intent(MainActivity.this, RecordGestures.class);
		MainActivity.this.startActivity(myIntent);
	}
	
	public void onClickTestGestures(View v) {
		Intent myIntent = new Intent(MainActivity.this, TestGestures.class);
		MainActivity.this.startActivity(myIntent);
	}
	
	public void onClickClearDatabase(View v) {
		AlertDialog diaBox = AskOption(this);
		diaBox.show();
	}
	
	private AlertDialog AskOption(final Context context)
	{
		AlertDialog clearDatabaseDialog = new AlertDialog.Builder(this) 
	    //set message and title
		.setTitle("Clear Database") 
		.setMessage("Do you want to clear database?")
		
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				context.deleteDatabase(MySQLiteHelper.DATABASE_NAME);
				try {
					recognitionService.deleteTrainingSet(GESTURES_RECORDED);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    dialog.dismiss();
		    }
		})
		
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int which) {
		        dialog.dismiss();
		    }
		})
		.create();
		return clearDatabaseDialog;
	}
	
	IBinder gestureListenerStub = new IGestureRecognitionListener.Stub() {
		@Override 
		public void onGestureLearned(String gestureName) throws RemoteException {
			System.out.println(gestureName + " learned and added to list!");
		}
		   
		@Override 
		public void onGestureRecognized(Distribution distribution) throws RemoteException {
			TestGestures.addItem(distribution.getBestMatch() + " Guys");
			TestGestures.addItem("" + distribution.getBestDistance());
			System.out.println(String.format("%s %f", distribution.getBestMatch(), distribution.getBestDistance()));
		}
		
		@Override
		public void onTrainingSetDeleted(String trainingSet) throws RemoteException {
			System.out.println("Training Set " + trainingSet + " deleted!");
		}
	};

	private final ServiceConnection gestureConnection = new ServiceConnection() {

	   public void onServiceConnected(ComponentName className, IBinder service) {
	      recognitionService = IGestureRecognitionService.Stub.asInterface(service);
	      try {
	    	 textView1.setText("registered");
	         recognitionService.registerListener(IGestureRecognitionListener.Stub.asInterface(gestureListenerStub));
	      } catch (RemoteException e) {
	    	  textView1.setText("error");
	    	  e.printStackTrace();
	      }
	   }
	   
	   public void onServiceDisconnected(ComponentName className) {
		   textView1.setText("disconnected");
	   }
	};
}
