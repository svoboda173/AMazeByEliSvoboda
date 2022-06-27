package edu.wm.cs.cs301.EliSvoboda.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import edu.wm.cs.cs301.EliSvoboda.R;

public class GeneratingActivity extends AppCompatActivity {

    private static int progress;
    private Handler handler = new Handler();
    private int progressStatus = 0; //Method adopted from http://www.java2s.com/Code/Android/UI/UsingThreadandProgressbar.htm

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generating);

        ProgressBar generationProgress = findViewById(R.id.progressBar);
        TextView progressGeneration = findViewById(R.id.progressGenerating);

        int progress = 0;
        generationProgress.setMax(100);
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus = doWork();
                    handler.post(new Runnable() {
                        public void run() {
                            generationProgress.setProgress(progressStatus);
                            progressGeneration.setText(progressStatus + " %");
                        }
                    });
                }
                Intent intent = new Intent(GeneratingActivity.this, PlayManuallyActivity.class);
                startActivity(intent);
            }
        }).start();
    }

    private int doWork() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ++progressStatus;
    }
}