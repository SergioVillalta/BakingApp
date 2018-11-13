package com.example.android.bakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class BakingWidgetUpdateService extends IntentService {
    public static final String ACTION_UPDATE_BAKING_WIDGET = "com.example.android.bakingapp.action.update_baking_widgets";

    public BakingWidgetUpdateService() {
        super("BakingWidgetUpdateService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_BAKING_WIDGET.equals(action)) {
                handleActionUpdateBakingApp();
            }
        }
    }

    private void handleActionUpdateBakingApp() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingWidgetProvider.class));
        //Now update all widgets
        BakingWidgetProvider.updateBakingWidgets(this, appWidgetManager, appWidgetIds);
    }

    public static void startActionUpdateBakingWidget(Context context) {
        Intent intent = new Intent(context, BakingWidgetUpdateService.class);
        intent.setAction(ACTION_UPDATE_BAKING_WIDGET);
        context.startService(intent);
    }
}
