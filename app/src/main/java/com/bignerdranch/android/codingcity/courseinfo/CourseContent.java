package com.bignerdranch.android.codingcity.courseinfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_content);
        listView = findViewById(R.id.list_lesson);
        quizListView = findViewById(R.id.quiz_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), LessonContent.class);
                intent.putExtra("lessonContent", courseLessons.get(position).getLessonText());
                startActivity(intent);
            }
        });
        quizListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), LessonContent.class);
                intent.putExtra("lessonContent", courseLessons.get(position).getLessonText());
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
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        DatabaseReference rootDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = rootDatabase.child("courses").child(intent.getStringExtra("courseId"));
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                populateLessonContent(dataSnapshot);
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
                ArrayList<String> quizzesAttempted= new ArrayList<>();
                DataSnapshot taken = dataSnapshot.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("quizzes").child(courseId);
                for (DataSnapshot x : taken.getChildren()) {
                    quizzesAttempted.add(x.getKey().toString());
                }
                Log.e("dash", quizzesAttempted.toString());

                DataSnapshot ds = dataSnapshot.child("courses").child(getIntent().getStringExtra("courseId"))
                        .child("quiz");
                ArrayList<String> quizIds= new ArrayList<>();
                for (DataSnapshot x : ds.getChildren()) {
                    quizIds.add(x.getKey());
                }
                QuizListAdapter qz = new QuizListAdapter(getApplicationContext(), quizIds, quizIds.size(), courseId, quizzesAttempted);
                quizListView.setAdapter(qz);

//                populateQuizContent(dataSnapshot);
//                CourseContentAdapter adapter = getCourseContentAdapter();
//                listView.setAdapter(adapter);
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

        public CourseContentAdapter(Context context, List<Lessons> localLessons, int size ) {
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
            //TextView tvLessonText = (TextView) thisView.findViewById(R.id.tv_lesson_text);

            List<Lessons> lessonsList = lessons;
            String lesson_topic = lessonsList.get(position).getLessonName();
            String lesson_text = lessonsList.get(position).getLessonText();

            tvLessonTitle.setText(lesson_topic);
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

        QuizListAdapter(Context context, ArrayList<String> courseList, int size, String courseIdTemp, ArrayList quizAttemptedTemp) {
            this.context = context;
            this.quizList = courseList;
            this.listSize = size;
            courseId = courseIdTemp;
            quizzesAttempted = quizAttemptedTemp;
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
            if(quizzesAttempted.contains(quizList.get(position))) {
                bt.setEnabled(false);
            }
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO PASS The INTENT HERE
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
