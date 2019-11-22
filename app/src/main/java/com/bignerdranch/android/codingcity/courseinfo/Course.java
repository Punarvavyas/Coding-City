package com.bignerdranch.android.codingcity.courseinfo;

import java.util.List;

public class Course {
    String courseId;
    String courseName;
    String courseDescription;
    String courseImageUrl;
    String isPremium;

    public Course() {
    }

    public Course(String courseId, String courseName, String courseDescription, String courseImageUrl, String isPremium) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.courseImageUrl = courseImageUrl;
        this.isPremium = isPremium;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCourseImageUrl() {
        return courseImageUrl;
    }

    public void setCourseImageUrl(String courseImageUrl) {
        this.courseImageUrl = courseImageUrl;
    }

    public String getIsPremium() {
        return isPremium;
    }

    public void setIsPremium(String isPremium) {
        this.isPremium = isPremium;
    }
}
