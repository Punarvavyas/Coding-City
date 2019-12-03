package com.bignerdranch.android.codingcity.enrollment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bignerdranch.android.codingcity.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    DatabaseReference rootDatabase = FirebaseDatabase.getInstance().getReference();
    DatabaseReference courseRef = rootDatabase.child("courses");
    ArrayList<Course> courseData = new ArrayList<>();
    ArrayList<Course> courseFilteredData = new ArrayList<>();
    ListView listView;
    TextInputEditText searchBar;
    DataSnapshot CourseDataSnapshot;
    CourseAdapter courseAdapterData;
    MaterialButton searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //fetch xml elements
        listView = findViewById(R.id.search_courses);
        courseFilteredData = courseData;
        courseAdapterData = new CourseAdapter(SearchActivity.this, courseFilteredData, courseFilteredData.size());
        listView.setAdapter(courseAdapterData);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        searchButton = findViewById(R.id.searchButton);
        // sense of completion
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Query completed", Toast.LENGTH_LONG).show();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), CourseEnrollmentActivity.class);
                intent.putExtra("courseId", courseFilteredData.get(position).getId());
                startActivity(intent);
            }
        });
        searchBar = findViewById(R.id.search_edit_text);
        //React on text change in search bar
        searchBar.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                courseFilteredData = new ArrayList<>();
                for (Course x : courseData) {
                    if (x.getName().toLowerCase().contains(s.toString().toLowerCase())) {
                        courseFilteredData.add(x);
                    }
                }

                courseAdapterData = new CourseAdapter(SearchActivity.this, courseFilteredData, courseFilteredData.size());
                listView.setAdapter(courseAdapterData);
                courseAdapterData.notifyDataSetChanged();
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
        courseData = new ArrayList<>();
        courseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CourseDataSnapshot = dataSnapshot;
                // get course data
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    courseData.add(new Course(postSnapshot.child("courseName").getValue().toString(),
                            postSnapshot.child("courseDescription").getValue().toString(), postSnapshot.child("courseName").getValue().toString(),
                            postSnapshot.child("courseImageUri").getValue().toString(), postSnapshot.child("courseId").getValue().toString()));
                }
                courseFilteredData = courseData;
                listView.setAdapter(new CourseAdapter(SearchActivity.this, courseFilteredData, courseData.size()));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(getApplicationContext(), "Error retrieving data: check help section", Toast.LENGTH_LONG).show();
            }
        });
    }

    private class CourseAdapter extends BaseAdapter {

        Context context;
        ArrayList<Course> courseList;
        LayoutInflater inflater;
        int listSize;

        CourseAdapter(Context context, ArrayList<Course> courseList, int size) {
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
                v = View.inflate(parent.getContext(), R.layout.list_course_item, null);
            } else {
                v = convertView;
            }
            // fill datat in item view
            TextView tv = v.findViewById(R.id.tv_title_course);
            tv.setText(courseList.get(position).getName());
            TextView tv2 = v.findViewById(R.id.tv_description_course);
            tv2.setText(courseList.get(position).getDescription());
            ImageView iv = v.findViewById(R.id.iv_course);
            iv.setImageResource(getResources().getIdentifier(courseList.get(position).getImgSrc(), "drawable", getPackageName()));
            return v;
        }
    }
}
