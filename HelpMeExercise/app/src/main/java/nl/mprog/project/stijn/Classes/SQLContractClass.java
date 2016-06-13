package nl.mprog.project.stijn.Classes;

import android.provider.BaseColumns;

/**
 * Created by Stijn on 08/06/2016.
 */
public class SQLContractClass {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public SQLContractClass() {}

    /**
     * Inner class that defines the table contents
     */
    public static abstract class FeedEntry implements BaseColumns {

        // Variables for table holding all exercises
        public static final String TABLE_NAME = "AllExercises";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_EXERCISE_ID = "exerciseid";
        public static final String COLUMN_NAME_EXERCISE_NAME = "exercisename";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_LANGUAGE = "language";
        public static final String COLUMN_NAME_MUSCLES = "muscles";
        public static final String COLUMN_NAME_EXPLANATION = "explanation";
        public static final String COLUMN_NAME_NULLABLE = "null";

        // Variables for table holding workoutcontent
        public static final String WORKOUT_TABLE_NAME = "workoutcontent";
        public static final String COLUMN_NAME_ENTRY_ID_TAG = "entryidtag";
        public static final String COLUMN_NAME_WORKOUT_EXERCISE_NAME = "exercisename";
        public static final String COLUMN_NAME_WEIGHT = "exerciseweight";
        public static final String COLUMN_NAME_SETS = "exercisesets";
        public static final String COLUMN_NAME_REPS = "exercisereps";
        public static final String COLUMN_NAME_WORKOUT_MUSCLES = "muscles";
        public static final String COLUMN_NAME_WORKOUT_CATEGORY = "category";
        public static final String COLUMN_NAME_WORKOUT_EXPLANATION = "explanation";

        // Variables for table holding created workouts
        public static final String WORKOUTS_TABLE_NAME = "workouts";
        public static final String COLUMN_NAME_WORKOUT = "workout";
        public static final String COLUMN_NAME_DAY = "day";

    }

    // Methods to create and maintain database
    public static final String TEXT_TYPE = " TEXT";
    public static final String INT_TYPE = " INTEGER";
    public static final String COMMA_SEP = ",";

    // Create table for all exercises
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_ENTRY_ID + INT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_CATEGORY + INT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_EXERCISE_ID + INT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_EXERCISE_NAME + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_LANGUAGE + INT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_MUSCLES + INT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_EXPLANATION + TEXT_TYPE +
            " )";

    //Create table for workoutcontent
    public static final String SQL_CREATE_WORKOUT_CONTENT =
            "CREATE TABLE " + FeedEntry.WORKOUT_TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_ENTRY_ID + INT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_WORKOUT_EXERCISE_NAME + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_WEIGHT + INT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_SETS + INT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_REPS + INT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_WORKOUT_MUSCLES + INT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_WORKOUT_CATEGORY + INT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_WORKOUT_EXPLANATION + TEXT_TYPE +
                    " )";

    // Create table for created workouts
    public static final String SQL_CREATE_WORKOUTS_CONTENT =
            "CREATE TABLE " + FeedEntry.WORKOUTS_TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_WORKOUT + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_DAY + TEXT_TYPE +
                    " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
}
