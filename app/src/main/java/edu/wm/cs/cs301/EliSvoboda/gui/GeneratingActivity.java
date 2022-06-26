package edu.wm.cs.cs301.EliSvoboda.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import edu.wm.cs.cs301.EliSvoboda.R;

public class GeneratingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generating);

        ProgressBar generationProgress = findViewById(R.id.progressBar);
        TextView progressGeneration = findViewById(R.id.progressGenerating);
    }
}