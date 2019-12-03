package com.bignerdranch.android.codingcity.enrollment;

// Course structure
public class Course {

    private String name;
    private String description;
    private String premium;
    private String img;
    private String id;

    Course(String cname, String cDescription, String cPremium, String cImgSrc, String cId) {
        name = cname;
        description = cDescription;
        premium = cPremium;
        img = cImgSrc;
        id = cId;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getImgSrc() {
        return img;
    }

    public String getName() {
        return name;
    }

    public String getPremium() {
        return premium;
    }
}
