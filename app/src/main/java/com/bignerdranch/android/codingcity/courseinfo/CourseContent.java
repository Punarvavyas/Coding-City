package com.bignerdranch.android.codingcity.courseinfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bignerdranch.android.codingcity.R;
import com.bignerdranch.android.codingcity.bean.MyAdapter;
import com.bignerdranch.android.codingcity.quizinfo.QuizActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
/**
 * This is the Lesson page which show what lesson we have each course right now
 * @author Ruize Nie
 */
public class CourseContent extends AppCompatActivity {

    private ListView listView;
    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_content);
        initList();

        this.listView = findViewById(R.id.list_lesson);
        final MyAdapter adapter = new MyAdapter(this, mList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(CourseContent.this, "Click item" + i, Toast.LENGTH_SHORT).show();
            }
        });

        adapter.setOnItemClickListener(new MyAdapter.onItemClickListener() {
            @Override
            public void onLessonClick(int i) {

                Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initList() {
        for (int i = 1; i < 5; i++) {
            mList.add(i + "");
        }
    }
}
