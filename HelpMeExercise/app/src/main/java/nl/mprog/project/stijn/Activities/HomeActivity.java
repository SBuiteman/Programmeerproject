package nl.mprog.project.stijn.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import nl.mprog.project.stijn.Classes.AsyncTaskManager;
import nl.mprog.project.stijn.R;

/**
 * TODO
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    //Fields
    public Button newWorkoutButton;
    public Button myScheduleButton;
    public Button myProgressButton;

    public AsyncTaskManager asyncTaskManager;

    /**
     * TODO
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize views
        init();

        // Start AsyncTaskManager
        executeAsync();
    }

    /**
     * TODO
     */
    public void init() {

        // Initialize all buttons
        newWorkoutButton = (Button) findViewById(R.id.newWorkoutButton);
        myScheduleButton = (Button) findViewById(R.id.myScheduleButton);
        myProgressButton = (Button) findViewById(R.id.myProgressButton);

        // Set OnClick listeners
        newWorkoutButton.setOnClickListener(this);
        myScheduleButton.setOnClickListener(this);
        myProgressButton.setOnClickListener(this);

        // Initialize AsyncTaskManager
        asyncTaskManager = new AsyncTaskManager(this);
    }

    /**
     *TODO
    */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.newWorkoutButton:
                startNewWorkout();
                break;
            case R.id.myScheduleButton:
                startMySchedule();
                break;
            case R.id.myProgressButton:
                startMyProgress();
                break;
            default:
                break;
        }
    }

    /**
     * TODO
     */
    public void startNewWorkout() {
        Intent newWorkoutIntent = new Intent(this, NewWorkoutActivity.class);
        startActivity(newWorkoutIntent);
    }

    /**
     * TODO
     */
    public void startMyProgress() {
        Intent intent = new Intent(this, ProgressActivity.class);
        startActivity(intent);
    }

    /**
     * TODO
     */
    public void startMySchedule() {
        Intent intent = new Intent(this, ProgressActivity.class);
        startActivity(intent);
    }

    /**
     * launch TagAsyncTask and pass edited searchterm
     */
    public void executeAsync() {
        asyncTaskManager.execute();
    }
}
