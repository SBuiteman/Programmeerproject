package nl.mprog.project.stijn.Classes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import nl.mprog.project.stijn.R;

/**
 * Created by Stijn on 16/06/2016.
 */
public class InScheduleAdapter extends BaseAdapter {

    // Fields
    public Context mContext;
    public List<WorkoutModel> mList;

    // Constructor
    public InScheduleAdapter(Context context, List<WorkoutModel> List) {
        mContext = context;
        mList = List;
        Log.d("InSchedule", "constructor"+mList.size());
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public WorkoutModel getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        // If there is a view
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.collection_list_single_item, parent, false);

            WorkoutModel mWorkoutModel = mList.get(position);

            TextView mWeekDay = (TextView) view.findViewById(R.id.dailyWorkout);
            mWeekDay.setText(mWorkoutModel.getmWorkoutName());

        }
        return view;
    }
}


//    String mWorkout = ((TextView) view.findViewById(R.id.dailyWorkout))
//            .getText().toString();
//Log.d("2e List wordt geklikt?", "item = "+mWorkout);
