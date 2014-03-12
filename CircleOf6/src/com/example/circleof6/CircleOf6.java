package com.example.circleof6;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

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
import android.widget.TextView;
import android.widget.Toast;

public class CircleOf6 extends Activity {

	Button addContact1, addContact2, addContact3, addContact4, addContact5,
			addContact6;
	TextView textContact1, textContact2, textContact3, textContact4,
			textContact5, textContact6;
	HashMap<Integer, String> contactList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_circle_of_6);
		// Show the Up button in the action bar.
		setupActionBar();

		contactList = new HashMap<Integer, String>();

		addContact1 = (Button) findViewById(R.id.Button6);
		addContact2 = (Button) findViewById(R.id.Button2);
		addContact3 = (Button) findViewById(R.id.Button1);
		addContact4 = (Button) findViewById(R.id.Button3);
		addContact5 = (Button) findViewById(R.id.Button5);
		addContact6 = (Button) findViewById(R.id.Button4);

		textContact1 = (TextView) findViewById(R.id.textView01);
		textContact2 = (TextView) findViewById(R.id.textView02);
		textContact3 = (TextView) findViewById(R.id.textView03);
		textContact4 = (TextView) findViewById(R.id.textView04);
		textContact5 = (TextView) findViewById(R.id.textView05);
		textContact6 = (TextView) findViewById(R.id.textView06);
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

	public void activateFeature(View v) {

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
		case (R.id.Button6):
			if (resultCode == Activity.RESULT_OK) {
				saveContact(addContact1, textContact1,
						retrieveContactName(data), retrieveContactPhoto(data),
						retrievePhoneNumber(data));
			}
			break;
		case (R.id.Button2):
			if (resultCode == Activity.RESULT_OK) {
				saveContact(addContact2, textContact2,
						retrieveContactName(data), retrieveContactPhoto(data),
						retrievePhoneNumber(data));
			}
			break;
		case (R.id.Button1):
			if (resultCode == Activity.RESULT_OK) {
				saveContact(addContact3, textContact3,
						retrieveContactName(data), retrieveContactPhoto(data),
						retrievePhoneNumber(data));
			}
			break;
		case (R.id.Button3):
			if (resultCode == Activity.RESULT_OK) {
				saveContact(addContact4, textContact4,
						retrieveContactName(data), retrieveContactPhoto(data),
						retrievePhoneNumber(data));
			}
			break;
		case (R.id.Button5):
			if (resultCode == Activity.RESULT_OK) {
				saveContact(addContact5, textContact5,
						retrieveContactName(data), retrieveContactPhoto(data),
						retrievePhoneNumber(data));
			}
			break;
		case (R.id.Button4):
			if (resultCode == Activity.RESULT_OK) {
				saveContact(addContact6, textContact6,
						retrieveContactName(data), retrieveContactPhoto(data),
						retrievePhoneNumber(data));
			}
			break;
		}
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void saveContact(Button button, TextView textView, String name,
			Drawable contactPhoto, String contactNumber) {
		if (contactNumber != null) {
			if (contactPhoto != null)
				button.setBackground(contactPhoto);
			if (name != null)
				textView.setText(name);
			contactList.put(button.getId(), contactNumber);
		} else
			Toast.makeText(
					getApplicationContext(),
					"Can't find contact phone number. Please select one that has a phone number!",
					Toast.LENGTH_LONG).show();
	}

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

	private String retrieveContactName(Intent data) {

		String name = null;

		Uri contactData = data.getData();
		Cursor c = managedQuery(contactData, null, null, null, null);
		if (c.moveToFirst()) {
			String id = c.getString(c
					.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
			name = c.getString(c
					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
		}

		return name;
	}

	private Drawable retrieveContactPhoto(Intent data) {
		Drawable photo = null;

		Uri contactData = data.getData();
		Cursor c = managedQuery(contactData, null, null, null, null);
		if (c.moveToFirst()) {
			String photoURI = c
					.getString(c
							.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));
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
		}

		return photo;
	}
}
