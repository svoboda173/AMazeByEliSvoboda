package edu.wm.cs.cs301.EliSvoboda.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import edu.wm.cs.cs301.EliSvoboda.R;

public class PlayAnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_animation);

        Button shortCut = findViewById(R.id.shortcutManual);
        ImageButton map = findViewById(R.id.imageButton);
        CheckedTextView revealMaze = findViewById(R.id.revealMaze);
        CheckedTextView revealPath = findViewById(R.id.revealPath);
        Button pause = findViewById(R.id.pause);
        ProgressBar distanceFromMinotaurBar = findViewById(R.id.progressBar2);
    }
}