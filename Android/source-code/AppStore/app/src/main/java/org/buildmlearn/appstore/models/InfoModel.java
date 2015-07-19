package org.buildmlearn.appstore.models;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Srujan Jha on 6/6/2015.
 */

public class InfoModel {

	private String mInfoName;
	private String mInfoAuthor;
	private ArrayList<String> mListTitleList;
	private HashMap<String, String> mInfoMap;

	private static InfoModel mModel;

	public static InfoModel getInstance() {
		if (mModel == null)
			mModel = new InfoModel();
		return mModel;
	}

	public HashMap<String, String> getInfoMap() {
		return mInfoMap;
	}

	public void setInfoMap(HashMap<String, String> mInfoMap) {
		this.mInfoMap = mInfoMap;
	}

	public String getInfoAuthor() {
		return mInfoAuthor;
	}

	public void setInfoAuthor(String mInfoAuthor) {
		this.mInfoAuthor = mInfoAuthor;
	}

	public String getInfoName() {
		return mInfoName;
	}

	public void setInfoName(String mInfoName) {
		this.mInfoName = mInfoName;
	}

	public ArrayList<String> getListTitleList() {
		return mListTitleList;
	}

	public void setListTitleList(ArrayList<String> mListTitleList) {
		this.mListTitleList = mListTitleList;
	}

}
