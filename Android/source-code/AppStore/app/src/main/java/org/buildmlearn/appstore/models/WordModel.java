package org.buildmlearn.appstore.models;

/**
 * Template class for the words for the Spellings puzzle apps.
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
