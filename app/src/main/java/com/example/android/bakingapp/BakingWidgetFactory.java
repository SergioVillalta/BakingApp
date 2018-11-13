package com.example.android.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

public class BakingWidgetFactory implements RemoteViewsService.RemoteViewsFactory {
    private Recipe mSelectedRecipe;
    private Context context = null;
    private int appWidgetId;

    public BakingWidgetFactory(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        populateListItem();
    }

    private void populateListItem() {
        if(RecipeDetailFragment.mSelectedRecipe != null) {
            mSelectedRecipe = RecipeDetailFragment.mSelectedRecipe;
        }

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        populateListItem();
    }

    @Override
    public void onDestroy() {
        mSelectedRecipe = null;
    }

    @Override
    public int getCount() {
        return mSelectedRecipe != null ? mSelectedRecipe.getIngredients().size() : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /*
     *Similar to getView of Adapter where instead of View
     *we return RemoteViews
     *
     */
    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.baking_widgwet_list_row);
        if(mSelectedRecipe!= null){
            if(mSelectedRecipe.getIngredients().size() > 0){
                Ingredient ingredient = mSelectedRecipe.getIngredients().get(position);
                remoteView.setTextViewText(R.id.tv_widget_quantity, String.valueOf(ingredient.getQuantity()));
                remoteView.setTextViewText(R.id.tv_widget_ingredient, ingredient.getName());
                remoteView.setTextViewText(R.id.tv_widget_measure, ingredient.getMeasure());

                Intent fillInIntent = new Intent();
                fillInIntent.putExtra(BakingWidgetProvider.EXTRA_LABEL, mSelectedRecipe.getName());
                remoteView.setOnClickFillInIntent(R.id.widgetItemContainer, fillInIntent);
            }
        }

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

}
