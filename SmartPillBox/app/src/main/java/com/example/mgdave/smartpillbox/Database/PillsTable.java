package com.example.mgdave.smartpillbox.Database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PillsTable {

    public static final String TABLE_NAME = "pills_table";
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String CURRENT_AMOUNT = "current_amount";
    public static final String FREQUENCY = "frequency";
    public static final String LAST_TAKEN_TIME = "last_taken_time";
    public static final String EXPECTED_LAST_DATE = "expected_last_date";

    public static final String DATABASE_CREATE =
            "create table "
            + TABLE_NAME
            + "("
            + ID + " integer not null primary key, "
            + NAME + " text not null, "
            + DESCRIPTION + " text, "
            + CURRENT_AMOUNT + " integer, "
            + FREQUENCY + " integer, "
            + LAST_TAKEN_TIME + " integer, "
            + EXPECTED_LAST_DATE + " integer "
            + ");" ;

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.v(PillsTable.class.getName(), "Upgrading database from version " + oldVersion
                + " to " + newVersion);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }

}
