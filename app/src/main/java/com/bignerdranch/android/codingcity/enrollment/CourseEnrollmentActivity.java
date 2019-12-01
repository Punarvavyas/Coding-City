package com.bignerdranch.android.codingcity.enrollment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CourseEnrollmentActivity extends AppCompatActivity {

    DataSnapshot courseData;
    DatabaseReference rootDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth mAuth;
    String courseId;
    ListView listView;
    private ArrayList<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_enrollment);
        listView = findViewById(R.id.enrollment_page_content_listview);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LessonAdapter adapter = new LessonAdapter(this, mList, mList.size());
        listView.setAdapter(adapter);
        Button btn = findViewById(R.id.enroll_button);
        mAuth = FirebaseAuth.getInstance();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootDatabase.child("users").child(mAuth.getCurrentUser().getUid()).child("courses").child(courseId).setValue("");
                Toast.makeText(CourseEnrollmentActivity.this, "You have been enrolled in this course", Toast.LENGTH_LONG);
                if (courseData.child("isPremium").getValue().toString().equals("1")) {
                    Intent intent = new Intent(getApplicationContext(), Payments.class);
                    startActivity(intent);
                }
            }
        });
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
        DatabaseReference rootDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = rootDatabase.child("courses").child(intent.getStringExtra("courseId"));
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                courseData = dataSnapshot;
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
                if (dataSnapshot.child("isPremium").getValue().toString().equals("0")) {
                    enroll.setText("Enroll Free");
                } else {
                    enroll.setText("Buy $3");
                }
                courseId = dataSnapshot.child("courseId").getValue().toString();
                ArrayList<String> courseLessons = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.child("courseContents").getChildren()) {
                    String key = data.getKey();
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
                Log.e("main activity", "Failed to read value.", error.toException());
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
