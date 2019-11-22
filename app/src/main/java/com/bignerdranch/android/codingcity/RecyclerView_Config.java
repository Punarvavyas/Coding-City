package com.bignerdranch.android.codingcity;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.codingcity.courseinfo.Course;
import com.google.android.gms.measurement.api.AppMeasurementSdk;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerView_Config {
    private Context mContext;
    private CourseAdapter mCourseAdapter;

    public void setConfig(RecyclerView recyclerView,Context context, List<Course> courses, List<String> keys){
        mContext = context;
        mCourseAdapter = new CourseAdapter(courses, keys);
        recyclerView.setAdapter(mCourseAdapter);
    }

    class CourseItemView extends RecyclerView.ViewHolder{
        private TextView courseName;
        private TextView courseDescription;
        private ImageView courseImage;

        private String key;

        public CourseItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.course_list_item, parent,false));

            courseName = (TextView) itemView.findViewById(R.id.txt_CourseName);
            courseDescription = (TextView) itemView.findViewById(R.id.txtCourseDescription);
            courseImage = (ImageView) itemView.findViewById(R.id.imageView);
        }

        public void bind(Course course, String key){
            courseName.setText(course.getCourseName());
            courseDescription.setText(course.getCourseDescription());
            courseImage.setImageURI(Uri.parse(course.getCourseImageUrl()));
            this.key = key;
        }
    }

    class CourseAdapter extends RecyclerView.Adapter<CourseItemView>{
        private List<Course> mCourseList;

        public CourseAdapter(List<Course> mCourseList, List<String> mKeys) {
            this.mCourseList = mCourseList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public CourseItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new CourseItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CourseItemView holder, int position) {
                holder.bind(mCourseList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mCourseList.size();
        }

        private List<String> mKeys;

    }
}
