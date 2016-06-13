package nl.mprog.project.stijn.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import nl.mprog.project.stijn.Classes.ExerciseListAdapter;
import nl.mprog.project.stijn.Classes.ExerciseModel;
import nl.mprog.project.stijn.Classes.SQLDatabaseControler;
import nl.mprog.project.stijn.R;

/**
 * TODO
 */
public class ResultsActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    // Fields
    public TextView resultsTitle;
    public TextView exerciseInstructions;
    public ImageView instructionImages;
    public Button homeButton;
    public ListView exerciseListView;
    public Spinner mCategorySpinner;
    public Spinner mMuscleSpinner;

    public String mChosenWorkout;

    public List<ExerciseModel> mList;

    public ExerciseListAdapter mAdapter;
    public SQLDatabaseControler mSQLDatabaseController;

    /**
     * TODO
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // gets the name of the selectected workout
        mChosenWorkout = this.getIntent().getStringExtra("key");

        // Initialize views
        init();
    }

    /**
     * TODO
     */
    public void init() {

        resultsTitle = (TextView) findViewById(R.id.resultsTitle);
        exerciseInstructions = (TextView) findViewById(R.id.exerciseInstructions);

        instructionImages = (ImageView) findViewById(R.id.instructionImages);

        homeButton = (Button) findViewById(R.id.homeButton);

        exerciseListView = (ListView) findViewById(R.id.resultsListView);

        mCategorySpinner = (Spinner) findViewById(R.id.resultsSpinner1);
        mMuscleSpinner = (Spinner) findViewById(R.id.resultsSpinner2);

        // String for categories
        String[] mCategories = new String[]{"7", "8", "9", "10", "11", "12", "13", "14"};

        // String for categories
        String[] mMuscles = new String[]{"1", "2", "3", "4", "5", "6", "7", "8"};

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
         * Adapter implementing categorySpinner
         */
        ArrayAdapter<String> mMuscleAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,mMuscles);

        mMuscleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMuscleSpinner.setAdapter(mCategoryAdapter);
        mMuscleSpinner.setOnItemSelectedListener(this);

        /**
         * onItemClick launch SingleMovieActivity and pass title of movie clicked
         */
        exerciseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = ((TextView) view.findViewById(R.id.exerciseName)).getText().toString();

                Intent intent = new Intent(getApplicationContext(),
                        ExerciseSettingsActivity.class);
                intent.putExtra("key", title);
                startActivity(intent);
            }
        });
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
