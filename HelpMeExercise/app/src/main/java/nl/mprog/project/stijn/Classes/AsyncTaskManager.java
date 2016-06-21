package nl.mprog.project.stijn.Classes;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nl.mprog.project.stijn.Activities.HomeActivity;

/**
 * Created by Stijn on 06/06/2016.
 */
public class AsyncTaskManager extends AsyncTask<String, Integer, String> {

    // Fields
    public HttpRequestHelper httpRequestHelper;
    private Context context;
    private HomeActivity homeActivity;
    private String[] muscle;
    private List<ExerciseModel> exerciseListModel;
    private String muscleString;

    // Constructor
    public AsyncTaskManager(HomeActivity homeActivity) {
        super();
        this.homeActivity = homeActivity;
        this.context = this.homeActivity.getApplicationContext();
    }

    /**
     *
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // maybe present message data is being loaded
    }

    /**
     *
     */
    @Override
    protected String doInBackground(String... params) {

        // // call HttpRequestHelper and pass searchterm
        return httpRequestHelper.downLoadFromServer(params);
    }

    /**
     *
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    /**
     *
     */
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        // alert user if nothing was found
        if (result.length() == 0) {
            Toast.makeText(context, "Oops,no data was found!", Toast.LENGTH_LONG).show();
        }

        // if data was found
        else {
            Log.d("JSON results", "onPostExecute: " + result);

            // create model and Arraylist to hold results
            exerciseListModel = new ArrayList<>();

            // parse JSON data
            try {

                // create JSON object and array
                JSONObject resultObject = new JSONObject(result);
                JSONArray exercises = resultObject.getJSONArray("results");

                // create second JSON object and array to handle array in JSONArray
                JSONObject musclesObject = exercises.getJSONObject(0);
                JSONArray musclesArray = musclesObject.getJSONArray("muscles");

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
                    exerciseListModel.add(exerciseModel);
                }

                // Set list in activity
                //newWorkoutActivity.holdList(exerciseListModel);
                homeActivity.holdList(exerciseListModel);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Remove specific char combination from string
     */
    public String removeFromInstructions(String string){
        String clearedString = string.replaceAll("<p>", "");
        String clearedString2 = clearedString.replaceAll("</p>", "");
        return clearedString2;
    }

    /**
     * Takes int representing category and returns corresponding String
     */
    public String convertCategoryToString(Integer category) {
        String mCategory = null;
            switch (category) {
                case 8:
                    mCategory = "Arms";
                    break;
                case 9:
                    mCategory = "Legs";
                    break;
                case 10:
                    mCategory = "Abs";
                    break;
                case 11:
                    mCategory= "Chest";
                    break;
                case 12:
                    mCategory = "Back";
                    break;
                case 13:
                    mCategory = "Shoulders";
                    break;
                case 14:
                    mCategory = "Calves";
                    break;
                default:
                    break;
            }
        return mCategory;
    }

}
