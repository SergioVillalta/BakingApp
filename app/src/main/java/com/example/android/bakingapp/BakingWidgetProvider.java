package com.example.android.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidgetProvider extends AppWidgetProvider {

    private static final String WIDGET_RECIPE_SELECTED_ID_KEY = "WIDGET_RECIPE_SELECTED_ID";

    static void updateAppWidget(final Context context, AppWidgetManager appWidgetManager,
                                List<Recipe> recipeInfo, final int appWidgetId) {

        // Create an Intent to launch MainActivity when clicked
        Intent intent = new Intent(context, RecipesActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        // Construct the RemoteViews object
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget_provider);
        prepareViewFlipper(context, recipeInfo, appWidgetId);

        // Widgets allow click handlers to only launch pending intents
        views.setOnClickPendingIntent(R.id.view_flipper, pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    static void prepareViewFlipper(final Context context, List<Recipe> recipeInfo, final int appWidgetId){
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget_provider);
        int lastUsedindex = 5;
        if(recipeInfo != null && recipeInfo.size()>0){
            for (int i = 0; i<5; i++) {


                int imageViewId= 0;
                int recipeTextViewId= 0;
                int idTextViewId = 0;
                switch (i+1){
                    case 1:
                        imageViewId = R.id.iv_top1_recipe;
                        recipeTextViewId = R.id.tv_top1_recipe;
                        idTextViewId = R.id.tv_top1_id;
                        break;
                    case 2:
                        imageViewId = R.id.iv_top2_recipe;
                        recipeTextViewId = R.id.tv_top2_recipe;
                        idTextViewId = R.id.tv_top2_id;
                        break;
                    case 3:
                        imageViewId = R.id.iv_top3_recipe;
                        recipeTextViewId = R.id.tv_top3_recipe;
                        idTextViewId = R.id.tv_top3_id;
                        break;
                    case 4:
                        imageViewId = R.id.iv_top4_recipe;
                        recipeTextViewId = R.id.tv_top4_recipe;
                        idTextViewId = R.id.tv_top4_id;
                        break;
                    case 5:
                        imageViewId = R.id.iv_top5_recipe;
                        recipeTextViewId = R.id.tv_top5_recipe;
                        idTextViewId = R.id.tv_top5_id;
                        break;
                }
                if(i<recipeInfo.size()){
                    URL posterUrl = null;
                    try {
                        posterUrl = new URL(recipeInfo.get(i).getImage());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    //Use of Picasso library to set the ImageView
                    Handler uiHandler = new Handler(Looper.getMainLooper());
                    final URL finalPosterUrl = posterUrl;
                    final int finalImageViewId = imageViewId;
                    uiHandler.post(new Runnable(){
                        @Override
                        public void run() {
                            Picasso.with(context)
                                    .load(finalPosterUrl.toString())
                                    .resize(100,100)
                                    .into(views, finalImageViewId,new int[]{appWidgetId});
                        }
                    });
                    views.setTextViewText(recipeTextViewId,recipeInfo.get(i).getName());
                    views.setTextViewText(idTextViewId,String.valueOf(recipeInfo.get(i).getId()));
                }
                else{
                    views.setImageViewResource(imageViewId,R.drawable.ic_cake_black_80dp);
                    views.setTextViewText(recipeTextViewId,context.getString(R.string.app_name));
                    views.setTextViewText(idTextViewId,"-1");
                }




            }

        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        BakingWidgetService.startActionUpdateBakingWidget(context);
    }

    public static void updateBakingWidgets(Context context, AppWidgetManager appWidgetManager,
                                           List<Recipe> recipeInfo, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, recipeInfo, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

