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
    //private NewWorkoutActivity newWorkoutActivity;
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
                    exerciseModel.setCategory(exercise.getInt("category"));
                    exerciseModel.setLanguage(exercise.getInt("language"));

                    // For each muscle found
                    for (int j = 0; j < musclesArray.length(); j++) {
                        JSONObject muscles = musclesArray.getJSONObject(j);
                        muscle[j] = String.valueOf(muscles.getInt("muscles"));
                        muscleString = convertArrayToString(muscle);
                    }
                    exerciseModel.setMuscles(muscleString);

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

    // String for separation
    public static String strSeparator = "__,__";

    /**
     * http://stackoverflow.com/questions/9053685/android-sqlite-saving-string-array
     */
    public static String convertArrayToString(String[] array){
        String str = "";
        for (int i = 0;i<array.length; i++) {
            str = str+array[i];

            // Do not append comma at the end of last element
            if(i<array.length-1){
                str = str+strSeparator;
            }
        }
        return str;
    }
}
