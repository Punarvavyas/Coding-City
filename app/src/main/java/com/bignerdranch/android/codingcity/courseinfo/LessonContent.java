package com.bignerdranch.android.codingcity.courseinfo;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bignerdranch.android.codingcity.R;
//import io.github.kbiakov.codeview.CodeView;
//import io.github.kbiakov.codeview.OnCodeLineClickListener;
//import io.github.kbiakov.codeview.adapters.Format;
//import io.github.kbiakov.codeview.adapters.Options;
//import io.github.kbiakov.codeview.classifier.CodeProcessor;
//import io.github.kbiakov.codeview.highlight.ColorTheme;
//import io.github.kbiakov.codeview.highlight.Font;
//import io.github.kbiakov.codeview.highlight.FontCache;

/**
 * @Athor: Akshay Singh
 * Description: This class is responsible for displaying the lesson contents.
 */
public class LessonContent extends AppCompatActivity {
    TextView lessonText;
    TextView lessonTitle;
    //CodeView lessonText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //CodeProcessor.init(this);
        setContentView(R.layout.lesson_content);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lessonText = findViewById(R.id.ml_lesson_content);
        lessonTitle = findViewById(R.id.ml_lesson_title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        String lessonContent = intent.getStringExtra("lessonContent").trim();
        String lessonTitleText = intent.getStringExtra("lessonTitle").trim();
        Spanned htmlString = Html.fromHtml(lessonContent);
        lessonText.setText(htmlString);
        lessonTitle.setText(lessonTitleText);
    }
}
