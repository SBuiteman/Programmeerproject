package nl.mprog.project.stijn.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import nl.mprog.project.stijn.R;

/**
 * Created by Stijn on 15/06/2016.
 */
public class ScheduleAdapter extends BaseAdapter {

    // Fields
    public Context mContext;
    public List<String> mList;
    public String[] mDayList;

    // Constructor
    public ScheduleAdapter(Context context, String List) {
        mContext = context;
        //this.mList = List;
        mDayList = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday",
                "Sunday"};
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

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        // If there is a view
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.planner_list_single_item, parent, false);

            String mDay = mDayList[position];

            TextView mWeekDay = (TextView) view.findViewById(R.id.weekDay);
            mWeekDay.setText(mDay);
        }

        return view;
    }
}
