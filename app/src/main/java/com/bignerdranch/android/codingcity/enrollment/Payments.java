package com.bignerdranch.android.codingcity.enrollment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bignerdranch.android.codingcity.R;
import com.braintreepayments.cardform.view.CardForm;

public class Payments extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);
        CardForm cardForm = (CardForm) findViewById(R.id.card_form);
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .cardholderName(CardForm.FIELD_REQUIRED)
                .postalCodeRequired(true)
                .actionLabel("Purchase")
                .setup(this);
    }
}
