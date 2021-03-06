package com.example.eng4kgestures;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.util.Log;

public class Gesture implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	int id = -1;
	private Acceleration[] accelerationArray;
	
	public Gesture(String name, Acceleration [] accelerationArray)
	{
		this.name =  name;
		this.accelerationArray = accelerationArray;
	}
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getID() {
		return id;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public Acceleration[] getAccelerationArray() {
		return accelerationArray;
	}
	
//	public void setAccelerationList(Acceleration[] accelerationList) {
//		this.accelerationArray = accelerationList;
//	}
	
	public void setAccelerationArray(Acceleration[] accelerationArray) {
		this.accelerationArray = accelerationArray;
	}
	
	public static byte[] serializeGesture(Gesture gesture) { 
		ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
		
		try { 
		  ObjectOutput out = new ObjectOutputStream(bos); 
		  out.writeObject(gesture); 
		  out.close();
		
		  // Get the bytes of the serialized object 
		  byte[] buf = bos.toByteArray(); 
		
		  return buf; 
		} catch(IOException ioe) { 
		  Log.e("serializeObject", "error", ioe); 
		
		  return null;
		}
	}
		    
	public static Gesture deserializeGesture(byte[] b) { 
		try { 
			ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(b)); 
			Gesture gesture = (Gesture) in.readObject(); 
			in.close(); 
		
			return gesture; 
		} catch(ClassNotFoundException cnfe) { 
			Log.e("deserializeObject", "class not found error", cnfe); 
	
			return null; 
		} catch(IOException ioe) { 
		  Log.e("deserializeObject", "io error", ioe); 
	
		  return null;
		}
	}
}
