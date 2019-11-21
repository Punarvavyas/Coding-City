package com.bignerdranch.android.codingcity.setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bignerdranch.android.codingcity.R;

public class SettingActivity extends AppCompatActivity {

    private SwitchCompat mSwitchV;
    private SwitchCompat mSwitchN;
    private boolean switchStatusV;
    private boolean switchStatusN;
    private RelativeLayout privacy;
    private RelativeLayout documentation;
    private RelativeLayout aboutUs;
    private RelativeLayout feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mSwitchV = findViewById(R.id.vibration);
        mSwitchN = findViewById(R.id.notification);

        privacy= findViewById(R.id.privacy);
        documentation= findViewById(R.id.documentation);
        aboutUs= findViewById(R.id.about_us);
        feedback= findViewById(R.id.feedback);

        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "privacy", Toast.LENGTH_SHORT).show();
            }
        });

        documentation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "documentation", Toast.LENGTH_SHORT).show();
            }
        });

        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "aboutUs", Toast.LENGTH_SHORT).show();
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "feedback", Toast.LENGTH_SHORT).show();
            }
        });

        final SharedPreferences spv = getSharedPreferences("Vibration", MODE_PRIVATE);
        switchStatusV = spv.getBoolean("Status", false);
        final SharedPreferences spn = getSharedPreferences("Notification", MODE_PRIVATE);
        switchStatusN = spn.getBoolean("Status", false);

        mSwitchV.setChecked(switchStatusV);
        mSwitchN.setChecked(switchStatusN);
        mSwitchV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchStatusV = !switchStatusV;
                mSwitchV.setChecked(switchStatusV);
                SharedPreferences.Editor editor = spv.edit();
                editor.putBoolean("Status", switchStatusV);
                editor.apply();
                Toast.makeText(getApplicationContext(), "Vibration is "+switchStatusV, Toast.LENGTH_SHORT).show();
            }
        });

        mSwitchN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchStatusN = !switchStatusN;
                mSwitchN.setChecked(switchStatusN);
                SharedPreferences.Editor editor = spn.edit();
                editor.putBoolean("Status", switchStatusN);
                editor.apply();
                Toast.makeText(getApplicationContext(), "Notification is "+switchStatusN, Toast.LENGTH_SHORT).show();
            }
        });
    }
}