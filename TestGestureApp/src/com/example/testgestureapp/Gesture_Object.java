package com.example.testgestureapp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.util.Log;

public class Gesture_Object implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	private Acceleration[] accelerationArray;
	
	public Gesture_Object(String name, Acceleration [] accelerationArray)
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
	
	public Acceleration[] getAccelerationArray() {
		return accelerationArray;
	}
	
	public void setAccelerationList(Acceleration[] accelerationList) {
		this.accelerationArray = accelerationList;
	}
	
	public static byte[] serializeGesture(Gesture_Object gesture) { 
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
		    
	public static Gesture_Object deserializeGesture(byte[] b) { 
		try { 
			ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(b)); 
			Gesture_Object gesture = (Gesture_Object) in.readObject(); 
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
