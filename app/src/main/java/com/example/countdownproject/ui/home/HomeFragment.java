package com.example.countdownproject.ui.home;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.countdownproject.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView counterTextView;
    private Button start_stop_button;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        homeViewModel = ViewModelProviders.of(this, new HomeViewModelFactory(getContext(), root)).get(HomeViewModel.class);

        counterTextView = root.findViewById(R.id.counter_text);
        start_stop_button = root.findViewById(R.id.start_stop_button);

        homeViewModel.getCounterText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                counterTextView.setText(s);
            }
        });
        homeViewModel.getButtonText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                start_stop_button.setText(s);
            }
        });

        start_stop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeViewModel.StartStop();
            }
        });

        return root;
    }
}