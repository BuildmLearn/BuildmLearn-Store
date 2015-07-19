package org.buildmlearn.appstore.models;

import java.util.ArrayList;

/**
 * Created by Srujan Jha on 6/6/2015.
 */
public class QuizModel {
    private String mQuizName;
    private String mQuizAuthor;
    private ArrayList<Question> mQueAnsList;
    private int totalCorrect=0,totalWrong=0;

    private static QuizModel mQuizModel;
    public static void clearInstance()
    {
        if(mQuizModel!=null) {
            mQuizModel.totalCorrect = 0;
            mQuizModel.totalWrong = 0;
        }
    }
    public static QuizModel getInstance()
    {
        if(mQuizModel==null)
            mQuizModel=new QuizModel();
        return mQuizModel;
    }

    public ArrayList<Question> getQueAnsList() {
        return mQueAnsList;
    }

    public void setQueAnsList(ArrayList<Question> mQueAnsList) {
        this.mQueAnsList = mQueAnsList;
    }

    public String getQuizAuthor() {
        return mQuizAuthor;
    }

    public void setQuizAuthor(String mQuizAuthor) {
        this.mQuizAuthor = mQuizAuthor;
    }

    public String getQuizName() {
        return mQuizName;
    }

    public void setQuizName(String mQuizName) {
        this.mQuizName = mQuizName;
    }

    public int getTotalWrong() {
        return totalWrong;
    }

    public void setTotalWrong(int totalWrong) {
        this.totalWrong = totalWrong;
    }

    public int getTotalCorrect() {
        return totalCorrect;
    }

    public void setTotalCorrect(int totalCorrect) {
        this.totalCorrect = totalCorrect;
    }
}
