package com.example.countdownproject.ui.share;

import android.content.Intent;
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
import com.example.countdownproject.ui.highScore.HighScoreRowModel;

import java.util.ArrayList;

public class ShareFragment extends Fragment {

    private ShareViewModel shareViewModel;
    private Button shareAsMessageButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_share, container, false);

        shareViewModel = ViewModelProviders.of(this, new ShareViewModelFactory(getContext())).get(ShareViewModel.class);
        shareAsMessageButton = root.findViewById(R.id.shareAsMessageButton);


        shareAsMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<HighScoreRowModel> highScoreRowModels = shareViewModel.LoadHighScores();
                String shareText = "Hy guys!\nCheck out my high score on this amazing app!\nGo get the app and try to get better scores than me!\n";
                for (HighScoreRowModel elem: highScoreRowModels) {
                    shareText += "      " + elem.toString() + "\n";
                }

                Intent SharingIntent = new Intent(Intent.ACTION_SEND);
                SharingIntent.setType("text/plain");
                SharingIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                startActivity(Intent.createChooser(SharingIntent, "Share high scores via"));
            }
        });

        return root;
    }
}