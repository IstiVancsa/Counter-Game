package com.example.countdownproject.ui.share;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.countdownproject.ui.highScore.HighScoreRowModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ShareViewModel extends ViewModel {
    private Context CurrentContext;
    public static final String FILE_NAME = "high_scores.txt";

    public ShareViewModel(Context currentContext) {
        this.CurrentContext = currentContext;
    }

    public ArrayList<HighScoreRowModel> LoadHighScores(){
        ArrayList<HighScoreRowModel> highScoreRowModels = new ArrayList<>();

        FileInputStream fileInputStream = null;

        try {
            File file = new File(CurrentContext.getFilesDir() + "/" + FILE_NAME);
            if(!file.exists())
                return highScoreRowModels;

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