package com.bignerdranch.android.codingcity.courseinfo;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
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

public class LessonContent extends AppCompatActivity {
    TextView lessonText;

    //CodeView lessonText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //CodeProcessor.init(this);
        setContentView(R.layout.lesson_content);
        //lessonText = (CodeView)findViewById(R.id.ml_lesson_content);
        lessonText = findViewById(R.id.ml_lesson_content);
        //lessonText.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        String lessonContent = intent.getStringExtra("lessonContent").trim();
        Spanned htmlString = Html.fromHtml(lessonContent);
        //lessonText.append(lessonContent);
        //lessonText.setCode(lessonContent);

        // expanded form of initialization


//        lessonText.setOptions(new Options(
//                this,                                   // context
//                lessonContent,                                  // code
//                "java",                                // language
//                ColorTheme.SOLARIZED_LIGHT.theme(),             // theme (data)
//                FontCache.get(this).getTypeface(this, Font.DroidSansMonoSlashed),   // font
//                Format.Default.getMedium(),                     // format
//                true,                          // animate on highlight
//                true,                                   // shadows visible
//                true,                                   // shortcut
//                getString(R.string.show_all),                    // shortcut note
//                1000,                                   // max lines
//                new OnCodeLineClickListener() {                  // line click listener
//                    @Override
//                    public void onCodeLineClicked(int n, @NotNull String line) {
//                        Log.i("ListingsActivity", "On " + (n + 1) + " line clicked");
//                    }
//                }));

        lessonText.setText(htmlString);
    }
}
