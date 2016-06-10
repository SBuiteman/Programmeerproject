package nl.mprog.project.stijn.Classes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import nl.mprog.project.stijn.Activities.NewWorkoutActivity;
import nl.mprog.project.stijn.R;

/**
 * Created by Stijn on 09/06/2016.
 */
public class NewWorkoutAdapter extends BaseAdapter {

    private List<String> mList;
    public Context mContext;
    public NewWorkoutActivity mNewWorkoutActivity;

    public NewWorkoutAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
        System.out.println("123123     " + mList.size());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("WorkoutAdapter", "getView: "+position);

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.workout_list_single_item, parent, false);
        }

        // Get each exercise object
        String mString = mList.get(position);

        // get name from object and place in TV
        TextView nameTextView = (TextView) convertView.findViewById(R.id.workoutnames);

        nameTextView.setText(mString);

        return convertView;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
