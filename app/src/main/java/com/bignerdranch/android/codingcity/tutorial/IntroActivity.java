package com.bignerdranch.android.codingcity.tutorial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bignerdranch.android.codingcity.R;
import com.bignerdranch.android.codingcity.authentication.LoginActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the Tutorial page which show basic instruction and give some introduction
 *
 * @author Ruize Nie
 */
public class IntroActivity extends AppCompatActivity {

    TabLayout tabIndicator;
    Button btnNext;
    Button btnSkip;
    Button btnStart;
    Animation btnAnim;
    int index = 0;
    private ViewPager tutorialPager;
    private TutorialViewPagerAdapter tutorialViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // check the if the user use this app before
        if (readCache()) {
            Intent mainActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(mainActivity);
            finish();
        }

        setContentView(R.layout.activity_intro);
        getSupportActionBar().hide();

        // set the welcome page
        final List<TutorialItem> mList = new ArrayList<>();
        mList.add(new TutorialItem(getResources().getString(R.string.build_intro), getResources().getString(R.string.build_des), R.drawable.intro_1));
        mList.add(new TutorialItem(getResources().getString(R.string.learn_intro), getResources().getString(R.string.learn_des), R.drawable.intro_2));
        mList.add(new TutorialItem(getResources().getString(R.string.comp_intro), getResources().getString(R.string.comp_des), R.drawable.intro_3));

        // find specific component using id
        tutorialPager = findViewById(R.id.viewPager);
        tutorialViewPagerAdapter = new TutorialViewPagerAdapter(this, mList);
        tutorialPager.setAdapter(tutorialViewPagerAdapter);
        tabIndicator = findViewById(R.id.tab_indicator);
        tabIndicator.setupWithViewPager(tutorialPager);

        btnNext = findViewById(R.id.btn_next);
        btnSkip = findViewById(R.id.btn_skip);
        btnStart = findViewById(R.id.btn_start);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btn_anim);

        // next button implement
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set appropriate page using index
                index = tutorialPager.getCurrentItem();
                if (index < mList.size()) {
                    index++;
                    tutorialPager.setCurrentItem(index);
                }
                if (index == mList.size() - 1) {
                    // disable the button when page come to the end
                    btnNext.setVisibility(View.INVISIBLE);
                    tabIndicator.setVisibility(View.INVISIBLE);
                    btnStart.setVisibility(View.VISIBLE);
                    btnSkip.setVisibility(View.INVISIBLE);
                    btnStart.setAnimation(btnAnim);
                }
            }
        });

        // when use press skip, just go to the last page the tutorial
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tutorialPager.setCurrentItem(mList.size());
                btnSkip.setVisibility(View.INVISIBLE);
                tabIndicator.setVisibility(View.INVISIBLE);
                btnNext.setVisibility(View.INVISIBLE);
                btnStart.setVisibility(View.VISIBLE);
                btnStart.setAnimation(btnAnim);
            }
        });

        // after user click on start, then go to login page
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivity = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(mainActivity);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                saveCache();
                finish();
            }
        });
    }

    // save the user's status using cache
    private void saveCache() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("IntroState", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("Opened", true);
        editor.commit();
    }

    // check the user's status using cache
    private boolean readCache() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("IntroState", MODE_PRIVATE);
        boolean open = pref.getBoolean("Opened", false);
        return open;
    }
}