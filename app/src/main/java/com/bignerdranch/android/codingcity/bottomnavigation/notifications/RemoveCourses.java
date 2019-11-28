package com.bignerdranch.android.codingcity.bottomnavigation.notifications;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bignerdranch.android.codingcity.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RemoveCourses extends AppCompatActivity {
    private ListView dataListView;
    //   check the use later on for edittext
    private EditText itemText;
    private Button findButton;
    private Button deleteButton;
    private Button addButton;
    private Boolean searchMode = false;
    private Boolean itemSelected = false;
    private int selectedPosition = 0;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference().child("courses");
    private ArrayList<String> listItems = new ArrayList<String>();
    ArrayList<String> listKeys = new ArrayList<String>();
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remove_courses);
        listItems.add("CSCI 5408");
        dataListView = (ListView) findViewById(R.id.list);
//        itemText = (EditText) findViewById(R.id.itemText);
//        findButton = (Button) findViewById(R.id.findButton);
        deleteButton = (Button) findViewById(R.id.remove);
        deleteButton.setEnabled(false);
        addButton = (Button) findViewById(R.id.add);

        adapter = new ArrayAdapter<String>(this,
                R.layout.remove_course_list_item,
                listItems);
        dataListView.setAdapter(adapter);
        dataListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        dataListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent,
                                            View view, int position, long id) {
                        selectedPosition = position;
                        itemSelected = true;
                        deleteButton.setEnabled(true);
                    }
                });

        addChildEventListener();

    }


    private void addChildEventListener() {
        ChildEventListener childListener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                adapter.add(
                        (String) dataSnapshot.child("courses").getValue());

                listKeys.add(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                int index = listKeys.indexOf(key);

                if (index != -1) {
                    listItems.remove(index);
                    listKeys.remove(index);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        dbRef.addChildEventListener(childListener);
    }

    //if wants to add something written in edittext
//    public void addItem(View view) {
//
//        String item = itemText.getText().toString();
//        String key = dbRef.push().getKey();
//
//        itemText.setText("");
//        dbRef.child(key).child("courses").setValue(item);
//
//        adapter.notifyDataSetChanged();
//    }


    private class CourseAdapter extends ArrayAdapter<String> {
        public CourseAdapter(Context context, ArrayList<String> users) {
            super(context, 0, users);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v;
            if (convertView == null) {
                v = LayoutInflater.from(getContext()).inflate(R.layout.remove_course_list_item, parent, false);
            } else {
                v = convertView;
            }
            String user = getItem(position);
            // Lookup view for data population
//            TextView tvName = (TextView) v.findViewById(R.id.tex);
//            tvHome.setText(user.hometown);
//            // Return the completed view to render on screen
            return convertView;
        }
    }


}