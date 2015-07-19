package org.buildmlearn.appstore.models;

/**
 * Created by Srujan Jha on 6/6/2015.
 */
import java.util.ArrayList;

public class SpellingsModel {

	private String mPuzzleName;
	private String mPuzzleAuthor;
	private ArrayList<WordModel> mSpellingsList;
	private int totalCorrect=0,totalWrong=0,activeCount=0;
	

	private static SpellingsModel mSpellingsModel;
	public static void clearInstance()
    {
		if(mSpellingsModel!=null) {
			mSpellingsModel.totalCorrect = 0;
			mSpellingsModel.totalWrong = 0;
			mSpellingsModel.activeCount = 0;
		}
    }
	public static SpellingsModel getInstance()
	{
		if(mSpellingsModel==null)
			mSpellingsModel=new SpellingsModel();
		return mSpellingsModel;
	}

	public String getPuzzleName() {
		return mPuzzleName;
	}

	public void setPuzzleName(String mPuzzleName) {
		this.mPuzzleName = mPuzzleName;
	}

	public String getPuzzleAuthor() {
		return mPuzzleAuthor;
	}

	public void setPuzzleAuthor(String mPuzzleAuthor) {
		this.mPuzzleAuthor = mPuzzleAuthor;
	}

	public ArrayList<WordModel> getSpellingsList() {
		return mSpellingsList;
	}

	public void setSpellingsList(ArrayList<WordModel> mSpellingsList) {
		this.mSpellingsList = mSpellingsList;
	}

	public int getTotalCorrect() {
		return totalCorrect;
	}

	public void setTotalCorrect(int totalCorrect) {
		this.totalCorrect = totalCorrect;
	}

	public int getTotalWrong() {
		return totalWrong;
	}

	public void setTotalWrong(int totalWrong) {
		this.totalWrong = totalWrong;
	}

	public int getActiveCount() {
		return activeCount;
	}

	public void setActiveCount(int activeCount) {
		this.activeCount = activeCount;
	}
}
