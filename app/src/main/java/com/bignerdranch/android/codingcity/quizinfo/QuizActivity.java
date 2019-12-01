package com.bignerdranch.android.codingcity.quizinfo;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bignerdranch.android.codingcity.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuizActivity extends AppCompatActivity {

    public int score = 0;
    public int i = 0;
    public Question[] mQuestionBank = new Question[5];
    private Button mTrueButton;
    private Button mFalseButton;
    private TextView mQuestionTextView;
    private int mCurrentIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        mQuestionTextView = findViewById(R.id.question_text_view);
        mTrueButton = findViewById(R.id.true_button);
        DatabaseReference rootDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = rootDatabase.child("courses").child(getIntent().getStringExtra("courseId"))
                .child("quiz").child(getIntent().getStringExtra("quizId"));
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Log.e(postSnapshot.getKey(), "hi");
                    Log.e(postSnapshot.getValue().toString(), "value");
                    boolean m;
                    String s = "True";
                    if (postSnapshot.getValue().toString().equals(s)) {
                        m = true;
                    } else {
                        m = false;
                    }
                    mQuestionBank[i] = new Question(postSnapshot.getKey(), m);
                    i++;
                    updateQuestion();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                if ((mCurrentIndex + 1) == mQuestionBank.length) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
                    builder.setMessage("Your Score is: " + score).setTitle("Quiz result").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference mRef = database.getReference().child("users");
                            mRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("quizzes").child(getIntent().getStringExtra("courseId"))
                                    .child(getIntent().getStringExtra("quizId")).setValue(Integer.toString(score));
                            finish();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                    updateQuestion();
                }
            }
        });

        mFalseButton = findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                if ((mCurrentIndex + 1) == mQuestionBank.length) {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
                    builder.setMessage("Your Score is:" + score).setTitle("Quiz result").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference mRef = database.getReference().child("users");
                            mRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("score").setValue(Integer.toString(score));

                            mRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("quizzes").child(getIntent().getStringExtra("courseId"))
                                    .child(getIntent().getStringExtra("quizId")).setValue(Integer.toString(score));
                            finish();


                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();


                } else {
                    mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                    updateQuestion();
                }


            }
        });


        //updateQuestion();

    }

    private void updateQuestion() {
        String question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        Log.e(Integer.toString(i), "ds");
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId;

        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
            score++;


        } else {
            messageResId = R.string.incorrect_toast;
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator.hasVibrator()) {
                vibrator.vibrate(500); // for 500 ms
            }
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

}
