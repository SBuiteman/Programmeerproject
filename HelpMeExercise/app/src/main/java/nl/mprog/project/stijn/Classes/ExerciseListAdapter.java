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
    public String mCategory;
    public Context workoutActivityContext;



    // Constructors
    public ExerciseListAdapter(Context context, List<ExerciseModel> list) {
        super(context, R.layout.exercise_list_item_layout, list);
        mList = list;
    }

    public void setCategory(String category, List<ExerciseModel> list){

        List<ExerciseModel> templist = new ArrayList<>();
        for (ExerciseModel exercise : list ) {
            if (exercise.getCategory().equals(category)){
                templist.add(exercise);
            }
        }
        mList = templist;
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
        
        // get name from object and place in TV
        TextView nameTextView = (TextView) view.findViewById(R.id.exerciseName);
        TextView categoryTextView = (TextView) view.findViewById(R.id.categoryResult);

        nameTextView.setText(singleExercise.getExerciseName());
        categoryTextView.setText(String.valueOf(singleExercise.getCategory()));

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
}
