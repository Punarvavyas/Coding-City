package com.bignerdranch.android.codingcity.bottomnavigation.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bignerdranch.android.codingcity.MainActivity;
import com.bignerdranch.android.codingcity.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfileFragment extends Fragment {

  //  private ProfileViewModel profileViewModel;
    private ImageView user_image;
    private TextView  user_Name;
    private TextView Email;
    private Button editProfile;
    private Button scoreboard;
    private FloatingActionButton removeCourse;
  private int contentView;


  public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
    //    profileViewModel =
    //            ViewModelProviders.of(this).get(ProfileViewModel.class);
      View root = inflater.inflate(R.layout.fragment_profile, container, false);
     //   final TextView textView = root.findViewById(R.id.text_profile);
     //   profileViewModel.getText().observe(this, new Observer<String>() {
     //       @Override
       //     public void onChanged(@Nullable String s) {
         //       textView.setText(s);
         //       editProfile = root.findViewById(R.id.btn_editProfile);
        //    }
       // });
        user_image=root.findViewById(R.id.ivProfile);
        user_Name= root.findViewById(R.id.tvName);
        Email=root.findViewById(R.id.tvEmail);
        scoreboard = root.findViewById(R.id.btn_scoreboard);
        removeCourse=root.findViewById(R.id.btnRemoveCourse);

        scoreboard.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Intent intent = new Intent(getContext(), ScoreTableActivity.class);
            startActivity(intent);
            //setContentView(R.layout.fragment_score);
          }
        });

        removeCourse.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

          }
        });
        return root;

       // return inflater.inflate(R.layout.fragment_profile, container, false);
    }

}