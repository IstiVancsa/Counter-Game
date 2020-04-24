package com.example.countdownproject.ui.home;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class HomeViewModelFactory implements ViewModelProvider.Factory {
    private Context CurrentContext;
    private View CurrentView;


    public HomeViewModelFactory(Context context, View view) {
        CurrentContext = context;
        CurrentView = view;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new HomeViewModel(CurrentContext, CurrentView);
    }
}

