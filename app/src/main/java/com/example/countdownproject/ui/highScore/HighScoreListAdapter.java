package com.example.countdownproject.ui.highScore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.countdownproject.R;

import java.util.List;

public class HighScoreListAdapter extends ArrayAdapter<HighScoreRowModel> {
    private static final String TAG = "ProductListAdapter";
    private Context mContext;
    private int mResource;

    public HighScoreListAdapter(@NonNull Context context, int resource, @NonNull List<HighScoreRowModel> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String score = getItem(position).getScore();
        String dateTime = getItem(position).getDateTime();

        HighScoreRowModel highScoreRowModel = new HighScoreRowModel(score, dateTime);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView highScoreTextView = convertView.findViewById(R.id.score_textview);
        TextView dateTimeTextView = convertView.findViewById(R.id.datetime_textview);

        highScoreTextView.setText(score);
        dateTimeTextView.setText(dateTime);

        return convertView;
    }
}
