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

        // Clear table for all exercises
        db.execSQL(sqlContractClass.SQL_DELETE_ENTRIES);

        //Create table for workoutcontent
        db.execSQL(sqlContractClass.SQL_CREATE_WORKOUT_CONTENT);

        // Create table for created workouts
        db.execSQL(sqlContractClass.SQL_CREATE_WORKOUTS_CONTENT);

        // Create table for week planning
        db.execSQL(sqlContractClass.SQL_CREATE_PLANNING_CONTENT);

        // Create table for all exercises
        db.execSQL(sqlContractClass.SQL_CREATE_ENTRIES);
    }

    /**
     * Online data may change -> make new database always
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(sqlContractClass.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    /**
     * Takes an exerciseList and inserts data in table, creates new row per exercise
     */
    public void updateExerciseDatabase(Context context, List<ExerciseModel> exerciseList) {
        List<ExerciseModel> storageList = exerciseList;

        // Initialize SQLiteOpenHelper
        SQLDatabaseControler mSQLDBControler = new SQLDatabaseControler(context);

        SQLiteDatabase db = mSQLDBControler.getReadableDatabase();

        // For each exercise in exercise list
        for (int i = 0; i < storageList.size(); i++) {

            ExerciseModel singleExercise = exerciseList.get(i);

            // New values for one columns
            ContentValues values = new ContentValues();
            values.put(SQLContractClass.FeedEntry.COLUMN_NAME_EXERCISE_NAME,
                    singleExercise.getExerciseName());
            values.put(SQLContractClass.FeedEntry.COLUMN_NAME_CATEGORY,
                    singleExercise.getCategory());

            // Which row to update, based on the ID
            String selection = SQLContractClass.FeedEntry._ID + " LIKE ?";
            String[] selectionArgs = {String.valueOf(i)};

            db.update(
                    SQLContractClass.FeedEntry.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);
        }
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
            values.put(SQLContractClass.FeedEntry.COLUMN_NAME_MUSCLES, singleExercise.getMuscles());

            // Insert the new row
            db.insert(
                    SQLContractClass.FeedEntry.TABLE_NAME,
                    SQLContractClass.FeedEntry.COLUMN_NAME_NULLABLE,
                    values);
        }
    }

    /**
     * TODO
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
            mExerciseModel.setCategory(mCursor.getInt(mCursor.getColumnIndexOrThrow(
                    SQLContractClass.FeedEntry.COLUMN_NAME_CATEGORY)));
            mExerciseModel.setExerciseId(mCursor.getInt(mCursor.getColumnIndexOrThrow(
                    SQLContractClass.FeedEntry.COLUMN_NAME_EXERCISE_ID)));

            // add exercise to exerciselistModel
            mList.add(mExerciseModel);
        }
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
    }

    /**
     * TODO
     */
    public void createWorkoutDay(Context context, String workoutname, int day ) {

        // Initialize SQLiteOpenHelper
        SQLDatabaseControler mSQLDBController = new SQLDatabaseControler(context);
        Log.d("TAGcreateWorkoutDay1", "kom ik er in?");

        // Gets the data repository in write mode
        SQLiteDatabase db = mSQLDBController.getWritableDatabase();

        // Add values
        ContentValues values = new ContentValues();
        values.put(SQLContractClass.FeedEntry.COLUMN_NAME_WEEKDAY, day);
        values.put(SQLContractClass.FeedEntry.COLUMN_NAME_WORKOUTNAME, workoutname);
        Log.d("TAGcreateWorkoutDay2", "Goede values geput? : " + workoutname +" & "+ day);

        // Insert the new row
        db.insert(SQLContractClass.FeedEntry.WEEK_TABLE, null, values);
        Log.d("TAGcreateWorkoutDay3", "wordt row geinsert?");
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
        return mExerciseid;
    }

    public List<WorkoutModel> getSchemaData(){
        List<WorkoutModel> tags = new ArrayList<>();
        Log.d("TAGgetSchemaData1", "Kom ik er in?");
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + SQLContractClass.FeedEntry.WEEK_TABLE;

        Cursor mCursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to list
        for(mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
            Log.d("TAGgetSchemaData2", "Kom ik in for-loop?");

            WorkoutModel mWorkoutModel = new WorkoutModel();

            mWorkoutModel.setmExerciseTag((mCursor.getColumnIndexOrThrow(
                    SQLContractClass.FeedEntry._ID)));
            mWorkoutModel.setmWorkoutName(mCursor.getString(mCursor.getColumnIndexOrThrow(
                    SQLContractClass.FeedEntry.COLUMN_NAME_WORKOUTNAME)));
            mWorkoutModel.setmWorkoutDay(mCursor.getColumnIndexOrThrow(
                    SQLContractClass.FeedEntry.COLUMN_NAME_WEEKDAY));
            Log.d("TAGgetSchemaData3", "Waarde _ID: "+ mWorkoutModel.getmExerciseTag());
            Log.d("TAGgetSchemaData4", "Waarde WORKOUTNAME: "+ mWorkoutModel.getmWorkoutName());
            Log.d("TAGgetSchemaData5", "Waarde WEEKDAY: "+ mWorkoutModel.getmWorkoutDay());




            // Add workoutmodel to list
            tags.add(mWorkoutModel);
        }

        return tags;
    }

    public void addWorkoutExercise(ExerciseModel exerciseModel){

        // Initialize SQLiteOpenHelper
        //SQLDatabaseControler mSQLDBController = new SQLDatabaseControler(context);

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
    }

    /**
     *
     */
    public List<ExerciseModel> getWorkoutData(String day){
        SQLiteDatabase db = this.getReadableDatabase();

        // Create list for exercises in workout
        List<ExerciseModel> mList = new ArrayList<>();
        ExerciseModel mExerciseModel = new ExerciseModel();

        // Get workout data corresponding to given day
        String selectQuery = "SELECT workout AND _id FROM " +
                SQLContractClass.FeedEntry.WORKOUTS_TABLE_NAME + " WHERE " +
                SQLContractClass.FeedEntry.COLUMN_NAME_DAY + " ='" + day + "'";

        // Looping through all rows and adding to list
        Cursor mCursor = db.rawQuery(selectQuery, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        WorkoutModel mWorkoutModel = new WorkoutModel();

        mWorkoutModel.setmExerciseTag((mCursor.getColumnIndexOrThrow(
                SQLContractClass.FeedEntry._ID)));
        mWorkoutModel.setmWorkoutName(mCursor.getString(mCursor.getColumnIndexOrThrow(
                SQLContractClass.FeedEntry.COLUMN_NAME_WORKOUT)));

        // Get workout data corresponding to given day
        String selectQuery2 = "SELECT * FROM " + SQLContractClass.FeedEntry.WORKOUT_TABLE_NAME
                + " WHERE " + SQLContractClass.FeedEntry.COLUMN_NAME_WORKOUT_TAG + " ='" +
                mWorkoutModel.getmExerciseTag() + "'";

        Cursor mCursor2 = db.rawQuery(selectQuery2, null);

        // Looping through all rows and adding exercises to list
        for(mCursor2.moveToFirst(); !mCursor2.isAfterLast(); mCursor2.moveToNext()) {

            mExerciseModel.setExerciseId((mCursor2.getColumnIndexOrThrow(
                    SQLContractClass.FeedEntry.COLUMN_NAME_EXERCISE_TAG)));
            mExerciseModel.setSets(mCursor2.getString(mCursor.getColumnIndexOrThrow(
                    SQLContractClass.FeedEntry.COLUMN_NAME_SETS)));
            mExerciseModel.setReps(String.valueOf(mCursor2.getColumnIndexOrThrow(
                    SQLContractClass.FeedEntry.COLUMN_NAME_REPS)));
            mExerciseModel.setWeight(String.valueOf(mCursor2.getColumnIndexOrThrow(
                    SQLContractClass.FeedEntry.COLUMN_NAME_WEIGHT)));

            // Add workoutmodel to list
            mList.add(mExerciseModel);
        }

        // Get additional data per added exercise from exercise table
        for (ExerciseModel exerciseModel: mList) {

            // Get data corresponding to current exercise
            String selectQuery3 = "SELECT * FROM " + SQLContractClass.FeedEntry.TABLE_NAME
                    + " WHERE " + SQLContractClass.FeedEntry.COLUMN_NAME_EXERCISE_ID + " ='" +
                    exerciseModel.getExerciseId() + "'";

            Cursor mCursor3 = db.rawQuery(selectQuery3, null);

            // Looping through all rows and adding info to exercisemodels in list
            for (mCursor3.moveToFirst(); !mCursor3.isAfterLast(); mCursor3.moveToNext()) {

                exerciseModel.setExerciseName(String.valueOf(mCursor3.getColumnIndexOrThrow(
                        SQLContractClass.FeedEntry.COLUMN_NAME_EXERCISE_NAME)));
                exerciseModel.setCategory(mCursor3.getColumnIndexOrThrow(
                        SQLContractClass.FeedEntry.COLUMN_NAME_CATEGORY));
                exerciseModel.setMuscles(mCursor3.getString(mCursor3.getColumnIndexOrThrow(
                        SQLContractClass.FeedEntry.COLUMN_NAME_MUSCLES)));
            }
        }
        return null;
    }
}
