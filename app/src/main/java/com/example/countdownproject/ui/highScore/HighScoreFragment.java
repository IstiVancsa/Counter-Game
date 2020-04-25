package com.example.countdownproject.ui.highScore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.countdownproject.R;

public class HighScoreFragment extends Fragment {

    private HighScoreViewModel highScoreViewModel;
    HighScoreListAdapter adapter;
    private ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_high_score, container, false);

        highScoreViewModel = ViewModelProviders.of(this, new HighScoreViewModelFactory(getContext())).get(HighScoreViewModel.class);

        adapter = new HighScoreListAdapter(getActivity(), R.layout.high_score_listview_row, highScoreViewModel.getHighScoreList());

        listView = root.findViewById((R.id.high_scores_listview));
        ViewGroup headerView = (ViewGroup)getLayoutInflater().inflate(R.layout.high_score_listview_header, listView, false);
        listView.addHeaderView(headerView);
        listView.setAdapter(adapter);

        return root;
    }
}