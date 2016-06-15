package nl.mprog.project.stijn.Classes;

import android.media.Image;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Stijn on 02/06/2016.
 */
public class ExerciseModel implements Serializable {

    // fields
    public String exerciseName;
    public String muscleGroup;
    public String instructions;
    public Image instructionImages;
    public int exerciseId;
    public int workoutID;
    public int category;        // is muscle group
    public int language;
    public String sets;
    public String reps;
    public String weight;
    public ArrayList<String> progress;
    public String muscles;

    // constructor
    public ExerciseModel(){
        exerciseName = "N/A";
        muscleGroup = "";
        instructions = "";
        instructionImages = null;
        exerciseId = 0;
        workoutID = 0;
        category = 0;
        language = 0;
        sets = "empty";
        reps = "empty";
        weight = "empty";
        progress = new ArrayList<>();
        muscles = "empty";
    }

    // methods

    // setters

    public void setSets(String sets) {
        this.sets = sets;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setProgress(ArrayList progress) {
        this.progress = progress;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public void setWorkoutID(int workoutID) {
        this.workoutID = workoutID;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public void setMuscles(String muscles) {
        this.muscles = muscles;
    }

    // getters

    public ArrayList<String> getProgress() {
        return progress;
    }

    public String getSets() {
        return sets;
    }

    public String getReps() {
        return reps;
    }

    public String getWeight() {
        return weight;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public int getWorkoutID() {
        return workoutID;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public String getCategory() {
        return String.valueOf(category);
    }

    public int getLanguage() {
        return language;
    }

    public String getMuscles() {
        return muscles;
    }
}

// variabelen uit constructor
// String exerciseNameArg, String muscleGroupArg, String instructionsArg,
//Image instructionImagesArg, int setsArg, int repsArg, int weightArg,
//    Array progressArg