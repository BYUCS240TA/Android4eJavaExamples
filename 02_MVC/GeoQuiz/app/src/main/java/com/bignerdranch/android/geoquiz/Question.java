package com.bignerdranch.android.geoquiz;

import androidx.annotation.StringRes;

public class Question {

    private int textResId;
    private boolean answer;

    public Question(@StringRes int textResId, boolean answer) {
        setTextResId(textResId);
        setAnswer(answer);
    }

    public int getTextResId() {
        return textResId;
    }

    public void setTextResId(int textResId) {
        this.textResId = textResId;
    }

    public boolean getAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }
}
