package nl.mprog.project.stijn.Classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stijn on 08/06/2016.
 * Source : https://developer.android.com/training/basics/data-storage/databases.html#WriteDbRow
 */
public class SQLDatabaseControler extends SQLiteOpenHelper {

    // Fields
    public SQLContractClass sqlContractClass;
    public List<ExerciseModel> mModelList;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "HelpMeExercise.db";

    // Constructor
    public SQLDatabaseControler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Create new database
     * */
    @Override
    public void onCreate(SQLiteDatabase db) {

        //Create table for workoutcontent
        db.execSQL(sqlContractClass.SQL_CREATE_WORKOUT_CONTENT);

        // Create table for created workouts
        db.execSQL(sqlContractClass.SQL_CREATE_WORKOUTS_CONTENT);

        // Create table for week planning
        db.execSQL(sqlContractClass.SQL_CREATE_PLANNING_CONTENT);

        // Create table for all exercises
        db.execSQL(sqlContractClass.SQL_CREATE_ENTRIES);
    }

    public void clearTable(SQLiteDatabase db) {
        if (checkDatabase("AllExercises", db)) {
            db.execSQL("DELETE FROM " + SQLContractClass.FeedEntry.TABLE_NAME);
        }
    }

    /**
     * Online data may change -> make new database always
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    /**
     * Check if table exists by selecting count from given table
     */
    public boolean checkDatabase(String tablename, SQLiteDatabase db){

        Boolean flag = true;
        String count = "SELECT count(*) FROM '" + tablename + "'";

        // Try to read from given table
        try {
            Cursor mCursor = db.rawQuery(count, null);
            mCursor.moveToFirst();
            int icount = mCursor.getInt(0);

            // Return false on empty table
            if(icount < 1){
                flag = false;
            }
            mCursor.close();
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        Log.d("Databasecheck", "boolean: "+ flag);
        return flag;
    }

    /**
     * Takes an exerciseList and inserts data in table, creates new row per exercise
     */
    public void writeExerciseDatabase(Context context, List<ExerciseModel> exerciseList){
        List<ExerciseModel> storageList = exerciseList;

        // Initialize SQLiteOpenHelper
        SQLDatabaseControler mSQLDBController = new SQLDatabaseControler(context);

        // Gets the data repository in write mode
        SQLiteDatabase db = mSQLDBController.getWritableDatabase();

        // Clear table for workoutcontent
        clearTable(db);

        // For each exercise in exercise list
        for (int i = 0; i < storageList.size(); i++) {

            ExerciseModel singleExercise = exerciseList.get(i);

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(SQLContractClass.FeedEntry.COLUMN_NAME_EXERCISE_NAME,
                    singleExercise.getExerciseName());
            values.put(SQLContractClass.FeedEntry.COLUMN_NAME_CATEGORY,
                    singleExercise.getCategory());
            values.put(SQLContractClass.FeedEntry.COLUMN_NAME_EXERCISE_ID,
                    singleExercise.getExerciseId());
            values.put(SQLContractClass.FeedEntry.COLUMN_NAME_EXPLANATION,
                    singleExercise.getInstructions());

            // Insert the new row
            db.insert(
                    SQLContractClass.FeedEntry.TABLE_NAME,
                    SQLContractClass.FeedEntry.COLUMN_NAME_NULLABLE,
                    values);
        }
    }

    /**
     * Reads all data from table holding the exercises as obtained from API
     */
    public List<ExerciseModel> readExerciseDatabase(Context context) {

        // Initialize SQLiteOpenHelper
        SQLDatabaseControler mSQLDBControler = new SQLDatabaseControler(context);

        // Gets the data repository in read  mode
        SQLiteDatabase db = mSQLDBControler.getReadableDatabase();

        // Define which columns from the database will be used after this query
        String[] projection = {
                SQLContractClass.FeedEntry._ID,
                SQLContractClass.FeedEntry.COLUMN_NAME_EXERCISE_NAME,
                SQLContractClass.FeedEntry.COLUMN_NAME_CATEGORY,
                SQLContractClass.FeedEntry.COLUMN_NAME_EXERCISE_ID,
                SQLContractClass.FeedEntry.COLUMN_NAME_EXPLANATION,
                SQLContractClass.FeedEntry.COLUMN_NAME_MUSCLES
        };

        // How the results are sorted in the resulting Cursor
        String sortOrder =
                SQLContractClass.FeedEntry.COLUMN_NAME_EXERCISE_NAME;

         Cursor mCursor = db.query(
                SQLContractClass.FeedEntry.TABLE_NAME,    // The table to query
                projection,                               // The columns to return
                null,                                     // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        // Put cursor data in list holding exercise objects
        // http://stackoverflow.com/questions/1354006/
        //      how-can-i-create-a-list-array-with-the-cursor-data-in-android
        List<ExerciseModel> mList = new ArrayList<>();
        for(mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
            // The Cursor is now set to the right position
            ExerciseModel mExerciseModel = new ExerciseModel();

            mExerciseModel.setExerciseName(mCursor.getString(mCursor.getColumnIndexOrThrow(
                   SQLContractClass.FeedEntry.COLUMN_NAME_EXERCISE_NAME)));
            mExerciseModel.setCategory(mCursor.getString(mCursor.getColumnIndexOrThrow(
                    SQLContractClass.FeedEntry.COLUMN_NAME_CATEGORY)));
            mExerciseModel.setExerciseId(mCursor.getInt(mCursor.getColumnIndexOrThrow(
                    SQLContractClass.FeedEntry.COLUMN_NAME_EXERCISE_ID)));
            mExerciseModel.setInstructions(mCursor.getString(mCursor.getColumnIndexOrThrow(
                    SQLContractClass.FeedEntry.COLUMN_NAME_EXPLANATION)));

            // Add exercise to exerciselistModel
            mList.add(mExerciseModel);
        }
        // Close database
        db.close();
        mCursor.close();
        return mList;
    }

    /**
     * TODO
     * */
    public void createWorkout(Context context, WorkoutModel mWorkoutModel) {

        // Initialize SQLiteOpenHelper
        SQLDatabaseControler mSQLDBController = new SQLDatabaseControler(context);

        // Gets the data repository in write mode
        SQLiteDatabase db = mSQLDBController.getWritableDatabase();

        // Add values
        ContentValues values = new ContentValues();
        values.put(SQLContractClass.FeedEntry.COLUMN_NAME_WORKOUT, mWorkoutModel.getmWorkoutName());

        // Insert the new row
        db.insert(SQLContractClass.FeedEntry.WORKOUTS_TABLE_NAME,
                null, values);

        // Close database
        db.close();
    }

    /**
     * TODO
     */
    public void createWorkoutDay(Context context, String workoutname, int day ) {

        // Initialize SQLiteOpenHelper
        SQLDatabaseControler mSQLDBController = new SQLDatabaseControler(context);

        // Gets the data repository in write mode
        SQLiteDatabase db = mSQLDBController.getWritableDatabase();

        // Add values
        ContentValues values = new ContentValues();
        values.put(SQLContractClass.FeedEntry.COLUMN_NAME_WEEKDAY, day);
        values.put(SQLContractClass.FeedEntry.COLUMN_NAME_WORKOUTNAME, workoutname);

        // Insert the new row
        db.insert(SQLContractClass.FeedEntry.WEEK_TABLE, null, values);

        // Close database
        db.close();
    }

    /**
     * getting all tags
     * */
    public List<WorkoutModel> getAllTags() {
        List<WorkoutModel> tags = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + SQLContractClass.FeedEntry.WORKOUTS_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to list
        for(mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {

            WorkoutModel mWorkoutModel = new WorkoutModel();

            mWorkoutModel.setmExerciseTag((mCursor.getColumnIndexOrThrow(
                    SQLContractClass.FeedEntry._ID)));
            mWorkoutModel.setmWorkoutName(mCursor.getString(mCursor.getColumnIndexOrThrow(
                    SQLContractClass.FeedEntry.COLUMN_NAME_WORKOUT)));
            mWorkoutModel.setmWorkoutDay(mCursor.getColumnIndexOrThrow(
                    SQLContractClass.FeedEntry.COLUMN_NAME_DAY));

            // Add workoutmodel to list
            tags.add(mWorkoutModel);
        }

        // Close database
        mCursor.close();
        db.close();
        return tags;
    }

    public int getWorkoutID(String workoutname){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT _id FROM " + SQLContractClass.FeedEntry.WORKOUTS_TABLE_NAME
                + " WHERE " + SQLContractClass.FeedEntry.COLUMN_NAME_WORKOUT + " ='" + workoutname+"'";

        Cursor mCursor = db.rawQuery(selectQuery, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        int mWorkoutid = mCursor.getInt(mCursor.getColumnIndexOrThrow(
                SQLContractClass.FeedEntry._ID));

        mCursor.close();
        return mWorkoutid;
    }

    public int getExerciseID(String exercisename){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT exerciseid FROM " + SQLContractClass.FeedEntry.TABLE_NAME
                + " WHERE " + SQLContractClass.FeedEntry.COLUMN_NAME_EXERCISE_NAME + " ='" +
                exercisename + "'";

        Cursor mCursor = db.rawQuery(selectQuery, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        int mExerciseid = mCursor.getInt(mCursor.getColumnIndexOrThrow(
                SQLContractClass.FeedEntry.COLUMN_NAME_EXERCISE_ID));

        // Close database
        mCursor.close();
        db.close();

        return mExerciseid;
    }

    /**
     *
     */
    public List<WorkoutModel> getSchemaData(){
        List<WorkoutModel> tags = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        // Check id database exists and not empty
        if(checkDatabase("weektable", db)) {

            String selectQuery = "SELECT  * FROM " + SQLContractClass.FeedEntry.WEEK_TABLE;

            Cursor mCursor = db.rawQuery(selectQuery, null);

            // Looping through all rows and adding to list
            for (mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {

                WorkoutModel mWorkoutModel = new WorkoutModel();
                mWorkoutModel.setmExerciseTag((mCursor.getColumnIndexOrThrow(
                        SQLContractClass.FeedEntry._ID)));
                mWorkoutModel.setmWorkoutName(mCursor.getString(mCursor.getColumnIndexOrThrow(
                        SQLContractClass.FeedEntry.COLUMN_NAME_WORKOUTNAME)));
                mWorkoutModel.setmWorkoutDay(mCursor.getInt(mCursor.getColumnIndexOrThrow(
                        SQLContractClass.FeedEntry.COLUMN_NAME_WEEKDAY)));

                // Add workoutmodel to list
                tags.add(mWorkoutModel);
            }

            // Close database
            mCursor.close();
            db.close();
        }

        return tags;
    }

    public void addWorkoutExercise(ExerciseModel exerciseModel){

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(SQLContractClass.FeedEntry.COLUMN_NAME_EXERCISE_TAG,
                exerciseModel.getExerciseId());
        values.put(SQLContractClass.FeedEntry.COLUMN_NAME_WORKOUT_TAG,
                exerciseModel.getWorkoutID());
        values.put(SQLContractClass.FeedEntry.COLUMN_NAME_SETS,
                exerciseModel.getSets());
        values.put(SQLContractClass.FeedEntry.COLUMN_NAME_REPS,
                exerciseModel.getReps());
        values.put(SQLContractClass.FeedEntry.COLUMN_NAME_WEIGHT,
                exerciseModel.getWeight());


        // Insert the new row
        db.insert(
                SQLContractClass.FeedEntry.WORKOUT_TABLE_NAME,
                SQLContractClass.FeedEntry.COLUMN_NAME_NULLABLE,
                values);

        // Close database
        db.close();
    }

    /**
     * Takes exercise model and updates the row where workout and exercise name match
     */
    public void updateWorkoutExercise(ExerciseModel exerciseModel){

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(SQLContractClass.FeedEntry.COLUMN_NAME_SETS,
                exerciseModel.getSets());
        values.put(SQLContractClass.FeedEntry.COLUMN_NAME_REPS,
                exerciseModel.getReps());
        values.put(SQLContractClass.FeedEntry.COLUMN_NAME_WEIGHT,
                exerciseModel.getWeight());

        // Update table
        db.update(SQLContractClass.FeedEntry.WORKOUT_TABLE_NAME,
                  values,
                  "workouttag='"+exerciseModel.getWorkoutID()+"' and "+
                  "exercisetag='"+exerciseModel.getExerciseId()+"'",
                  null);

        // Close database
        db.close();
    }

    /**
     * Get data from Allexercises, workouts and workoutcontent tables based on chosen
     * workout.
     */
    public List<ExerciseModel> getWorkoutData(String workout){

        SQLiteDatabase db = this.getReadableDatabase();
        checkDatabase("workouts", db);
        String mWorkout = workout;

        // Create list for exercises in workout
        mModelList = new ArrayList<>();

        // Get workout data corresponding to given day
        String selectQuery = "SELECT _id FROM " +
                SQLContractClass.FeedEntry.WORKOUTS_TABLE_NAME + " WHERE " +
                SQLContractClass.FeedEntry.COLUMN_NAME_WORKOUT + " ='" + mWorkout + "'";

        WorkoutModel mWorkoutModel = new WorkoutModel();

        // Looping through all rows and adding to list
        Cursor mCursor = db.rawQuery(selectQuery, null);
        for (mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {

            mWorkoutModel.setmExerciseTag(mCursor.getInt(mCursor.getColumnIndexOrThrow(
                    SQLContractClass.FeedEntry._ID)));
        }

        String tag = String.valueOf(mWorkoutModel.getmExerciseTag());
        String selectQuery2 = "SELECT * FROM " + SQLContractClass.FeedEntry.WORKOUT_TABLE_NAME
                + " WHERE " + SQLContractClass.FeedEntry.COLUMN_NAME_WORKOUT_TAG + " ='" +
                tag + "'";

        Cursor mCursor2 = db.rawQuery(selectQuery2, null);

        // Looping through all rows and adding exercises to list
        for(mCursor2.moveToFirst(); !mCursor2.isAfterLast(); mCursor2.moveToNext()) {

            ExerciseModel mExerciseModel = new ExerciseModel();
            mExerciseModel.setTableInputID((mCursor2.getInt(mCursor2.getColumnIndexOrThrow(
                    SQLContractClass.FeedEntry._ID))));
            mExerciseModel.setExerciseId((mCursor2.getInt(mCursor2.getColumnIndexOrThrow(
                    SQLContractClass.FeedEntry.COLUMN_NAME_EXERCISE_TAG))));
            mExerciseModel.setSets(mCursor2.getString(mCursor2.getColumnIndexOrThrow(
            SQLContractClass.FeedEntry.COLUMN_NAME_SETS)));
            mExerciseModel.setReps(mCursor2.getString(mCursor2.getColumnIndexOrThrow(
                    SQLContractClass.FeedEntry.COLUMN_NAME_REPS)));
            mExerciseModel.setWeight(mCursor2.getString(mCursor2.getColumnIndexOrThrow(
                    SQLContractClass.FeedEntry.COLUMN_NAME_WEIGHT)));

            // Add workoutmodel to list
            mModelList.add(mExerciseModel);
        }

        // Get additional data per added exercise from exercise table
        for (ExerciseModel exerciseModel: mModelList) {

            // Get data corresponding to current exercise
            String selectQuery3 = "SELECT * FROM " + SQLContractClass.FeedEntry.TABLE_NAME
                    + " WHERE " + SQLContractClass.FeedEntry.COLUMN_NAME_EXERCISE_ID + " ='" +
                    exerciseModel.getExerciseId() + "'";

            Cursor mCursor3 = db.rawQuery(selectQuery3, null);

            // Looping through all rows and adding info to exercisemodels in list
            for (mCursor3.moveToFirst(); !mCursor3.isAfterLast(); mCursor3.moveToNext()) {

                exerciseModel.setExerciseName(mCursor3.getString(mCursor3.getColumnIndexOrThrow(
                        SQLContractClass.FeedEntry.COLUMN_NAME_EXERCISE_NAME)));
                exerciseModel.setCategory(mCursor3.getString(mCursor3.getColumnIndexOrThrow(
                        SQLContractClass.FeedEntry.COLUMN_NAME_CATEGORY)));
            }
            mCursor3.close();
        }

        // Close database
        mCursor.close();
        mCursor2.close();
        db.close();

        return mModelList;
    }

    /**
     * Delete chosen workout related data from workoutcontent, workouts and weektable
     */
    public void deleteWorkout(String workout){
        SQLiteDatabase db = this.getReadableDatabase();

        // Delete data corresponding to given workout
        String selectQuery = "DELETE FROM " + SQLContractClass.FeedEntry.WEEK_TABLE + " WHERE " +
                SQLContractClass.FeedEntry.COLUMN_NAME_WORKOUTNAME + "='" + workout + "'";

        Cursor mCursor = db.rawQuery(selectQuery, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        // Close databse
        mCursor.close();
        db.close();

    }
}
