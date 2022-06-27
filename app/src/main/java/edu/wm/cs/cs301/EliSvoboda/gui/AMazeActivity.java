package edu.wm.cs.cs301.EliSvoboda.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import edu.wm.cs.cs301.EliSvoboda.R;

public class AMazeActivity extends AppCompatActivity {

    boolean includeRooms = false;
    int currentLevel = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button escapeButton = findViewById(R.id.escapeButton);
        SeekBar sizeBar = findViewById(R.id.sizeBar);
        CheckBox roomCheckbox = findViewById(R.id.roomCheckbox);
        Spinner generationSpinner = findViewById(R.id.generationSpinner);
        Spinner modeSpinner = findViewById(R.id.modeSpinner);
        Button retryButton = findViewById(R.id.retryButton);



        escapeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AMazeActivity.this, GeneratingActivity.class);
                startActivity(intent);
            }
        });

        sizeBar.setMax(15);
        sizeBar.setMin(0);
        sizeBar.setProgress(0);
        sizeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar sizeBar, int i, boolean b) {
               currentLevel = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.v("AMazeActivity", "You changed the size of the maze to " + currentLevel);
                Toast.makeText(AMazeActivity.this, "You changed the size of the maze to " + currentLevel, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void roomsClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        if (checked) {
            includeRooms = true;
            Log.v("AMazeActivity", "You enabled room generation");
            Toast.makeText(AMazeActivity.this, "You enabled room generation", Toast.LENGTH_SHORT).show();
        } else {
            includeRooms = false;
            Log.v("AMazeActivity", "You disabled room generation");
            Toast.makeText(AMazeActivity.this, "You disabled room generation", Toast.LENGTH_SHORT).show();
        }
    }
}