package com.example.countdownproject.ui.highScore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class HighScoreRowModel{
    private String Score;
    private String DateTime;
    private int Position;

    public HighScoreRowModel(String score, String dateTime, int position) {
        Score = score;
        DateTime = dateTime;
        Position = position;
    }

    public int getPosition() {
        return Position;
    }

    public void setPosition(int position) {
        Position = position;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public int AmIBigger(HighScoreRowModel highScoreRowModel){
        if(Integer.parseInt(getScore()) > Integer.parseInt(highScoreRowModel.getScore()))
            return 1;
        else
            if(Integer.parseInt(getScore()) == Integer.parseInt(highScoreRowModel.getScore()))
                return 0;
            else
                return -1;
    }

    @NonNull
    @Override
    public String toString() {
        return getScore();
    }
}
