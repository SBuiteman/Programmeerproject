package nl.mprog.project.stijn.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nl.mprog.project.stijn.R;

/**
 * Created by Stijn on 07/06/2016.
 */
public class ExerciseListAdapter extends ArrayAdapter<ExerciseModel> {

    // Fields
    private List<ExerciseModel> mList;

    // Constructors
    public ExerciseListAdapter(Context context, List<ExerciseModel> list) {
        super(context, R.layout.exercise_list_item_layout, list);
        mList = list;
    }

    /**
     * Filters exercises based on selected categories
     */
    public void setCategory(String category, List<ExerciseModel> list){

        List<ExerciseModel> templist = new ArrayList<>();
        if (!category.equals("0")) {
            for (ExerciseModel exercise : list) {
                if (exercise.getCategory().equals(category)) {
                    templist.add(exercise);
                }
            }
            mList = templist;
        } else {
            mList = list;
        }
        notifyDataSetChanged();
    }

    // Methods
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        // Get each exercise object
        ExerciseModel singleExercise = getItem(position);

        // If there is a view
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater)
                    getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.exercise_list_item_layout, parent, false);
        }

        // Get muscle string back to array
        String[] mMusclesArray = convertStringToArray(singleExercise.getMuscles());

        // Change muscle value to actual word
        //String mMuscles = convertMuscleToString(mMusclesArray);
        
        // Get data from object and place in TV
        TextView nameTextView = (TextView) view.findViewById(R.id.exerciseName);
        TextView categoryTextView = (TextView) view.findViewById(R.id.categoryResult);
        TextView muscleTextView = (TextView) view.findViewById(R.id.muscleResult);
        TextView instructionsTextView = (TextView) view.findViewById(R.id.instructionsResult);

        nameTextView.setText(singleExercise.getExerciseName());
        String categoryString = "Category: " + String.valueOf(singleExercise.getCategory());
        categoryTextView.setText(categoryString);
        String muscleString = "Muscles: " + convertMuscleToString(mMusclesArray);
        muscleTextView.setText(muscleString);
        instructionsTextView.setText(singleExercise.getInstructions());

        return view;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public ExerciseModel getItem(int position) {
       return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Turns String to array
     * http://stackoverflow.com/questions/9053685/android-sqlite-saving-string-array
     */
    public static String[] convertStringToArray(String str){
        String strSeparator = "__,__";
        String[] arr = str.split(strSeparator);
        return arr;
    }

    /**
     * Takes int representing muscle and returns corresponding String
     */
    public String convertMuscleToString(String[] array){
        String mMuscle = new String();
        for (int i = 0; i<array.length; i++) {
            String mMuscleString = (array[i]);
            switch (mMuscleString){
                case "1":
                     mMuscle = "Biceps brachii";
                    break;
                case "2":
                    mMuscle = "Anterior deltoid";
                    break;
                case "3":
                    mMuscle = "Serratus anterior";
                    break;
                case "4":
                    mMuscle = "Pectoralis major";
                    break;
                case "5":
                    mMuscle = "Triceps brachii";
                    break;
                case "6":
                    mMuscle = "Rectus abdominis";
                    break;
                case "7":
                    mMuscle = "Gastrocnemius";
                    break;
                case "8":
                    mMuscle = "Gluteus maximus";
                    break;
                case "9":
                    mMuscle = "Trapezius";
                    break;
                case "10":
                    mMuscle = "Quadriceps femoris";
                    break;
                case "11":
                    mMuscle = "Biceps brachii";
                    break;
                case "12":
                    mMuscle = "Latissimus dorsi";
                    break;
                case "13":
                    mMuscle = "Brachialis";
                    break;
                case "14":
                    mMuscle = "Obliquus externus abdominis";
                    break;
                case "15":
                    mMuscle = "Soleus";
                    break;
                default:
                    mMuscle = "N/A";
                    break;
            }
        }
        return mMuscle;
    }
}
