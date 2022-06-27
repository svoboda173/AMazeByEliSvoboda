package edu.wm.cs.cs301.EliSvoboda.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import edu.wm.cs.cs301.EliSvoboda.R;

public class PlayAnimationActivity extends AppCompatActivity {
    int stepsTaken = 0;
    int distFromMinotaur = 0;

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

        shortCut.setOnClickListener(new View.OnClickListener() {

            /**
             * Move to finish screen, sending relevant info about steps taken and distance from Minotaur (battery usage)
             */
            public void onClick(View v) {
                Intent intent = new Intent(PlayAnimationActivity.this, FinishActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("stepsTaken", stepsTaken);
                bundle.putInt("distFromMinotaur", distFromMinotaur);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            /**
             * Toggle map
             */
            public void onClick(View v) {
                Log.v("PlayAnimationActivity", "You toggled the map");
                Toast.makeText(PlayAnimationActivity.this, "You toggled the map", Toast.LENGTH_SHORT).show();
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            /**
             * Pause automatic exploration animation
             */
            public void onClick(View v) {
                Log.v("PlayAnimationActivity", "You paused the animation");
                Toast.makeText(PlayAnimationActivity.this, "You paused the animation", Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * Respond to the user clicking on "reveal maze?", revealing the entire maze on the map display rather than just the nearest walls
     */
    public void revealMazeClicked(View view) {
        boolean checked = ((CheckedTextView) view).isChecked();
        if (checked) {
            Log.v("PlayManuallyActivity", "You can now see the whole maze on your map");
            Toast.makeText(PlayAnimationActivity.this, "You can now see the whole maze on your map", Toast.LENGTH_SHORT).show();
        } else {
            Log.v("PlayManuallyActivity", "You can no longer see the whole maze on your map");
            Toast.makeText(PlayAnimationActivity.this, "You can no longer see the whole maze on your map", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * Respond to the user clicking on "reveal path out?", revealing the yellow solution path out on their map
     */
    public void revealPathClicked(View view) {
        boolean checked = ((CheckedTextView) view).isChecked();
        if (checked) {
            Log.v("PlayManuallyActivity", "You can now see the path out on your map");
            Toast.makeText(PlayAnimationActivity.this, "You can now see the path out on your map", Toast.LENGTH_SHORT).show();
        } else {
            Log.v("PlayManuallyActivity", "You can no longer see the path out on your map");
            Toast.makeText(PlayAnimationActivity.this, "You can no longer see the path out on your map", Toast.LENGTH_SHORT).show();
        }
    }
}