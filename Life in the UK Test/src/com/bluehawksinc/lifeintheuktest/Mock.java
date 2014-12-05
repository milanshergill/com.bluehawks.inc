package com.bluehawksinc.lifeintheuktest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class Mock extends Activity {
	/** Called when the activity is first created. */

	public static SoundManager mSoundManager;
	// public static String selected;
	// public static int prov;

	// MediaPlayer music = MediaPlayer.create(Main_Screen.this, R.raw.twist);

	DataBaseHelper db = new DataBaseHelper(this);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.mock);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.titleback);
		try {
			db.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mSoundManager = new SoundManager();
		mSoundManager.initSounds(getBaseContext());
		mSoundManager.addSound(1, R.raw.button9);
		mSoundManager.addSound(2, R.raw.magic);

		final ImageView Sound_Button = (ImageView) findViewById(R.id.mute);
		if (Setting.Sound == true) {
			Sound_Button.setImageResource(R.drawable.sound);

		} else {
			Sound_Button.setImageResource(R.drawable.mute);

		}
		Sound_Button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (Setting.Sound == true) {
					Sound_Button.setImageResource(R.drawable.mute);
					Setting.Sound = false;
				} else {
					Sound_Button.setImageResource(R.drawable.sound);
					Setting.Sound = true;
				}
			}
		});

		// Spinner spinner = (Spinner) findViewById(R.id.spinner);

		/*
		 * ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
		 * this, R.array.spinner_array, android.R.layout.simple_spinner_item);
		 * adapter
		 * .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item
		 * );
		 */

		// spinner.setOnItemSelectedListener(List);

		final ListView lv = (ListView) findViewById(R.id.list1);
		String[] from = new String[] { "arrow", "mockname" };
		int[] to = new int[] { R.id.arrow, R.id.mockname };

		// prepare the list of all records
		List<Map<String, Object>> fillMaps = new ArrayList<Map<String, Object>>();

		Map<String, Object> map0 = new HashMap<String, Object>();
		Bitmap government = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.arrow);
		String name0 = "Mock Test-1";
		map0.put("arrow", government);
		map0.put("mockname", name0);
		fillMaps.add(map0);

		Map<String, Object> map = new HashMap<String, Object>();
		Bitmap alberta = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.arrow);
		String name1 = "Mock Test-2";
		map.put("arrow", alberta);
		map.put("mockname", name1);
		fillMaps.add(map);

		Map<String, Object> map1 = new HashMap<String, Object>();
		Bitmap british = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.arrow);
		String name2 = "Mock Test-3";
		map1.put("arrow", british);
		map1.put("mockname", name2);
		fillMaps.add(map1);

		Map<String, Object> map2 = new HashMap<String, Object>();
		Bitmap manitoba = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.arrow);
		String name3 = "Mock Test-4";
		map2.put("arrow", manitoba);
		map2.put("mockname", name3);
		fillMaps.add(map2);

		Map<String, Object> map3 = new HashMap<String, Object>();
		Bitmap brunswick = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.arrow);
		String name4 = "Mock Test-5";
		map3.put("arrow", brunswick);
		map3.put("mockname", name4);
		fillMaps.add(map3);

		Map<String, Object> map4 = new HashMap<String, Object>();
		Bitmap newfoundland = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.arrow);
		String name5 = "Mock Test-6";
		map4.put("arrow", newfoundland);
		map4.put("mockname", name5);
		fillMaps.add(map4);

		Map<String, Object> map5 = new HashMap<String, Object>();
		Bitmap northwest = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.arrow);
		String name6 = "Mock Test-7";
		map5.put("arrow", northwest);
		map5.put("mockname", name6);
		fillMaps.add(map5);

		Map<String, Object> map6 = new HashMap<String, Object>();
		Bitmap novascotia = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.arrow);
		String name7 = "Mock Test-8";
		map6.put("arrow", novascotia);
		map6.put("mockname", name7);
		fillMaps.add(map6);

		Map<String, Object> map7 = new HashMap<String, Object>();
		Bitmap nunavut = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.arrow);
		String name8 = "Mock Test-9";
		map7.put("arrow", nunavut);
		map7.put("mockname", name8);
		fillMaps.add(map7);

		Map<String, Object> map8 = new HashMap<String, Object>();
		Bitmap ontario = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.arrow);
		String name9 = "Mock Test-10";
		map8.put("arrow", ontario);
		map8.put("mockname", name9);
		fillMaps.add(map8);

		Map<String, Object> map9 = new HashMap<String, Object>();
		Bitmap princeedward = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.arrow);
		String name10 = "Mock Test-11";
		map9.put("arrow", princeedward);
		map9.put("mockname", name10);
		fillMaps.add(map9);

		Map<String, Object> map10 = new HashMap<String, Object>();
		Bitmap quebec = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.arrow);
		String name11 = "Mock Test-12";
		map10.put("arrow", quebec);
		map10.put("mockname", name11);
		fillMaps.add(map10);

		Map<String, Object> map11 = new HashMap<String, Object>();
		Bitmap sas = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.arrow);
		String name12 = "Mock Test-13";
		map11.put("arrow", sas);
		map11.put("mockname", name12);
		fillMaps.add(map11);

		Map<String, Object> map12 = new HashMap<String, Object>();
		Bitmap yukon = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.arrow);
		String name13 = "Mock Test-14";
		map12.put("arrow", yukon);
		map12.put("mockname", name13);
		fillMaps.add(map12);

		Map<String, Object> map13 = new HashMap<String, Object>();
		Bitmap mock15 = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.arrow);
		String name15 = "Mock Test-15";
		map13.put("arrow", mock15);
		map13.put("mockname", name15);
		fillMaps.add(map13);

		SimpleAdapter adapter1 = new SimpleAdapter(this, fillMaps,
				R.layout.item4, from, to);
		adapter1.setViewBinder(new MyViewBinder());
		// adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		lv.setAdapter(adapter1);
		// spinner.setAdapter(adapter1);

		lv.setOnItemClickListener(List);

		db.close();

		findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mSoundManager.playSound(1);
				Intent i1 = new Intent(Mock.this, Main_Screen.class);
				startActivity(i1);
				finish();
			}
		});
		findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mSoundManager.playSound(1);
				Intent i1 = new Intent(Mock.this, MockInfo.class);
				startActivity(i1);
				finish();
			}
		});

	}

	public AdapterView.OnItemClickListener List = new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) {
			if (pos == 0) {
				mSoundManager.playSound(1);
				Intent i1 = new Intent(Mock.this, Mock1.class);
				startActivity(i1);
				finish();
			} else if (pos == 1) {
				mSoundManager.playSound(1);
				Intent i1 = new Intent(Mock.this, Mock2.class);
				startActivity(i1);
				finish();
			} else if (pos == 2) {
				mSoundManager.playSound(1);
				Intent i1 = new Intent(Mock.this, Mock3.class);
				startActivity(i1);
				finish();
			} else if (pos == 3) {
				mSoundManager.playSound(1);
				Intent i1 = new Intent(Mock.this, Mock4.class);
				startActivity(i1);
				finish();
			} else if (pos == 4) {
				mSoundManager.playSound(1);
				Intent i1 = new Intent(Mock.this, Mock5.class);
				startActivity(i1);
				finish();
			} else if (pos == 5) {
				mSoundManager.playSound(1);
				Intent i1 = new Intent(Mock.this, Mock6.class);
				startActivity(i1);
				finish();
			} else if (pos == 6) {
				mSoundManager.playSound(1);
				Intent i1 = new Intent(Mock.this, Mock7.class);
				startActivity(i1);
				finish();
			} else if (pos == 7) {
				mSoundManager.playSound(1);
				Intent i1 = new Intent(Mock.this, Mock8.class);
				startActivity(i1);
				finish();

			} else if (pos == 8) {
				mSoundManager.playSound(1);
				Intent i1 = new Intent(Mock.this, Mock9.class);
				startActivity(i1);
				finish();

			} else if (pos == 9) {
				mSoundManager.playSound(1);
				Intent i1 = new Intent(Mock.this, Mock10.class);
				startActivity(i1);
				finish();

			} else if (pos == 10) {
				mSoundManager.playSound(1);
				Intent i1 = new Intent(Mock.this, Mock11.class);
				startActivity(i1);
				finish();

			} else if (pos == 11) {
				mSoundManager.playSound(1);
				Intent i1 = new Intent(Mock.this, Mock12.class);
				startActivity(i1);
				finish();

			} else if (pos == 12) {
				mSoundManager.playSound(1);
				Intent i1 = new Intent(Mock.this, Mock13.class);
				startActivity(i1);
				finish();

			} else if (pos == 13) {
				mSoundManager.playSound(1);
				Intent i1 = new Intent(Mock.this, Mock14.class);
				startActivity(i1);
				finish();

			} else if (pos == 14) {
				mSoundManager.playSound(1);
				Intent i1 = new Intent(Mock.this, Mock15.class);
				startActivity(i1);
				finish();
			}

		}
	};

}
