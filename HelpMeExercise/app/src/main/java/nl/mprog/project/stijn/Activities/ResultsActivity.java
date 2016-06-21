package nl.mprog.project.stijn.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import nl.mprog.project.stijn.Classes.ExerciseListAdapter;
import nl.mprog.project.stijn.Classes.SQLDatabaseControler;
import nl.mprog.project.stijn.R;

/**
 * TODO
 */
public class ResultsActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    // Fields
    public Button homeButton;
    public ListView exerciseListView;
    public Spinner mCategorySpinner;

    public String mChosenWorkout;

    public ExerciseListAdapter mAdapter;
    public SQLDatabaseControler mSQLDatabaseController;

    /**
     * Get intent containing selected workout
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // gets the name of the selectected workout
        mChosenWorkout = this.getIntent().getStringExtra("key");
        Log.w("Eerste intent", "Is:" + mChosenWorkout);

        // Initialize views
        init();
    }

    /**
     * Initialize views
     */
    public void init() {

        homeButton = (Button) findViewById(R.id.homeButton);

        exerciseListView = (ListView) findViewById(R.id.resultsListView);

        mCategorySpinner = (Spinner) findViewById(R.id.resultsSpinner1);

        // String for categories
        String[] mCategories = new String[]{"All", "Arms", "Legs", "Abs", "Chest", "Back",
                "Shoulders", "Calves"};

        homeButton.setOnClickListener(this);

        mSQLDatabaseController = new SQLDatabaseControler(getApplicationContext());

        startAdapterView();

        /**
         * Adapter implementing categorySpinner
         */
        ArrayAdapter<String> mCategoryAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, mCategories);

        mCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCategorySpinner.setAdapter(mCategoryAdapter);
        mCategorySpinner.setOnItemSelectedListener(this);

        /**
         * Take name selected exercise and put in bundle with chosen workout, start
         * ExerciseSettingsActivity
         */
        exerciseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String mExerciseName = ((TextView) view.findViewById(R.id.exerciseName)).getText()
                        .toString();
                String calledBy = "ResultsActivity";

                Intent intent = new Intent(getApplicationContext(),
                        ExerciseSettingsActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("workoutname", mChosenWorkout);
                mBundle.putString("exercisename", mExerciseName);
                mBundle.putString("calledby", calledBy);
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        backToHome();
    }

    /**
     * Back to HomeActivity
     */
    public void backToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    /**
     * Handles item selection of categorySpinner
     */
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        String mSelectedCategory = (String) parent.getItemAtPosition(position);
        mAdapter.setCategory(mSelectedCategory , mSQLDatabaseController.readExerciseDatabase(this));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * Starts ExerciseListAdapter to show search results
     */
    public void startAdapterView() {
        mAdapter = new ExerciseListAdapter(this, mSQLDatabaseController.readExerciseDatabase(this));
        exerciseListView.setAdapter(mAdapter);
    }
}
