package com.bignerdranch.android.codingcity.enrollment;

import android.content.Intent;
import android.os.Bundle;

import com.bignerdranch.android.codingcity.R;
import com.bignerdranch.android.codingcity.bean.MyAdapter;
import com.bignerdranch.android.codingcity.courseinfo.CourseContent;
import com.bignerdranch.android.codingcity.quizinfo.QuizActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CourseEnrollmentActivity extends AppCompatActivity {

    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_enrollment);
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

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        DatabaseReference rootDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = rootDatabase.child("courses").child(intent.getStringExtra("courseId"));
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("search act", dataSnapshot.toString());
                ImageView img = findViewById(R.id.enroll_img);
                img.setImageResource(R.drawable.javascript);
                TextView title = findViewById(R.id.enroll_title);
                title.setText(dataSnapshot.child("courseName").getValue().toString());
                TextView des = findViewById(R.id.enroll_description);
                des.setText("not available in database");
                Button enroll = findViewById(R.id.enroll_button);
                if(dataSnapshot.child("isPremium").getValue().toString() == "0"){
                    enroll.setText("Enroll Free");
                    // TODO: add data to firebase
                    Toast.makeText(CourseEnrollmentActivity.this, "Course added in user id", Toast.LENGTH_LONG);
                }
                else {
                    enroll.setText("Buy $3");
                    Toast.makeText(CourseEnrollmentActivity.this, "Buy page to be implemented", Toast.LENGTH_LONG);
                }
                //TODO: course comparison logic, can be helpful on home screen
                //                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
////                    courseData.add(new Course(postSnapshot.getValue()))
//                    if(postSnapshot.child("courseId").getValue().toString() == intent.getStringExtra("courseId")) {
//                        selectedCourse = new Course(postSnapshot.child("courseName").getValue().toString(),
//                                "description example", postSnapshot.child("courseName").getValue().toString(),
//                                "javascript", postSnapshot.child("courseId").getValue().toString());
//                    }
//                }


//                listView.setAdapter(new SearchActivity.CourseAdapter(SearchActivity.this, courseData, courseData.size()));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("main activity", "Failed to read value.", error.toException());
            }
        });
    }
}
