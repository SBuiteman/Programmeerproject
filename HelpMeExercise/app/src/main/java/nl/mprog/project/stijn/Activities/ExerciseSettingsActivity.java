package nl.mprog.project.stijn.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    public Button mAddSetsButton;
    public Button mAddRepsButton;
    public Button mAddWeightButton;

    public String mChosenExerciseName;
    public String mChosenWorkoutName;

    public SQLDatabaseControler mSQLDatabaseController;

    /**
     * TODO
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_settings);

        init();

        // Retriece chosen workout and execise from previous Activities.
        Bundle extras = getIntent().getExtras();
        mChosenWorkoutName = extras.getString("workoutname");;
        mChosenExerciseName = extras.getString("exercisename");
    }

    /**
     * TODO
     */
    public void init(){
        mExerciseName = (TextView) findViewById(R.id.showName);
        mSetsET = (EditText) findViewById(R.id.addSetsEditText);
        mRepsET = (EditText) findViewById(R.id.addRepsEditText);
        mWeightET = (EditText) findViewById(R.id.addWeightEditText);
        mAddSetsButton = (Button) findViewById(R.id.addSetsButton);
        mAddRepsButton = (Button) findViewById(R.id.addRepsButton);
        mAddWeightButton = (Button) findViewById(R.id.addWeightButton);

        mExerciseName.setText(mChosenExerciseName);

        mAddRepsButton.setOnClickListener(this);
        mAddSetsButton.setOnClickListener(this);
        mAddWeightButton.setOnClickListener(this);

        mSQLDatabaseController = new SQLDatabaseControler(getApplicationContext());
    }

    /**
     * TODO
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addSetsButton:

                ExerciseModel mExerciseModel = new ExerciseModel();
                // get workoutid

                mChosenWorkoutName = mChosenWorkoutName.replaceAll(" ", "_");

                // get exercise id
                // put info in object
                // add object to workoutcontent table
                // go back to main or schedule
                break;
            case R.id.addRepsButton:
                break;
            case R.id.addWeightButton:
                break;
            default:
                break;
        }

    }
}
