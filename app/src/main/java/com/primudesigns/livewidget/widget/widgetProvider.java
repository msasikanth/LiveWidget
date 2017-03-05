package com.primudesigns.livewidget.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.primudesigns.livewidget.R;
import com.primudesigns.livewidget.fragments.EventsListFragment;

public class widgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int widgetID : appWidgetIds) {

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_view);

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
        }

    }

    private void remoteAdapter(Context context, final RemoteViews views) {
        views.setRemoteAdapter(R.id.lv_events_widget, new Intent(context, widgetService.class));
    }

}
