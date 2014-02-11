package com.example.eng4kgestures;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;

public class DeleteGestures extends Activity {

	TextView gesturesDeleteText;
	GridView gesturesList;
	int gestureToDelete;
	long gestureIDDelete;
	
	private GestureDataBase gestureDataBase;
	ArrayList<Gesture> savedGestures;
    ArrayList<String> listItems = new ArrayList<String>();
    ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.delete_gesture);
		
		gesturesDeleteText = (TextView) findViewById(R.id.gestureDeleteText);
		gesturesList = (GridView) findViewById(R.id.gesturesList);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
		
		gesturesList.setAdapter(adapter);
		gesturesList.setOnItemLongClickListener(listItemClickListener);
		
		//setting up database for acceleration recording
		gestureDataBase = new GestureDataBase(this);
	    gestureDataBase.openWriteable();
	    
	    savedGestures = gestureDataBase.getAllGestures();
	    for (int i = 0; i < savedGestures.size(); i++)
		{
	    	addItem(savedGestures.get(i).getName());
		}
	}

    protected void onResume() {
	    super.onResume();
	    gestureDataBase.openWriteable();
    }
    
    protected void onPause() {
	    super.onPause();
	    gestureDataBase.close();
    }
    
    public void addItem(String value) {
		listItems.add(value);
		adapter.notifyDataSetChanged();
	}
    
    public void removeItem(int index) {
		listItems.remove(index);
		adapter.notifyDataSetChanged();
	}
	
	public void clearItems() {
		listItems.clear();
		adapter.notifyDataSetChanged();
	}
	
	OnItemLongClickListener listItemClickListener = new OnItemLongClickListener() {
		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			gestureToDelete = position;
			String name = (String) parent.getItemAtPosition(position);
			for (int i = 0; i < savedGestures.size(); i++) {
        		if(savedGestures.get(i).getName().equals(name)) {
        			gestureIDDelete = savedGestures.get(i).getID();
        		}
			}
			AlertDialog diaBox = AskOption();
			diaBox.show();
			return true;
		}
	};
    
    private AlertDialog AskOption()
	{
		AlertDialog deleteGestureDialog = new AlertDialog.Builder(this) 
	    //set message and title
		.setTitle("Delete Gesture") 
		.setMessage("Do you want to delete this gesture?")
		
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				removeItem(gestureToDelete);
				gestureDataBase.deleteGesture(gestureIDDelete);
				dialog.dismiss();
		    }
		})
		
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int which) {
		        dialog.dismiss();
		    }
		})
		.create();
		return deleteGestureDialog;
	}
}
