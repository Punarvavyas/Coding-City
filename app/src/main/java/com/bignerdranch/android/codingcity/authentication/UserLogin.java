package com.bignerdranch.android.codingcity.authentication;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;

public class UserLogin {

    private static UserLogin mInstance;
    private static Context mCtx;
    FirebaseUser user;

    public UserLogin(Context context) {
        mCtx = context.getApplicationContext();
    }

    public static synchronized UserLogin getInstance(Context context) {
        if(mInstance == null ){
            mInstance = new UserLogin(context.getApplicationContext());
        }
        return mInstance;
    }

    public FirebaseUser getUser(){
        return user;
    }

    public void setUser(FirebaseUser temp){
        user = temp;
    }

}
