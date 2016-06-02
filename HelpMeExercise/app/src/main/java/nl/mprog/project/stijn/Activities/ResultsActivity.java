package nl.mprog.project.stijn.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import nl.mprog.project.stijn.R;

/**
 * TODO
 */
public class ResultsActivity extends AppCompatActivity implements View.OnClickListener {

    // Fields
    public TextView resultsTitle;
    public TextView exerciseInstructions;
    public ImageView instructionImages;
    public Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Initialize views
        init();
    }

    /**
     *
     */
    public void init() {

        resultsTitle = (TextView) findViewById(R.id.resultsTitle);
        exerciseInstructions = (TextView) findViewById(R.id.exerciseInstructions);

        instructionImages = (ImageView) findViewById(R.id.instructionImages);

        homeButton = (Button) findViewById(R.id.homeButton);

        homeButton.setOnClickListener(this);

    }

    /**
     * TODO
     */
    @Override
    public void onClick(View v) {
        backToHome();
    }

    public void backToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
