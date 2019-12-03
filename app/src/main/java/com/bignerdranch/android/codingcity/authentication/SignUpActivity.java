package com.bignerdranch.android.codingcity.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bignerdranch.android.codingcity.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * This is Sign up page for create new account and upload the user information
 * to the google firebase
 *
 * @author Ruize Nie
 */
public class SignUpActivity extends AppCompatActivity {

    private DatabaseReference rootDatabase;
    private EditText userName, userEmail, userPassword;
    private Button regBtn;
    private FirebaseAuth mAuth;
    private RadioButton radio;
    private ProgressBar progressBar;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();

        // find specific component using id
        userName = findViewById(R.id.signup_user_name);
        userEmail = findViewById(R.id.signup_user_email);
        userPassword = findViewById(R.id.signup_user_password);
        radio = findViewById(R.id.signup_radio_btn);
        regBtn = findViewById(R.id.signup_btn);
        progressBar = findViewById(R.id.signup_progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        // get firebase auth instance and firebase realtime database instance
        mAuth = FirebaseAuth.getInstance();
        rootDatabase = FirebaseDatabase.getInstance().getReference();

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regBtn.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                final String email = userEmail.getText().toString();
                final String password = userPassword.getText().toString();
                name = userName.getText().toString();
                Log.e("Sign up", name);

                // check the field in order to register the account
                if (email.isEmpty() || name.isEmpty() || password.isEmpty() || !radio.isChecked()) {
                    showMessage("Please Verify all fields");
                    regBtn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                } else {

                    //create a account using Firebase SDK
                    createUserAccount(email, name, password);
                }
            }
        });
    }

    // show error message
    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private void createUserAccount(String email, final String name, String password) {

        //create a account using Firebase SDK
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            showMessage("Account created");
                            progressBar.setVisibility(View.INVISIBLE);
                            Intent toSignUp = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(toSignUp);

                            // update the profile when user success create the account
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();
                            mAuth.getCurrentUser().updateProfile(profileUpdates);

                            // update information on Firebase Realtime database
                            rootDatabase.child("users").child(mAuth.getCurrentUser().getUid()).child("courses").child("starter").setValue("");
                            rootDatabase.child("users").child(mAuth.getCurrentUser().getUid()).child("name").setValue(
                                    mAuth.getCurrentUser().getDisplayName() == null || mAuth.getCurrentUser().getDisplayName() == "" ?
                                            "No username" : mAuth.getCurrentUser().getDisplayName());
                            rootDatabase.child("users").child(mAuth.getCurrentUser().getUid()).child("email").setValue(mAuth.getCurrentUser().getEmail());
                            rootDatabase.child("users").child(mAuth.getCurrentUser().getUid()).child("profileImageUri").setValue("@drawable/user_photo");

                        } else {
                            // account creation failed
                            showMessage("account creation failed" + task.getException().getMessage());
                            regBtn.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }
}
