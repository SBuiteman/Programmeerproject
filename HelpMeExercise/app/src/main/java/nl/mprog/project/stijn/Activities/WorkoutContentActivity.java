package nl.mprog.project.stijn.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import nl.mprog.project.stijn.Classes.ExerciseModel;
import nl.mprog.project.stijn.Classes.SQLDatabaseControler;
import nl.mprog.project.stijn.Classes.WorkoutContentAdapter;
import nl.mprog.project.stijn.R;

public class WorkoutContentActivity extends AppCompatActivity {

    // Fields
    public String mSelectedWorkout;
    public List<ExerciseModel> mList;
    public ListView mListView;
    public WorkoutContentAdapter mWorkoutContentAdapter;
    public SQLDatabaseControler mSQLDatabaseController;

    /**
     * Get intent with chosen workout
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_content);

        mSelectedWorkout = this.getIntent().getStringExtra("workout");

        // Initializer
        init();
    }

    /**
     * Initialize views
     */
    public void init(){
        mListView = (ListView) findViewById(R.id.workoutContent);

        // Initialize database
        mSQLDatabaseController = new SQLDatabaseControler(getApplicationContext());

        // Get data from database
        getData();

        /**
         * Set click listener on exercise to enable calls to ExerciseSettings
         */
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String mExercise = mWorkoutContentAdapter.getTextViewText();
                String calledBy = "ContentAdapter";

                // Create intent to start ExerciseSettingsActivity
                Intent intent = new Intent(WorkoutContentActivity.this,
                        ExerciseSettingsActivity.class);

                // Put workoutname and exercise name in bundle
                Bundle mBundle = new Bundle();
                mBundle.putString("workoutname", mSelectedWorkout);
                mBundle.putString("exercisename", mExercise);
                mBundle.putString("calledby", calledBy);
                intent.putExtras(mBundle);

                // Make ExerciseSettings return the object it creates when it's done
                startActivityForResult(intent, 0);
            }
        });

        /**
         * On long click delete entry from workoutcontent table with the matching primary key
         */
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String mTableKey = ((TextView) view.findViewById(R.id.keyValueField))
                        .getText().toString();
                mSQLDatabaseController.deleteExercise(mTableKey);

                // Call getData to update list content
                getData();
                return true;
            }
        });
    }

    /**
     * Get all exercises matching current workout from database
     */
    public void getData() {
        mList = mSQLDatabaseController.getWorkoutData(mSelectedWorkout);
        setAdapter();
    }

    /**
     * Show list of exercises
     */
    public void setAdapter() {
        mWorkoutContentAdapter = new WorkoutContentAdapter(this, mList, mSelectedWorkout);
        mListView.setAdapter(mWorkoutContentAdapter);
        mWorkoutContentAdapter.notifyDataSetChanged();
    }

    /**
     * Takes the results from ExerciseSettingsActivity, calls for SQLdatabase to update and
     * updates adapter.
     * http://stackoverflow.com/questions/12293884/how-can-i-send-back-data-using-finish
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == Activity.RESULT_OK){
            ExerciseModel exerciseModel = (ExerciseModel) data.getSerializableExtra("newmodel");

            // Call for database update
            mSQLDatabaseController.updateWorkoutExercise(exerciseModel);
            getData();
        }
    }
}
