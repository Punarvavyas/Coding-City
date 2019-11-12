package com.bignerdranch.android.codingcity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bignerdranch.android.codingcity.courseinfo.CourseContent;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ListView listView = (ListView) findViewById(R.id.search_courses);
        listView.setAdapter(new CourseAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), CourseEnrollmentActivity.class);
                startActivity(intent);
            }
        });
    }
    //TODO: search filter implementation


    private class CourseAdapter extends BaseAdapter {

        @Override
        //How many items are in the data set represented by this Adapter.
        public int getCount() {
            return 3;
        }

        @Override
        //Get the data item associated with the specified position in the data set.
        public Object getItem(int position) {
            return null;
        }

        @Override
        //Get the row id associated with the specified position in the list.
        public long getItemId(int position) {
            return 0;
        }

        @Override
        //Get a View that displays the data at the specified position in the data set.
        public View getView(int position, View convertView, ViewGroup parent) {
            View v;

            if(convertView == null){

                v = View.inflate(parent.getContext(), R.layout.list_course_item, null);

            }else{
                v = convertView;
            }

            return v;
        }
    }
}