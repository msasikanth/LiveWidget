package com.primudesigns.livewidget.ui.widget;

import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.primudesigns.livewidget.R;
import com.primudesigns.livewidget.data.EventContract;

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
            String desc = eventCursor.getString(eventCursor.getColumnIndex(EventContract.EventEntry.COLUMN_DESCRIPTION));
            String start_time = eventCursor.getString(eventCursor.getColumnIndex(EventContract.EventEntry.COLUMN_START_TIMESTAMP));
            String end_time = eventCursor.getString(eventCursor.getColumnIndex(EventContract.EventEntry.COLUMN_END_TIMESTAMP));
            String status = eventCursor.getString(eventCursor.getColumnIndex(EventContract.EventEntry.COLUMN_STATUS));
            String url = eventCursor.getString(eventCursor.getColumnIndex(EventContract.EventEntry.COLUMN_URL));

            remoteViews.setTextViewText(R.id.tv_event_title_widget, title);
            remoteViews.setTextViewText(R.id.tv_event_desc_widget, desc);
            remoteViews.setTextViewText(R.id.tv_event_start_widget, start_time);
            remoteViews.setTextViewText(R.id.tv_event_end_widget, end_time);
            remoteViews.setTextViewText(R.id.tv_event_status_widget, status);


            final Intent fillInIntent = new Intent();
            fillInIntent.putExtra("url", url);
            remoteViews.setOnClickFillInIntent(R.id.content_view_widget, fillInIntent);

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
