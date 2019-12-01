package com.bignerdranch.android.codingcity.enrollment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bignerdranch.android.codingcity.R;
import com.braintreepayments.cardform.view.CardForm;

public class Payments extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);
        CardForm cardForm = findViewById(R.id.card_form);
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .cardholderName(CardForm.FIELD_REQUIRED)
                .postalCodeRequired(true)
                .actionLabel("Purchase")
                .setup(this);
        Button pay = findViewById(R.id.payButton);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "You have been enrolled in this course", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
