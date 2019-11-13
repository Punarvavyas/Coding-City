package com.bignerdranch.android.codingcity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bignerdranch.android.codingcity.courseinfo.CourseContent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    DatabaseReference rootDatabase = FirebaseDatabase.getInstance().getReference();
    DatabaseReference myRef = rootDatabase.child("courses");
    ArrayList<Course> courseData = new ArrayList<>();
    ListView listView;
    DataSnapshot CourseDataSnapshot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        listView = (ListView) findViewById(R.id.search_courses);
        listView.setAdapter(new CourseAdapter( SearchActivity.this, courseData, courseData.size()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), CourseEnrollmentActivity.class);
                intent.putExtra("courseId", courseData.get(position).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CourseDataSnapshot = dataSnapshot;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//                    courseData.add(new Course(postSnapshot.getValue()))
                    Log.e("ds", postSnapshot.toString());// values fetched
                        //TODO: img and description
                    courseData.add(new Course(postSnapshot.child("courseName").getValue().toString(), "description example", postSnapshot.child("courseName").getValue().toString(),"javascript", postSnapshot.child("courseId").getValue().toString()));
                }
                listView.setAdapter(new CourseAdapter(SearchActivity.this, courseData, courseData.size()));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("main activity", "Failed to read value.", error.toException());
            }
        });
    }
    //TODO: search filter implementation

    private class CourseAdapter extends BaseAdapter {

        Context context;
        ArrayList<Course> al;
        LayoutInflater inflater;
        int limit;
        CourseAdapter(Context context, ArrayList<Course> al, int size){
            this.context = context;
            this.al = al;
            this.limit = size;
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        //How many items are in the data set represented by this Adapter.
        public int getCount() {
            return limit;
        }

        @Override
        //Get the data item associated with the specified position in the data set.
        public Object getItem(int position) {
            return al.get(position);
        }

        @Override
        //Get the row id associated with the specified position in the list.
        public long getItemId(int position) {
            return 0; //al.get(position).getId();
        }

        @Override
        //Get a View that displays the data at the specified position in the data set.
        public View getView(int position, View convertView, ViewGroup parent) {
            View v;

            if(convertView == null){

                v = View.inflate(parent.getContext(), R.layout.list_course_item, null);

            } else {
                v = convertView;
                TextView tv = (TextView) v.findViewById(R.id.tv_title_course);
                tv.setText(al.get(position).getName());
            }

            return v;
        }
    }

    private class Course {
        String name = "";
        String description = "";
        Boolean premium = false;
        String img = "";
        String id = "";

        Course(String a, String b, String c, String d, String e) {
            name = a;
            description = b;
            premium = c.equals("0");
            img = d;
            id = e;
        }

        public String getId() {
            return id;
        }

        public String getDescription() {
            return description;
        }

        public String getImg() {
            return img;
        }

        public String getName() {
            return name;
        }

        public Boolean getPremium() {
            return premium;
        }
    }
}
