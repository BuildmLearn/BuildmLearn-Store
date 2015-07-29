package org.buildmlearn.appstore.models;

import java.util.ArrayList;

/**
 * Template model for Quiz type of apps. It has a model for questions.
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
