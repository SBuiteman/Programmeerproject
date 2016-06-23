package nl.mprog.project.stijn.Classes;

import android.util.Log;

/**
 * Stijn Buiteman
 * stijnbuiteman@gmail.com
 */

/**
 * Model that can hold any parameters regarding workouts.
 */
public class WorkoutModel {

    // Fields
    public String mWorkoutName;
    public int mWorkoutDay;
    public int mExerciseTag;

    // Constructor
    public WorkoutModel(){
        mWorkoutName = "N/A";
        mWorkoutDay = 5;
        mExerciseTag = 0;
    }

    // Methods

    // Getters
    public String getmWorkoutName() {
        Log.d("Name in model", "12312312312   "+ mWorkoutName);
        return mWorkoutName;
    }

    public int getmWorkoutDay() {
        Log.d("Day in model", "12312312312   "+ mWorkoutDay);
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


