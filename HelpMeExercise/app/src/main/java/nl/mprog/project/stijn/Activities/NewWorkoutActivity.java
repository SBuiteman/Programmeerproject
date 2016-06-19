package nl.mprog.project.stijn.Activities;

import android.content.Intent;
import android.os.Bundle;
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
public class NewWorkoutActivity extends AppCompatActivity implements View.OnClickListener {

    // fields
    public EditText mWorkoutNameBox;
    public ListView workoutListView;
    public Button mCreateButton;
    public Button homeButton;
    public NumberPicker mDayPicker;

    public int mChosenDay;
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
        homeButton = (Button) findViewById(R.id.homeButton);
        mDayPicker = (NumberPicker) findViewById(R.id.numberPicker);

        homeButton.setOnClickListener(this);
        mCreateButton.setOnClickListener(this);

        mSQLDatabaseController = new SQLDatabaseControler(getApplicationContext());

        // Set NumberPicker to show days
        final String[] mDayArray =  new String[] {"Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday", "Sunday"};

        // Settings for NumberPicker
        mDayPicker.setMinValue(1);
        mDayPicker.setMaxValue(7);
        mDayPicker.setValue(1);
        mDayPicker.setWrapSelectorWheel(true);
        mDayPicker.setDisplayedValues(mDayArray);
        mDayPicker.setOnValueChangedListener(mValueChangeListener);

        /**
         * Listens for itemclicks, remove spaces and call sendIntent
         */
        workoutListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String mWorkoutName = ((TextView) view.findViewById(R.id.workoutnames))
                        .getText().toString();
                mWorkoutName = mWorkoutName.replaceAll(" ", "_");

                sendIntent(mWorkoutName);
            }
        });
    }

    /**
     * Handles button clicks
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.createButton:
                addWorkout();
                break;
            case R.id.homeButton:
                backToHome();
                break;
            default:
                break;
        }
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
     * Start HomeActivity
     */
    public void backToHome(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    /**
     * Take user input and create a workout in SQL table
     */
    public void addWorkout() {

        // Check for input
        if (mWorkoutNameBox.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
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
}
