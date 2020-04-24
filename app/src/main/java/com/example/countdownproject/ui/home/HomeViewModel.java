package com.example.countdownproject.ui.home;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.countdownproject.R;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> CounterText;
    private MutableLiveData<String> ButtonText;
    private int[] Colors;
    private Context CurrentContext;
    private View CurrentView;

    private int CurrentColorIndex = -1;
    private CountDownTimer countDownTimer;
    private long timeLeft;
    private long timeInterval;
    private boolean timerRunning;
    private boolean firstTick = true;

    public HomeViewModel(Context context, View view) {
        CurrentContext = context;
        CurrentView = view;
        timeInterval = getRandomNumberInRange(1000, 3000);
        Log.i("timeInterval", Long.toString(timeInterval));
        timeLeft = timeInterval * 5;
        CounterText = new MutableLiveData<>();
        ButtonText = new MutableLiveData<>();
        CounterText.setValue("5");
        ButtonText.setValue("Start");
        timerRunning = false;
        Colors = new int[]{ResourcesCompat.getColor(CurrentContext.getResources(), R.color.BLUE, null),
                 ResourcesCompat.getColor(CurrentContext.getResources(), R.color.YELLOW, null),
                 ResourcesCompat.getColor(CurrentContext.getResources(), R.color.RED, null)};
    }

    public LiveData<String> getCounterText() {
        return CounterText;
    }
    public LiveData<String> getButtonText(){ return ButtonText; }

    public void StartStop(){
        if(timerRunning)
            StopTimer();
        else
            StartTimer();
    }

    private void StopTimer(){
        countDownTimer.cancel();
        ButtonText.setValue("Start");
        timerRunning = false;
    }
    private void StartTimer(){
        countDownTimer = new CountDownTimer(timeLeft, timeInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                if(!firstTick) {
                    int secondsLeft = (int) (timeLeft / timeInterval) + 1;
                    Log.i("time", Long.toString(timeLeft));
                    if (secondsLeft >= 2) {
                        CurrentColorIndex = CurrentColorIndex + 1;
                        CurrentView.setBackgroundColor(Colors[CurrentColorIndex]);
                        CounterText.setValue(Integer.toString(secondsLeft));
                    }
                }
                firstTick = false;
            }

            @Override
            public void onFinish() {
                new AlertDialog.Builder(CurrentContext)
                        .setTitle("The timer finished")
                        .setMessage("Better luck next time")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }.start();
        ButtonText.setValue("Stop");
        timerRunning = true;
    }
    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}