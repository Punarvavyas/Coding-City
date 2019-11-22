package com.bignerdranch.android.codingcity.dbhelper;

import com.bignerdranch.android.codingcity.courseinfo.Course;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceCourses;
    private List<Course> courses = new ArrayList<Course>();
    private String courseNode = "courses";


    public interface DataStatus {
        void DataIsLoaded(List<Course> courses, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }
    public FirebaseDatabaseHelper(){
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceCourses = mDatabase.getReference(courseNode);
    }

    public void readCourses(final DataStatus dataStatus){
        mReferenceCourses.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                courses.clear();
                List<String> keys = new ArrayList<String>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Course course = keyNode.getValue(Course.class);
                    courses.add(course);
                }
                dataStatus.DataIsLoaded(courses, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
