package com.example.eng4kgestures;

public class Acceleration {

	private float x;
	private float y;
	private float z;


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
