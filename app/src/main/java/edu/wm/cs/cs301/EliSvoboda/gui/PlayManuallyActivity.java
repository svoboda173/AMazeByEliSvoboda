package edu.wm.cs.cs301.EliSvoboda.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageButton;

import edu.wm.cs.cs301.EliSvoboda.R;

public class PlayManuallyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_manually);

        Button shortCut = findViewById(R.id.shortcutManual);
        ImageButton map = findViewById(R.id.imageButton);
        CheckedTextView revealMaze = findViewById(R.id.revealMaze);
        CheckedTextView revealPath = findViewById(R.id.revealPath);
        Button forward = findViewById(R.id.forward);
        Button jump = findViewById(R.id.jump);
        Button lookLeft = findViewById(R.id.left);
        Button lookRight = findViewById(R.id.right);

    }
}