package nl.mprog.project.stijn.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import nl.mprog.project.stijn.R;

/**
 * TODO
 */
public class NewWorkoutActivity extends AppCompatActivity implements View.OnClickListener {

    // fields
    public TextView newWorkoutTitle;
    public EditText searchTermBox;
    public Button searchButton;
    public Button homeButton;

    /**
     * TODO
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_workout);

        // Initializes views
        init();
    }

    /**
     * TODO
     */
    public void init() {
        newWorkoutTitle = (TextView) findViewById(R.id.newWorkoutTitle);
        searchTermBox = (EditText) findViewById(R.id.searchTermBox);
        searchButton = (Button) findViewById(R.id.searchButton);
        homeButton = (Button) findViewById(R.id.homeButton);

        homeButton.setOnClickListener(this);
        searchButton.setOnClickListener(this);
    }

    /**
     * TODO
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.searchButton:
                startResulsActivity();
                break;
            case R.id.homeButton:
                backToHome();
                break;
            default:
                break;
        }
    }

    /**
     * TODO
     */
    public void startResulsActivity() {
        Intent intent = new Intent(this, ResultsActivity.class);
        startActivity(intent);
    }

    public void backToHome(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
