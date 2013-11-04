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

import java.net.DatagramPacket; 
import java.net.DatagramSocket; 
import java.net.InetAddress;

import android.app.Activity; 
import android.os.Bundle; 
import android.os.Handler; 
import android.os.Message; 
import android.view.View; 
import android.view.View.OnClickListener; 
import android.widget.Button; 
import android.widget.EditText; 
import android.widget.TextView;

public class UDPExample extends Activity implements OnClickListener{ 
public static final String SERVERIP = "127.0.0.1"; // ‘Within’ the emulator! 
public static final int SERVERPORT = 4444; 
public TextView text1; 
public EditText input; 
public Button btn; 
public boolean start; 
public Handler Handler; 
/** Called when the activity is first created. */ 
@Override 
public void onCreate(Bundle savedInstanceState) 
{ 
	super.onCreate(savedInstanceState); 
	setContentView(R.layout.main);
	
	text1=(TextView)findViewById(R.id.textView1); 
	input=(EditText)findViewById(R.id.editText1); 
	btn = (Button)findViewById(R.id.button1); 
	btn.setOnClickListener(this); 
	start=false; 
	
	new Thread(new Server()).start(); 
	try { 
		Thread.sleep(500); 
	} catch (InterruptedException e) { } 
	
	new Thread(new Client()).start(); 
	Handler = new Handler() { 
		@Override public void handleMessage(Message msg) { 
			String text = (String)msg.obj; 
			text1.append(text); 
		} 
	}; 
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
			// TODO Auto-generated catch block 
			e1.printStackTrace(); 
		} 
		try { 
			InetAddress serverAddr = InetAddress.getByName(SERVERIP); 
			updatetrack("Client: Start connectingn"); 
			DatagramSocket socket = new DatagramSocket(); 
			byte[] buf; 
			if(!input.getText().toString().isEmpty()) 
			{ 
				buf=input.getText().toString().getBytes(); 
			} 
			else 
			{ 
				buf = ("Default message").getBytes(); 
			} 
			DatagramPacket packet = new DatagramPacket(buf, buf.length, serverAddr, SERVERPORT); 
			updatetrack("Client: Sending ‘" + new String(buf) + "’n"); 
			socket.send(packet); 
			updatetrack("Client: Message sentn"); 
			updatetrack("Client: Succeed!n"); 
		} catch (Exception e) { 
			updatetrack("Client: Error!n"); 
		} 
	} 
} 

public class Server implements Runnable {

	@Override 
	public void run() { 
		while(start==false) 
		{ 
		} 
		try { 
			InetAddress serverAddr = InetAddress.getByName(SERVERIP); 
			updatetrack("nServer: Start connectingn"); 
			DatagramSocket socket = new DatagramSocket(SERVERPORT, serverAddr); 
			byte[] buf = new byte[17]; 
			DatagramPacket packet = new DatagramPacket(buf, buf.length); 
			updatetrack("Server: Receivingn"); 
			socket.receive(packet); 
			updatetrack("Server: Message received: ‘" + new String(packet.getData()) + "’n"); 
			updatetrack("Server: Succeed!n"); 
		} catch (Exception e) { 
			updatetrack("Server: Error!n"); 
		} 
	} 
} 

@Override 
public void onClick(View v) { 
	// TODO Auto-generated method stub 
	start=true; 
} 

public void updatetrack(String s){ 
	Message msg = new Message(); 
	String textTochange = s; 
	msg.obj = textTochange; 
	Handler.sendMessage(msg); 
}

}
