package com.example.eng4kgestures;

public class Acceleration {

	private long id;
	private String name;
	private Float accelerationX;
	private Float accelerationY;
	private Float accelerationZ;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public Float getAccelerationX() {
		return accelerationX;
	}

	public Float getAccelerationY() {
		return accelerationY;
	}

	public Float getAccelerationZ() {
		return accelerationZ;
	}

	public void setName(String name) {
		this.name = name;
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
		return " Name "+ name + " accelerationX " + accelerationX.toString() + " AccelerationY "
				+ accelerationY.toString() + " AccelerationZ "
				+ accelerationZ.toString();
	}
}
