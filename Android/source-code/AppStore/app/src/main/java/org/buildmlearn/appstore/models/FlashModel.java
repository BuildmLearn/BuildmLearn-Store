package org.buildmlearn.appstore.models;

/**
 * Created by Srujan Jha on 6/6/2015.
 */

import java.util.ArrayList;

public class FlashModel {

	private String mName;
	private String mAuthor;
	private ArrayList<Card> mList;

	private static FlashModel mFlashModel;

	public static FlashModel getInstance() {
		if (mFlashModel == null)
			mFlashModel = new FlashModel();
		return mFlashModel;

	}

	public String getAuthor() {
		return mAuthor;
	}

	public void setAuthor(String mAuthor) {
		this.mAuthor = mAuthor;
	}

	public String getName() {
		return mName;
	}

	public void setName(String mName) {
		this.mName = mName;
	}

	public ArrayList<Card> getList() {
		return mList;
	}

	public void setList(ArrayList<Card> mList) {
		this.mList = mList;
	}

}
