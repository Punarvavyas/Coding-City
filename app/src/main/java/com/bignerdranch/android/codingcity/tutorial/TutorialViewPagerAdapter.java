package com.bignerdranch.android.codingcity.tutorial;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bignerdranch.android.codingcity.R;

import java.util.List;

/**
 * This is the class to inflate the layout on view page
 *
 * @author Ruize Nie
 */
public class TutorialViewPagerAdapter extends PagerAdapter {

    // attribute
    Context mContext;
    List<TutorialItem> mListItem;

    // constructor
    public TutorialViewPagerAdapter(Context mContext, List<TutorialItem> mList) {
        this.mContext = mContext;
        this.mListItem = mList;
    }

    // inflate each item on view pager
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View tutorial = inflater.inflate(R.layout.layout_tutorial, null);

        ImageView imgSlide = tutorial.findViewById(R.id.img_intro);
        TextView title = tutorial.findViewById(R.id.title_intro);
        TextView description = tutorial.findViewById(R.id.desc_intro);

        title.setText(mListItem.get(position).getTitle());
        description.setText(mListItem.get(position).getDescription());
        imgSlide.setImageResource(mListItem.get(position).getTutorialImg());

        container.addView(tutorial);
        return tutorial;
    }

    @Override
    public int getCount() {
        return mListItem.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}