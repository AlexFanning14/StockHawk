package com.udacity.stockhawk.widget;


import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.udacity.stockhawk.R;

/**
 * Created by alex.fanning on 03/08/2017.
 */

public class StockWidgetProvider extends AppWidgetProvider {
    @Override
    public void onReceive(Context context, Intent intent) {

        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetID : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetID);
        }

    }


    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    private static void updateAppWidget(Context c, AppWidgetManager awm, int appWidgetId) {

        RemoteViews views = new RemoteViews(c.getPackageName(), R.layout.widget_list_view);
        Intent i = new Intent(c, StockWidgetService.class);
        views.setRemoteAdapter(R.id.widget_list_view, i);

        awm.updateAppWidget(appWidgetId, views);
    }

}





















