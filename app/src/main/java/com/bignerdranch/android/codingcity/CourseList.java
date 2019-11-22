package com.bignerdranch.android.codingcity;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bignerdranch.android.codingcity.courseinfo.Course;

import java.util.List;

public class CourseList extends ArrayAdapter<Course> {
    private Activity context;
    List<Course> cours;


    public CourseList(Activity context, List<Course> cours) {
        super(context, R.layout.list_item, cours);
        this.context = context;
        this.cours = cours;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_item, null, true);

        TextView textCourseName = (TextView) listViewItem.findViewById(R.id.tv_title);
        //TextView textCourseDescription = (TextView) listViewItem.findViewById(R.id.tv_content);

        Course course = cours.get(position);
        textCourseName.setText(course.getCourseName());
        //textCourseDescription.setText(course.getCourseDescription());

        return listViewItem;
    }

}
