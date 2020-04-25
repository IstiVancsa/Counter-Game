package com.example.countdownproject.ui.game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.countdownproject.R;

public class GameFragment extends Fragment {

    private GameViewModel gameViewModel;
    private TextView counterTextView;
    private Button start_stop_button;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_game, container, false);

        gameViewModel = ViewModelProviders.of(this, new GameViewModelFactory(getContext(), root)).get(GameViewModel.class);

        counterTextView = root.findViewById(R.id.counter_text);
        start_stop_button = root.findViewById(R.id.start_stop_button);

        gameViewModel.getCounterText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                counterTextView.setText(s);
            }
        });
        gameViewModel.getButtonText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                start_stop_button.setText(s);
            }
        });

        start_stop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameViewModel.HandleStop_StartButton_Clicked();
            }
        });
        return root;
    }
}