package com.bignerdranch.android.codingcity.bottomnavigation.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bignerdranch.android.codingcity.R;
import com.bignerdranch.android.codingcity.courseinfo.Course;
import com.bignerdranch.android.codingcity.courseinfo.CourseActivity;
import com.bignerdranch.android.codingcity.courseinfo.CourseContent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is the home page which show what course we have right now
 * @author Ruize Nie
 * @Modified by Akshay Singh
 */
public class HomeFragment extends Fragment {

    DatabaseReference rootDatabase = FirebaseDatabase.getInstance().getReference();
    DatabaseReference myRef = rootDatabase.child("courses");
    ArrayList<Course> courseData = new ArrayList<>();
    ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container,
                false);
        listView = rootView.findViewById(R.id.list_course);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), CourseContent.class);
                intent.putExtra("courseId", courseData.get(position).getCourseId());
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        getAllCourses();
    }

    private void getAllCourses(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                courseData.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Log.e("courseId", postSnapshot.child("courseId").getValue().toString());// values fetched
                    String courseId = postSnapshot.child("courseId").getValue().toString().trim();

                    courseData.add(
                            new Course(
                                    courseId,
                                    postSnapshot.child("courseName").getValue().toString(),
                                    postSnapshot.child("courseDescription").getValue().toString(),
                                    postSnapshot.child("courseImageUri").getValue().toString(),
                                    postSnapshot.child("isPremium").getValue().toString())
                    );
                }

                listView.setAdapter(new HomeFragment.CourseAdapter(getContext(), courseData, courseData.size()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * To be implemented
     * @param userId
     */
    private void getCourseByUserId(String userId){
        final Intent intent = getActivity().getIntent();
        final List<String> enrolledCourses = Arrays.asList(intent.getStringArrayExtra("courseId"));
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                courseData.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Log.e("courseId", postSnapshot.child("courseId").getValue().toString());// values fetched
                    String courseId = postSnapshot.child("courseId").getValue().toString().trim();

                    if(enrolledCourses != null){
                        if(enrolledCourses.contains(courseId)){
                            courseData.add(
                                    new Course(
                                            courseId,
                                            postSnapshot.child("courseName").getValue().toString(),
                                            postSnapshot.child("courseDescription").getValue().toString(),
                                            postSnapshot.child("courseImageUri").getValue().toString(),
                                            postSnapshot.child("isPremium").getValue().toString())
                            );
                        }
                    }
                }

                listView.setAdapter(new HomeFragment.CourseAdapter(getContext(), courseData, courseData.size()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private class CourseAdapter extends BaseAdapter {

        Context context;
        ArrayList<Course> courseList;
        LayoutInflater inflater;
        int limit;
        CourseAdapter(Context context, ArrayList<Course> courseList, int size){
            this.context = context;
            this.courseList = courseList;
            this.limit = size;
        }

        @Override
        //How many items are in the data set represented by this Adapter.
        public int getCount() {
            return limit;
        }

        @Override
        //Get the data item associated with the specified position in the data set.
        public Object getItem(int position) {
            return courseList.get(position);
        }

        @Override
        //Get the row id associated with the specified position in the list.
        public long getItemId(int position) {
            return 0; //al.get(position).getId();
        }

        @Override
        //Get a View that displays the data at the specified position in the data set.
        public View getView(int position, View convertView, ViewGroup parent) {
            inflater = LayoutInflater.from(getContext());
            View thisView = View.inflate(parent.getContext(), R.layout.list_course_item, null);
            TextView tvCourseName = (TextView) thisView.findViewById(R.id.tv_title_course);
            TextView tvCourceDescription = (TextView) thisView.findViewById(R.id.tv_description_course);
            ImageView imageView = (ImageView) thisView.findViewById(R.id.iv_course);
            tvCourseName.setText(courseList.get(position).getCourseName());
            tvCourceDescription.setText(courseList.get(position).getCourseDescription());
            String uri = courseList.get(position).getCourseImageUrl();
            int icon = getResources().getIdentifier(uri, "drawable", getActivity().getPackageName());
            imageView.setImageResource(icon);

            return thisView;
        }
    }
}