package com.bignerdranch.android.codingcity.courseinfo;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bignerdranch.android.codingcity.R;
import com.bignerdranch.android.codingcity.quizinfo.QuizActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the Lesson page which show what lesson we have each course right now
 *
 * @author Akshay Singh
 */
public class CourseContent extends AppCompatActivity {

    List<Lessons> courseLessons = new ArrayList<>();
    ListView listView;
    ListView quizListView;
    ImageView headerImage;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_content);
        listView = findViewById(R.id.list_lesson);
        quizListView = findViewById(R.id.quiz_list);
        headerImage = findViewById(R.id.lesson_picture);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = getApplicationContext();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String lessonTitle = courseLessons.get(position).getLessonName();
                Intent intent = new Intent(getApplicationContext(), LessonContent.class);
                intent.putExtra("lessonContent", courseLessons.get(position).getLessonText());
                intent.putExtra("lessonTitle", lessonTitle);
                populateVisitedLessons(lessonTitle);
                startActivity(intent);
            }
        });
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        quizListView.setVisibility(View.GONE);
                        listView.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        listView.setVisibility(View.GONE);
                        quizListView.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                headerImage.setImageResource(getResources().getIdentifier(dataSnapshot.child("courseImageUri").getValue().toString(),
                        "drawable", context.getPackageName()));
                populateLessonContent(dataSnapshot);
                getVisitedLessons();
                CourseContentAdapter adapter = getCourseContentAdapter();
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        DatabaseReference myRefQuiz = rootDatabase;
        final String courseId = intent.getStringExtra("courseId");
        myRefQuiz.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> quizzesAttempted = new ArrayList<>();
                ArrayList<String> quizzesAttemptedMarks = new ArrayList<>();
                DataSnapshot taken = dataSnapshot.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("quizzes").child(courseId);
                for (DataSnapshot x : taken.getChildren()) {
                    quizzesAttempted.add(x.getKey());
                    quizzesAttemptedMarks.add(x.getValue().toString());
                }
                DataSnapshot ds = dataSnapshot.child("courses").child(getIntent().getStringExtra("courseId"))
                        .child("quiz");
                ArrayList<String> quizIds = new ArrayList<>();

                for (DataSnapshot x : ds.getChildren()) {
                    quizIds.add(x.getKey());
                }
                QuizListAdapter qz = new QuizListAdapter(getApplicationContext(), quizIds, quizIds.size(), courseId, quizzesAttempted, quizzesAttemptedMarks);
                quizListView.setAdapter(qz);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void populateVisitedLessons(final String lessonTitle){
        final StringBuilder sb_lessons = new StringBuilder();
        final String courseId = getIntent().getStringExtra("courseId");
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference rootDatabase = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference coursesRef = rootDatabase.child("users").child(userId).child("courses");
        coursesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {

                    for(DataSnapshot course : dataSnapshot.getChildren()){
                        if(course.getKey().equalsIgnoreCase(courseId)){
                            sb_lessons.append(course.getValue());
                        }
                    }

                    if(sb_lessons.indexOf(lessonTitle) < 0){
                        if(sb_lessons.length() == 0){
                            sb_lessons.append(lessonTitle);
                        }
                        else{
                            sb_lessons.append(",");
                            sb_lessons.append(lessonTitle);
                        }
                        coursesRef.child(courseId).setValue(sb_lessons.toString());
                    }
                }
                catch(Exception ex){
                    Log.e("ERROR",ex.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getVisitedLessons(){
        final StringBuilder sb_lessons = new StringBuilder();
        final String courseId = getIntent().getStringExtra("courseId");
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference rootDatabase = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference coursesRef = rootDatabase.child("users").child(userId).child("courses");
        coursesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {

                    for(DataSnapshot course : dataSnapshot.getChildren()){
                        if(course.getKey().equalsIgnoreCase(courseId)){
                            sb_lessons.append(course.getValue());
                        }
                    }

                   String[] lessons = sb_lessons.toString().split(",");
                    for(String lesson : lessons){
                        for(Lessons lessonObj : courseLessons){
                            if(lessonObj.getLessonName().equalsIgnoreCase(lesson)){
                                lessonObj.setVisited(true);
                            }
                        }
                    }
                }
                catch(Exception ex){
                    Log.e("ERROR",ex.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void populateLessonContent(DataSnapshot dataSnapshot) {
        courseLessons.clear();
        for (DataSnapshot data : dataSnapshot.child("courseContents").getChildren()) {
            String key = data.getKey();
            for (DataSnapshot topics : data.getChildren()) {
                String topic = topics.getKey();
                String topic_content = topics.getValue().toString();
                Lessons lesson = new Lessons(topic, topic_content);
                courseLessons.add(lesson);
            }
        }
    }

    private CourseContentAdapter getCourseContentAdapter() {
        CourseContentAdapter adapter = new CourseContentAdapter(CourseContent.this, courseLessons, courseLessons.size());
        return adapter;
    }

    private class CourseContentAdapter extends BaseAdapter {
        Context context;
        List<Lessons> lessons;
        LayoutInflater inflater;
        int limit;

        public CourseContentAdapter(Context context, List<Lessons> localLessons, int size) {
            context = context;
            lessons = localLessons;
            limit = size;
            if (lessons != null) {
            } else {
            }
        }

        @Override
        public int getCount() {
            return limit;
        }

        @Override
        public Object getItem(int position) {
            return lessons.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            inflater = LayoutInflater.from(getApplicationContext());
            View thisView = View.inflate(parent.getContext(), R.layout.list_lesson_item, null);
            TextView tvLessonTitle = thisView.findViewById(R.id.tv_title);
            ImageView visitedIcon = thisView.findViewById(R.id.icon_visited);
            //TextView tvLessonText = (TextView) thisView.findViewById(R.id.tv_lesson_text);

            List<Lessons> lessonsList = lessons;
            String lesson_topic = lessonsList.get(position).getLessonName();
            String lesson_text = lessonsList.get(position).getLessonText();
            boolean isVisited = lessonsList.get(position).isVisited();
            tvLessonTitle.setText(lesson_topic);
            if(isVisited){
                visitedIcon.setVisibility(View.VISIBLE);
            }
            else{
                visitedIcon.setVisibility(View.INVISIBLE);
            }
            //tvLessonText.setText(lesson_text);
            return thisView;
        }
    }

    private class QuizListAdapter extends BaseAdapter {

        Context context;
        ArrayList<String> quizList;
        LayoutInflater inflater;
        int listSize;
        String courseId;
        ArrayList<String> quizzesAttempted;
        ArrayList<String> quizzesAttemptedMarks;

        QuizListAdapter(Context context, ArrayList<String> courseList, int size, String courseIdTemp,
                        ArrayList quizAttemptedTemp, ArrayList quizzesAttemptedMarksTemp) {
            this.context = context;
            this.quizList = courseList;
            this.listSize = size;
            courseId = courseIdTemp;
            quizzesAttempted = quizAttemptedTemp;
            quizzesAttemptedMarks = quizzesAttemptedMarksTemp;
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
            return quizList.get(position);
        }

        @Override
        //Get the row id associated with the specified position in the list.
        public long getItemId(int position) {
            return 0; //courseList.get(position).getId();
        }

        @Override
        //Get a View that displays the data at the specified position in the data set.
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v;
            if (convertView == null) {
                v = View.inflate(parent.getContext(), R.layout.quiz_list_item, null);
            } else {
                v = convertView;
            }
            TextView tv = v.findViewById(R.id.quiz_tv);
            tv.setText(quizList.get(position));
            Button bt = v.findViewById(R.id.qz_button);
            TextView tv2 = v.findViewById(R.id.result);

            if (quizzesAttempted.contains(quizList.get(position))) {
                bt.setVisibility(View.GONE);
                tv2.setVisibility(View.VISIBLE);
                Log.e("marks", quizzesAttemptedMarks.toString());
                tv2.setText((String.valueOf((Integer.parseInt(
                            quizzesAttemptedMarks.get(quizzesAttempted.indexOf(quizList.get(position)))
                    )*100) /5) + "%"));
            }
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                    intent.putExtra("quizId", quizList.get(position));
                    intent.putExtra("courseId", courseId);
                    startActivity(intent);
                }
            });
            return v;
        }
    }
}
