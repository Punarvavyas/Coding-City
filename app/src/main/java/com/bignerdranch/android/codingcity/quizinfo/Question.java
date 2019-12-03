package com.bignerdranch.android.codingcity.quizinfo;


public class Question {

    private String mTextResId;
    private boolean mAnswerTrue;

    public Question(String textResId, boolean answerTrue) {
        //Setting the question and its answers

        mTextResId = textResId;
        mAnswerTrue = answerTrue;
    }

    public String getTextResId() {
        return mTextResId;
    }

    public void setTextResId(String textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}