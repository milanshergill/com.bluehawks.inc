package com.example.eng4kgestures;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class TestGestures extends Activity {
	
	Button start, stop;
	TextView results;
	ListView resultsList;
	
	//LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems = new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_gestures);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
		
		start = (Button) findViewById(R.id.start);
		start = (Button) findViewById(R.id.stop);
		results = (TextView) findViewById(R.id.resultsView);
		resultsList = (ListView) findViewById(R.id.resultsList);
		
		resultsList.setAdapter(adapter);
	}
	
	public void onClickStart(View v) {
		// Start Testing
		addItem("Milan");
	}
	
	public void onClickStop(View v) {
		// Stop Testing and add results to the resultsList
	}
	
	public void addItem(String value) {
		listItems.add(value);
		adapter.notifyDataSetChanged();
	}
}
