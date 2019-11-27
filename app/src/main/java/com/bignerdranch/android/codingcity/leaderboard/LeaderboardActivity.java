package com.bignerdranch.android.codingcity.leaderboard;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bignerdranch.android.codingcity.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LeaderboardActivity extends AppCompatActivity {


    DatabaseReference rootDatabase = FirebaseDatabase.getInstance().getReference();
    DatabaseReference myRef = rootDatabase.child("users");


    ArrayList<UserData> userDataList = new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.leaderboard_content);
        listView = (ListView) findViewById(R.id.lv_user_scores);
    }

    @Override
    protected void onStart() {
        super.onStart();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userDataList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Log.e("userId", postSnapshot.getKey());
                    String userId = postSnapshot.getKey().trim();
                    String userName = postSnapshot.child("name").getValue().toString().trim();
                    int userScore = Integer.parseInt(postSnapshot.child("score").getValue().toString().trim());
                    String userProfileImage = postSnapshot.child("profileImageUri").getValue().toString().trim();

                    UserData data = new UserData(userId, userName, userScore, userProfileImage);
                    userDataList.add(data);
                }
                listView.setAdapter(new LeaderboardActivity.LeaderBoardAdapter(LeaderboardActivity.this, userDataList, userDataList.size()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        /*
        String userId = "sdfssdf88745454bjjbh4bhb4h";
        String userName = "Akshay SIngh";
        int userScore = 85;
        String userProfileImage = "drawable/icons_user";

        UserData data = new UserData(userId, userName, userScore, userProfileImage);
        userDataList.add(data);
        listView.setAdapter(new LeaderboardActivity.LeaderBoardAdapter(LeaderboardActivity.this, userDataList, userDataList.size()));

         */
    }


    private class LeaderBoardAdapter extends BaseAdapter {

        Context context;
        ArrayList<UserData> userDataList;
        LayoutInflater inflater;
        int limit;
        LeaderBoardAdapter(Context mContext, ArrayList<UserData> usersList, int size){
            this.context = mContext;
            this.userDataList = usersList;
            this.limit = size;
            Collections.sort(userDataList);
        }

        private int getUserRank(String userId){
            int rank = -1;
            int index = 1;
            for(UserData data : userDataList){
                if(data.getUserId().equals(userId)){
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
            inflater = LayoutInflater.from(getApplicationContext());
            View thisView = View.inflate(parent.getContext(), R.layout.list_leaderboard_item, null);
            TextView tvRank = (TextView) thisView.findViewById(R.id.tv_rank);
            TextView tvUserName = (TextView) thisView.findViewById(R.id.tv_username);
            TextView tvScore = (TextView) thisView.findViewById(R.id.tv_score);
            ImageView imageView = (ImageView) thisView.findViewById(R.id.iv_profile_picture);

            // User profile image
            String uri = userDataList.get(position).getUserProfileImage();
            int icon = getResources().getIdentifier(uri, "drawable", getPackageName());
            imageView.setImageResource(icon);

            // User rank
            String userId = userDataList.get(position).getUserId();
            String rank = String.valueOf(getUserRank(userId));
            tvRank.setText(rank);

            // Username
            String username = String.valueOf(userDataList.get(position).getUserName());
            tvUserName.setText(username);

            // User Score
            String score = String.valueOf(userDataList.get(position).getUserScore());
            tvScore.setText(score);

            return thisView;
        }
    }
}
