package com.buildmlearnstore.manager;

public class InfoManager {

	public static InfoManager mManager;

	public static InfoManager getInstance() {
		if (mManager == null)
			mManager = new InfoManager();
		return mManager;
	}

}
