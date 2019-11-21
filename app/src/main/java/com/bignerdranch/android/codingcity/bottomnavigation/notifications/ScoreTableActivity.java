package com.bignerdranch.android.codingcity.bottomnavigation.notifications;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.bignerdranch.android.codingcity.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ScoreTableActivity extends AppCompatActivity {

        @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        TableLayout t1;
        setContentView(R.layout.fragment_score);


//      TableLayout tl = (TableLayout) findViewById(R.id.score_table);
//
//        TableRow tr_head = new TableRow(this);
////        tr_head.setId(10);
//        tr_head.setBackgroundColor(Color.GRAY);
//        tr_head.setLayoutParams(new TableLayout.LayoutParams(
//                TableLayout.LayoutParams.FILL_PARENT,
//                TableLayout.LayoutParams.WRAP_CONTENT));
//
//        TextView label_quiz = new TextView(this);
////        label_quiz.setId(20);
//        label_quiz.setText("Quiz");
//        label_quiz.setTextColor(Color.WHITE);
//        label_quiz.setPadding(5, 5, 5, 5);
//        tr_head.addView(label_quiz);
//
//        TextView label_marks = new TextView(this);
////        label_marks.setId(21);
//        label_marks.setText("Marks");
//        label_marks.setTextColor(Color.WHITE);
//        label_marks.setPadding(5, 5, 5, 5);
//        tr_head.addView(label_marks);
//
//
//        tl.addView(tr_head, new TableLayout.LayoutParams(
//                TableLayout.LayoutParams.FILL_PARENT,
//                TableLayout.LayoutParams.WRAP_CONTENT));
//
//        Cursor cursor = null;
//
//        Integer count=0;
//        while (cursor.moveToNext()) {
//
//            String quiz = cursor.getString(2);// Change this variables to access values from firebase db
//            String marks = cursor.getString(4);// get the second variable
//
//
//// Create the table row
//            TableRow tr = new TableRow(this);
//            if(count%2!=0) tr.setBackgroundColor(Color.GRAY);
//            tr.setId(100+count);
//            tr.setLayoutParams(new TableLayout.LayoutParams(
//                    TableLayout.LayoutParams.FILL_PARENT,
//                    TableLayout.LayoutParams.WRAP_CONTENT));
//
////Create two columns to add as table data
//            // Create a TextView to add date
//            TextView labelQUIZ = new TextView(this);
//            labelQUIZ.setId(200+count);
//            labelQUIZ.setText(quiz);
//            labelQUIZ.setPadding(2, 0, 5, 0);
//            labelQUIZ.setTextColor(Color.WHITE);
//            tr.addView(labelQUIZ);
//            TextView labelMARKS = new TextView(this);
//            labelMARKS.setId(200+count);
//            labelMARKS.setText(marks);
//            labelMARKS.setTextColor(Color.WHITE);
//            tr.addView(labelMARKS);
//
//// finally add this to the table row
//            tl.addView(tr, new TableLayout.LayoutParams(
//                    TableLayout.LayoutParams.FILL_PARENT,
//                    TableLayout.LayoutParams.WRAP_CONTENT));
//            count++;
//        }
//    }
//    }

/*
public class ScoreTableActivity extends AppCompatActivity {
    private ListView QuiznameList;
    private ListView ScoreList;

    /**
     * Called when the activity is first created
     */
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayAdapter<String> listAdapter;
        ArrayAdapter<Integer> list2Adapter;

        //Find the ListView resources
        QuiznameList = (ListView) findViewById(R.id.QuiznameList);
        ScoreList = (ListView) findViewById(R.id.ScoreList);

        //Create and populate the list of usernames.
        //This is where the information from the Database would need to be entered. and the String[] removed
        String[] quiznames = new String[]{"Quiz 1", "Quiz 2", "Quiz 3"};
        ArrayList<String> quiznameList = new ArrayList<String>();
        quiznameList.addAll(Arrays.asList(quiznames));

        //Create and populate the list of scores
        //This is where the information from the Database would need to be entered. and the Integer[] removed
        Integer[] scores = new Integer[]{7, 4, 10};
        ArrayList<Integer> scoreList = new ArrayList<Integer>();
        scoreList.addAll(Arrays.asList(scores));
        Log.e("score table", quiznameList.toString());
        //Create Array Adapter using the username list
        listAdapter = new ArrayAdapter<String>(this, R.layout.fragment_score, quiznameList);
        //Add more users
      //  listAdapter.add("Quiz 4");
        //Set the Array Adapter as the ListView's adapter
        QuiznameList.setAdapter(listAdapter);

        //Create Array Adapter using the username list
        list2Adapter = new ArrayAdapter<Integer>(this, R.layout.fragment_score, scoreList);
        //Add more users
      //  list2Adapter.add(0);
        //Set the Array Adapter as the ListView's adapter
        ScoreList.setAdapter(list2Adapter);

    }
*/
    }
}
