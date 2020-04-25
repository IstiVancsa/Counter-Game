package com.example.countdownproject.ui.highScore;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class HighScoreViewModel extends ViewModel {

    private static final String SharedPrefName = "HighScoresPreferences";
    private static final String HighScoreText = "HighScore";
    private static final String DateTimeText = "DateTime";
    private Context CurrentContext;
    public static final String FILE_NAME = "high_scores.txt";

    public HighScoreViewModel(Context context) {
        CurrentContext = context;
    }

    public ArrayList<HighScoreRowModel> getHighScoreList() {
       return LoadHighScores(CurrentContext);
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
                String[] words = line.split("___");
                highScoreRowModels.add(Integer.parseInt(words[0]), new HighScoreRowModel(words[1], words[2]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return highScoreRowModels;
    }
}