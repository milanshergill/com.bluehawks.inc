package com.example.eng4kgestures;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	private GestureDataBase gestureDataBase;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
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
	    super.onResume();
	    gestureDataBase.openWriteable();
    }
    
    protected void onPause() {
	    super.onPause();
	    gestureDataBase.close();
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
}
