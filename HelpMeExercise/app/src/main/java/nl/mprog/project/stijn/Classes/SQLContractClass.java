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

        public static final String TABLE_NAME = "AllExercises";
        public static final String COLUMN_NAME_EXERCISE_ID = "exerciseid";
        public static final String COLUMN_NAME_EXERCISE_NAME = "exercisename";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_LANGUAGE = "language";
        public static final String COLUMN_NAME_MUSCLES = "muscles";
        public static final String COLUMN_NAME_NULLABLE = "null";

    }

    // Methods to create and maintain database
    public static final String TEXT_TYPE = " TEXT";
    public static final String INT_TYPE = " INTEGER";
    public static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_CATEGORY + INT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_EXERCISE_NAME + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_NULLABLE + TEXT_TYPE + COMMA_SEP +
            " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
}
