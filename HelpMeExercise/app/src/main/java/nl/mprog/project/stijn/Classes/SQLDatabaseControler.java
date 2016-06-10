package nl.mprog.project.stijn.Classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

        // Create table for all exercises
        db.execSQL(sqlContractClass.SQL_CREATE_ENTRIES);

        //Create table for workoutcontent
        db.execSQL(sqlContractClass.SQL_CREATE_WORKOUT_CONTENT);

        // Create table for created workouts
        db.execSQL(sqlContractClass.SQL_CREATE_WORKOUTS_CONTENT);
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
            values.put(SQLContractClass.FeedEntry.COLUMN_NAME_CATEGORY, singleExercise.getCategory());

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

            // add exercise to exerciselistModel
            mList.add(mExerciseModel);
        }
        return mList;
    }

    public void createWorkout(Context context, String name) {

        // Initialize SQLiteOpenHelper
        SQLDatabaseControler mSQLDBController = new SQLDatabaseControler(context);

        // Gets the data repository in write mode
        SQLiteDatabase db = mSQLDBController.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SQLContractClass.FeedEntry.COLUMN_NAME_WORKOUT, name);

        // Insert the new row
        db.insert(SQLContractClass.FeedEntry.WORKOUTS_TABLE_NAME,
                null, values);
    }

    /**
     * getting all tags
     * */
    public List<String> getAllTags() {
        List<String> tags = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + SQLContractClass.FeedEntry.WORKOUTS_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        for(mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
            String mString = mCursor.getString(mCursor.getColumnIndexOrThrow(
                    SQLContractClass.FeedEntry.COLUMN_NAME_WORKOUT));
            tags.add(mString);
        }
        return tags;
    }
}
