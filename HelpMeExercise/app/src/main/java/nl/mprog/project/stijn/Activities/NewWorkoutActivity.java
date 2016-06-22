package nl.mprog.project.stijn.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import nl.mprog.project.stijn.Classes.NewWorkoutAdapter;
import nl.mprog.project.stijn.Classes.SQLDatabaseControler;
import nl.mprog.project.stijn.Classes.WorkoutModel;
import nl.mprog.project.stijn.R;

/**
 * TODO
 */
public class NewWorkoutActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    // fields
    public EditText mWorkoutNameBox;
    public ListView workoutListView;
    public Button mCreateButton;
    public NumberPicker mDayPicker;

    public int mChosenDay;
    public String mWorkoutName;
    public NewWorkoutAdapter mNewWorkoutAdapter;
    public SQLDatabaseControler mSQLDatabaseController;

    /**
     * Initialize views and call for list of workouts
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_workout);

        // Initializes views
        init();

        showWorkoutList();
    }

    /**
     * Initialize views
     */
    public void init() {
        mWorkoutNameBox = (EditText) findViewById(R.id.workoutNameBox);
        workoutListView = (ListView) findViewById(R.id.singleList);
        mCreateButton = (Button) findViewById(R.id.createButton);
        mDayPicker = (NumberPicker) findViewById(R.id.numberPicker);

        mSQLDatabaseController = new SQLDatabaseControler(getApplicationContext());

        // Set NumberPicker to show days
        final String[] mDayArray = new String[]{getString(R.string.num_pick_mon),
                getString(R.string.num_pick_tue), getString(R.string.num_pick_wed),
                getString(R.string.num_pick_thu), getString(R.string.num_pick_fri),
                getString(R.string.num_pick_sat), getString(R.string.num_pick_sun)};

        // Settings for NumberPicker
        mDayPicker.setMinValue(1);
        mDayPicker.setMaxValue(7);
        mDayPicker.setValue(1);
        mDayPicker.setWrapSelectorWheel(true);
        mDayPicker.setDisplayedValues(mDayArray);
        mDayPicker.setOnValueChangedListener(mValueChangeListener);

        mCreateButton.setOnClickListener(this);
        workoutListView.setOnItemClickListener(this);
        workoutListView.setOnItemLongClickListener(this);
    }

    /**
     * Handles button click
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.createButton:
                addWorkout();
                break;
            default:
                break;
        }
    }

    /**
     * Listens for itemclicks, remove spaces and call sendIntent
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String mWorkoutName = ((TextView) view.findViewById(R.id.workoutnames))
                .getText().toString();
        mWorkoutName = mWorkoutName.replaceAll(" ", "_");

        sendIntent(mWorkoutName);
    }


    /**
     * On longclick delete a workout from database and update view
     * http://stackoverflow.com/questions/2115758/how-do-i-display-an-alert-dialog-on-android
     */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        mWorkoutName = ((TextView) view.findViewById(R.id.workoutnames))
                .getText().toString();

        // Start alert dialog to ask user if he is sure
        new AlertDialog.Builder(this)
                .setTitle("Delete workout: " + mWorkoutName)
                .setMessage("Are you sure you want to delete this workout?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        // On yes, delete workout
                        mSQLDatabaseController.deleteWorkout(mWorkoutName);

                        // Update view
                        showWorkoutList();
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

    /**
     * Listen for scrolling in NumberPicker
     */
    private NumberPicker.OnValueChangeListener mValueChangeListener =
            new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            mChosenDay = newVal;
        }
    };

    /**
     * Take user input and create a workout in SQL table
     */
    public void addWorkout() {

        // Check for input
        if (mWorkoutNameBox.getText().toString().trim().equals("")) {
            Toast.makeText(this, R.string.ask_for_input_name, Toast.LENGTH_LONG).show();
        } else {
            // Add input to table and update adapter
            WorkoutModel mWorkoutModel = new WorkoutModel();
            String mWorkoutName = mWorkoutNameBox.getText().toString();

            // SQL table can't handle spaces
            mWorkoutName = mWorkoutName.replaceAll(" ", "_");
            mWorkoutModel.setmWorkoutName(mWorkoutName);
            mSQLDatabaseController.createWorkout(this, mWorkoutModel);
            showWorkoutList();

            addWorkoutToDay(mWorkoutName);

            // Send name of workout to ResultsActivity
            sendIntent(mWorkoutModel.getmWorkoutName());
        }
    }

    /**
     * Show all created workouts
     */
    public void showWorkoutList() {
        mNewWorkoutAdapter = new NewWorkoutAdapter(this, mSQLDatabaseController.getAllTags());
        workoutListView.setAdapter(mNewWorkoutAdapter);
        mNewWorkoutAdapter.notifyDataSetChanged();
    }

    /**
     * Store workout with a specific day
     */
    public void addWorkoutToDay(String workoutName) {
        mSQLDatabaseController.createWorkoutDay(this, workoutName, mDayPicker.getValue());
    }

    /**
     * Start ResultsActivity and pass the selected workout to be filled with exercises
     */
    public void sendIntent(String mWorkoutName){
        Intent intent = new Intent(getApplicationContext(),
                ResultsActivity.class);
        intent.putExtra("key", mWorkoutName);
        startActivity(intent);
    }

    /**
     * On back presses always go to HomeActivity
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.putExtra("backpressed", true);
        startActivity(intent);
    }
}
