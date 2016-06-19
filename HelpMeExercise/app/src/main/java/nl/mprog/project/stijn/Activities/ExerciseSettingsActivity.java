package nl.mprog.project.stijn.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import nl.mprog.project.stijn.Classes.ExerciseModel;
import nl.mprog.project.stijn.Classes.SQLDatabaseControler;
import nl.mprog.project.stijn.R;

public class ExerciseSettingsActivity extends AppCompatActivity implements View.OnClickListener {

    public TextView mExerciseName;
    public EditText mSetsET;
    public EditText mRepsET;
    public EditText mWeightET;
    public Button mApplyButton;

    public String mChosenExerciseName;
    public String mChosenWorkoutName;

    public SQLDatabaseControler mSQLDatabaseController;

    /**
     * Get intent with chosen workout and exercise
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_settings);

        // Initialize views
        init();

        // Retrieve chosen workout and execise from previous Activities.
        Bundle extras = getIntent().getExtras();
        mChosenWorkoutName = extras.getString("workoutname");
        mChosenExerciseName = extras.getString("exercisename");
    }

    /**
     * Initialize views
     */
    public void init(){
        mExerciseName = (TextView) findViewById(R.id.showName);
        mSetsET = (EditText) findViewById(R.id.addSetsEditText);
        mRepsET = (EditText) findViewById(R.id.addRepsEditText);
        mWeightET = (EditText) findViewById(R.id.addWeightEditText);
        mApplyButton = (Button) findViewById(R.id.applyButton);

        mExerciseName.setText(mChosenExerciseName);

        mApplyButton.setOnClickListener(this);

        mSQLDatabaseController = new SQLDatabaseControler(getApplicationContext());
    }

    /**
     * OnClick add the chosen parameters along with the exercise name to exercise table, link
     * with workout table and table containing API data
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.applyButton:

                ExerciseModel mExerciseModel = new ExerciseModel();

                // Get workoutid
                mChosenWorkoutName = mChosenWorkoutName.replaceAll(" ", "_");
                mExerciseModel.setWorkoutID(mSQLDatabaseController.getWorkoutID(
                        mChosenWorkoutName));

                // Get exercise id
                mExerciseModel.setExerciseId(mSQLDatabaseController.getExerciseID(
                        mChosenExerciseName));
                Log.d("WTB exerciseID", "id is " + mExerciseModel.getExerciseId());

                // Put info in object
                ExerciseModel mInputModel = new ExerciseModel();
                mInputModel.setExerciseId(mExerciseModel.getExerciseId());
                mInputModel.setWorkoutID(mExerciseModel.getWorkoutID());
                mInputModel.setSets(mSetsET.getText().toString());
                mInputModel.setReps(mRepsET.getText().toString());
                mInputModel.setWeight(mWeightET.getText().toString());

                // Add object to workoutcontent table
                mSQLDatabaseController.addWorkoutExercise(mInputModel);

                // Go back to previous
                super.finish();
                break;
            default:
                break;
        }

    }
}
