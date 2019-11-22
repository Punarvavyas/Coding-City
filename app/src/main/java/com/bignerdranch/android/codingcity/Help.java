package com.bignerdranch.android.codingcity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;


public class Help extends AppCompatActivity {
    TextView txthelp;
    TextView txthelp1;
    TextView txthelp2;
    TextView txthelp3;
    TextView txthelp4;
    TextView txthelp5;
    TextView txthelp6;
    TextView txthelp7;
    TextView txthelp8;
    EditText edittxt_help_queries;
    Button btnQuerySent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        edittxt_help_queries = findViewById(R.id.edittxt_help_queries);
        btnQuerySent= findViewById(R.id.btnQuerySent);

        btnQuerySent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"codingcity001@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Mail from app!");
                intent.putExtra(Intent.EXTRA_TEXT, edittxt_help_queries.getText());

                try {
                    startActivity(Intent.createChooser(intent, "How to send mail?"));
                } catch (android.content.ActivityNotFoundException ex) {
                    //do something else
                }
            }
        });

    }
}
