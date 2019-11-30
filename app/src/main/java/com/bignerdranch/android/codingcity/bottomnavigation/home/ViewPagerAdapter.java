package com.bignerdranch.android.codingcity.bottomnavigation.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.bignerdranch.android.codingcity.R;
import com.bignerdranch.android.codingcity.enrollment.CourseEnrollmentActivity;

import java.util.List;


/**
 * This is pager adapter for slide show in home dashboard
 *
 * @author Ruize Nie
 */
public class ViewPagerAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;
    private List<SlideItem> mList;

    public ViewPagerAdapter(Context context, List<SlideItem> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_slide, container, false);

        ImageView imgslide = view.findViewById(R.id.slide_img);
        TextView txttitle = view.findViewById(R.id.slide_title);

        imgslide.setImageResource(mList.get(position).getImage());
        txttitle.setText(mList.get(position).getTitle());
        final int temp = position;
        imgslide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CourseEnrollmentActivity.class);
                intent.putExtra("courseId", mList.get(temp).getCourseid());
                context.startActivity(intent);
            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
