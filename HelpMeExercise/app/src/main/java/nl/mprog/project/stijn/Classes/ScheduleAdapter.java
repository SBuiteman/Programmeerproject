package nl.mprog.project.stijn.Classes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nl.mprog.project.stijn.R;

/**
 * Created by Stijn on 15/06/2016.
 */
public class ScheduleAdapter extends BaseAdapter {

    // Fields
    public Context mContext;
    public List<WorkoutModel> mList;
    public List<WorkoutModel> mWorkoutList;
    public String[] mDayList;
    public InScheduleAdapter mInScheduleAdapter;
    public ListView mWorkoutCollection;

    // Constructor
    public ScheduleAdapter(Context context, List<WorkoutModel> List) {
        this.mContext = context;
        mList = List;
        mDayList = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        mWorkoutList = new ArrayList<>();
        Log.d("lengte dagenlijst", "count = "+ mDayList.length);
    }

    @Override
    public int getCount() {
        return mDayList.length;
    }

    @Override
    public String[] getItem(int position) {
        return null;
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
            view = inflater.inflate(R.layout.planner_list_single_item, parent, false);

            String mDay = mDayList[position];
            Log.d("mDay", "Day = "+ mDay);
            Log.d("positions", "pos = "+ position);

            TextView mWeekDay = (TextView) view.findViewById(R.id.weekDay);
            mWorkoutCollection = (ListView) view.findViewById(R.id.workoutCollection);
            mWeekDay.setText(mDay);

            // Get workouts matching current day only
            for (WorkoutModel workoutModel : mList) {
                if(workoutModel.getmWorkoutDay() == position+1){
                    Log.d("Schedule if test", "nog goed?"+position);
                    mWorkoutList.add(workoutModel);
                    Log.d("List leeg?", "Name = " + mWorkoutList.get(0).getmWorkoutDay());
                }
            }
            if(mWorkoutList.size() == 0){
                WorkoutModel emptyWorkout = new WorkoutModel();
                emptyWorkout.setmWorkoutName("Free");
                mWorkoutList.add(emptyWorkout);
            }
            Log.d("Wel geen workout", "name = " + mWorkoutList.get(0).getmWorkoutName());

            // Pass selected workouts to ListView in ListView
            mInScheduleAdapter = new InScheduleAdapter(mContext, mWorkoutList);
            Log.d("Voor set adapter test", "nog goed?");
            mWorkoutCollection.setAdapter(mInScheduleAdapter);
            Log.d("Na set adapter test", "nog goed?");
            //mInScheduleAdapter.notifyDataSetChanged();
        }

        return view;
    }
}
