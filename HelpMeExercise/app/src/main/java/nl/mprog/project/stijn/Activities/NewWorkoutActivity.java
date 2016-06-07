package nl.mprog.project.stijn.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import nl.mprog.project.stijn.Classes.AsyncTaskManager;
import nl.mprog.project.stijn.Classes.ExerciseListAdapter;
import nl.mprog.project.stijn.Classes.ExerciseModel;
import nl.mprog.project.stijn.R;

/**
 * TODO
 */
public class NewWorkoutActivity extends AppCompatActivity implements View.OnClickListener {

    // fields
    public TextView newWorkoutTitle;
    public EditText searchTermBox;
    public ListView exerciseListView;
    public Button searchButton;
    public Button homeButton;

    public List<ExerciseModel> exerciseList;
    public ExerciseListAdapter mAdapter;

    // OM TE TESTEN!!!!!!!!!!!!!!
    public List<ExerciseModel> storageList;

    public AsyncTaskManager asyncTaskManager;

    /**
     * TODO
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_workout);

        // Initializes views
        init();

        // OM TE TESTEN !!!!!!
        // Start AsyncTaskManager
        executeAsync();
    }

    /**
     * TODO
     */
    public void init() {
        newWorkoutTitle = (TextView) findViewById(R.id.newWorkoutTitle);
        searchTermBox = (EditText) findViewById(R.id.searchTermBox);
        exerciseListView = (ListView) findViewById(R.id.singleList);
        searchButton = (Button) findViewById(R.id.searchButton);
        homeButton = (Button) findViewById(R.id.homeButton);

        homeButton.setOnClickListener(this);
        searchButton.setOnClickListener(this);

        // OM TE TESTEN!!!!!!!
        // Initialize AsyncTaskManager
        asyncTaskManager = new AsyncTaskManager(this);

        // Check for intent and retrieve it
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            exerciseList = (List<ExerciseModel>) extras.get("stored list");
            showExerciseData(exerciseList);
        }
    }

    /**
     * TODO
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.searchButton:
                startResultsActivity();
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
}
