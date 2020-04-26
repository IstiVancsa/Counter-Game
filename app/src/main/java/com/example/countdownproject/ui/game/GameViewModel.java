package com.example.countdownproject.ui.game;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.countdownproject.R;
import com.example.countdownproject.ui.highScore.HighScoreRowModel;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;
import static com.example.countdownproject.ui.highScore.HighScoreViewModel.FILE_NAME;

public class GameViewModel extends ViewModel {

    private MutableLiveData<String> CounterText;
    private MutableLiveData<String> ButtonText;
    private int[] Colors;
    private Context CurrentContext;
    private View CurrentView;

    private int CurrentColorIndex = -1;
    private CountDownTimer countDownTimer;
    private long timeLeft;
    private long timeInterval;
    private boolean GameStarted = false;
    private long Score;
    private boolean GameOver = false;
    private static final String SharedPrefName = "HighScoresPreferences";
    private static final String HighScoreText = "HighScore";
    private static final String DateTimeText = "DateTime";


    public GameViewModel(Context context, View view) {
        CurrentContext = context;
        CurrentView = view;
        timeInterval = getRandomNumberInRange(1000, 3000);
        Log.i("timeInterval", Long.toString(timeInterval));
        timeLeft = timeInterval * 6;
        CounterText = new MutableLiveData<>();
        ButtonText = new MutableLiveData<>();
        CounterText.setValue("5");
        ButtonText.setValue("Start");
        Colors = new int[]{
                ResourcesCompat.getColor(CurrentContext.getResources(), R.color.GREEN, null),
                ResourcesCompat.getColor(CurrentContext.getResources(), R.color.BLUE, null),
                ResourcesCompat.getColor(CurrentContext.getResources(), R.color.YELLOW, null),
                ResourcesCompat.getColor(CurrentContext.getResources(), R.color.RED, null)};
    }

    public LiveData<String> getCounterText() {
        return CounterText;
    }
    public LiveData<String> getButtonText(){ return ButtonText; }

    public void HandleStop_StartButton_Clicked(){
        if(!GameOver)
            if(GameStarted)
                StopTimer();
            else
                StartTimer();
    }

    private void StopTimer(){
        countDownTimer.cancel();
        ButtonText.setValue("Start");
        GameOver = true;
        int secondsLeft = (int) Math.round(((double)timeLeft / timeInterval) - 1);
        if(secondsLeft >= 2){
            new AlertDialog.Builder(CurrentContext)
                    .setTitle("The timer just started")
                    .setMessage("Better luck next time.\nUnfortunately you scored 0 points")

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
            HandleHighScore(0);
        }
        else{
            double exactSeconds = (double)timeLeft / timeInterval - 1;
            if(exactSeconds >= 0.95){//0.95 because of time needed to run the code
                Score = (long)(10000 - (exactSeconds - 0.95) * 10000);
            }
            else
                Score = (long)(10000 - (0.95 - exactSeconds) * 10000);
            HandleHighScore((int) Score);
            new AlertDialog.Builder(CurrentContext)
                    .setTitle("Congratulations")
                    .setMessage("You did a good job.\nYou scored " + Long.toString(Score) + " points")

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
    }

    private void HandleHighScore(int highScore) {
        ArrayList<HighScoreRowModel> highScoreRowModels = LoadHighScores(CurrentContext);
        int position = highScoreRowModels.size();
        boolean wasOverwritten = false;
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());

        if(highScoreRowModels.size() == 0)
            SaveHighScore(Integer.toString(highScore), 0, formatter.format(date), MODE_PRIVATE);
        else {
            if (!(highScore < Integer.parseInt(highScoreRowModels.get(highScoreRowModels.size() - 1).getScore()))) {
                for (int j = highScoreRowModels.size() - 1; j >= 0; j--) {
                    int currentHighScore = Integer.parseInt(highScoreRowModels.get(j).getScore());
                    if (currentHighScore < highScore)
                        position = j;
                    else
                        break;
                }
                if(position < 10) {
                    for (int i = 0; i < position; i++)
                        if (!wasOverwritten) {
                            SaveHighScore(highScoreRowModels.get(i).getScore(), i, highScoreRowModels.get(i).getDateTime(), MODE_PRIVATE);
                            wasOverwritten = true;
                        } else
                            SaveHighScore(highScoreRowModels.get(i).getScore(), i, highScoreRowModels.get(i).getDateTime(), MODE_APPEND);
                    if (!wasOverwritten) {
                        SaveHighScore(Integer.toString(highScore), position, formatter.format(date), MODE_PRIVATE);
                        wasOverwritten = true;
                    } else
                        SaveHighScore(Integer.toString(highScore), position, formatter.format(date), MODE_APPEND);

                    for (int j = position; j < highScoreRowModels.size() && j < 9; j++)
                        if (!wasOverwritten) {
                            SaveHighScore(highScoreRowModels.get(j).getScore(), j + 1, highScoreRowModels.get(j).getDateTime(), MODE_PRIVATE);
                            wasOverwritten = true;
                        } else
                            SaveHighScore(highScoreRowModels.get(j).getScore(), j + 1, highScoreRowModels.get(j).getDateTime(), MODE_APPEND);
                }
            }
            else
                if(highScoreRowModels.size() < 10)
                    SaveHighScore(Integer.toString(highScore), highScoreRowModels.size(),formatter.format(date), MODE_APPEND);
        }
    }

