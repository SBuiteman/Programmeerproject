package nl.mprog.project.stijn.Classes;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Stijn on 02/06/2016.
 */
public class ExerciseListModel implements Serializable {

    // fields
    private List<ExerciseModel> workout;

    // methods

    public List<ExerciseModel> getWorkout() {
        return workout;
    }

    public void setWorkout(List<ExerciseModel> workout) {
        this.workout = workout;
    }

    /**
     * TODO
     */

}
