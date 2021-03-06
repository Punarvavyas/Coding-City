package com.bignerdranch.android.codingcity.courseinfo;

public class Lessons {
    String lessonName;
    String lessonText;
    boolean isVisited;

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public Lessons(String lessonName, String lessonText, boolean isVisited) {
        this.lessonName = lessonName;
        this.lessonText = lessonText;
        this.isVisited = isVisited;
    }

    public Lessons(String lessonName, String lessonText) {
        this.lessonName = lessonName;
        this.lessonText = lessonText;
    }

    public Lessons() {
    }

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
}
