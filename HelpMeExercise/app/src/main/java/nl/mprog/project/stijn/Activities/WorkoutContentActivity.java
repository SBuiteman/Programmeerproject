package nl.mprog.project.stijn.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

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
        mWorkoutContentAdapter = new WorkoutContentAdapter(this, mList);
        mListView.setAdapter(mWorkoutContentAdapter);
        mWorkoutContentAdapter.notifyDataSetChanged();
    }
}
