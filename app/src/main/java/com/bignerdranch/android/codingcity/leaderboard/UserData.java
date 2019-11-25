package com.bignerdranch.android.codingcity.leaderboard;

public class UserData implements Comparable<UserData>{

    private String userId;

    @Override
    public int compareTo(UserData data) {
        int compareScore = data.getUserScore();
        return (compareScore - this.userScore);
    }

    private String userName;

    public UserData(String userId, String userName, int userScore, String userProfileImage) {
        this.userId = userId;
        this.userName = userName;
        this.userScore = userScore;
        this.userProfileImage = userProfileImage;
    }

    public UserData() {
    }

    private int userScore;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }

    public String getUserProfileImage() {
        return userProfileImage;
    }

    public void setUserProfileImage(String userProfileImage) {
        this.userProfileImage = userProfileImage;
    }

    private String userProfileImage;
}
