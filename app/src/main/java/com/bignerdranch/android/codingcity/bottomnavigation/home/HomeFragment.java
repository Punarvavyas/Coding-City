package com.bignerdranch.android.codingcity.bottomnavigation.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bignerdranch.android.codingcity.R;
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
    private RecyclerView homeRecycleView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        lstSlides = new ArrayList<>();
        lstSlides.add(new SlideItem(R.drawable.course_javascript,""));
        lstSlides.add(new SlideItem(R.drawable.course_python,""));
        lstSlides.add(new SlideItem(R.drawable.course_android,""));

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mViewPager = rootView.findViewById(R.id.view_pager);
        ViewPagerAdapter myadapter = new ViewPagerAdapter(getContext(), lstSlides);
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mViewPager.setAdapter(myadapter);
        indicator = rootView.findViewById(R.id.indicator);
        indicator.setupWithViewPager(mViewPager,true);

        List<CourseHomeItem> lstCourses = new ArrayList<>();

        lstCourses.add(new CourseHomeItem("Python", "Descript for python", R.drawable.course_python));
        lstCourses.add(new CourseHomeItem("JavaScript", "Descript for javascript", R.drawable.course_javascript));
        lstCourses.add(new CourseHomeItem("Android", "Descript for Android", R.drawable.course_android));

        homeRecycleView = rootView.findViewById(R.id.list_course);
        RecycleAdapter reAdapter = new RecycleAdapter(getContext(), lstCourses);
        homeRecycleView.setAdapter(reAdapter);
        homeRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
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