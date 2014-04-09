package com.example.safetyproject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.NavUtils;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CircleOf6 extends Activity {

	private static final int MAX_SMS_MESSAGE_LENGTH = 160;
	private static final String SMS_SENT = "circle.of.6.sentsms";
	private static final String SMS_DELIVERED = "circle.of.6.smsdelivered";
	Button addContact1, addContact2, addContact3, addContact4, addContact5,
			addContact6;
	TextView textContact1, textContact2, textContact3, textContact4,
			textContact5, textContact6;
	static int[] buttonIDs = new int[6];
	static HashMap<Integer, CircleFriend> contactList;

	@SuppressLint("UseSparseArrays")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_circle_of_6);
		// Show the Up button in the action bar.
		setupActionBar();

		registerReceiver(receiver, new IntentFilter(SMS_SENT)); // SMS_SENT is a
																// constant

		contactList = new HashMap<Integer, CircleFriend>();

		addContact1 = (Button) findViewById(R.id.Button1);
		addContact2 = (Button) findViewById(R.id.Button2);
		addContact3 = (Button) findViewById(R.id.Button3);
		addContact4 = (Button) findViewById(R.id.Button4);
		addContact5 = (Button) findViewById(R.id.Button5);
		addContact6 = (Button) findViewById(R.id.Button6);

		buttonIDs[0] = addContact1.getId();
		buttonIDs[1] = addContact2.getId();
		buttonIDs[2] = addContact3.getId();
		buttonIDs[3] = addContact4.getId();
		buttonIDs[4] = addContact5.getId();
		buttonIDs[5] = addContact6.getId();

		textContact1 = (TextView) findViewById(R.id.textView01);
		textContact2 = (TextView) findViewById(R.id.textView02);
		textContact3 = (TextView) findViewById(R.id.textView03);
		textContact4 = (TextView) findViewById(R.id.textView04);
		textContact5 = (TextView) findViewById(R.id.textView05);
		textContact6 = (TextView) findViewById(R.id.textView06);
		
