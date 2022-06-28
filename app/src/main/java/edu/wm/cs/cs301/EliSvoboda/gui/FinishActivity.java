package edu.wm.cs.cs301.EliSvoboda.gui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import edu.wm.cs.cs301.EliSvoboda.R;

public class FinishActivity extends AppCompatActivity {
    boolean escaped;
    int stepsTaken;
    int distFromMinotaur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        ImageView successImage = findViewById(R.id.successImage);
        TextView victory = findViewById(R.id.victory);
        TextView youEscaped = findViewById(R.id.youEscaped);
        TextView minotaurDist = findViewById(R.id.MinotaurDist);
        TextView journeyLength = findViewById(R.id.JourneyLength);

        Bundle b = getIntent().getExtras();
        stepsTaken = b.getInt("stepsTaken");
        distFromMinotaur = b.getInt("distFromMinotaur");
        escaped = b.getBoolean("escaped");

        if (!escaped) {
            successImage.setImageResource(R.drawable.defeat);
            victory.setText("DEFEAT...");
            youEscaped.setText("The minotaur ate well tonight. Try again?");
        }

        minotaurDist.setText(distFromMinotaur + " meters");
        journeyLength.setText(stepsTaken + " steps");
    }
}