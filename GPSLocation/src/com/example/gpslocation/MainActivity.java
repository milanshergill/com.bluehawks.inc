package com.example.gpslocation;

import android.location.*;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView mytext;	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mytext = (TextView)findViewById(R.id.mytext);
        mytext.setTextSize(20);
       mytext.setText("SafetyFirst Application and its features");
    }
    public void onClickUpdateLocation(View v){
 
    	Intent intent = new Intent(this, InfoMain.class);
    	startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
      
}
