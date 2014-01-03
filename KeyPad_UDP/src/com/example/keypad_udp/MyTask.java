package com.example.keypad_udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.os.AsyncTask;

public class MyTask extends AsyncTask<String, Void, Void>{

	InetAddress serverAddr = null;
    @Override
    protected Void doInBackground(String... arg0) {
    	InetAddress serverAddr = null;
		try {
			serverAddr = InetAddress.getByName(arg0[0]);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		byte[] buf = arg0[1].getBytes();
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket(12345);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			socket.setBroadcast(true);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DatagramPacket packet = new DatagramPacket(buf, buf.length,
		    serverAddr, 12345);
		try {
			socket.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(socket != null)
			socket.close();
		return null;
    } 
}