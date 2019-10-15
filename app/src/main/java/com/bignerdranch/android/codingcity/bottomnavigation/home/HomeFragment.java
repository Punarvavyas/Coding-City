package com.bignerdranch.android.codingcity.bottomnavigation.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bignerdranch.android.codingcity.R;

public class HomeFragment extends Fragment {

//    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
//        View rootView = inflater.inflate(R.layout.fragment_home, container,
//                false);
//        final TextView textView = rootView.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        View rootView = inflater.inflate(R.layout.fragment_home, container,
                false);
        String[] values = new String[] { "Course1", "Course2", "Course3" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, values);
        ListView list = rootView.findViewById(R.id.list_course);
        list.setAdapter(adapter);
        return rootView;
    }
}