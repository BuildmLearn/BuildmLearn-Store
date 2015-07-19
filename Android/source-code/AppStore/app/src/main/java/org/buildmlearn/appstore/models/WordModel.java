package org.buildmlearn.appstore.models;

/**
 * Created by Srujan Jha on 6/6/2015.
 */

public class WordModel {

	private String mWord;
	private String mDescription;
	public WordModel(String word,String description)
	{
		mWord=word;
		mDescription=description;
		
	}
	public String getWord() {
		return mWord;
	}

	public String getDescription() {
		return mDescription;
	}

}
