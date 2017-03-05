package com.primudesigns.livewidget.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.widget.RemoteViews;

import com.primudesigns.livewidget.R;
import com.primudesigns.livewidget.database.EventContract;
import com.primudesigns.livewidget.fragments.EventsListFragment;
import com.primudesigns.livewidget.ui.MainActivity;

public class widgetProvider extends AppWidgetProvider {

    public static final String CLICK_ACTION = "com.primudesigns.livewidget";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int widgetID : appWidgetIds) {

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_view);

            Cursor eventCursor = query(context);

            remoteViews.setTextViewText(R.id.tv_event_count, String.valueOf(eventCursor.getCount()));


            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            remoteViews.setOnClickPendingIntent(R.id.fl_title_frame, pendingIntent);

            Intent clickIntent = new Intent(context, widgetProvider.class);
            clickIntent.setAction(widgetProvider.CLICK_ACTION);
            clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, clickIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setPendingIntentTemplate(R.id.lv_events_widget, toastPendingIntent);

            remoteAdapter(context, remoteViews);

            remoteViews.setEmptyView(R.id.lv_events_widget, R.id.widget_empty);
            appWidgetManager.updateAppWidget(widgetID, remoteViews);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);


    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (EventsListFragment.ACTION_DATA_UPDATED.equals(intent.getAction())) {

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                    new ComponentName(context, getClass()));
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.lv_events_widget);

        } else if (intent.getAction().equals(CLICK_ACTION)) {

            String url = intent.getExtras().getString("url");

            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            builder.setToolbarColor(context.getResources().getColor(R.color.colorAccent));
            customTabsIntent.launchUrl(context, Uri.parse(url));
        }

    }

    private void remoteAdapter(Context context, final RemoteViews views) {
        views.setRemoteAdapter(R.id.lv_events_widget, new Intent(context, widgetService.class));
    }

    private Cursor query(Context context) {
        return context.getContentResolver().query(EventContract.EventEntry.CONTENT_URI,
                null,
                null,
                null,
                EventContract.EventEntry._ID);
    }

}
