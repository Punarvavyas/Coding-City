package com.bignerdranch.android.codingcity;

import android.content.Intent;
import android.os.Bundle;

import com.bignerdranch.android.codingcity.bean.MyAdapter;
import com.bignerdranch.android.codingcity.courseinfo.CourseContent;
import com.bignerdranch.android.codingcity.quizinfo.QuizActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CourseEnrollmentActivity extends AppCompatActivity {


    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_enrollment);
        initList();
        ListView listView = findViewById(R.id.enrollment_page_content_listview);
        final MyAdapter adapter = new MyAdapter(this, mList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "Click item" + i, Toast.LENGTH_SHORT).show();
            }
        });

        adapter.setOnItemClickListener(new MyAdapter.onItemClickListener() {
            @Override
            public void onLessonClick(int i) {
//                Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
//                startActivity(intent);
            }
        });
    }

    private void initList() {
        for (int i = 1; i < 5; i++) {
            mList.add(i + "");
        }
    }
}
