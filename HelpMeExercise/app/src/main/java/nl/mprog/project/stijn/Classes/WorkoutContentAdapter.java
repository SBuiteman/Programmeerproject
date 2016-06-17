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
 * Created by Stijn on 17/06/2016.
 */
public class WorkoutContentAdapter extends ArrayAdapter<ExerciseModel> {

    // Fields
    public List<ExerciseModel> mList;

    // Constructors
    public WorkoutContentAdapter(Context context, List<ExerciseModel> list) {
        super(context, R.layout.content_list_single_item, list);
        mList = list;
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
        TextView nameTextView = (TextView) view.findViewById(R.id.nameField);

        nameTextView.setText(singleExercise.getExerciseName());



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
