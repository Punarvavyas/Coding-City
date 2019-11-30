package com.bignerdranch.android.codingcity.tutorial;

/**
 * This is the Tutorial Item using on the screen
 *
 * @author Ruize Nie
 */
public class TutorialItem {
    String Title, Description;
    int TutorialImg;

    public TutorialItem(String title, String description, int tutorialImg) {
        Title = title;
        Description = description;
        TutorialImg = tutorialImg;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getTutorialImg() {
        return TutorialImg;
    }

    public void setTutorialImg(int tutorialImg) {
        TutorialImg = tutorialImg;
    }
}