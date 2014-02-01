package com.example.testgestureapp;

import java.io.Serializable;

public class Acceleration implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float x;
	private float y;
	private float z;


	public Acceleration(float accelX, float accelY, float accelZ) {
		// TODO Auto-generated constructor stub
		this.x = accelX;
		this.y = accelY;
		this.z = accelZ;
		
	}

	public float getAccelerationX() {
		return x;
	}

	public float getAccelerationY() {
		return y;
	}

	public float getAccelerationZ() {
		return z;
	}

	public void setAccelerationX(float x) {
		this.x = x;
	}

	public void setAccelerationY(float y) {
		this.y = y;
	}

	public void setAccelerationZ(float z) {
		this.z = z;
	}
	
	public void setAccelerationXYZ(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return " accelerationX " + x + " AccelerationY "
				+ y + " AccelerationZ "
				+ z;
	}
}
