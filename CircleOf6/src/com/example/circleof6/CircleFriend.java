package com.example.circleof6;

import java.io.Serializable;

public class CircleFriend implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String phoneNumber;
	private String photoURI;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhotoURI() {
		return photoURI;
	}

	public void setPhotoURI(String photoURI) {
		this.photoURI = photoURI;
	}

	public CircleFriend(String name, String phoneNumber, String photoURI) {
		super();
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.photoURI = photoURI;
	}
}
