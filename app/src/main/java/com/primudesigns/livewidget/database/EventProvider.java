package com.primudesigns.livewidget.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.primudesigns.livewidget.database.EventContract.EventEntry;

public class EventProvider extends ContentProvider {

    private EventHelper helper;


    @Override
    public boolean onCreate() {
        Context context = getContext();
        helper = new EventHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionARgs, @Nullable String sortorder) {

        final SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor;

        cursor = database.query(EventEntry.TABLE_NAME,
                projection,
                selection,
                selectionARgs,
                null,
                null,
                sortorder);

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        final SQLiteDatabase database = helper.getWritableDatabase();

        long id = database.insert(EventEntry.TABLE_NAME, null, contentValues);

        Uri returnUri;

        if (id > 0) {
            returnUri = ContentUris.withAppendedId(EventEntry.CONTENT_URI, id);
            Log.d("database", "Added item to database");
        } else {
            throw new SQLException("Failed to insert" + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase database = helper.getWritableDatabase();
        int rowsDeleted;

        if (selection == null) {
            selection = "1";
        }

        String name = EventEntry.getStockFromUri(uri);
        rowsDeleted = database.delete(EventEntry.TABLE_NAME,
                '"' + name + '"' + " =" + EventEntry._ID,
                selectionArgs);

        if (rowsDeleted != 0) {
            Context context = getContext();
            if (context != null){
                context.getContentResolver().notifyChange(uri, null);
            }
        }

        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

}
