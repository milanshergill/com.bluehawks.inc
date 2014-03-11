package com.example.circleof6;

import java.io.FileNotFoundException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class CircleOf6 extends Activity {

	final int PICK_CONTACT = 0;
	Button addContact1, addContact2, addContact3, addContact4, addContact5, addContact6;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_circle_of6);
		// Show the Up button in the action bar.
		setupActionBar();
		
		addContact1 = (Button) findViewById(R.id.Button01);
		addContact2 = (Button) findViewById(R.id.Button02);
		addContact3 = (Button) findViewById(R.id.Button03);
		addContact4 = (Button) findViewById(R.id.Button04);
		addContact5 = (Button) findViewById(R.id.Button05);
		addContact6 = (Button) findViewById(R.id.Button06);
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.circle_of6, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void addContact(View v) {
		Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
	}
	
	@SuppressLint("NewApi")
	@Override 
	public void onActivityResult(int reqCode, int resultCode, Intent data){
		
		super.onActivityResult(reqCode, resultCode, data);
	
		switch(reqCode)
		{
		   case (PICK_CONTACT):
		     if (resultCode == Activity.RESULT_OK)
		     {
		    	 Uri contactData = data.getData();
		         Cursor c = managedQuery(contactData, null, null, null, null);
		         if (c.moveToFirst()) {
		        	 String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
		        	 String photoURI = c.getString(c.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));
		        
		        	 Uri uri = Uri.parse(photoURI);
		        	 Drawable photo;
		        	 try {
		        		    InputStream inputStream = getContentResolver().openInputStream(uri);
		        		    photo = Drawable.createFromStream(inputStream, photoURI.toString() );
		        		} catch (FileNotFoundException e) {
		        		    photo = getResources().getDrawable(R.drawable.ic_launcher);
		        		}
		        	  
		        	 if (photoURI != null) {
		        		 addContact1.setBackground(photo);
		        	 }
				
				     String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
				
				     if (hasPhone.equalsIgnoreCase("1")) 
				     {
				    	 Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, 
				    			 ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id, null, null);
				    	 phones.moveToFirst();
				    	 String cNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				    	 // Toast.makeText(getApplicationContext(), cNumber, Toast.LENGTH_SHORT).show();
				    	 Log.e("Phone number recorded", cNumber);
				     }
			    }
		    }
		}
	}


}
