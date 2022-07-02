package edu.wm.cs.cs301.EliSvoboda.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Random;

import edu.wm.cs.cs301.EliSvoboda.R;

public class AMazeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    Random rand;
    boolean includeRooms = false;
    int currentLevel = 0;
    int seed;

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
        sharedPref = AMazeActivity.this.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        rand = new Random();

        modeSpinner.setOnItemSelectedListener(this);
        generationSpinner.setOnItemSelectedListener(this);

        /**
         * when Retry button is clicked, switch activities and bundle important info
         */
        retryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AMazeActivity.this, GeneratingActivity.class);
                Bundle bundle = new Bundle();

                int encoded = includeRooms ? 1 : 0;
                switch (generationSpinner.getSelectedItem().toString()) {
                    case "Kruskal":
                        break;
                    case "Prim":
                        encoded += 10;
                        break;
                    case "DFS":
                        encoded += 20;
                        break;
                }
                encoded += 100 * currentLevel;

                seed = sharedPref.getInt(String.valueOf(encoded), rand.nextInt());

                editor.putInt(String.valueOf(encoded), seed);
                editor.apply();

                bundle.putBoolean("includeRooms", includeRooms);
                bundle.putInt("currentLevel", currentLevel);
                bundle.putString("generation", generationSpinner.getSelectedItem().toString());
                bundle.putString("mode", modeSpinner.getSelectedItem().toString());
                bundle.putInt("seed", seed);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        /**
         * when Escape button is clicked, switch activities and bundle important info
         */
        escapeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AMazeActivity.this, GeneratingActivity.class);
                Bundle bundle = new Bundle();

                seed = rand.nextInt();
                int encoded = includeRooms ? 1 : 0;
                switch (generationSpinner.getSelectedItem().toString()) {
                    case "Kruskal":
                        break;
                    case "Prim":
                        encoded += 10;
                        break;
                    case "DFS":
                        encoded += 20;
                        break;
                }
                encoded += 100 * currentLevel;
                editor.putInt(String.valueOf(encoded), seed);
                editor.apply();

                bundle.putBoolean("includeRooms", includeRooms);
                bundle.putInt("currentLevel", currentLevel);
                bundle.putInt("seed", seed);
                bundle.putString("generation", generationSpinner.getSelectedItem().toString());
                bundle.putString("mode", modeSpinner.getSelectedItem().toString());
                intent.putExtras(bundle);
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

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.generation_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        generationSpinner.setAdapter(adapter);


        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.mode_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modeSpinner.setAdapter(adapter2);

    }

    /**
     * Remember whether or not rooms should be used during generation
     */
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.v("AMazeActivity", "You changed your selection to " + adapterView.getItemAtPosition(i));
        Toast.makeText(AMazeActivity.this, "You changed your selection to " + adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}