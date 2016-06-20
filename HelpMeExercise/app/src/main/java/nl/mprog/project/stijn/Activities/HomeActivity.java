package nl.mprog.project.stijn.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import nl.mprog.project.stijn.Classes.AsyncTaskManager;
import nl.mprog.project.stijn.Classes.ExerciseModel;
import nl.mprog.project.stijn.Classes.SQLDatabaseControler;
import nl.mprog.project.stijn.Classes.ScheduleAdapter;
import nl.mprog.project.stijn.Classes.WorkoutModel;
import nl.mprog.project.stijn.R;

/**
 * Creates list of workouts per day, starts data retrieval from API and stores that data in SQL
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    //Fields
    public Button newWorkoutButton;
    public ListView mPlannerList;
    public ListView mWorkoutCollection;

    public List<ExerciseModel> storageList;

    public AsyncTaskManager asyncTaskManager;
    public SQLDatabaseControler mSQLDatabaseController;
    public ScheduleAdapter mScheduleAdapter;

    /**
     * Initialize views and start Asynctaskmanager
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
     * Initialize views
     */
    public void init() {

        // Initialize all buttons
        newWorkoutButton = (Button) findViewById(R.id.newWorkoutButton);

        // Set week planner view
        mPlannerList = (ListView) findViewById(R.id.weekList);
        mWorkoutCollection = (ListView) findViewById(R.id.workoutCollection);

        // Set OnClick listeners
        newWorkoutButton.setOnClickListener(this);

        // Initialize List
        storageList = new ArrayList<>();

        // Initialize database
        mSQLDatabaseController = new SQLDatabaseControler(getApplicationContext());

        // Initialize AsyncTaskManager
        asyncTaskManager = new AsyncTaskManager(this);

        // Load list with week planning
        showWeekSchema();
    }

    /**
     * Handles click event
    */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.newWorkoutButton:
                startNewWorkout();
                break;
            default:
                break;
        }
    }

    /**
     * Starts NewWorkoutActivity
     */
    public void startNewWorkout() {
        Intent newWorkoutIntent = new Intent(this, NewWorkoutActivity.class);
        startActivity(newWorkoutIntent);
    }

    /**
     * Launch TagAsyncTask
     */
    public void executeAsync() {
        asyncTaskManager.execute();
    }

    /**
     * Store data from API in local SQL database
     */
    public void holdList(List<ExerciseModel> exerciseList){
        storageList = exerciseList;
        mSQLDatabaseController.writeExerciseDatabase(this, storageList);
    }

    /**
     * Get workouts from SQL database and show in list
     */
    public void showWeekSchema() {
        List<WorkoutModel> mList = mSQLDatabaseController.getSchemaData();
        mScheduleAdapter = new ScheduleAdapter(this, mList);
        mPlannerList.setAdapter(mScheduleAdapter);
        mScheduleAdapter.notifyDataSetChanged();

    }
}
