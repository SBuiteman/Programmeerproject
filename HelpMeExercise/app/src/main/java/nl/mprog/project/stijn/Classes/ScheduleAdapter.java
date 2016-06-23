package nl.mprog.project.stijn.Classes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nl.mprog.project.stijn.Activities.WorkoutContentActivity;
import nl.mprog.project.stijn.R;

/**
 * Stijn Buiteman
 * stijnbuiteman@gmail.com
 */

/**
 * Shows list of weekdays and contains a second list of workouts. The adapter for that list is
 * called from this adapter and it also implements is onclicklistener. Sorts the given objectlist
 * per day and gives objectlists per day to next adapter.
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
        mDayList = new String[]{mContext.getString(R.string.num_pick_mon),
                mContext.getString(R.string.num_pick_tue),
                mContext.getString(R.string.num_pick_wed),
                mContext.getString(R.string.num_pick_thu),
                mContext.getString(R.string.num_pick_fri),
                mContext.getString(R.string.num_pick_sat),
                mContext.getString(R.string.num_pick_sun)};
        mWorkoutList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mDayList.length;
    }

    @Override
    public String getItem(int position) {
        return mDayList[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Checks list for workouts that belong with each day in the list, starts the adapter for the
     * list nested in this list, makes the size of that list dynamic and listens for clicks on that
     * list.
     */
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        // If there is a view
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.planner_list_single_item, parent, false);

            String mDay = mDayList[position];
            mWorkoutList = new ArrayList<>();

            TextView mWeekDay = (TextView) view.findViewById(R.id.weekDay);
            mWorkoutCollection = (ListView) view.findViewById(R.id.workoutCollection);
            mWeekDay.setText(mDay);

            // Get workouts matching current day only
            for (WorkoutModel workoutModel : mList) {

                // Plus one because array starts at 0 but days are stored starting with 1
                if(workoutModel.getmWorkoutDay() == position+1){
                    mWorkoutList.add(workoutModel);
                }
            }

            // Add a 'Free' workoutmodel if there are no workouts
            if(mWorkoutList.size() == 0){
                WorkoutModel emptyWorkout = new WorkoutModel();
                emptyWorkout.setmWorkoutName("Free");
                mWorkoutList.add(emptyWorkout);
            }

            // Set onclicklistener on list in current list
            mWorkoutCollection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                  @Override
                  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                      String mWorkout = ((TextView) view.findViewById(R.id.dailyWorkout))
                              .getText().toString();

                      // Start WorkoutContentActivity and pass clicked workout
                      Intent intent = new Intent(mContext,
                              WorkoutContentActivity.class);
                      intent.putExtra("workout", mWorkout);
                      mContext.startActivity(intent);
                  }
              });

            // Pass selected workouts to ListView in ListView
            mInScheduleAdapter = new InScheduleAdapter(mContext, mWorkoutList);
            mWorkoutCollection.setAdapter(mInScheduleAdapter);
            mInScheduleAdapter.notifyDataSetChanged();

            // Make list height dynamic depending on it's items
            LinearLayout.LayoutParams mParam = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, mWorkoutList.size()*60);
            mWorkoutCollection.setLayoutParams(mParam);
        }

        return view;
    }
}
