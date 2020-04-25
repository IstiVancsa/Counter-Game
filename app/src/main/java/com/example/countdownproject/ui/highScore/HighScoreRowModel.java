package com.example.countdownproject.ui.highScore;

public class HighScoreRowModel {
    private String Score;
    private String DateTime;

    public HighScoreRowModel(String score, String dateTime) {
        Score = score;
        DateTime = dateTime;
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
}
