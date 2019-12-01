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
import com.bignerdranch.android.codingcity.courseinfo.Course;
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
 * This is the home page which show what course we have right now
 *
 * @author Ruize Nie, Akshay Singh
 */
public class HomeFragment extends Fragment {

    ArrayList<Course> courseData = new ArrayList<>();
    DatabaseReference rootDatabase = FirebaseDatabase.getInstance().getReference();
    //show specific course that user registered
    DatabaseReference coursesRef = rootDatabase;
    //check the enrollment status
    DatabaseReference userRef = rootDatabase.child("users");
    private ViewPager mViewPager;
    private List<SlideItem> lstSlides;
    private TabLayout indicator;
    private RecyclerView homeRecycleView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        lstSlides = new ArrayList<>();
        lstSlides.add(new SlideItem(R.drawable.course_python, "", "c1001"));
        lstSlides.add(new SlideItem(R.drawable.course_android, "", "c1005"));
        lstSlides.add(new SlideItem(R.drawable.course_c, "", "c1002"));

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mViewPager = rootView.findViewById(R.id.view_pager);
        ViewPagerAdapter myadapter = new ViewPagerAdapter(getContext(), lstSlides);
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mViewPager.setAdapter(myadapter);
        indicator = rootView.findViewById(R.id.indicator);
        indicator.setupWithViewPager(mViewPager, true);

        getEnrolledCourses();
        homeRecycleView = rootView.findViewById(R.id.list_course);
        return rootView;
    }

    private void getEnrolledCourses() {
        coursesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                courseData.clear();
                DataSnapshot dataSnapshot2 = dataSnapshot.child("courses");
                ArrayList<String> arr = new ArrayList<>();
                DataSnapshot dx = dataSnapshot.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("courses");
                for (DataSnapshot x : dx.getChildren()) {
                    arr.add(x.getKey().toString());
                }
                for (DataSnapshot postSnapshot : dataSnapshot2.getChildren()) {
                    String courseId = postSnapshot.child("courseId").getValue().toString().trim();
                    if (arr.contains(courseId)) {

                        courseData.add(
                                new Course(
                                        courseId,
                                        postSnapshot.child("courseName").getValue().toString(),
                                        postSnapshot.child("courseDescription").getValue().toString(),
                                        postSnapshot.child("courseImageUri").getValue().toString(),
                                        postSnapshot.child("isPremium").getValue().toString())
                        );
                    }
                }

                RecycleAdapter reAdapter = new RecycleAdapter(getContext(), courseData);
                homeRecycleView.setAdapter(reAdapter);
                homeRecycleView.setScrollContainer(false);
                homeRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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