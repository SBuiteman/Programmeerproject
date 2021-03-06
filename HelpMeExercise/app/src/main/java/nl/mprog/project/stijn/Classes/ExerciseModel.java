package nl.mprog.project.stijn.Classes;

import java.io.Serializable;

/**
 * Stijn Buiteman
 * stijnbuiteman@gmail.com
 */

/**
 * Object model that can hold any parameter related to exercises.
 */
public class ExerciseModel implements Serializable {

    // fields
    public String exerciseName;
    public String instructions;
    public int exerciseId;
    public int workoutID;
    public String category;        // is muscle group
    public int language;
    public String sets;
    public String reps;
    public String weight;
    public int tableInputID;

    // constructor
    public ExerciseModel(){
        exerciseName = "N/A";
        instructions = "";
        exerciseId = 0;
        workoutID = 0;
        category = "empty";
        language = 0;
        sets = "empty";
        reps = "empty";
        weight = "empty";
        tableInputID = 0;
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

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public void setWorkoutID(int workoutID) {
        this.workoutID = workoutID;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void setTableInputID(int tableInputID) {
        this.tableInputID = tableInputID;
    }

    // getters

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
        return category;
    }

    public int getLanguage() {
        return language;
    }

    public String getInstructions() {
        return instructions;
    }

    public int getTableInputID() {
        return tableInputID;
    }
}
