package com.bignerdranch.android.codingcity.bottomnavigation.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.codingcity.R;
import com.bignerdranch.android.codingcity.courseinfo.Course;
import com.bignerdranch.android.codingcity.courseinfo.CourseContent;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder>{

    Context context ;
    List<Course> mData;

    public RecycleAdapter(Context context, List<Course> data) {
        this.context = context;
        this.mData = data;
    }

    @NonNull
    @Override
    public RecycleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_course_item,parent,false);

        view.setOnClickListener(new View.OnClickListener() {

            //TODO LINK TO COURSE CONTENT
            @Override
            public void onClick(View v) {
                Intent course = new Intent(context, CourseContent.class);
                context.startActivity(course);
            }
        });
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAdapter.MyViewHolder holder, int position) {
        holder.TvTitle.setText(mData.get(position).getCourseName());
        holder.Descrip.setText(mData.get(position).getCourseDescription());
        String uri = mData.get(position).getCourseImageUrl();
        int icon = context.getResources().getIdentifier(uri, "drawable", context.getPackageName());
        holder.ImgMovie.setImageResource(icon);
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

            TvTitle = itemView.findViewById(R.id.tv_title_course);
            Descrip = itemView.findViewById(R.id.tv_description_course);
            ImgMovie = itemView.findViewById(R.id.iv_course);


        }
    }
}
