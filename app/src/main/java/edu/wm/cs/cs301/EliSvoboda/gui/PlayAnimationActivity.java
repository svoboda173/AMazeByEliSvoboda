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
import edu.wm.cs.cs301.EliSvoboda.generation.Maze;

public class PlayAnimationActivity extends AppCompatActivity {
    int stepsTaken = 0;
    int distFromMinotaur = 0;
    boolean escaped;
    private BasicRobot robot;
    private RobotDriver driver;
    private String mode;
    private Maze maze;
    StatePlaying statePlaying;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_animation);

        MazePanel panel = findViewById(R.id.mazePanel);
        ImageButton map = findViewById(R.id.imageButton);
        CheckedTextView revealMaze = findViewById(R.id.revealMaze);
        CheckedTextView revealPath = findViewById(R.id.revealPath);
        Button pause = findViewById(R.id.pause);
        ProgressBar distanceFromMinotaurBar = findViewById(R.id.progressBar2);
        statePlaying = new StatePlaying();

        mode = getIntent().getStringExtra("driver");
        maze = GeneratingActivity.maze;

        switch (mode) {
            case "Wizard":
                setRobotAndDriver(new BasicRobot(), new Wizard());
                Log.v("PlayAnimationActivity","Running Wizard");
                break;
            case "Wall Follower":
                setRobotAndDriver(new BasicRobot(), new WallFollower());
                Log.v("PlayAnimationActivity","Running WallFollower");
                break;
        }

        getRobot().addDistanceSensor(new BasicSensor(), Robot.Direction.LEFT);
        getRobot().addDistanceSensor(new BasicSensor(), Robot.Direction.FORWARD);
        getDriver().setMaze(maze);
        getDriver().setRobot(getRobot());
        robot.setState(statePlaying);

        statePlaying.setMaze(GeneratingActivity.maze);
        statePlaying.start(panel);


        //shortCut.setOnClickListener(new View.OnClickListener() {

            /**
             * Move to finish screen, sending relevant info about steps taken and distance from Minotaur (battery usage)
             */
            /*public void onClick(View v) {
                Intent intent = new Intent(PlayAnimationActivity.this, FinishActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("stepsTaken", stepsTaken);
                bundle.putInt("distFromMinotaur", distFromMinotaur);
                bundle.putBoolean("escaped", true);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

        shortCutLoss.setOnClickListener(new View.OnClickListener() {
*/
            /**
             * Move to finish screen, sending relevant info about steps taken and distance from Minotaur (battery usage)
             */
           /* public void onClick(View v) {
                Intent intent = new Intent(PlayAnimationActivity.this, FinishActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("stepsTaken", stepsTaken);
                bundle.putInt("distFromMinotaur", distFromMinotaur);
                bundle.putBoolean("escape", false);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
        */

        map.setOnClickListener(new View.OnClickListener() {
            /**
             * Toggle map
             */
            public void onClick(View v) {
                PlayAnimationActivity.this.handleUserInput(Constants.UserInput.TOGGLELOCALMAP, 0);
                Log.v("PlayAnimationActivity", "You toggled the map");
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            /**
             * Pause automatic exploration animation
             */
            public void onClick(View v) {
                //TODO
                Log.v("PlayAnimationActivity", "You paused the animation");
            }
        });

        try {
            getDriver().drive2Exit();
        } catch (Exception e) {
        }
    }
    /**
     * Respond to the user clicking on "reveal maze?", revealing the entire maze on the map display rather than just the nearest walls
     */
    public void revealMazeClicked(View view) {
        PlayAnimationActivity.this.handleUserInput(Constants.UserInput.TOGGLEFULLMAP, 0);
        Log.v("PlayManuallyActivity", "You toggled seeing the full maze.");
    }
    /**
     * Respond to the user clicking on "reveal path out?", revealing the yellow solution path out on their map
     */
    public void revealPathClicked(View view) {
        PlayAnimationActivity.this.handleUserInput(Constants.UserInput.TOGGLESOLUTION, 0);
        Log.v("PlayManuallyActivity", "You toggled seeing the path out of your map");

    }

    private void setRobotAndDriver (Robot robot, RobotDriver robotdriver){
        this.robot = (BasicRobot) robot;
        driver = robotdriver;
    }
    /**
     * The robot that is used in the automated playing mode.
     * Null if run in the manual mode.
     * @return the robot, may be null
     */
    public Robot getRobot() {
        return robot;
    }
    /**
     * The robot driver that is used in the automated playing mode.
     * Null if run in the manual mode.
     * @return the driver, may be null
     */
    public RobotDriver getDriver() {
        return driver;
    }

    public void switchFromPlayingToWinning () {
        Intent intent = new Intent(PlayAnimationActivity.this, FinishActivity.class);
        Bundle bundle = new Bundle();
        stepsTaken = robot.getOdometerReading();
        distFromMinotaur = (int)robot.getBatteryLevel();
        bundle.putInt("stepsTaken", stepsTaken);
        bundle.putInt("distFromMinotaur", distFromMinotaur);
        bundle.putBoolean("escaped", escaped);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();

    }

    public boolean handleUserInput(Constants.UserInput userInput, int value) {
        Boolean ret =  statePlaying.handleUserInput(userInput, value);
        if (statePlaying.isOutside(statePlaying.px, statePlaying.py)) {
            switchFromPlayingToWinning();
        }
        return ret;
    }
}