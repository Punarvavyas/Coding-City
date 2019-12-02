package com.bignerdranch.android.codingcity.quizinfo;


import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bignerdranch.android.codingcity.R;
import com.google.android.material.button.MaterialButton;
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
    private MaterialButton mTrueButton;
    private MaterialButton mFalseButton;
    private MaterialButton nextButton;
    private TextView mQuestionTextView;
    private int mCurrentIndex = 0;
    private CardView resultCard;
    private TextView resultTV;
    private LinearLayout answerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        resultCard = findViewById(R.id.resultCard);
        resultTV = findViewById(R.id.resultTv);
        answerLayout = findViewById(R.id.answerLayout);
        mQuestionTextView = findViewById(R.id.question_text_view);
        mTrueButton = findViewById(R.id.true_button);
        nextButton = findViewById(R.id.nextQuizQue);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
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
                }
                else {
                    updateQuestion();
                }

            }
        });
        DatabaseReference rootDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = rootDatabase.child("courses").child(getIntent().getStringExtra("courseId"))
                .child("quiz").child(getIntent().getStringExtra("quizId"));
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
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
                            MediaPlayer mp = MediaPlayer.create(getApplicationContext(),R.raw.song);
                            mp.start();

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
                            MediaPlayer mp = MediaPlayer.create(getApplicationContext(),R.raw.song);
                            mp.start();
                            //mRef.child(UserLogin.getInstance(getApplicationContext()).getUser().getUid()).child("score").setValue(Integer.toString(score));
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);

    }

    private void updateQuestion() {
        String question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        resultCard.setVisibility(View.GONE);
        answerLayout.setVisibility(View.VISIBLE);
        nextButton.setVisibility(View.GONE);
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
        resultCard.setVisibility(View.VISIBLE);
        answerLayout.setVisibility(View.GONE);
        resultTV.setText(messageResId);
        nextButton.setVisibility(View.VISIBLE);
    }
}
