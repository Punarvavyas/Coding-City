package com.bignerdranch.android.codingcity.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bignerdranch.android.codingcity.R;

/**
 * This is Sign up page for create new account and upload the user information
 * to the google firebase
 * @author Ruize Nie
 */
public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();
    }
}
