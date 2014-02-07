package com.example.eng4kgestures;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class TestGestures extends Activity implements SensorEventListener {
	
	Button start, stop;
	TextView results;
	EditText serverIP;
	GridView resultsList;
	
	SensorManager sensorManager;
	Sensor accelerometerSensor;
	private ArrayList<Acceleration> accelerationList;
	Acceleration acceletationObject;
	private float accelX, accelY, accelZ;
	private float oldAccelX, oldAccelY, oldAccelZ = 0;
	private static CountDownTimer timer, udpTimer;
	int foundSame = 0, count = 0;
	double minDistance = -1;
	boolean buttonPressed = false;
	
	private GestureDataBase gestureDataBase;
	ArrayList<Gesture> savedGestures;
    ArrayList<String> listItems = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    Gesture gestureToSend;
    
    AsyncTask<String, Void, Void> task = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_gestures);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
		
		start = (Button) findViewById(R.id.start);
		start = (Button) findViewById(R.id.stop);
		results = (TextView) findViewById(R.id.resultsView);
		resultsList = (GridView) findViewById(R.id.resultsList);
		serverIP = (EditText) findViewById(R.id.serverIP);
		
		resultsList.setAdapter(adapter);
		resultsList.setOnItemLongClickListener(listItemClickListener);
		
		//setting up database for acceleration recording
		gestureDataBase = new GestureDataBase(this);
	    gestureDataBase.openReadable();
	    
	    //Array list to hold sensor data
	    accelerationList = new ArrayList<Acceleration>();

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	    accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	    
	    timer = new CountDownTimer(10000, 20) {
	        public void onTick(long millisUntilFinished) {
        			acceletationObject =  new Acceleration(accelX, accelY, accelZ);
		    		accelerationList.add(acceletationObject);
    		}

	        public void onFinish() {
	            processData(14.0);
	            foundSame = 0;
	            oldAccelX = 0;
        		oldAccelY = 0;
        		oldAccelZ = 0;
        		// Clear the old readings from the list
        		accelerationList.clear();
	        }
	     };
	     
	     udpTimer = new CountDownTimer(12500, 250) {
	        public void onTick(long millisUntilFinished) {
	        	if (count < gestureToSend.getAccelerationArray().length) {
		        	String data = gestureToSend.getAccelerationArray()[count].getAccelerationX() + "," + gestureToSend.getAccelerationArray()[count].getAccelerationY() + ","
		        			+ gestureToSend.getAccelerationArray()[count].getAccelerationZ() + ",";
		        	task = new MyTask().execute(serverIP.getText().toString(), data);
		        	count++;
	        	}
    		}

	        public void onFinish() {
	        	if (task != null)
	    			task.cancel(true);
	        	count = 0;
	        	Toast.makeText(getBaseContext(), "Stopped sending gesture " + gestureToSend.getName(), Toast.LENGTH_SHORT).show();
	        }
	     };
	}
	
	OnItemLongClickListener listItemClickListener = new OnItemLongClickListener() {
		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			// Should click on the name to send the gesture, position start from 0
			if(position % 2 == 0 ) {
				String name = (String) parent.getItemAtPosition(position);
				for (int i = 0; i < savedGestures.size(); i++) {
	        		if(savedGestures.get(i).getName().equals(name)) {
	        			gestureToSend = savedGestures.get(i);
	        		}
				}
				Toast.makeText(getBaseContext(), "Item Clicked " + name + " ID is " + position, Toast.LENGTH_SHORT).show();
				AlertDialog diaBox = AskOption(getBaseContext());
				diaBox.show();
				return true;
			}
			else
				return false;
		}
	};
	
	protected void processData(double d) {
		//Process only when there is new data recorded to test
		if(!accelerationList.isEmpty()) {
			boolean thresholdMet = false;
			int size = accelerationList.size();
			for(int i =0; (i< accelerationList.size() )&&(!thresholdMet);)
			{
				float value = (float) Math.pow((Math.pow(accelerationList.get(i).getAccelerationX(), 2)
							+Math.pow(accelerationList.get(i).getAccelerationY(), 2)
							+Math.pow(accelerationList.get(i).getAccelerationZ(), 2)), 0.5);
				if(value < d)
				{
					accelerationList.remove(i);
				}
				else
				{
					thresholdMet = true;
				}
				
			}
			int k = accelerationList.size();
			if(!accelerationList.isEmpty()) {
				int j = Math.min(50,accelerationList.size());
				for(;j<accelerationList.size();)
				{
					accelerationList.remove(j);
				}
			}
			
			if(!accelerationList.isEmpty()) {
			
				//Get the list of all the gestures stored in Database
				savedGestures = gestureDataBase.getAllGestures();
				
				if(savedGestures.size() > 0)
					results.setText("Results");
				else
					results.setText("No saved gestures in database, record gestures first!");
				
				//Create the gesture object for newly recorded gesture
				String name = "Test Gesture";
				Gesture testGesture = createGesureObject(name, accelerationList);
				
				//Compare the newly gesture object with all saved gestures
				for (int i = 0; i < savedGestures.size(); i++)
				{
					minDistance = (double) DynamicTimeWarping.calcDistance(savedGestures.get(i), testGesture);
					addItem(savedGestures.get(i).getName());
					addItem("" + minDistance);
				}
			}
			else
				results.setText("Nothing recorded, press Start to record data.");
		}
//		else
//			results.setText("Nothing recorded, press Start to record data.");
	}

	protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_FASTEST);
        gestureDataBase.openReadable();
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        gestureDataBase.close();
    }
	
	public void onClickStart(View v) {
		// Start Recording Data & send for Testing
		results.setText("Testing...");
		clearItems();
		timer.start();
	}
	
	public void onClickStop(View v) {
		// Stop Testing and add results to the resultsList
		if (timer != null) {
			timer.cancel();
			timer.onFinish();
		}
	}
	
	public void onClickStopUDPTransfer(View v) {
		// Stop transfering gesture via UDP
		if (udpTimer != null) {
			udpTimer.cancel();
			udpTimer.onFinish();
		}
	}
	
	public void addItem(String value) {
		listItems.add(value);
		adapter.notifyDataSetChanged();
	}
	
	public void clearItems() {
		listItems.clear();
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			accelX = event.values[0];
			accelY = event.values[1];
			accelZ = event.values[2];
		}
	}
	
	public Gesture createGesureObject(String name, ArrayList<Acceleration> accelerationList) {
		int size =  accelerationList.size();
		Acceleration [] accelerationArray = new Acceleration[size] ;
		for (int i = 0; i < size; i++) {
			accelerationArray[i] = accelerationList.get(i);
		}
		 
		Gesture gestureObject =  new  Gesture(name, accelerationArray);
		return gestureObject;
	}
	
	
	public boolean dispatchKeyEvent(KeyEvent event) {
	    int action = event.getAction();
	    int keyCode = event.getKeyCode();
        switch (keyCode) {
        case KeyEvent.KEYCODE_VOLUME_UP:
            if (action == KeyEvent.ACTION_DOWN) {
            	if(!buttonPressed) {
            		buttonPressed = true;
            		results.setText("Testing...");
            		clearItems();
            		timer.start();
            	}
            }
            if (action == KeyEvent.ACTION_UP) {
            	if (timer != null) {
        			timer.cancel();
        			timer.onFinish();
        		}
				buttonPressed = false;
            }
            return true;
        case KeyEvent.KEYCODE_VOLUME_DOWN:
            if (action == KeyEvent.ACTION_DOWN) {
            	if(!buttonPressed) {
            		buttonPressed = true;
            		results.setText("Testing...");
            		clearItems();
            		timer.start();
            	}
            }
            if (action == KeyEvent.ACTION_UP) {
            	if (timer != null) {
        			timer.cancel();
        			timer.onFinish();
        		}
				buttonPressed = false;
            }
            return true;
        default:
            return super.dispatchKeyEvent(event);
        }
	}
	
	private AlertDialog AskOption(final Context context)
	{
		AlertDialog sendGestureDialog = new AlertDialog.Builder(this) 
	    //set message and title
		.setTitle("Send Gesture via UDP") 
		.setMessage("Do you want to send this Gesture?")
		
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				udpTimer.start();
			    dialog.dismiss();
		    }
		})
		
		.setNegativeButton("No", new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int which) {
		        dialog.dismiss();
		    }
		})
		.create();
		return sendGestureDialog;
	}
}
