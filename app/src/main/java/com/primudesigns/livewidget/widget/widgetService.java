package com.primudesigns.livewidget.widget;

import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.primudesigns.livewidget.R;
import com.primudesigns.livewidget.database.EventContract;

public class widgetService extends RemoteViewsService {

    private Cursor eventCursor;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new listEventView();
    }


    public class listEventView implements RemoteViewsService.RemoteViewsFactory {

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {

            if (eventCursor != null) eventCursor.close();

            final Long identityToken = Binder.clearCallingIdentity();

            eventCursor = getContentResolver().query(EventContract.EventEntry.CONTENT_URI,
                    null,
                    null,
                    null,
                    EventContract.EventEntry._ID);

            Binder.restoreCallingIdentity(identityToken);

        }

        @Override
        public void onDestroy() {
            if (eventCursor != null) {
                eventCursor.close();
                eventCursor = null;
            }
        }

        @Override
        public int getCount() {
            return eventCursor == null ? 0 : eventCursor.getCount();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            if (position == AdapterView.INVALID_POSITION || eventCursor == null
                    || !eventCursor.moveToPosition(position)) {
                return null;
            }

            RemoteViews remoteViews = new RemoteViews(getPackageName(),
                    R.layout.widget_event_item);


            String title = eventCursor.getString(eventCursor.getColumnIndex(EventContract.EventEntry.COLUMN_TITLE));

            Log.d("title", title);

            remoteViews.setTextViewText(R.id.tv_event_title_widget, "Granular Deep Learning");

            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }


}
