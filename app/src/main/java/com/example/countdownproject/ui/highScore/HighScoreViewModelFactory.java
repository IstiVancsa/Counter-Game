package com.example.countdownproject.ui.highScore;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class HighScoreViewModelFactory implements ViewModelProvider.Factory {
    private Context CurrentContext;

    public HighScoreViewModelFactory(Context context) {
        CurrentContext = context;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new HighScoreViewModel(CurrentContext);
    }
}
