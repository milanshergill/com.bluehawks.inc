package com.example.eng4kgestures;

public class Acceleration {

	private Float accelerationX;
	private Float accelerationY;
	private Float accelerationZ;


	public Float getAccelerationX() {
		return accelerationX;
	}

	public Float getAccelerationY() {
		return accelerationY;
	}

	public Float getAccelerationZ() {
		return accelerationZ;
	}

	public void setAccelerationX(Float accelerationX) {
		this.accelerationX = accelerationX;
	}

	public void setAccelerationY(Float accelerationY) {
		this.accelerationY = accelerationY;
	}

	public void setAccelerationZ(Float accelerationZ) {
		this.accelerationZ = accelerationZ;
	}

	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return " accelerationX " + accelerationX.toString() + " AccelerationY "
				+ accelerationY.toString() + " AccelerationZ "
				+ accelerationZ.toString();
	}
}
