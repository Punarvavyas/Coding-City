package com.bignerdranch.android.codingcity.bottomnavigation.home;

/**
 * This is Simple slide page which show in dashboard
 * @author Ruize Nie
 */
public class SlideItem {

    private int Image;
    private String title;

    public SlideItem(int image, String title) {
        Image = image;
        this.title = title;
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
}
