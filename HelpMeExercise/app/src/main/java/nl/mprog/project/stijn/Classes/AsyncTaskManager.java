package nl.mprog.project.stijn.Classes;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nl.mprog.project.stijn.Activities.HomeActivity;
import nl.mprog.project.stijn.R;

/**
 * Created by Stijn on 06/06/2016.
 */
public class AsyncTaskManager extends AsyncTask<String, Integer, String> {

    // Fields
    public HttpRequestHelper httpRequestHelper;
    private Context mContext;
    private HomeActivity mHomeActivity;
    private List<ExerciseModel> mExerciseListModel;

    // Constructor
    public AsyncTaskManager(HomeActivity homeActivity) {
        super();
        this.mHomeActivity = homeActivity;
        this.mContext = this.mHomeActivity.getApplicationContext();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * Call httprequest to get data from server.
     */
    @Override
    protected String doInBackground(String... params) {

        // // call HttpRequestHelper and pass searchterm
        return httpRequestHelper.downLoadFromServer(params);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    /**
     * Check if data was received and handle the JSON object if there was data. Fills exercise
     * models with data and returns a list with all the models.
     */
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        // alert user if nothing was found
        if (result.length() == 0) {
            Toast.makeText(mContext, R.string.no_data_from_server, Toast.LENGTH_LONG).show();
        }

        // if data was found
        else {

            // create model and Arraylist to hold results
            mExerciseListModel = new ArrayList<>();

            // parse JSON data
            try {

                // create JSON object and array
                JSONObject resultObject = new JSONObject(result);
                JSONArray exercises = resultObject.getJSONArray("results");

                // for each exercise found create exercise object
                for (int i = 0; i < exercises.length(); i++) {

                    ExerciseModel exerciseModel = new ExerciseModel();

                    JSONObject exercise = exercises.getJSONObject(i);
                    exerciseModel.setExerciseId(exercise.getInt("id"));
                    exerciseModel.setExerciseName(exercise.getString("name"));

                    // Make int representing category a describing String
                    int mCat = exercise.getInt("category");
                    exerciseModel.setCategory(convertCategoryToString(mCat));

                    String description = exercise.getString("description");
                    description = removeFromInstructions(description);
                    exerciseModel.setInstructions(description);

                    // Add exercise to exerciselistModel
                    mExerciseListModel.add(exerciseModel);
                }

                // Set list in activity
                //newWorkoutActivity.holdList(exerciseListModel);
                mHomeActivity.holdList(mExerciseListModel);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Remove specific char combination from string.
     */
    public String removeFromInstructions(String string){
        String clearedString = string.replaceAll("<p>", "");
        String clearedString2 = clearedString.replaceAll("</p>", "");
        return clearedString2;
    }

    /**
     * Takes int representing category and returns corresponding String.
     */
    public String convertCategoryToString(Integer category) {
        String mCategory = null;
            switch (category) {
                case 8:
                    mCategory = mContext.getString(R.string.arm_category);
                    break;
                case 9:
                    mCategory = mContext.getString(R.string.legs_category);
                    break;
                case 10:
                    mCategory = mContext.getString(R.string.abs_category);
                    break;
                case 11:
                    mCategory= mContext.getString(R.string.chest_category);
                    break;
                case 12:
                    mCategory = mContext.getString(R.string.back_category);
                    break;
                case 13:
                    mCategory = mContext.getString(R.string.shoulders_category);
                    break;
                case 14:
                    mCategory = mContext.getString(R.string.calves_category);
                    break;
                default:
                    break;
            }
        return mCategory;
    }

}
