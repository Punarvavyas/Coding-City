package com.bignerdranch.android.codingcity.courseinfo;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.bignerdranch.android.codingcity.R;

import androidx.appcompat.app.AppCompatActivity;
/**
 * This is the Lesson page which show what lesson we have each course right now
 * @author Ruize Nie
 */
public class CourseContent extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_content);

        ListView lv = findViewById(R.id.list_lesson);
        lv.setAdapter(new MyListAdpter());

    }

    private class MyListAdpter extends BaseAdapter {

        @Override
        //How many items are in the data set represented by this Adapter.
        public int getCount() {
            return 4;
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
                //第一种写法
                v = View.inflate(getApplicationContext(), R.layout.lsit_lesson_item, null);

            }else{
                v = convertView;
            }

            return v;
        }
    }
}
