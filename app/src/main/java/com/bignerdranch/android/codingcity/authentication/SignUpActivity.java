package com.bignerdranch.android.codingcity.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bignerdranch.android.codingcity.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * This is Sign up page for create new account and upload the user information
 * to the google firebase
 * @author Ruize Nie
 */
public class SignUpActivity extends AppCompatActivity {

    private EditText userName, userEmail, userPassword;
    private Button regBtn;
    private FirebaseAuth mAuth;
    private RadioButton radio;
    private ProgressBar progressBar;
    DatabaseReference rootDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();

        userName = findViewById(R.id.signup_user_name);
        userEmail = findViewById(R.id.signup_user_email);
        userPassword = findViewById(R.id.signup_user_password);
        radio = findViewById(R.id.signup_radio_btn);
        regBtn = findViewById(R.id.signup_btn);
        progressBar = findViewById(R.id.signup_progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        mAuth = FirebaseAuth.getInstance();
        rootDatabase = FirebaseDatabase.getInstance().getReference();

        regBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                regBtn.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                final String email = userEmail.getText().toString();
                final String password = userPassword.getText().toString();
                final String name = userName.getText().toString();

                if( email.isEmpty() || name.isEmpty() || password.isEmpty() || !radio.isChecked()) {
                    showMessage("Please Verify all fields");
                    regBtn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    CreateUserAccount(email,name,password);
                }
            }
        });
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }

    private void CreateUserAccount(String email, final String name, String password) {
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            rootDatabase.child("users").child(mAuth.getCurrentUser().getUid()).child("courses").child("starter").setValue("");
                            showMessage("Account created");
                            progressBar.setVisibility(View.INVISIBLE);
                            Intent toSignUp = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(toSignUp);
                        }else{
                            // account creation failed
                            showMessage("account creation failed" + task.getException().getMessage());
                            regBtn.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }
}
