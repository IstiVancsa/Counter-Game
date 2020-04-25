package com.example.countdownproject.ui.welcome;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.countdownproject.R;

import java.util.Random;

public class WelcomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public WelcomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Welcome to my beautiful app!");
    }

    public LiveData<String> getText() {
        return mText;
    }
}