package com.bignerdranch.android.codingcity.enrollment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bignerdranch.android.codingcity.R;
import com.bignerdranch.android.codingcity.bean.MyAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CourseEnrollmentActivity extends AppCompatActivity {

    private List<String> mList = new ArrayList<>();
    DataSnapshot courseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_enrollment);
        ListView listView = findViewById(R.id.enrollment_page_content_listview);
        final MyAdapter adapter = new MyAdapter(this, mList);
        listView.setAdapter(adapter);
        Button btn = findViewById(R.id.enroll_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (courseData.child("isPremium").getValue().toString().equals("0")) {
                    Toast.makeText(CourseEnrollmentActivity.this, "You have been enrolled in this course", Toast.LENGTH_LONG);
                } else {
                    Intent intent = new Intent(getApplicationContext(), Payments.class);
                    startActivity(intent);
                }
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
                courseData = dataSnapshot;
                ImageView img = findViewById(R.id.enroll_img);
                img.setImageResource(R.drawable.javascript);
                TextView title = findViewById(R.id.enroll_title);
                title.setText(dataSnapshot.child("courseName").getValue().toString());
                TextView des = findViewById(R.id.enroll_description);
                des.setText(dataSnapshot.child("courseDescription").getValue().toString());
                Button enroll = findViewById(R.id.enroll_button);
                Log.e("x", "" + Integer.parseInt(dataSnapshot.child("isPremium").getValue().toString()));
                if (dataSnapshot.child("isPremium").getValue().toString().equals("0")) {
                    enroll.setText("Enroll Free");
                } else {
                    enroll.setText("Buy $3");
                }
}

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("main activity", "Failed to read value.", error.toException());
            }
        });
    }
}
