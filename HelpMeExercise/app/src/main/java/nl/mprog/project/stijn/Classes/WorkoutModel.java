package nl.mprog.project.stijn.Classes;

import android.util.Log;

/**
 * Created by Stijn on 13/06/2016.
 */
public class WorkoutModel {

    // Fields
    public String mWorkoutName;
    public int mWorkoutDay;
    public int mExerciseTag;

    // Constructor
    public WorkoutModel(){
        mWorkoutName = "N/A";
        mWorkoutDay = 0;
        mExerciseTag = 0;
    }

    // Methods

    // Getters
    public String getmWorkoutName() {
        Log.d("Name in model", "12312312312   "+ mWorkoutName);
        return mWorkoutName;
    }

    public int getmWorkoutDay() {
        return mWorkoutDay;
    }

    public int getmExerciseTag() {
        return mExerciseTag;
    }

    // Setters
    public void setmWorkoutName(String mWorkoutName) {
        this.mWorkoutName = mWorkoutName;
    }

    public void setmWorkoutDay(int mWorkoutDay) {
        this.mWorkoutDay = mWorkoutDay;
    }

    public void setmExerciseTag(int mExerciseTag) {
        this.mExerciseTag = mExerciseTag;
    }
}


