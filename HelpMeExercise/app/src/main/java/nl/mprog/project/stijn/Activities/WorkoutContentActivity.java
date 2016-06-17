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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_content);

        mSelectedWorkout = this.getIntent().getStringExtra("key");

        // Initializer
        init();
    }

    /**
     *
     */
    public void init(){
        mListView = (ListView) findViewById(R.id.workoutContent);

        getData();

    }

    /**
     *
     */
    public void getData() {
        mList = mSQLDatabaseController.getWorkoutData(mSelectedWorkout);
        setAdapter();
    }

    /**
     *
     */
    public void setAdapter() {
        mWorkoutContentAdapter = new WorkoutContentAdapter(this, mList);
        mListView.setAdapter(mWorkoutContentAdapter);
        mWorkoutContentAdapter.notifyDataSetChanged();
    }
}
