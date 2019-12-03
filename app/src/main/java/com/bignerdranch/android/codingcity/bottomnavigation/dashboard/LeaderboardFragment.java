package com.bignerdranch.android.codingcity.bottomnavigation.dashboard;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bignerdranch.android.codingcity.R;
import com.bignerdranch.android.codingcity.leaderboard.UserData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class LeaderboardFragment extends Fragment {

    DatabaseReference rootDatabase = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userRef = rootDatabase.child("users");
    ArrayList<UserData> userDataList = new ArrayList<>();
    ListView listView;
    private LeaderboardViewModel leaderboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        leaderboardViewModel =
                ViewModelProviders.of(this).get(LeaderboardViewModel.class);
        View root = inflater.inflate(R.layout.leaderboard_content, container, false);
        listView = root.findViewById(R.id.lv_user_scores);

        leaderboardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userDataList.clear();
                boolean isLoggedIn = false;
                String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Log.e("userId", postSnapshot.getKey());
                    String userId = postSnapshot.getKey().trim();
                    String userName = postSnapshot.child("name").getValue().toString().trim();
                    String userProfileImage = postSnapshot.hasChild("profileImageUri") ?
                            postSnapshot.child("profileImageUri").getValue().toString().trim() :
                            "drawable/icons_user";
                    isLoggedIn = currentUser.equals(userId);
                    int userScore = 0;
                    //quizzes
                    if (postSnapshot.hasChild("quizzes")) {
                        DataSnapshot quizSnapshot = postSnapshot.child("quizzes");
                        for (DataSnapshot enrolledCourse : quizSnapshot.getChildren()) {
                            for (DataSnapshot quiz : enrolledCourse.getChildren()) {
                                userScore = +Integer.parseInt(String.valueOf(quiz.getValue()).trim());
                            }
                        }
                    }

                    UserData data = new UserData(userId, userName, userScore, userProfileImage, isLoggedIn);
                    userDataList.add(data);
                }
                listView.setAdapter(new LeaderboardFragment.LeaderBoardAdapter(getContext(), userDataList, userDataList.size()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private class LeaderBoardAdapter extends BaseAdapter {

        Context context;
        ArrayList<UserData> userDataList;
        LayoutInflater inflater;
        int limit;

        LeaderBoardAdapter(Context mContext, ArrayList<UserData> usersList, int size) {
            this.context = mContext;
            this.userDataList = usersList;
            this.limit = size;
            Collections.sort(userDataList);
        }

        private int getUserRank(String userId) {
            int rank = -1;
            int index = 1;
            for (UserData data : userDataList) {
                if (data.getUserId().equals(userId)) {
                    rank = index;
                    break;
                }
                index++;
            }
            return rank;
        }

        @Override
        //How many items are in the data set represented by this Adapter.
        public int getCount() {
            return limit;
        }

        @Override
        //Get the data item associated with the specified position in the data set.
        public Object getItem(int position) {
            return userDataList.get(position);
        }

        @Override
        //Get the row id associated with the specified position in the list.
        public long getItemId(int position) {
            return 0; //al.get(position).getId();
        }

        @Override
        //Get a View that displays the data at the specified position in the data set.
        public View getView(int position, View convertView, ViewGroup parent) {
            inflater = LayoutInflater.from(getContext());
            View thisView = View.inflate(parent.getContext(), R.layout.list_leaderboard_item, null);
            TextView tvRank = thisView.findViewById(R.id.tv_rank);
            TextView tvUserName = thisView.findViewById(R.id.tv_username);
            TextView tvScore = thisView.findViewById(R.id.tv_score);
            ImageView imageView = thisView.findViewById(R.id.iv_profile_picture);
            CardView cardView = thisView.findViewById(R.id.ly_root);

            //User Id
            String userId = userDataList.get(position).getUserId();

            // User rank
            String rank = String.valueOf(getUserRank(userId));
            tvRank.setText(rank);

            // Username
            String username = String.valueOf(userDataList.get(position).getUserName());
            tvUserName.setText(username);

            // User Score
            String score = String.valueOf(userDataList.get(position).getUserScore());
            tvScore.setText(score);

            // highlight logged in user in leader board
            boolean isLoggedIn = userDataList.get(position).isLoggedIn();
            if (isLoggedIn) {

                // Set user profile image
                String uri = userDataList.get(position).getUserProfileImage();
                //int icon = getResources().getIdentifier(uri, "drawable", getPackageName());
                //imageView.setImageResource(icon);
                Uri imageFilePath = Uri.parse(uri);
                imageView.setImageURI(imageFilePath);

                // highlight card view
                cardView.setBackgroundColor(getResources().getColor(R.color.leaderboard_highlight));
            }
            return thisView;
        }
    }
}