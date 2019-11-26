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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        lstSlides = new ArrayList<>();
        lstSlides.add(new SlideItem(R.drawable.course_javascript,"Slide Title \nmore text here"));
        lstSlides.add(new SlideItem(R.drawable.course_python,"Slide Title \nmore text here"));
        lstSlides.add(new SlideItem(R.drawable.course_android,"Slide Title \nmore text here"));

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mViewPager = rootView.findViewById(R.id.view_pager);
        ViewPagerAdapter myadapter = new ViewPagerAdapter(getContext(), lstSlides);
        mViewPager.setAdapter(myadapter);
        ListView lv = rootView.findViewById(R.id.list_course);
        lv.setAdapter(new MyListAdpter());
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
}