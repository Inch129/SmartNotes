package com.asgard.smartnotes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StorageDatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "storageDB";
    public static final String TABLE_NOTES = "notes";

    public static final String KEY_ID = "_id";
    public static final String KEY_HEADER = "header";
    public static final String KEY_BODY = "body";
    public static final String IMPORTANCE = "importance";
    public static final String DATE = "date";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NOTES;

    public StorageDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NOTES + " (" + KEY_ID
                + " integer primary key, " + KEY_HEADER + " text, " + KEY_BODY + " text, " + IMPORTANCE + " text, " + DATE + " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
