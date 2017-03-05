package com.primudesigns.livewidget.database;

import com.primudesigns.livewidget.database.EventContract.EventEntry;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EventHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "liveevents.db";
    private static final int DATABASE_VERSION = 1;

    public EventHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String CREATE_EVENTS_TABLE = "CREATE TABLE " +
                EventEntry.TABLE_NAME + "(" +
                EventEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                EventEntry.COLUMN_TITLE + " VARCHAR," +
                EventEntry.COLUMN_DESCRIPTION + " VARCHAR," +
                EventEntry.COLUMN_STATUS + " VARCHAR," +
                EventEntry.COLUMN_CHALLENGE_TYPE + " VARCHAR," +
                EventEntry.COLUMN_START_TIMESTAMP + " VARCHAR," +
                EventEntry.COLUMN_END_TIMESTAMP + " VARCHAR," +
                EventEntry.COLUMN_COLLEGE + " VARCHAR," +
                EventEntry.COLUMN_URL + " VARCHAR," +
                EventEntry.COLUMN_COVER_IMAGE + " VARCHAR" +
                ");";

        sqLiteDatabase.execSQL(CREATE_EVENTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EventEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}
