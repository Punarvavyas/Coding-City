package com.bignerdranch.android.codingcity.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bignerdranch.android.codingcity.MainActivity;
import com.bignerdranch.android.codingcity.R;

/**
 * This is the Login Page which check the authentication in firbase
 * @author Ruize Nie
 */
public class LoginActivity extends AppCompatActivity {

    private TextView create_account;
    private Button sign_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        //create the account
        create_account = findViewById(R.id.tv_create_account);
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUp = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(signUp);
            }
        });

        sign_in = findViewById(R.id.btn_sign_in);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toHome = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(toHome);
            }
        });
    }
}
