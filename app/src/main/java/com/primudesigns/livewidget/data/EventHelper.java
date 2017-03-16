package com.primudesigns.livewidget.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.primudesigns.livewidget.data.EventContract.EventEntry;

public class EventHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "liveevents.db";
    private static final int DATABASE_VERSION = 1;

    public EventHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String CREATE_EVENTS_TABLE = "CREATE TABLE " +
                EventContract.EventEntry.TABLE_NAME + "(" +
                EventContract.EventEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                EventContract.EventEntry.COLUMN_TITLE + " VARCHAR," +
                EventContract.EventEntry.COLUMN_DESCRIPTION + " VARCHAR," +
                EventContract.EventEntry.COLUMN_STATUS + " VARCHAR," +
                EventContract.EventEntry.COLUMN_CHALLENGE_TYPE + " VARCHAR," +
                EventContract.EventEntry.COLUMN_START_TIMESTAMP + " VARCHAR," +
                EventContract.EventEntry.COLUMN_END_TIMESTAMP + " VARCHAR," +
                EventContract.EventEntry.COLUMN_COLLEGE + " VARCHAR," +
                EventContract.EventEntry.COLUMN_URL + " VARCHAR," +
                EventContract.EventEntry.COLUMN_COVER_IMAGE + " VARCHAR," +
                "UNIQUE(" + EventContract.EventEntry.COLUMN_TITLE + ") ON CONFLICT REPLACE" +
                ");";

        sqLiteDatabase.execSQL(CREATE_EVENTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EventContract.EventEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}
