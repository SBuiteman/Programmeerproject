package nl.mprog.project.stijn.Classes;

import android.media.Image;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    public int category;        // is muscle group
    public int language;
    public int sets;
    public int reps;
    public int weight;
    public ArrayList<String> progress;
    public List<Integer> muscles;

    // constructor
    public ExerciseModel(){
        exerciseName = "";
        muscleGroup = "";
        instructions = "";
        instructionImages = null;
        exerciseId = 0;
        category = 0;
        language = 0;
        sets = 0;
        reps = 0;
        weight = 0;
        progress = new ArrayList<>();
        muscles = new ArrayList<>();
    }

    // methods

    // setters

    public void setSets(int sets) {
        this.sets = sets;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setProgress(ArrayList progress) {
        this.progress = progress;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
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

    public void setMuscles(List<Integer> muscles) {
        this.muscles = muscles;
    }

    // getters

    public ArrayList<String> getProgress() {
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

    public int getExerciseId() {
        return exerciseId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public int getCategory() {
        return category;
    }

    public int getLanguage() {
        return language;
    }

    public List<Integer> getMuscles() {
        return muscles;
    }
}

// variabelen uit constructor
// String exerciseNameArg, String muscleGroupArg, String instructionsArg,
//Image instructionImagesArg, int setsArg, int repsArg, int weightArg,
//    Array progressArg