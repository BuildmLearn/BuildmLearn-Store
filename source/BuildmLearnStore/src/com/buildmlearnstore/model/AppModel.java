package com.buildmlearnstore.model;

public class AppModel {

	private String appName;
	private int appImageId;

	public AppModel(String appName, int appImageId) {
		this.appImageId = appImageId;

		this.appName = appName;

	}

	public int getAppImageId() {
		return appImageId;
	}

	public void setAppImageId(int appImageId) {
		this.appImageId = appImageId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

}
