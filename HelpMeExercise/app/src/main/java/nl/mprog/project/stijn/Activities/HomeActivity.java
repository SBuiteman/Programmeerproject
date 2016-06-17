package nl.mprog.project.stijn.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import nl.mprog.project.stijn.Classes.AsyncTaskManager;
import nl.mprog.project.stijn.Classes.ExerciseModel;
import nl.mprog.project.stijn.Classes.InScheduleAdapter;
import nl.mprog.project.stijn.Classes.SQLContractClass;
import nl.mprog.project.stijn.Classes.SQLDatabaseControler;
import nl.mprog.project.stijn.Classes.ScheduleAdapter;
import nl.mprog.project.stijn.Classes.WorkoutModel;
import nl.mprog.project.stijn.R;

/**
 * TODO
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    //Fields
    public Button newWorkoutButton;
    public ListView mPlannerList;
    public ListView mWorkoutCollection;

    public List<ExerciseModel> storageList;

    public AsyncTaskManager asyncTaskManager;
    public SQLContractClass mSQLContractClass;
    public SQLDatabaseControler mSQLDatabaseController;
    public ScheduleAdapter mScheduleAdapter;
    public InScheduleAdapter mInScheduleAdapter;

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

        // Set week planner view
        mPlannerList = (ListView) findViewById(R.id.weekList);
        mWorkoutCollection = (ListView) findViewById(R.id.workoutCollection);

        // Set OnClick listeners
        newWorkoutButton.setOnClickListener(this);

        // Initialize List
        storageList = new ArrayList<>();

        // Initialize AsyncTaskManager
        asyncTaskManager = new AsyncTaskManager(this);

        mSQLDatabaseController = new SQLDatabaseControler(getApplicationContext());

        showWeekSchema();
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
            default:
                break;
        }
    }

    /**
     * TODO
     */
    public void startNewWorkout() {
        Intent newWorkoutIntent = new Intent(this, NewWorkoutActivity.class);
        Bundle extras = new Bundle();
        extras.putSerializable("stored list", (Serializable) storageList);
        startActivity(newWorkoutIntent);
    }

    /**
     * launch TagAsyncTask and pass edited searchterm
     */
    public void executeAsync() {
        asyncTaskManager.execute();
    }

    /**
     * TODO
     */
    public void holdList(List<ExerciseModel> exerciseList){
        storageList = exerciseList;
        Log.d("Krijg ik objectlist?", "holdList: " + storageList.get(0).getExerciseName());

        mSQLDatabaseController.writeExerciseDatabase(this, storageList);
    }

    public void showWeekSchema() {
        List<WorkoutModel> mList = mSQLDatabaseController.getSchemaData();
        mScheduleAdapter = new ScheduleAdapter(this, mList);
        mPlannerList.setAdapter(mScheduleAdapter);
        mScheduleAdapter.notifyDataSetChanged();

    }
}
