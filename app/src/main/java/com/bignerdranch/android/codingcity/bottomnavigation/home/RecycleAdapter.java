package com.bignerdranch.android.codingcity.bottomnavigation.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.codingcity.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder>{

    Context context ;
    List<CourseHomeItem> mData;

    public RecycleAdapter(Context context, List<CourseHomeItem> data) {
        this.context = context;
        this.mData = data;
    }

    @NonNull
    @Override
    public RecycleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_course_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAdapter.MyViewHolder holder, int position) {
        holder.TvTitle.setText(mData.get(position).getTitle());
        holder.Descrip.setText(mData.get(position).getDescription());
        holder.ImgMovie.setImageResource(mData.get(position).getImg());
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
