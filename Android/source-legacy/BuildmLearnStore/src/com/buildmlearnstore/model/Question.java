package com.buildmlearnstore.model;

import java.util.ArrayList;

public class Question {
	private String mQuestion;
	private ArrayList<String> mAnswerOption;
	private int mOptionNumber;

	public String getQuestion() {
		return mQuestion;
	}

	public void setQuestion(String mQuestion) {
		this.mQuestion = mQuestion;
	}

	public ArrayList<String> getAnswerOption() {
		return mAnswerOption;
	}

	public void setAnswerOption(ArrayList<String> mAnswerOption) {
		this.mAnswerOption = mAnswerOption;
	}

	public int getOptionNumber() {
		return mOptionNumber;
	}

	public void setOptionNumber(int mOptionNumber) {
		this.mOptionNumber = mOptionNumber;
	}

}