//		clearData();
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
	
	@Override
	public void onStop() {
		super.onStop(); // Always call the superclass method first
		
		// Save the data before stopping
		try {
			saveToSharedFile(0, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onStart() {
		super.onStart(); // Always call the superclass method first
		
		// Set the data back before starting
		SharedPreferences sharedPref = getSharedPreferences(
				getString(R.string.preference_file_key), Context.MODE_PRIVATE);
		for (int i = 0; i < 6; i++) {
			String buttonStr = Integer.toString(buttonIDs[i]);
			String friendInfo = sharedPref.getString(buttonStr, null);
			if (friendInfo != null) {
				CircleFriend friend = null;
				try {
					friend = (CircleFriend) ObjectSerializer.deserialize(friendInfo);
					Log.i("SharedPreference", "Key: " + i + " " + friend.getName());
					Log.i("SharedPreference", "Value: " + i + " " + friend.getPhotoURI());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				contactList.put(buttonIDs[i], friend);
			}
		}
		updateCircles();
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(receiver);
		super.onDestroy();
	}
	
	private void saveToSharedFile(int buttonID, boolean checkAll) throws IOException {
		// Save the circle of 6 data
		SharedPreferences sharedPref = getSharedPreferences(
				getString(R.string.preference_file_key), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		
		if (!checkAll) {
			String buttonStr = Integer.toString(buttonID);
			String data = ObjectSerializer.serialize(contactList.get(buttonID));
			editor.putString(buttonStr, data);
		}
		else {
			for (int i = 0; i < 6; i++) {
				if (contactList.get(buttonIDs[i]) != null) {
					String buttonStr = Integer.toString(buttonIDs[i]);
					String data = ObjectSerializer.serialize(contactList.get(buttonIDs[i]));
					editor.putString(buttonStr, data);
				}
			}
		}
		editor.commit();
	}
	
	private void clearData() {
		// Clear all the preferences in the shared preference file
		SharedPreferences sharedPref = getSharedPreferences(
				getString(R.string.preference_file_key), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();

		for (int i = 0; i < 6; i++) {
			String buttonStr = Integer.toString(buttonIDs[i]);
			editor.remove(buttonStr);
		}
		editor.commit();
	}

	@SuppressLint("NewApi")
	private void updateCircles() {
		for (int i = 0; i < 6; i++) {
			if (contactList.get(buttonIDs[i]) != null && contactList.get(buttonIDs[i]).getPhoneNumber() != null) {
				if(buttonIDs[i] == buttonIDs[0]){
					if (contactList.get(buttonIDs[i]).getPhotoURI() != null)
						addContact1.setBackground(getContactPhoto(contactList.get(buttonIDs[i])
								.getPhotoURI()));
					if (contactList.get(buttonIDs[i]).getName() != null)
						textContact1.setText(contactList.get(buttonIDs[i]).getName());
				}
				else if(buttonIDs[i] == buttonIDs[1]){ 
					if (contactList.get(buttonIDs[i]).getPhotoURI() != null)
						addContact2.setBackground(getContactPhoto(contactList.get(buttonIDs[i])
								.getPhotoURI()));
					if (contactList.get(buttonIDs[i]).getName() != null)
						textContact2.setText(contactList.get(buttonIDs[i]).getName());
				}
				else if(buttonIDs[i] == buttonIDs[2]){ 
					if (contactList.get(buttonIDs[i]).getPhotoURI() != null)
						addContact3.setBackground(getContactPhoto(contactList.get(buttonIDs[i])
								.getPhotoURI()));
					if (contactList.get(buttonIDs[i]).getName() != null)
						textContact3.setText(contactList.get(buttonIDs[i]).getName());
				}
				else if(buttonIDs[i] == buttonIDs[3]){ 
					if (contactList.get(buttonIDs[i]).getPhotoURI() != null)
						addContact4.setBackground(getContactPhoto(contactList.get(buttonIDs[i])
								.getPhotoURI()));
					if (contactList.get(buttonIDs[i]).getName() != null)
						textContact4.setText(contactList.get(buttonIDs[i]).getName());
				}
				else if(buttonIDs[i] == buttonIDs[4]){ 
					if (contactList.get(buttonIDs[i]).getPhotoURI() != null)
						addContact5.setBackground(getContactPhoto(contactList.get(buttonIDs[i])
								.getPhotoURI()));
					if (contactList.get(buttonIDs[i]).getName() != null)
						textContact5.setText(contactList.get(buttonIDs[i]).getName());
				}
				else if(buttonIDs[i] == buttonIDs[5]){ 
					if (contactList.get(buttonIDs[i]).getPhotoURI() != null)
						addContact6.setBackground(getContactPhoto(contactList.get(buttonIDs[i])
								.getPhotoURI()));
					if (contactList.get(buttonIDs[i]).getName() != null)
						textContact6.setText(contactList.get(buttonIDs[i]).getName());
				}
			}
		}
	}

	public void activateFeature(View v) {
		for (int i = 0; i < 6; i++) {
			CircleFriend friend = contactList.get(buttonIDs[i]);
			if (friend != null && friend.getPhoneNumber() != null)
				sendSMS(friend.getPhoneNumber(),
						"Hello this message is from Circle of 6");
		}
	}

	public void addContact(View v) {
		Intent intent = new Intent(Intent.ACTION_PICK,
				ContactsContract.Contacts.CONTENT_URI);
		startActivityForResult(intent, v.getId());
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public void onActivityResult(int reqCode, int resultCode, Intent data) {
		super.onActivityResult(reqCode, resultCode, data);
		switch (reqCode) {
		case (R.id.Button1):
			if (resultCode == Activity.RESULT_OK) {
				saveContact(addContact1, textContact1,
						retrieveContactName(data), retrievePhotoURI(data), retrieveContactPhoto(data),
						retrievePhoneNumber(data));
			}
			break;
		case (R.id.Button2):
			if (resultCode == Activity.RESULT_OK) {
				saveContact(addContact2, textContact2,
						retrieveContactName(data), retrievePhotoURI(data), retrieveContactPhoto(data),
						retrievePhoneNumber(data));
			}
			break;
		case (R.id.Button3):
			if (resultCode == Activity.RESULT_OK) {
				saveContact(addContact3, textContact3,
						retrieveContactName(data), retrievePhotoURI(data), retrieveContactPhoto(data),
						retrievePhoneNumber(data));
			}
			break;
		case (R.id.Button4):
			if (resultCode == Activity.RESULT_OK) {
				saveContact(addContact4, textContact4,
						retrieveContactName(data), retrievePhotoURI(data), retrieveContactPhoto(data),
						retrievePhoneNumber(data));
			}
			break;
		case (R.id.Button5):
			if (resultCode == Activity.RESULT_OK) {
				saveContact(addContact5, textContact5,
						retrieveContactName(data), retrievePhotoURI(data), retrieveContactPhoto(data),
						retrievePhoneNumber(data));
			}
			break;
		case (R.id.Button6):
			if (resultCode == Activity.RESULT_OK) {
				saveContact(addContact6, textContact6,
						retrieveContactName(data), retrievePhotoURI(data), retrieveContactPhoto(data),
						retrievePhoneNumber(data));
			}
			break;
		}
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void saveContact(Button button, TextView textView, String name, String photoURI,
			Drawable contactPhoto, String contactNumber) {
		if (contactNumber != null) {
			CircleFriend newFriend = new CircleFriend(name, contactNumber,
					photoURI);
			if (photoURI != null && contactPhoto != null)
				button.setBackground(getContactPhoto(newFriend.getPhotoURI()));
			if (newFriend.getName() != null)
				textView.setText(newFriend.getName());
			int id = button.getId();
			contactList.put(id, newFriend);
			try {
				int id1 = button.getId();
				saveToSharedFile(id1, false);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else
			Toast.makeText(
					getApplicationContext(),
					"Can't find contact phone number. Please select one that has a phone number!",
					Toast.LENGTH_LONG).show();
	}

	@SuppressWarnings("deprecation")
	private String retrievePhoneNumber(Intent data) {

		String phoneNumber = null;

		Uri contactData = data.getData();
		Cursor c = managedQuery(contactData, null, null, null, null);
		if (c.moveToFirst()) {
			String id = c.getString(c
					.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
			String hasPhone = c
					.getString(c
							.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

			if (hasPhone.equalsIgnoreCase("1")) {
				Cursor phones = getContentResolver().query(
						ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
						null,
						ContactsContract.CommonDataKinds.Phone.CONTACT_ID
								+ " = " + id, null, null);
				phones.moveToFirst();
				phoneNumber = phones
						.getString(phones
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				Log.e("Phone number recorded", phoneNumber);
			}
		}

		return phoneNumber;
	}

	@SuppressWarnings("deprecation")
	private String retrieveContactName(Intent data) {

		String name = null;

		Uri contactData = data.getData();
		Cursor c = managedQuery(contactData, null, null, null, null);
		if (c.moveToFirst()) {
			name = c.getString(c
					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
		}

		return name;
	}

	@SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private Drawable retrieveContactPhoto(Intent data) {
		Drawable photo = null;

		Uri contactData = data.getData();
		Cursor c = managedQuery(contactData, null, null, null, null);
		if (c.moveToFirst()) {
			String photoURI = c
					.getString(c
							.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));
			
			photo = getContactPhoto(photoURI);
		}

		return photo;
	}
	
	
	
	private Drawable getContactPhoto(String photoURI) {
		Drawable photo = null;
		
		Uri uri = null;
		if (photoURI != null) {
			uri = Uri.parse(photoURI);
			try {
				InputStream inputStream = getContentResolver()
						.openInputStream(uri);
				photo = Drawable.createFromStream(inputStream,
						photoURI.toString());
			} catch (FileNotFoundException e) {
				photo = getResources().getDrawable(R.drawable.ic_launcher);
			}
		} else
			photo = getResources().getDrawable(R.drawable.ic_launcher);
		
		return photo;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressWarnings("deprecation")
	private String retrievePhotoURI(Intent data) {
		String photoURI = null;
		Uri contactData = data.getData();
		Cursor c = managedQuery(contactData, null, null, null, null);
		if (c.moveToFirst()) {
			photoURI = c
					.getString(c
							.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));
		}
		return photoURI;
	}

	private void sendSMS(String phonenumber, String message) {
		SmsManager manager = SmsManager.getDefault();

		PendingIntent piSend = PendingIntent.getBroadcast(this, 0, new Intent(
				SMS_SENT), 0);
		PendingIntent piDelivered = PendingIntent.getBroadcast(this, 0,
				new Intent(SMS_DELIVERED), 0);

		int length = message.length();

		if (length > MAX_SMS_MESSAGE_LENGTH) {
			ArrayList<String> messagelist = manager.divideMessage(message);

			manager.sendMultipartTextMessage(phonenumber, null, messagelist,
					null, null);
		} else {
			manager.sendTextMessage(phonenumber, null, message, piSend,
					piDelivered);
		}
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String message = null;

			switch (getResultCode()) {
			case Activity.RESULT_OK:
				message = "Message sent!";
				break;
			case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
				message = "Error. Message not sent.";
				break;
			case SmsManager.RESULT_ERROR_NO_SERVICE:
				message = "Error: No service.";
				break;
			case SmsManager.RESULT_ERROR_NULL_PDU:
				message = "Error: Null PDU.";
				break;
			case SmsManager.RESULT_ERROR_RADIO_OFF:
				message = "Error: Radio off.";
				break;
			}

			Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG)
					.show();
		}
	};
}
