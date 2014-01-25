package com.example.eng4kgestures;

import java.util.ArrayList;

public class Gesture {

	String name;
	private ArrayList<Acceleration> accelerationList;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Acceleration> getAccelerationList() {
		return accelerationList;
	}
	public void setAccelerationList(ArrayList<Acceleration> accelerationList) {
		this.accelerationList = accelerationList;
	}
}
