package com.bignerdranch.android.codingcity.quizinfo;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bignerdranch.android.codingcity.R;
import com.bignerdranch.android.codingcity.authentication.UserLogin;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class QuizActivity extends AppCompatActivity {

    public int score = 0;
    private Button mTrueButton;
    private Button mFalseButton;
    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_1, true),
            new Question(R.string.question_2, false),
            new Question(R.string.question_3, false),
            new Question(R.string.question_4, true),
            new Question(R.string.question_5, true),
    };

    private int mCurrentIndex = 0;

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = findViewById(R.id.question_text_view);

        mTrueButton = findViewById(R.id.true_button);
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
                            DatabaseReference myRef = database.getReference().child("users");
                            myRef.child(UserLogin.getInstance(getApplicationContext()).getUser().getUid()).child("quizzes").child("Quiz 1").setValue(Integer.toString(score));
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
                            DatabaseReference myRef = database.getReference().child("users");
                            myRef.child(UserLogin.getInstance(getApplicationContext()).getUser().getUid()).child("quizzes").child("Quiz 1").setValue(Integer.toString(score));
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


        updateQuestion();
    }

}
