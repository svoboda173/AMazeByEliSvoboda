package edu.wm.cs.cs301.EliSvoboda.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import edu.wm.cs.cs301.EliSvoboda.R;
import edu.wm.cs.cs301.EliSvoboda.generation.Factory;
import edu.wm.cs.cs301.EliSvoboda.generation.Maze;
import edu.wm.cs.cs301.EliSvoboda.generation.MazeFactory;
import edu.wm.cs.cs301.EliSvoboda.generation.Order;

public class GeneratingActivity extends AppCompatActivity implements Order {

    private Handler handler;
    private int progressStatus; //Method adopted from http://www.java2s.com/Code/Android/UI/UsingThreadandProgressbar.htm
    private boolean includeRooms;
    private int currentLevel;
    private int seed;
    private Builder builder;
    private String generation;
    private String mode;
    private Order order;
    private Factory factory;
    static Maze maze;
    Intent intent;
    ProgressBar generationProgress;
    TextView progressGeneration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generating);

        generationProgress = findViewById(R.id.progressBar);
        progressGeneration = findViewById(R.id.progressGenerating);

        Bundle b = getIntent().getExtras();
        handler = new Handler();
        seed = b.getInt("seed");
        includeRooms = b.getBoolean("includeRooms");
        currentLevel = b.getInt("currentLevel");
        generation = b.getString("generation");
        mode = b.getString("mode");
        factory = new MazeFactory();

        switch (generation) {
            case "Prim":
                builder = Order.Builder.Prim;
                Log.v("GeneratingActivity","Running Prim");
                break;
            case "Kruskal":
                builder = Order.Builder.Kruskal;
                Log.v("GeneratingActivity","Running Kruskal");
                break;
            default:
                builder = Order.Builder.DFS;
                Log.v("GeneratingActivity","Running DFS");
                break;
        }

        generationProgress.setProgress(0);
        progressGeneration.setText("0 %");
        generationProgress.setMax(100);

        factory.order(GeneratingActivity.this);

       /* new Thread(new Runnable() {
            public void run() {

                /*while (progressStatus < 100) {
                    progressStatus = doWork();
                    handler.post(new Runnable() {
                        public void run() {
                            updateProgress(progressStatus, generationProgress, progressGeneration);
                        }
                    });
                }
            }
        }).start();*/
    }

    /**
     * Increment progress for simulated progress bar
     */
    /*private int doWork() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ++progressStatus;
    }*/

    @Override
    public int getSkillLevel() {
        return currentLevel;
    }

    @Override
    public Builder getBuilder() {
        return builder;
    }

    @Override
    public boolean isPerfect() {
        return !includeRooms;
    }

    @Override
    public int getSeed() {
        return seed;
    }

    @Override
    public void deliver(Maze mazeConfig) {
        this.maze = mazeConfig;
        if (mode.equals("Manual")) {
            intent = new Intent(GeneratingActivity.this, PlayManuallyActivity.class);
        } else {
            intent = new Intent(GeneratingActivity.this, PlayAnimationActivity.class);
            intent.putExtra("driver", mode);
        }
        startActivity(intent);
        finish();

    }

    @Override
    public void updateProgress(int percentage) {
        if (0 <= percentage && percentage <= 100) {
            progressStatus = percentage;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    generationProgress.setProgress(progressStatus);
                    progressGeneration.setText(progressStatus + " %");
                }
            });
        } else {
            progressStatus = (percentage < 0) ? 0 : 100;
            Log.v("GeneratingActivity","range violation, " + percentage + " outside 0,1,...,100 range. Used closest legit value for mitigation.");
        }

    }
/*
    public void updateProgress(int percentage, ProgressBar bar, TextView text) {
        if (0 <= percentage && percentage <= 100) {
            progressStatus = percentage;
            bar.setProgress(progressStatus);
            text.setText(progressStatus + " %");

        } else {
            progressStatus = (percentage < 0) ? 0 : 100;
            bar.setProgress(progressStatus);
            text.setText(progressStatus + " %");
            Log.v("GeneratingActivity","range violation, " + percentage + " outside 0,1,...,100 range. Used closest legit value for mitigation.");
        }

    }*/
}
