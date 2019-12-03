package com.bignerdranch.android.codingcity.bottomnavigation.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.android.codingcity.R;
import com.bignerdranch.android.codingcity.courseinfo.Course;
import com.bignerdranch.android.codingcity.courseinfo.CourseContent;

import java.util.List;

/**
 * This is a adapter for recycleview
 *
 * @author Ruize Nie, Akshay Singh
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {

    // attribute
    Context context;
    List<Course> mData;

    // constructor
    public RecycleAdapter(Context context, List<Course> data) {
        this.context = context;
        this.mData = data;
    }

    // inflate view on each view holder
    @NonNull
    @Override
    public RecycleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_course_item, parent, false);

        return new MyViewHolder(view);
    }

    // bind the view into each view holder
    @Override
    public void onBindViewHolder(@NonNull RecycleAdapter.MyViewHolder holder, final int position) {
        holder.TvTitle.setText(mData.get(position).getCourseName());
        holder.Descrip.setText(mData.get(position).getCourseDescription());
        String uri = mData.get(position).getCourseImageUrl();
        int icon = context.getResources().getIdentifier(uri, "drawable", context.getPackageName());
        holder.ImgMovie.setImageResource(icon);

        // item click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent course = new Intent(context, CourseContent.class);
                course.putExtra("courseId", mData.get(position).getCourseId());
                context.startActivity(course);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView TvTitle;
        private TextView Descrip;
        private ImageView ImgMovie;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            // find the component using specific id
            TvTitle = itemView.findViewById(R.id.tv_title_course);
            Descrip = itemView.findViewById(R.id.tv_description_course);
            ImgMovie = itemView.findViewById(R.id.iv_course);

        }
    }
}
