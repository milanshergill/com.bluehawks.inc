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
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Arrays;
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
import android.util.Log;
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
private static final String TAG = "Sensor_Mine"; 
public TextView text1; 
public EditText serverIp;
public Button btn;
public Button btn2;
public boolean start; 
public Handler Handler;
private float mSensorX;
private float mSensorY;
private float mSensorZ;
private long mSensorTimeStamp_Old;
private long mSensorTimeStamp_Diff;
private long mCpuTimeStamp;
private String da;
private Display mDisplay;
SensorManager sensorManager;
List<Sensor> sensorList;
private Client myThread;
private long myThreadId;


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
    
    
	myThread = new Client();
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
	start = true;
	text1.append("Started");
	myThread.start();
//	myThread.onResumeThread();
} 

public void onClick_Stop(View v) { 
	// TODO Auto-generated method stub 
	start = false;
	text1.append("Stopped");
	myThread.stop();
//	myThread.onPause(); 
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

    /*switch (mDisplay.getRotation()) {
        case Surface.ROTATION_0:
            mSensorX = event.values[0];
            mSensorY = event.values[1];
            mSensorZ = event.values[2];
            break;
        case Surface.ROTATION_90:
            mSensorX = -event.values[1];
            mSensorY = event.values[0];
            mSensorZ = event.values[2];
            break;
        case Surface.ROTATION_180:
            mSensorX = -event.values[0];
            mSensorY = -event.values[1];
            mSensorZ = event.values[2];
            break;
        case Surface.ROTATION_270:
            mSensorX = event.values[1];
            mSensorY = -event.values[0];
            mSensorZ = event.values[2];
            break;
    }*/
	
//	text1.append("Sensor Changed");
	 mSensorX = event.values[0];
     mSensorY = event.values[1];
     mSensorZ = event.values[2];
    
    mCpuTimeStamp = System.nanoTime();
    mSensorTimeStamp_Diff = event.timestamp - mSensorTimeStamp_Old;
    mSensorTimeStamp_Old = event.timestamp;
    
    da = mSensorX + "," + mSensorY + "," + mSensorZ + "," + mSensorTimeStamp_Old + ",";
    
//    da = "Accelerameter values:\nX: ," + mSensorX + ",\nY: ," + mSensorY + ",\nZ: ," +
//    		mSensorZ + ",\nEvent Time: ," + mSensorTimeStamp_Diff;
//    
//    da = "Accelerameter values:\nX: " + mSensorX + "\nY: " + mSensorY + "\nZ: " +
//    		mSensorZ + "\nEvent Time: " + mSensorTimeStamp_Diff;
    
////    if(start) {
////	    try {
////	    	text1.append("Trying to send");
////			String serverIP = serverIp.getText().toString();
////			InetAddress serverAddr = InetAddress.getByName(serverIP);
////			
////	//		text1.append(da);
////			//updatetrack(da); 
////			byte[] buf = da.getBytes();
////			DatagramSocket socket = new DatagramSocket(SERVERPORT);
////			socket.setBroadcast(true);
////			DatagramPacket packet = new DatagramPacket(buf, buf.length,
////			    serverAddr, SERVERPORT);
////			socket.send(packet); 
////			socket.close();
////		} catch (Exception e) { 
////			//updatetrack("Client: Error\n"); 
////		}	
//    }
//    byte[] string1 = ("Accelerameter values:\nX: ").getBytes();
//    byte[] xValue = ByteBuffer.allocate(4).putFloat(mSensorX).array();
//    byte[] string2 = "\nY: ".getBytes();
//    byte[] yValue = ByteBuffer.allocate(4).putFloat(mSensorY).array();
//    byte[] string3 = "\nZ: ".getBytes();
//    byte[] zValue = ByteBuffer.allocate(4).putFloat(mSensorZ).array();
//    byte[] string4 = "\nEvent Time: ".getBytes();
//    byte[] timeDiff = ByteBuffer.allocate(4).putLong(mSensorTimeStamp_Diff).array();
//    
//    dataToSend = new byte[string1.length + xValue.length + string2.length + yValue.length + 
//                          string3.length + zValue.length + string4.length + timeDiff.length];
//    
//    int des_Offset = 0;
//    System.arraycopy(string1, 0, dataToSend, des_Offset, string1.length);
//    des_Offset = string1.length;
//    System.arraycopy(xValue, 0, dataToSend, des_Offset, xValue.length);
//    des_Offset = des_Offset + xValue.length;
//    System.arraycopy(string2, 0, dataToSend, des_Offset, string2.length);
//    des_Offset = des_Offset + string2.length;
//    System.arraycopy(yValue, 0, dataToSend, des_Offset, yValue.length);
//    des_Offset = des_Offset + yValue.length;
//    System.arraycopy(string3, 0, dataToSend, des_Offset, string3.length);
//    des_Offset = des_Offset + string3.length;
//    System.arraycopy(zValue, 0, dataToSend, des_Offset, zValue.length);
//    des_Offset = des_Offset + zValue.length;
//    System.arraycopy(string4, 0, dataToSend, des_Offset, string4.length);
//    des_Offset = des_Offset + string4.length;
//    System.arraycopy(timeDiff, 0, dataToSend, des_Offset, timeDiff.length);
}

