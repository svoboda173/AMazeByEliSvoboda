package edu.wm.cs.cs301.EliSvoboda.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import edu.wm.cs.cs301.EliSvoboda.R;

public class FinishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        TextView minotaurDist = findViewById(R.id.MinotaurDist);
        TextView journeyLength = findViewById(R.id.JourneyLength);
    }
}