/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.mediafx;

import java.io.IOException;
import java.net.DatagramPacket; 
import java.net.DatagramSocket; 
import java.net.InetAddress;
import java.util.List;

import android.app.Activity; 
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle; 
import android.os.Handler; 
import android.os.Message; 
import android.view.Display;
import android.view.Surface;
import android.view.View; 
import android.view.View.OnClickListener; 
import android.view.WindowManager;
import android.widget.Button; 
import android.widget.EditText; 
import android.widget.TextView;

public class UDPExample extends Activity implements OnClickListener, SensorEventListener { 
public static final String SERVERIP = "127.0.0.1"; // ‘Within’ the emulator! 
public static final int SERVERPORT = 12345; 
public TextView text1; 
public EditText serverIp;
public Button btn;
public Button btn2;
public boolean start; 
public Handler Handler;
private float mSensorX;
private float mSensorY;
private long mSensorTimeStamp;
private long mCpuTimeStamp;
private String dataToSend;
private Display mDisplay;
SensorManager sensorManager;
List<Sensor> sensorList;


/** Called when the activity is first created. */ 
@Override 
public void onCreate(Bundle savedInstanceState) 
{ 
	super.onCreate(savedInstanceState); 
	setContentView(R.layout.main);
	
	text1=(TextView)findViewById(R.id.textView1); 
	serverIp=(EditText)findViewById(R.id.editText2); 
	btn = (Button)findViewById(R.id.button1); 
	btn.setOnClickListener(this); 
	btn2 = (Button)findViewById(R.id.button2); 
	start=false;
	
	sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
    
    for (Sensor s : sensorList) {
    	sensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);
    }
    
    WindowManager mWindowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
    mDisplay = mWindowManager.getDefaultDisplay();
	
	new Thread(new Client()).start(); 
	Handler = new Handler() { 
		@Override 
		public void handleMessage(Message msg) { 
			String text = (String)msg.obj; 
			text1.append(text); 
		} 
	}; 
}  

@Override 
public void onClick(View v) { 
	// TODO Auto-generated method stub 
	start=true; 
} 

public void onClick_Stop(View v) { 
	// TODO Auto-generated method stub 
	start=false; 
} 

public void updatetrack(String s){ 
	Message msg = new Message(); 
	String textTochange = s; 
	msg.obj = textTochange; 
	Handler.sendMessage(msg); 
}

InetAddress getBroadcastAddress() throws IOException {
    WifiManager wifi = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
    DhcpInfo dhcp = wifi.getDhcpInfo();
    // handle null somehow

    int broadcast = (dhcp.ipAddress & dhcp.netmask) | ~dhcp.netmask;
    byte[] quads = new byte[4];
    for (int k = 0; k < 4; k++)
      quads[k] = (byte) ((broadcast >> k * 8) & 0xFF);
    return InetAddress.getByAddress(quads);
}

@Override
public void onAccuracyChanged(Sensor arg0, int arg1) {
	// TODO Auto-generated method stub
	
}

@Override
public void onSensorChanged(SensorEvent event) {
	if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
        return;
    /*
     * record the accelerometer data, the event's timestamp as well as
     * the current time. The latter is needed so we can calculate the
     * "present" time during rendering. In this application, we need to
     * take into account how the screen is rotated with respect to the
     * sensors (which always return data in a coordinate space aligned
     * to with the screen in its native orientation).
     */

    switch (mDisplay.getRotation()) {
        case Surface.ROTATION_0:
            mSensorX = event.values[0];
            mSensorY = event.values[1];
            break;
        case Surface.ROTATION_90:
            mSensorX = -event.values[1];
            mSensorY = event.values[0];
            break;
        case Surface.ROTATION_180:
            mSensorX = -event.values[0];
            mSensorY = -event.values[1];
            break;
        case Surface.ROTATION_270:
            mSensorX = event.values[1];
            mSensorY = -event.values[0];
            break;
    }
    
    mSensorTimeStamp = event.timestamp;
    mCpuTimeStamp = System.nanoTime();
    dataToSend = "Accelerameter values:\nX: "+ mSensorX + " Y: " + mSensorY + "\nEvent Time: " +
    		mSensorTimeStamp + " System Time: " + mCpuTimeStamp +"\n\n";
}

public class Client implements Runnable { 
	@Override 
	public void run() { 
		while(start==false) 
		{ 
		} 
		try { 
			Thread.sleep(500); 
		} catch (InterruptedException e1) { 
			e1.printStackTrace(); 
		}
		while (start) {
			try {
				String serverIP = serverIp.getText().toString();
				InetAddress serverAddr = InetAddress.getByName(serverIP);
				
				byte[] buf = dataToSend.getBytes();
				
				DatagramSocket socket = new DatagramSocket(SERVERPORT);
				socket.setBroadcast(true);
				DatagramPacket packet = new DatagramPacket(buf, buf.length,
				    serverAddr, SERVERPORT);
				socket.send(packet); 
				socket.close();
				Thread.sleep(500);
			} catch (Exception e) { 
				updatetrack("Client: Error\n"); 
			} 
		}	
	} 
}

}
