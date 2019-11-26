package com.bignerdranch.android.codingcity.bottomnavigation.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bignerdranch.android.codingcity.R;
import com.bignerdranch.android.codingcity.courseinfo.CourseContent;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the home page which show what course we have right now
 * @author Ruize Nie
 */
public class HomeFragment extends Fragment {

    private ViewPager mViewPager;
    private List<SlideItem> lstSlides;
    private TabLayout indicator;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        lstSlides = new ArrayList<>();
        lstSlides.add(new SlideItem(R.drawable.course_javascript,"JavaScript"));
        lstSlides.add(new SlideItem(R.drawable.course_python,"Python"));
        lstSlides.add(new SlideItem(R.drawable.course_android,"Android"));

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mViewPager = rootView.findViewById(R.id.view_pager);
        ViewPagerAdapter myadapter = new ViewPagerAdapter(getContext(), lstSlides);
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mViewPager.setAdapter(myadapter);
        indicator = rootView.findViewById(R.id.indicator);
        indicator.setupWithViewPager(mViewPager,true);

        ListView lv = rootView.findViewById(R.id.list_course);
        MyListAdpter adpter = new MyListAdpter();
        adpter.notifyDataSetChanged();
        lv.setAdapter(adpter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), CourseContent.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    //TODO: duplicated in search activity
    private class MyListAdpter extends BaseAdapter {

        @Override
        //How many items are in the data set represented by this Adapter.
        public int getCount() {
            return 3;
        }

        @Override
        //Get the data item associated with the specified position in the data set.
        public Object getItem(int position) {
            return null;
        }

        @Override
        //Get the row id associated with the specified position in the list.
        public long getItemId(int position) {
            return 0;
        }

        @Override
        //Get a View that displays the data at the specified position in the data set.
        public View getView(int position, View convertView, ViewGroup parent) {
            View v;

            if(convertView == null){

                v = View.inflate(getContext(), R.layout.list_course_item, null);

            }else{
                v = convertView;
            }

            return v;
        }
    }

    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);

            } else if (position <= 1) { // [-1,1]
                // Modify the default layout_slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));
            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }
    }
}