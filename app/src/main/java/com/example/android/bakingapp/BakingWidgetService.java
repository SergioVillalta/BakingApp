package com.example.android.bakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViewsService;

public class BakingWidgetService extends RemoteViewsService {

    private static final String TAG = "WidgetService";

    @Override
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {

        return (new BakingWidgetFactory(this.getApplicationContext(), intent));
    }


}
