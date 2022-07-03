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

import edu.wm.cs.cs301.EliSvoboda.R;
import edu.wm.cs.cs301.EliSvoboda.generation.Maze;

public class PlayManuallyActivity extends AppCompatActivity {

    int stepsTaken = 0;
    int distFromMinotaur = 0;
    StatePlaying statePlaying;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int stepsTaken = 0;
        int distFromMinotaur = 0;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_manually);

//        Button shortCut = findViewById(R.id.shortcutManual);
        MazePanel panel = findViewById(R.id.mazePanel);
        ImageButton map = findViewById(R.id.imageButton);
        CheckedTextView revealMaze = findViewById(R.id.revealMaze);
        CheckedTextView revealPath = findViewById(R.id.revealPath);
        Button forward = findViewById(R.id.forward);
        Button jump = findViewById(R.id.jump);
        Button lookLeft = findViewById(R.id.left);
        Button lookRight = findViewById(R.id.right);
        statePlaying = new StatePlaying();



        statePlaying.setMaze(GeneratingActivity.maze);
        statePlaying.start(panel);

//        shortCut.setOnClickListener(new View.OnClickListener() {
//            /**
//             * Move to finish screen, sending relevant info about steps taken and distance from Minotaur (battery usage)
//             */
//            public void onClick(View v) {
//                Intent intent = new Intent(PlayManuallyActivity.this, FinishActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putInt("stepsTaken", stepsTaken);
//                bundle.putInt("distFromMinotaur", distFromMinotaur);
//                bundle.putBoolean("escaped", true);
//                intent.putExtras(bundle);
//                startActivity(intent);
//                finish();
//            }
//        });

        map.setOnClickListener(new View.OnClickListener() {
            /**
             * Toggle map
             */
            public void onClick(View v) {
                PlayManuallyActivity.this.handleUserInput(Constants.UserInput.TOGGLELOCALMAP, 0);
                Log.v("PlayManuallyActivity", "You toggled the map");
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            /**
             * Move character forward
             */
            public void onClick(View v) {
                PlayManuallyActivity.this.handleUserInput(Constants.UserInput.UP, 0);
                Log.v("PlayManuallyActivity", "You moved forward");
            }
        });

        jump.setOnClickListener(new View.OnClickListener() {
            /**
             * Jump character over wall in front of them
             */
            public void onClick(View v) {
                PlayManuallyActivity.this.handleUserInput(Constants.UserInput.JUMP, 0);
                Log.v("PlayManuallyActivity", "You jumped over the wall");
            }
        });

        lookLeft.setOnClickListener(new View.OnClickListener() {
            /**
             * Rotate view left
             */
            public void onClick(View v) {
                PlayManuallyActivity.this.handleUserInput(Constants.UserInput.LEFT, 0);
                Log.v("PlayManuallyActivity", "You looked left");
            }
        });

        lookRight.setOnClickListener(new View.OnClickListener() {
            /**
             * Rotate view right
             */
            public void onClick(View v) {
                PlayManuallyActivity.this.handleUserInput(Constants.UserInput.RIGHT, 0);
                Log.v("PlayManuallyActivity", "You looked right");
            }
        });

    }

    /**
     * Respond to the user clicking on "reveal maze?", revealing the entire maze on the map display rather than just the nearest walls
     */
    public void revealMazeClicked(View view) {

        PlayManuallyActivity.this.handleUserInput(Constants.UserInput.TOGGLEFULLMAP, 0);
        Log.v("PlayManuallyActivity", "You toggled seeing the full maze.");

    }
    /**
     * Respond to the user clicking on "reveal path out?", revealing the yellow solution path out on their map
     */
    public void revealPathClicked(View view) {
        PlayManuallyActivity.this.handleUserInput(Constants.UserInput.TOGGLESOLUTION, 0);
        Log.v("PlayManuallyActivity", "You toggled seeing the path out of your map");

    }

    public boolean handleUserInput(Constants.UserInput userInput, int value) {
        Boolean ret =  statePlaying.handleUserInput(userInput, value);
        if (statePlaying.isOutside(statePlaying.px, statePlaying.py)) {
            switchFromPlayingToWinning();
        }
        return ret;
    }

    public void switchFromPlayingToWinning () {
            Intent intent = new Intent(PlayManuallyActivity.this, FinishActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("stepsTaken", stepsTaken);
            bundle.putInt("distFromMinotaur", distFromMinotaur);
            bundle.putBoolean("escaped", true);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();

    }
}