package org.buildmlearn.appstore.models;

import java.util.ArrayList;

/**
 * Created by Srujan Jha on 6/6/2015.
 */
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
