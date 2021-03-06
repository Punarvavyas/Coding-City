package com.bignerdranch.android.codingcity.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bignerdranch.android.codingcity.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * This class is used for user to reset their password by using email address
 *
 * @author Ruize Nie
 */
public class ResetPasswordActivity extends AppCompatActivity {

    private EditText resetEmail;
    private Button sendEmail;
    private FirebaseAuth firebaseAuth;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        // enable the go back button on top left side
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // find specific component using id
        resetEmail = findViewById(R.id.reset_user_email);
        sendEmail = findViewById(R.id.reset_send_email);
        firebaseAuth = FirebaseAuth.getInstance();

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(resetEmail.getText())){
                    userEmail = resetEmail.getText().toString();

                    // using Firebase SDK to send the reset password email
                    firebaseAuth.sendPasswordResetEmail(userEmail)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){

                                    //give hint when email sent
                                    Toast.makeText(getApplicationContext(), "Reset Password Email Sended", Toast.LENGTH_SHORT).show();
                                    Intent toHome = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(toHome);
                                }
                            }
                        });
                }else{
                    // avoid empty email address
                    Toast.makeText(getApplicationContext(), "Please Enter Email Address", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    // finish activity when back button on top left side clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
