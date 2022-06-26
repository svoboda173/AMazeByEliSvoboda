package edu.wm.cs.cs301.EliSvoboda.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Spinner;

import edu.wm.cs.cs301.EliSvoboda.R;

public class AMazeActivity extends AppCompatActivity {

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
    }
}