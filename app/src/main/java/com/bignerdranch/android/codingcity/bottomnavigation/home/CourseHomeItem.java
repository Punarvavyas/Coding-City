package com.bignerdranch.android.codingcity.bottomnavigation.home;

/**
 * This is Simple course item page which show in dashboard
 *
 * @author Ruize Nie
 */
public class CourseHomeItem {
    private String title;
    private String description;
    private int img;

    public CourseHomeItem(String title, String description, int img) {
        this.title = title;
        this.description = description;
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