public class Client implements Runnable { 
	
	Thread backgroundThread;

    public void start() {
       if( backgroundThread == null ) {
          backgroundThread = new Thread( this );
          backgroundThread.start();
       }
    }

    public void stop() {
       if( backgroundThread != null ) {
          backgroundThread.interrupt();
       }
    }

    public void run() {
        try {
           
           while( !backgroundThread.interrupted() ) {
        	    String serverIP = serverIp.getText().toString();
				InetAddress serverAddr = InetAddress.getByName(serverIP);
				
//				text1.append(da);
				//updatetrack(da); 
				byte[] buf = da.getBytes();
				DatagramSocket socket = new DatagramSocket(SERVERPORT);
				socket.setBroadcast(true);
				DatagramPacket packet = new DatagramPacket(buf, buf.length,
				    serverAddr, SERVERPORT);
				socket.send(packet); 
				socket.close();
				Thread.sleep(50);
           }
           
        } catch( InterruptedException ex ) {
           // important you respond to the InterruptedException and stop processing 
           // when its thrown!  Notice this is outside the while loop.
           
        } catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
           backgroundThread = null;
        }
    }
//	private Object mPauseLock;
//    private boolean mPaused;
//    private boolean mFinished;
//    
//	public Client() {
//        mPauseLock = new Object();
//        mPaused = false;
//        mFinished = false;
//    }
//	
//	@Override 
//	public void run() {
//		text1.append("Running");
//		while (!mFinished) {
//			try {
//				String serverIP = serverIp.getText().toString();
//				InetAddress serverAddr = InetAddress.getByName(serverIP);
//				
////				text1.append(da);
//				//updatetrack(da); 
//				byte[] buf = da.getBytes();
//				DatagramSocket socket = new DatagramSocket(SERVERPORT);
//				socket.setBroadcast(true);
//				DatagramPacket packet = new DatagramPacket(buf, buf.length,
//				    serverAddr, SERVERPORT);
//				socket.send(packet); 
//				socket.close();
//				Thread.sleep(50);
//			} catch (Exception e) { 
//				//updatetrack("Client: Error\n"); 
//			}
//			
//            synchronized (mPauseLock) {
//                while (mPaused) {
//                    try {
//                        mPauseLock.wait();
//                    } catch (InterruptedException e) {
//                    }
//                }
//            }
//        }	
//	}
//	
//	 /**
//     * Call this on pause.
//     */
//    public void onPause() {
//    	text1.append("In Pause Method");
//        synchronized (mPauseLock) {
//            mPaused = true;
//        }
//    }
//
//    /**
//     * Call this on resume.
//     */
//    public void onResumeThread() {
//    	text1.append("In Resume Method");
//        synchronized (mPauseLock) {
//            mPaused = false;
//            mPauseLock.notifyAll();
//        }
//    }
}

}
