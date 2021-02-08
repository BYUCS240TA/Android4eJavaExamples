package com.bignerdranch.android.geoquiz;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private final String KEY_INDEX = "index";

    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private TextView questionTextView;

    private QuizViewModel getQuizViewModel() {
        return ViewModelProviders.of(this).get(QuizViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);

        QuizViewModel quizViewModel = getQuizViewModel();
        Log.d(TAG, "Got a QuizViewModel: " + quizViewModel);

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_INDEX))
            quizViewModel.setCurrentIndex(savedInstanceState.getInt(KEY_INDEX));

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        questionTextView = findViewById(R.id.question_text_view);

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // getQuizViewModel().moveToNext();
                updateQuestion();
           }
        });

        updateQuestion();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, getQuizViewModel().getCurrentIndex());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private void updateQuestion() {
        int questionTextResId = getQuizViewModel().getCurrentQuestionText();
        questionTextView.setText(questionTextResId);
    }

    private void checkAnswer(boolean userAnswer) {
        boolean correctAnswer = getQuizViewModel().getCurrentQuestionAnswer();
        int messageResId = ((userAnswer == correctAnswer) ?
            R.string.correct_toast : R.string.incorrect_toast);
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
                .show();
    }
}
