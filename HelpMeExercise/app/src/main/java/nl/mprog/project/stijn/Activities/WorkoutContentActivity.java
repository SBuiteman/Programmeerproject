package nl.mprog.project.stijn.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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

public class WorkoutContentActivity extends AppCompatActivity implements
        AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    // Fields
    public String mSelectedWorkout;
    public List<ExerciseModel> mList;
    public ListView mListView;
    public WorkoutContentAdapter mWorkoutContentAdapter;
    public SQLDatabaseControler mSQLDatabaseController;

    /**
     * Get intent with chosen workout.
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

        // Set item click listener
        mListView.setOnItemClickListener(this);

        // Set item longclick listener
        mListView.setOnItemLongClickListener(this);
    }

    /**
     * Get all exercises matching current workout from database.
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

    /**
     * Set click listener on exercise to enable calls to ExerciseSettings.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String mExercise = mWorkoutContentAdapter.getTextViewText();
        String calledBy = "ContentAdapter";

        // Create intent to start ExerciseSettingsActivity
        Intent intent = new Intent(WorkoutContentActivity.this,
                ExerciseSettingsActivity.class);

        // Put workout name and exercise name in bundle
        Bundle mBundle = new Bundle();
        mBundle.putString("workoutname", mSelectedWorkout);
        mBundle.putString("exercisename", mExercise);
        mBundle.putString("calledby", calledBy);
        intent.putExtras(mBundle);

        // Make ExerciseSettings return the object it creates when it's done
        startActivityForResult(intent, 0);
    }

    /**
     * On long click delete entry from workout content table with the matching primary key. Ask user
     * if sure with alert dialog.
     */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final String mTableKey = ((TextView) view.findViewById(R.id.keyValueField))
                .getText().toString();
        final String mExercise = ((TextView) view.findViewById(R.id.nameField))
                .getText().toString();

        // Start alert dialog to ask user if he is sure
        new AlertDialog.Builder(this)
                .setTitle("Delete exercise: " + mExercise)
                .setMessage("Are you sure you want to delete this exercise?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        // On yes, delete exercise
                        mSQLDatabaseController.deleteExercise(mTableKey);

                        // Call getData to update list content
                        getData();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        return true;
    }
}
