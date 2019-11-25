package com.bignerdranch.android.codingcity.courseinfo;

public class Lessons {
    String lessonName;
    String lessonText;

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getLessonText() {
        return lessonText;
    }

    public void setLessonText(String lessonText) {
        this.lessonText = lessonText;
    }

    public Lessons(String lessonName, String lessonText) {
        this.lessonName = lessonName;
        this.lessonText = lessonText;
    }

    public Lessons() {
    }
}
