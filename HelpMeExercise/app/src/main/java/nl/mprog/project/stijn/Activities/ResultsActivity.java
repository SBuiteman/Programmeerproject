package nl.mprog.project.stijn.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import nl.mprog.project.stijn.Classes.ExerciseListAdapter;
import nl.mprog.project.stijn.Classes.SQLDatabaseControler;
import nl.mprog.project.stijn.R;

/**
 *
 */
public class ResultsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        AdapterView.OnItemClickListener {

    // Fields
    public ListView exerciseListView;
    public Spinner mCategorySpinner;

    public String mChosenWorkout;
    public String[] mCategories;

    public ExerciseListAdapter mAdapter;
    public SQLDatabaseControler mSQLDatabaseController;

    /**
     * Get intent containing selected workout.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Gets the name of the selected workout
        mChosenWorkout = this.getIntent().getStringExtra("key");

        // Initialize views
        init();
    }

    /**
     * Initialize views.
     */
    public void init() {
        exerciseListView = (ListView) findViewById(R.id.resultsListView);

        mCategorySpinner = (Spinner) findViewById(R.id.resultsSpinner1);

        // String for categories
        mCategories = new String[]{getString(R.string.all_categories),
                getString(R.string.arm_category), getString(R.string.legs_category),
                getString(R.string.abs_categorie), getString(R.string.chest_category),
                getString(R.string.back_category), getString(R.string.shoulders_category),
                getString(R.string.calves_category)};

        mSQLDatabaseController = new SQLDatabaseControler(getApplicationContext());

        exerciseListView.setOnItemClickListener(this);

        setCategorySpinner();

        startAdapterView();
    }

    /**
     * Take name selected exercise and put in bundle with chosen workout, start
     * ExerciseSettingsActivity.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String mExerciseName = ((TextView) view.findViewById(R.id.exerciseName)).getText()
                .toString();
        String calledBy = getString(R.string.ResultsCalling);

        Intent intent = new Intent(getApplicationContext(),
                ExerciseSettingsActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putString("workoutname", mChosenWorkout);
        mBundle.putString("exercisename", mExerciseName);
        mBundle.putString("calledby", calledBy);
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    /**
     * Start Adapter implementing categorySpinner.
     */
    public void setCategorySpinner() {
        ArrayAdapter<String> mCategoryAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, mCategories);

        mCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCategorySpinner.setAdapter(mCategoryAdapter);
        mCategorySpinner.setOnItemSelectedListener(this);
    }

    /**
     * Handles item selection of categorySpinner.
     */
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        String mSelectedCategory = (String) parent.getItemAtPosition(position);
        mAdapter.setCategory(mSelectedCategory , mSQLDatabaseController.readExerciseDatabase(this));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    /**
     * Starts ExerciseListAdapter to show search results.
     */
    public void startAdapterView() {
        mAdapter = new ExerciseListAdapter(this, mSQLDatabaseController.readExerciseDatabase(this));
        exerciseListView.setAdapter(mAdapter);
    }

    /**
     * On back presses always go to HomeActivity.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.putExtra("backpressed", true);
        startActivity(intent);
    }
}
