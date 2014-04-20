package com.example.safetyproject;

// Model to represent data in each row of navigation drawer
public class Model {

	private int icon;
	private String title;
	private String info;

	private boolean isGroupHeader = false;

	public Model(String title) {
		this(-1, title, null);
		isGroupHeader = true;
	}

	public Model(int icon, String title, String info) {
		super();
		this.icon = icon;
		this.title = title;
		this.info = info;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public boolean isGroupHeader() {
		return isGroupHeader;
	}

	public void setGroupHeader(boolean isGroupHeader) {
		this.isGroupHeader = isGroupHeader;
	}

}