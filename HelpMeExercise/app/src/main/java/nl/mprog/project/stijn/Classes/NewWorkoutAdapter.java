package nl.mprog.project.stijn.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import nl.mprog.project.stijn.Activities.NewWorkoutActivity;
import nl.mprog.project.stijn.R;

/**
 * Created by Stijn on 09/06/2016.
 */
public class NewWorkoutAdapter extends ArrayAdapter<WorkoutModel> {

    private List<WorkoutModel> mList;
    public Context mContext;
    public NewWorkoutActivity mNewWorkoutActivity;

    public NewWorkoutAdapter(Context context, List<WorkoutModel> mList) {
        super(context, R.layout.workout_list_single_item, mList);

        mContext = context;
        this.mList = mList;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        // If there is a view
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)
                    getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.workout_list_single_item, parent, false);
        }

        // Get each exercise object
        WorkoutModel mWorkoutModel = mList.get(position);

        // get name from object and place in TV
        TextView nameTextView = (TextView) view.findViewById(R.id.workoutnames);

        nameTextView.setText(mWorkoutModel.getmWorkoutName().replaceAll("_", " "));

        return view;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public WorkoutModel getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
