package com.bignerdranch.android.geoquiz;

import androidx.lifecycle.ViewModel;

public class QuizViewModel extends ViewModel {

    private final String TAG = "QuizViewModel";

    private int currentIndex = 0;

    private Question[] questionBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)};

    public QuizViewModel() {
        super();
    }

    public void setCurrentIndex(int value)
    {
        currentIndex = value;
    }
    public int getCurrentIndex() {
        return currentIndex;
    }

    public boolean getCurrentQuestionAnswer() {
        return questionBank[currentIndex].getAnswer();
    }

    public int getCurrentQuestionText() {
        return questionBank[currentIndex].getTextResId();
    }

    public void moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.length;
    }
}
