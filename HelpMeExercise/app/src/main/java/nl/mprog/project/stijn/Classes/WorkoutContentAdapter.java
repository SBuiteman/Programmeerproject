package nl.mprog.project.stijn.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import nl.mprog.project.stijn.R;

/**
 * Stijn Buiteman
 * stijnbuiteman@gmail.com
 */

/**
 * Shows a list of data from each exercise.
 */
public class WorkoutContentAdapter extends ArrayAdapter<ExerciseModel> {

    // Fields
    public List<ExerciseModel> mList;
    public String mSelectedWorkout;
    public Context mContext;
    public TextView nameTextView;

    // Constructors
    public WorkoutContentAdapter(Context context, List<ExerciseModel> list, String selectedWorkout) {
        super(context, R.layout.content_list_single_item, list);
        mContext = context;
        mList = list;
        mSelectedWorkout = selectedWorkout;
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
            view = inflater.inflate(R.layout.content_list_single_item, parent, false);
        }

        // get name from object and place in TV
        nameTextView = (TextView) view.findViewById(R.id.nameField);
        TextView categoryTextView = (TextView) view.findViewById(R.id.categoryField);
        TextView setsTextView = (TextView) view.findViewById(R.id.setsField);
        TextView repsTextView = (TextView) view.findViewById(R.id.repsField);
        TextView weightTextView = (TextView) view.findViewById(R.id.weightField);
        TextView inputKeyTextView = (TextView) view.findViewById(R.id.keyValueField);
        TextView instructions = (TextView) view.findViewById(R.id.instructionsText);

        nameTextView.setText(singleExercise.getExerciseName());
        categoryTextView.setText(singleExercise.getCategory());
        setsTextView.setText(singleExercise.getSets());
        repsTextView.setText(singleExercise.getReps());
        weightTextView.setText(singleExercise.getWeight());
        inputKeyTextView.setText(String.valueOf(singleExercise.getTableInputID()));
        instructions.setText(singleExercise.getInstructions());

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
     * Returns name of clicked exercise
     */
    public String getTextViewText(){
        String name = nameTextView.getText().toString();
        return name;
    }
}
