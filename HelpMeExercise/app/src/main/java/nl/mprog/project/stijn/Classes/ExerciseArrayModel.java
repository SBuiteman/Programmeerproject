package nl.mprog.project.stijn.Classes;

import java.util.ArrayList;

/**
 * Created by Stijn on 02/06/2016.
 */
public class ExerciseArrayModel {

    // fields
    private ArrayList<ExerciseModel> workout;

    // constructor
    public ExerciseArrayModel(){
        workout = new ArrayList<>();
    }

    // methods

    public ArrayList<ExerciseModel> getWorkout() {
        return workout;
    }

    public void setWorkout(ArrayList<ExerciseModel> workout) {
        this.workout = workout;
    }
}
