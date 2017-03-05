package com.primudesigns.livewidget.database;

import android.net.Uri;
import android.provider.BaseColumns;


/**
 * Created by sasik on 3/4/2017.
 */

public class EventContract {

    public static final String AUTHORITY = "com.primudesigns.livewidget";
    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_TABLE = "events";

    private EventContract() {
    }

    public static class EventEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH_TABLE).build();

        public static final String TABLE_NAME = "events";
        public static final String COLUMN_STATUS = "status";
        public static final String COLUMN_CHALLENGE_TYPE = "challenge_type";
        public static final String COLUMN_START_TIMESTAMP = "start_timestamp";
        public static final String COLUMN_END_TIMESTAMP = "end_timestamp";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_COVER_IMAGE = "thumbnail";
        public static final String COLUMN_COLLEGE = "college";

        public static final int POSITION_ID = 0;

        public static final String[] QUOTE_COLUMNS = {
                _ID,
                COLUMN_TITLE,
                COLUMN_DESCRIPTION,
                COLUMN_COLLEGE,
                COLUMN_START_TIMESTAMP,
                COLUMN_END_TIMESTAMP,
                COLUMN_STATUS,
                COLUMN_URL,
                COLUMN_COVER_IMAGE,
                COLUMN_CHALLENGE_TYPE
        };

        public static Uri makeUriForStock(String name) {
            return CONTENT_URI.buildUpon().appendPath(name).build();
        }

        static String getStockFromUri(Uri queryUri) {
            return queryUri.getLastPathSegment();
        }

    }



}
