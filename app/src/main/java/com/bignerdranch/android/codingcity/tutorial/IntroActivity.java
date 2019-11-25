package com.bignerdranch.android.codingcity.tutorial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.bignerdranch.android.codingcity.MainActivity;
import com.bignerdranch.android.codingcity.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the Tutorial page which show basic instruction and give some introduction
 * @author Ruize Nie
 */
public class IntroActivity extends AppCompatActivity {

    private ViewPager tutorialPager;
    private TutorialViewPagerAdapter tutorialViewPagerAdapter;
    TabLayout tabIndicator;
    Button btnNext;
    Button btnSkip;
    Button btnStart;
    Animation btnAnim;
    int index = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        if (readCache()) {
//            Intent mainActivity = new Intent(getApplicationContext(),MainActivity.class);
//            startActivity(mainActivity);
//            finish();
//        }

        setContentView(R.layout.activity_intro);
        getSupportActionBar().hide();

        final List<TutorialItem> mList = new ArrayList<>();
        mList.add(new TutorialItem("Build Your Knowledge","This is a simple example. This is a simple example. This is a simple example.", R.drawable.intro_1));
        mList.add(new TutorialItem("Learn Coding","This is a simple example. This is a simple example. This is a simple example.", R.drawable.intro_2));
        mList.add(new TutorialItem("Complete Task","This is a simple example. This is a simple example. This is a simple example", R.drawable.intro_3));

        tutorialPager = findViewById(R.id.viewPager);
        tutorialViewPagerAdapter = new TutorialViewPagerAdapter(this, mList);
        tutorialPager.setAdapter(tutorialViewPagerAdapter);
        tabIndicator = findViewById(R.id.tab_indicator);
        tabIndicator.setupWithViewPager(tutorialPager);

        btnNext = findViewById(R.id.btn_next);
        btnSkip = findViewById(R.id.btn_skip);
        btnStart = findViewById(R.id.btn_start);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.btn_anim);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = tutorialPager.getCurrentItem();
                if (index < mList.size()) {
                    index++;
                    tutorialPager.setCurrentItem(index);
                }
                if (index == mList.size()-1) {
                    btnNext.setVisibility(View.INVISIBLE);
                    tabIndicator.setVisibility(View.INVISIBLE);
                    btnStart.setVisibility(View.VISIBLE);
                    btnSkip.setVisibility(View.INVISIBLE);
                    btnStart.setAnimation(btnAnim);
                }
            }
        });

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

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainActivity);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                //saveCache();
                finish();
            }
        });
    }

//    private void saveCache() {
//        SharedPreferences pref = getApplicationContext().getSharedPreferences("IntroState",MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putBoolean("Opened",true);
//        editor.commit();
//    }
//
//    private boolean readCache() {
//        SharedPreferences pref = getApplicationContext().getSharedPreferences("IntroState",MODE_PRIVATE);
//        boolean open = pref.getBoolean("Opened",false);
//        return  open;
//    }
}