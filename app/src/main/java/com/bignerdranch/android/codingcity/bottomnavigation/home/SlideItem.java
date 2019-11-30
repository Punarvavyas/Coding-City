package com.bignerdranch.android.codingcity.bottomnavigation.home;

/**
 * This is Simple slide page which show in dashboard
 * @author Ruize Nie
 */
public class SlideItem {

    private int Image;
    private String title;
    private String courseid;

    public SlideItem(int image, String title, String courseid) {
        Image = image;
        this.title = title;
        this.courseid = courseid;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCourseid() {
        return courseid;
    }
}
