package com.bignerdranch.android.codingcity.authentication;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bignerdranch.android.codingcity.MainActivity;
import com.bignerdranch.android.codingcity.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * This is the Login Page which check the authentication in Firebase
 *
 * @author Ruize Nie
 */
public class LoginActivity extends AppCompatActivity {

    private EditText userMail, userPassword;
    private ProgressBar loginProgress;
    private FirebaseAuth mAuth;
    private TextView createAccount;
    private Button signIn;
    private TextView reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        // find specific component using id
        userMail = findViewById(R.id.login_user_email);
        userPassword = findViewById(R.id.login_user_password);
        loginProgress = findViewById(R.id.login_progressBar);

        // instance the Firebase Authentication
        mAuth = FirebaseAuth.getInstance();
        loginProgress.setVisibility(View.INVISIBLE);

        //create the account
        createAccount = findViewById(R.id.login_create_account_tv);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to sign up page
                Intent signUp = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(signUp);
            }
        });

        signIn = findViewById(R.id.login_btn_sign_in);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // using given email and password to sign in the app
                loginProgress.setVisibility(View.VISIBLE);
                signIn.setVisibility(View.INVISIBLE);

                final String mail = userMail.getText().toString();
                final String password = userPassword.getText().toString();

                if (mail.isEmpty() || password.isEmpty()) {
                    showMessage("Please Enter All Field");
                } else {
                    signIn(mail, password);
                }
                signIn.setVisibility(View.VISIBLE);
                loginProgress.setVisibility(View.INVISIBLE);
            }
        });

        //reset the password
        reset = findViewById(R.id.login_forget_password);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toReset = new Intent(getApplicationContext(), ResetPasswordActivity.class);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                startActivity(toReset);
            }
        });
    }

    // show error message
    private void showMessage(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

    // check the email and password
    // login the home dashboard when information is correct
    private void signIn(String mail, String password) {
        // check the network available before sign in
        if(isNetworkAvailable()) {
            mAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    // redirect to home page when success
                    if (task.isSuccessful()) {
                        loginProgress.setVisibility(View.INVISIBLE);
                        signIn.setVisibility(View.VISIBLE);
                        Intent toHome = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(toHome);
                        finish();
                    } else {
                        showMessage(task.getException().getMessage());
                        signIn.setVisibility(View.VISIBLE);
                        loginProgress.setVisibility(View.INVISIBLE);
                    }
                }
            });
        } else{
            showMessage("Make sure you turn of your network");
        }
    }

    // override the onBackPressed in order to avoid login again
    // when they sign out the app
    @Override
    public void onBackPressed() {
    }

    // Check the user's network state
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
