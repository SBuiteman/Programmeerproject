package nl.mprog.project.stijn.Classes;

import android.media.Image;

import java.lang.reflect.Array;

/**
 * Created by Stijn on 02/06/2016.
 */
public class ExerciseModel {

    // fields
    public String exerciseName;
    public String muscleGroup;
    public String instructions;
    public Image instructionImages;
    public int sets;
    public int reps;
    public int weight;
    public Array progress;

    // constructor
    public ExerciseModel(String exerciseNameArg, String muscleGroupArg, String instructionsArg,
                         Image instructionImagesArg, int setsArg, int repsArg, int weightArg,
                         Array progressArg){
        exerciseName = exerciseNameArg;
        muscleGroup = muscleGroupArg;
        instructions = instructionsArg;
        instructionImages = instructionImagesArg;
        sets = setsArg;
        reps = repsArg;
        weight = weightArg;
        progress = progressArg;
    }

    // methods

    public void setSets(int sets) {
        this.sets = sets;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setProgress(Array progress) {
        this.progress = progress;
    }

    public Array getProgress() {
        return progress;
    }

    public int getSets() {
        return sets;
    }

    public int getReps() {
        return reps;
    }

    public int getWeight() {
        return weight;
    }


}
