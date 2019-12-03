package com.bignerdranch.android.codingcity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class Help extends AppCompatActivity {
    EditText editTextHelpQueries;
    Button btnQuerySent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        editTextHelpQueries = findViewById(R.id.edittxt_help_queries);
        btnQuerySent = findViewById(R.id.btnQuerySent);

        btnQuerySent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"codingcity001@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Mail from app!");
                intent.putExtra(Intent.EXTRA_TEXT, editTextHelpQueries.getText());
                try {
                    startActivity(Intent.createChooser(intent, "How to send mail?"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.query_unsuccessful),
                             Toast.LENGTH_LONG ).show();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

}
