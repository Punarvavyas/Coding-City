package com.bignerdranch.android.codingcity.quizinfo;

/**
 * This is the Quiz item which can used for create quiz question
 * @author Ruize Nie
 */
public class Question {

    private String mTextResId;
    private boolean mAnswerTrue;

    public Question(String textResId, boolean answerTrue){
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