    private void SaveHighScore(String highScore, int position, String date, int mode) {
        FileOutputStream fos = null;
        try {


            fos = CurrentContext.openFileOutput(FILE_NAME, mode);
            fos.write((position + "___").getBytes());
            fos.flush();
            fos.write((highScore + "___").getBytes());
            fos.write((date + "\n").getBytes());
            Log.i("path", "Saved to " + CurrentContext.getFilesDir() + "/" + FILE_NAME);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void StartTimer(){
        GameStarted = true;
        countDownTimer = new CountDownTimer(timeLeft, timeInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                int secondsLeft = (int) Math.round(((double)timeLeft / timeInterval) - 1);
                Log.i("seconds", Long.toString(timeLeft / timeInterval));
                Log.i("seconds", Integer.toString(secondsLeft) + " main");
                if (secondsLeft >= 2) {
                    CurrentColorIndex = CurrentColorIndex + 1;
                    CurrentView.setBackgroundColor(Colors[CurrentColorIndex]);
                    CounterText.setValue(Integer.toString(secondsLeft));
                }
            }

            @Override
            public void onFinish() {
                HandleHighScore(0);
                new AlertDialog.Builder(CurrentContext)
                        .setTitle("The timer finished")
                        .setMessage("Better luck next time.\nUnfortunately you scored 0 points")

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
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    private ArrayList<HighScoreRowModel> LoadHighScores(Context context){
        ArrayList<HighScoreRowModel> highScoreRowModels = new ArrayList<>();

        FileInputStream fileInputStream = null;

        try {
            fileInputStream = CurrentContext.openFileInput(FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();

            String text;
            while((text = bufferedReader.readLine()) != null){
                stringBuilder.append(text).append("\n");
            }

            String result = stringBuilder.toString();
            String[] lines = result.split("\n");
            for (String line : lines) {
                if(line != "") {
                    String[] words = line.split("___");
                    highScoreRowModels.add(Integer.parseInt(words[0]), new HighScoreRowModel(words[1], words[2], Integer.parseInt(words[0])));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        highScoreRowModels = GetOrderedList(highScoreRowModels);
        return highScoreRowModels;
    }

    private ArrayList<HighScoreRowModel> GetOrderedList(ArrayList<HighScoreRowModel> myList){
        Object[] list = myList.toArray();
        ArrayList<HighScoreRowModel> highScoreRowModels = new ArrayList<>();


        for(int i = 0; i < list.length - 1; i++)
            for(int j = i + 1; j <list.length; j++) {
                HighScoreRowModel elem1 = (HighScoreRowModel) list[i];
                HighScoreRowModel elem2 = (HighScoreRowModel) list[j];
                if (elem1.AmIBigger(elem2) < 0) {
                    Object aux = list[i];
                    list[i] = list[j];
                    list[j] = aux;
                }
            }

        for(int i = 0; i < list.length; i++)
            highScoreRowModels.add((HighScoreRowModel) list[i]);

        return highScoreRowModels;
    }
}