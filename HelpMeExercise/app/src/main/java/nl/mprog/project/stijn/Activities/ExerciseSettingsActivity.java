package nl.mprog.project.stijn.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import nl.mprog.project.stijn.Classes.ExerciseModel;
import nl.mprog.project.stijn.Classes.SQLDatabaseController;
import nl.mprog.project.stijn.R;

/**
 * Stijn Buiteman
 * stijnbuiteman@gmail.com
 */

/**
 * Input from edittexts is matched with exercises and stored in database via SQLDatabaseController.
 * User is always send back to the activity he came from.
 */
public class ExerciseSettingsActivity extends AppCompatActivity implements View.OnClickListener {

    public TextView mExerciseName;
    public EditText mSetsET;
    public EditText mRepsET;
    public EditText mWeightET;
    public Button mApplyButton;

    public String mChosenExerciseName;
    public String mChosenWorkoutName;
    public String mCalledBy;

    public SQLDatabaseController mSQLDatabaseController;

    /**
     * Get intent with chosen workout and exercise
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_settings);

        // Initialize views
        init();
    }

    /**
     * Initialize views
     */
    public void init(){

        // Retrieve chosen workout, execise and which activity called
        Bundle extras = getIntent().getExtras();
        mChosenWorkoutName = extras.getString("workoutname");
        mChosenExerciseName = extras.getString("exercisename");
        mCalledBy = extras.getString("calledby");

        mExerciseName = (TextView) findViewById(R.id.showName);
        mSetsET = (EditText) findViewById(R.id.addSetsEditText);
        mRepsET = (EditText) findViewById(R.id.addRepsEditText);
        mWeightET = (EditText) findViewById(R.id.addWeightEditText);
        mApplyButton = (Button) findViewById(R.id.applyButton);

        mSetsET.setRawInputType(Configuration.KEYBOARD_12KEY);
        mRepsET.setRawInputType(Configuration.KEYBOARD_12KEY);
        mWeightET.setRawInputType(Configuration.KEYBOARD_12KEY);

        mExerciseName.setText(mChosenExerciseName);

        mApplyButton.setOnClickListener(this);

        mSQLDatabaseController = new SQLDatabaseController(getApplicationContext());
    }

    /**
     * OnClick add the chosen parameters along with the exercise name to exercise table, link
     * with workout table and table containing API data
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.applyButton:

                // Check if user provided necessary input
                if (mSetsET.getText().toString().trim().equals("") || mRepsET.getText().
                        toString().trim().equals("") || mWeightET.getText().toString().trim().
                        equals("")) {
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_LONG).show();
                } else {
                    ExerciseModel mExerciseModel = new ExerciseModel();

                    // Get workoutid
                    mExerciseModel.setWorkoutID(mSQLDatabaseController.getWorkoutID(
                            mChosenWorkoutName));

                    // Get exercise id
                    mExerciseModel.setExerciseId(mSQLDatabaseController.getExerciseID(
                            mChosenExerciseName));

                    // Put info in object
                    ExerciseModel mInputModel = new ExerciseModel();
                    mInputModel.setExerciseId(mExerciseModel.getExerciseId());
                    mInputModel.setWorkoutID(mExerciseModel.getWorkoutID());
                    mInputModel.setSets(mSetsET.getText().toString());
                    mInputModel.setReps(mRepsET.getText().toString());
                    mInputModel.setWeight(mWeightET.getText().toString());

                    // Only call to database if previous Activity was ResultsActivity
                    if(mCalledBy.equals("ResultsActivity")) {

                        // Add object to workoutcontent table
                        mSQLDatabaseController.addWorkoutExercise(mInputModel);

                        Toast.makeText(this, mChosenExerciseName + " added to " + mChosenWorkoutName
                                + " workout.", Toast.LENGTH_LONG).show();
                    } else {

                        // Return the inputmodel to WorkoutContentActivity
                        setResult(Activity.RESULT_OK,
                                new Intent().putExtra("newmodel", mInputModel));
                    }

                    // Go back to previous
                    super.finish();
                }


                break;
            default:
                break;
        }

    }
}
