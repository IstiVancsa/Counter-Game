package com.example.countdownproject.ui.game;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


public class GameViewModelFactory implements ViewModelProvider.Factory {
    private Context CurrentContext;
    private View CurrentView;


    public GameViewModelFactory(Context context, View view) {
        CurrentContext = context;
        CurrentView = view;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new GameViewModel(CurrentContext, CurrentView);
    }
}

