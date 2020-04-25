package com.example.countdownproject.ui.newGame;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewGameViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NewGameViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is new game fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}