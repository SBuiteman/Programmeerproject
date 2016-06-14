package nl.mprog.project.stijn.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import nl.mprog.project.stijn.Classes.AsyncTaskManager;
import nl.mprog.project.stijn.Classes.ExerciseListAdapter;
import nl.mprog.project.stijn.Classes.ExerciseModel;
import nl.mprog.project.stijn.Classes.NewWorkoutAdapter;
import nl.mprog.project.stijn.Classes.SQLDatabaseControler;
import nl.mprog.project.stijn.Classes.WorkoutModel;
import nl.mprog.project.stijn.R;

/**
 * TODO
 */
public class NewWorkoutActivity extends AppCompatActivity implements View.OnClickListener {

    // fields
    public TextView newWorkoutTitle;
    public EditText mWorkoutNameBox;
    public ListView exerciseListView;
    public ListView workoutListView;
    public Button mCreateButton;
    public Button homeButton;

    public List<ExerciseModel> exerciseList;
    public ExerciseListAdapter mAdapter;
    public NewWorkoutAdapter mNewWorkoutAdapter;

    // OM TE TESTEN!!!!!!!!!!!!!!
    public List<ExerciseModel> storageList;

    public AsyncTaskManager asyncTaskManager;

    public SQLDatabaseControler mSQLDatabaseController;

    /**
     * TODO
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_workout);

        // Initializes views
        init();

        showWorkoutList();

        // OM TE TESTEN !!!!!!
        // Start AsyncTaskManager
        //executeAsync();
    }

    /**
     * TODO
     */
    public void init() {
        newWorkoutTitle = (TextView) findViewById(R.id.newWorkoutTitle);
        mWorkoutNameBox = (EditText) findViewById(R.id.workoutNameBox);
        //exerciseListView = (ListView) findViewById(R.id.singleList);
        workoutListView = (ListView) findViewById(R.id.singleList);
        mCreateButton = (Button) findViewById(R.id.createButton);
        homeButton = (Button) findViewById(R.id.homeButton);

        homeButton.setOnClickListener(this);
        mCreateButton.setOnClickListener(this);

        mSQLDatabaseController = new SQLDatabaseControler(getApplicationContext());

        // OM TE TESTEN!!!!!!!
        // Initialize AsyncTaskManager
        //asyncTaskManager = new AsyncTaskManager(this);

        // Check for intent and retrieve it
//        Bundle extras = getIntent().getExtras();
//        if (extras != null)
//        {
//            exerciseList = (List<ExerciseModel>) extras.get("stored list");
//            showExerciseData(exerciseList);
//        }

        /**
         * TODO
         */
        workoutListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String mWorkoutName = ((TextView) view.findViewById(R.id.workoutnames))
                        .getText().toString();
                mWorkoutName = mWorkoutName.replaceAll(" ", "_");

                sendIntent(mWorkoutName);
            }
        });
    }

    /**
     * TODO
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.createButton:
                addWorkout();
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
    public void startResultsActivity() {
        Intent intent = new Intent(this, ResultsActivity.class);
        startActivity(intent);
    }

    /**
     * TODO
     */
    public void backToHome(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    /**
    * TODO
    * */
    public void showExerciseData(List<ExerciseModel> list){
        mAdapter = new ExerciseListAdapter(this, list);
        exerciseListView.setAdapter(mAdapter);

    }

    // OM TE TESTEN!!!!!!!
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
        showExerciseData(storageList);
    }

    /**
     * TODO
     */
    public void addWorkout() {

        // Check for input
        if (mWorkoutNameBox.getText() != null) {

            // Add input to table and update adapter
            WorkoutModel mWorkoutModel = new WorkoutModel();
            String mWorkoutName = mWorkoutNameBox.getText().toString();
            //mWorkoutName.replaceAll(" ", "_");
            mWorkoutModel.setmWorkoutName(mWorkoutName.replaceAll(" ", "_"));
            Log.w("Goed in table", mWorkoutModel.getmWorkoutName());
            mSQLDatabaseController.createWorkout(this, mWorkoutModel);
            showWorkoutList();

            sendIntent(mWorkoutModel.getmWorkoutName());
            // Send name of workout to ResultsActivity


        } else {
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
        }

        // If name was entered start activity

    }

    /**
     * TODO
     */
    public void showWorkoutList() {
        mNewWorkoutAdapter = new NewWorkoutAdapter(this, mSQLDatabaseController.getAllTags());
        workoutListView.setAdapter(mNewWorkoutAdapter);
        mNewWorkoutAdapter.notifyDataSetChanged();
    }

    /**
     * TODO
     */
    public void sendIntent(String mWorkoutName){
        Intent intent = new Intent(getApplicationContext(),
                ResultsActivity.class);
        intent.putExtra("key", mWorkoutName);
        startActivity(intent);
    }
}
