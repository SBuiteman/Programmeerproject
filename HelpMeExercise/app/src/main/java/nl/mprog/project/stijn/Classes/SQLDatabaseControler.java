package nl.mprog.project.stijn.Classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

/**
 * Created by Stijn on 08/06/2016.
 * Source : https://developer.android.com/training/basics/data-storage/databases.html#WriteDbRow
 */
public class SQLDatabaseControler extends SQLiteOpenHelper {

    // Fields
    public SQLContractClass sqlContractClass;
    public ExerciseModel exerciseModel;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
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
    public void writeExerciseDatabase(Context context, List<ExerciseModel> exerciseList){
        List<ExerciseModel> storageList = exerciseList;

        // Initialize SQLiteOpenHelper
        SQLDatabaseControler mSQLDBControler = new SQLDatabaseControler(context);

        // Gets the data repository in write mode
        SQLiteDatabase db = mSQLDBControler.getWritableDatabase();

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
}
