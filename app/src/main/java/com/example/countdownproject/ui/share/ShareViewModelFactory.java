package com.example.countdownproject.ui.share;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.countdownproject.ui.highScore.HighScoreViewModel;

public class ShareViewModelFactory implements ViewModelProvider.Factory{
    private Context CurrentContext;

    public ShareViewModelFactory(Context context) {
        CurrentContext = context;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new ShareViewModel(CurrentContext);
    }
}
