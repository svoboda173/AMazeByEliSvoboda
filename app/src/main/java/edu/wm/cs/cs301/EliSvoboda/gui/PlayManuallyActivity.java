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
import android.widget.Toast;

import edu.wm.cs.cs301.EliSvoboda.R;

public class PlayManuallyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int stepsTaken = 0;
        int distFromMinotaur = 0;


        //MazePanel panel = new MazePanel(PlayManuallyActivity.this, );


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

        shortCut.setOnClickListener(new View.OnClickListener() {
            /**
             * Move to finish screen, sending relevant info about steps taken and distance from Minotaur (battery usage)
             */
            public void onClick(View v) {
                Intent intent = new Intent(PlayManuallyActivity.this, FinishActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("stepsTaken", stepsTaken);
                bundle.putInt("distFromMinotaur", distFromMinotaur);
                bundle.putBoolean("escaped", true);
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
                Log.v("PlayManuallyActivity", "You toggled the map");
                Toast.makeText(PlayManuallyActivity.this, "You toggled the map", Toast.LENGTH_SHORT).show();
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            /**
             * Move character forward
             */
            public void onClick(View v) {
                Log.v("PlayManuallyActivity", "You moved forward");
                Toast.makeText(PlayManuallyActivity.this, "You moved forward", Toast.LENGTH_SHORT).show();
            }
        });

        jump.setOnClickListener(new View.OnClickListener() {
            /**
             * Jump character over wall in front of them
             */
            public void onClick(View v) {
                Log.v("PlayManuallyActivity", "You jumped over the wall");
                Toast.makeText(PlayManuallyActivity.this, "You jumped over the wall", Toast.LENGTH_SHORT).show();
            }
        });

        lookLeft.setOnClickListener(new View.OnClickListener() {
            /**
             * Rotate view left
             */
            public void onClick(View v) {
                Log.v("PlayManuallyActivity", "You looked left");
                Toast.makeText(PlayManuallyActivity.this, "You looked left", Toast.LENGTH_SHORT).show();
            }
        });

        lookRight.setOnClickListener(new View.OnClickListener() {
            /**
             * Rotate view right
             */
            public void onClick(View v) {
                Log.v("PlayManuallyActivity", "You looked right");
                Toast.makeText(PlayManuallyActivity.this, "You looked right", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(PlayManuallyActivity.this, "You can now see the whole maze on your map", Toast.LENGTH_SHORT).show();
        } else {
            Log.v("PlayManuallyActivity", "You can no longer see the whole maze on your map");
            Toast.makeText(PlayManuallyActivity.this, "You can no longer see the whole maze on your map", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * Respond to the user clicking on "reveal path out?", revealing the yellow solution path out on their map
     */
    public void revealPathClicked(View view) {
        boolean checked = ((CheckedTextView) view).isChecked();
        if (checked) {
            Log.v("PlayManuallyActivity", "You can now see the path out on your map");
            Toast.makeText(PlayManuallyActivity.this, "You can now see the path out on your map", Toast.LENGTH_SHORT).show();
        } else {
            Log.v("PlayManuallyActivity", "You can no longer see the path out on your map");
            Toast.makeText(PlayManuallyActivity.this, "You can no longer see the path out on your map", Toast.LENGTH_SHORT).show();
        }
    }
}