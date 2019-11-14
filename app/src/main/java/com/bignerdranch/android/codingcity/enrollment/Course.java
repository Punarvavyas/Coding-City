package com.bignerdranch.android.codingcity.enrollment;

public class Course {

    String name = "";
    String description = "";
    Boolean premium = false;
    String img = "";
    String id = "";

    Course(String a, String b, String c, String d, String e) {
        name = a;
        description = b;
        premium = c.equals("0");
        img = d;
        id = e;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public Boolean getPremium() {
        return premium;
    }
}
