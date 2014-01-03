package com.example.android.mediafx;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import android.os.AsyncTask;

public class MyTask extends AsyncTask<String, Void, Void>{

	InetAddress serverAddr = null;
    @Override
    protected Void doInBackground(String... arg0) {
        try {
			serverAddr = InetAddress.getByName(arg0[0]);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        byte[] buffer = (arg0[1]).getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serverAddr, 12345);
            try {
            	DatagramSocket socket = new DatagramSocket(12345);
            	socket.setBroadcast(true);
                socket.send(packet);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return null;
    } 
}