package com.udacity.stockhawk;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.udacity.stockhawk.ui.MainActivity;

/**
 * Created by alex.fanning on 03/08/2017.
 */

public class StockWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context c, AppWidgetManager awm, int appWidgetId){
        Intent i = new Intent(c, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(c,0,i,0);
        RemoteViews views = new RemoteViews(c.getPackageName(),R.layout.widget_list_view);
        
    }


}
