package com.bignerdranch.android.codingcity.enrollment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bignerdranch.android.codingcity.R;
import com.bignerdranch.android.codingcity.courseinfo.CourseContent;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CourseEnrollmentActivity extends AppCompatActivity {

    DatabaseReference rootDatabase = FirebaseDatabase.getInstance().getReference();
    DataSnapshot courseData;
    FirebaseUser currentUser;
    String courseId;
    ListView listView;
    boolean enrolled = false;
    MaterialButton freeEnroll;
    MaterialButton enrolledButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_enrollment);
        listView = findViewById(R.id.enrollment_page_content_listview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        freeEnroll = findViewById(R.id.enroll_button);
        enrolledButton = findViewById(R.id.enroll_button_done);
        enrolledButton.setVisibility(View.GONE);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        enrolledButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                Intent intent = new Intent(context, CourseContent.class);
                intent.putExtra("courseId", getIntent().getStringExtra("courseId"));
                context.startActivity(intent);
            }
        });
        freeEnroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enrolled = true;
                freeEnroll.setVisibility(View.GONE);
                enrolledButton.setVisibility(View.VISIBLE);
                rootDatabase.child("users").child(currentUser.getUid()).child("courses")
                        .child(courseId).setValue("");
                if (courseData.child("isPremium").getValue().toString().equals("1")) {
                    Intent intent = new Intent(getApplicationContext(), Payments.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(CourseEnrollmentActivity.this,
                            "You have been enrolled in this course", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference myRef = rootDatabase;
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //temp array
                ArrayList<String> coursesTemp = new ArrayList<>();
                // get list of courses
                for (DataSnapshot coursesList : dataSnapshot.child("users").child(currentUser.
                        getUid()).child("courses").getChildren()) {
                    coursesTemp.add(coursesList.getKey());
                }
                //if course is enrolled
                if (coursesTemp.contains(getIntent().getStringExtra("courseId"))) {
                    freeEnroll.setVisibility(View.GONE);
                    enrolledButton.setVisibility(View.VISIBLE);
                }
                //iterate to specific course
                dataSnapshot = dataSnapshot.child("courses").child(getIntent().getStringExtra("courseId"));
                courseData = dataSnapshot;
                // populate UI
                ImageView img = findViewById(R.id.enroll_img);
                img.setImageResource(R.drawable.javascript);
                img.setImageResource(getResources().getIdentifier(dataSnapshot.child("courseImageUri").getValue().toString(), "drawable", getPackageName()));
                TextView title = findViewById(R.id.enroll_title);
                title.setText(dataSnapshot.child("courseName").getValue().toString());
                TextView des = findViewById(R.id.enroll_description);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                des.setText(dataSnapshot.child("courseDescription").getValue().toString());
                Button enroll = findViewById(R.id.enroll_button);
                // Is selected course paid
                if (dataSnapshot.child("isPremium").getValue().toString().equals("0")) {
                    enroll.setText("Enroll Free");
                } else {
                    enroll.setText("Buy $3");
                }
                courseId = dataSnapshot.child("courseId").getValue().toString();
                ArrayList<String> courseLessons = new ArrayList<>();
                // fetch lessons
                for (DataSnapshot data : dataSnapshot.child("courseContents").getChildren()) {
                    for (DataSnapshot topics : data.getChildren()) {
                        String topic = topics.getKey();
                        courseLessons.add(topic);
                    }
                }
                listView.setAdapter(new LessonAdapter(getApplicationContext(), courseLessons, courseLessons.size()));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(getApplicationContext(), "Error retrieving data: check help section", Toast.LENGTH_LONG).show();
                 }
        });
    }

    private class LessonAdapter extends BaseAdapter {
        Context context;
        ArrayList<String> courseList;
        LayoutInflater inflater;
        int listSize;

        LessonAdapter(Context context, ArrayList<String> courseList, int size) {
            this.context = context;
            this.courseList = courseList;
            this.listSize = size;
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        //How many items are in the data set represented by this Adapter.
        public int getCount() {
            return listSize;
        }

        @Override
        //Get the data item associated with the specified position in the data set.
        public Object getItem(int position) {
            return courseList.get(position);
        }

        @Override
        //Get the row id associated with the specified position in the list.
        public long getItemId(int position) {
            return 0; //courseList.get(position).getId();
        }

        @Override
        //Get a View that displays the data at the specified position in the data set.
        public View getView(int position, View convertView, ViewGroup parent) {
            View v;
            if (convertView == null) {
                v = View.inflate(parent.getContext(), R.layout.lesson_enrollment_item, null);
            } else {
                v = convertView;
            }
            TextView tv = v.findViewById(R.id.lesson_item_tv);
            tv.setText(courseList.get(position));
            return v;
        }
    }
}
