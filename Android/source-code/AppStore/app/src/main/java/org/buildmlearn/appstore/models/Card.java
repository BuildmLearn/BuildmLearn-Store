package org.buildmlearn.appstore.models;

/**
 * Created by Srujan Jha on 6/6/2015.
 */
public class Card {
	private String mQuestion,mAnswer,mHint,mImagePath;
	
	public Card(String question,String answer,String hint,String imagePath){
		this.mQuestion=question;
		this.mAnswer=answer;
		this.mHint=hint;
		this.mImagePath=imagePath;
	}
	
	public String getQuestion() {
		return mQuestion;
	}

	public void setQuestion(String mQuestion) {
		this.mQuestion = mQuestion;
	}

	public String getAnswer() {
		return mAnswer;
	}

	public void setAnswer(String mAnswer) {
		this.mAnswer = mAnswer;
	}

	public String getHint() {
		return mHint;
	}

	public void setHint(String mHint) {
		this.mHint = mHint;
	}

	public String getImagePath() {
		return mImagePath;
	}

	public void setImagePath(String mImagePath) {
		this.mImagePath = mImagePath;
	}

	
}
