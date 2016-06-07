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
import nl.mprog.project.stijn.Activities.NewWorkoutActivity;

/**
 * Created by Stijn on 06/06/2016.
 */
public class AsyncTaskManager extends AsyncTask<String, Integer, String> {

    // Fields
    public HttpRequestHelper httpRequestHelper;
    private Context context;
    private HomeActivity mainActivity;
    private NewWorkoutActivity newWorkoutActivity;
    private List<Integer> muscle;
    private List<ExerciseModel> exerciseListModel;

    // Constructor
    public AsyncTaskManager(NewWorkoutActivity newWorkoutActivity) {
        super();
        this.newWorkoutActivity = newWorkoutActivity;
        this.context = this.newWorkoutActivity.getApplicationContext();
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

                    // for each muscle found
                    muscle = new ArrayList<>();
                    for (int j = 0; j < musclesArray.length(); j++) {
                        JSONObject muscles = musclesArray.getJSONObject(j);
                        muscle.add(muscles.getInt("muscles"));
                    }
                    exerciseModel.setMuscles(muscle);

                    // add exercise to exerciselistModel
                    exerciseListModel.add(exerciseModel);
                }

                //
                newWorkoutActivity.holdList(exerciseListModel);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
