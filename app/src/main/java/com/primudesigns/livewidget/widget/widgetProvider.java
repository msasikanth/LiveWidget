package com.primudesigns.livewidget.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.primudesigns.livewidget.R;

/**
 * Created by sasik on 3/4/2017.
 */

public class widgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_view);


        remoteAdapter(context, remoteViews);

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    private void remoteAdapter(Context context, final RemoteViews views) {
        views.setRemoteAdapter(R.id.lv_events_widget, new Intent(context, widgetService.class));
    }

}
