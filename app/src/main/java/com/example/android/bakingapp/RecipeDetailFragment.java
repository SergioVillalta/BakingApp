package com.example.android.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailFragment extends Fragment implements StepsAdapter.StepOnClickHandler{
    public static final String STEP_SELECTED_KEY = "step_selected";
    public static final String PARENT_STEPS_ADAPTER_KEY = "parent_steps_adapter";
    private static final String SELECTED_RECIPE_KEY = "selected_recipe";


    public static Recipe mSelectedRecipe;

    @BindView(R.id.rv_recipe_steps)
    RecyclerView mStepsRecyclerView;

    @BindView(R.id.tl_recipe_ingredients)
    TableLayout mIngredientsTableLayout;

    public static StepsAdapter mStepsAdapter;
    private boolean twoPaneLayout = false;

    private OnStepClickListener mListener;

    public RecipeDetailFragment(){

    }

    public interface OnStepClickListener {
        void onStepSelected(Step step);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        ButterKnife.bind(this, rootView);
        twoPaneLayout = getResources().getBoolean(R.bool.isTablet);
        LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext(),LinearLayoutManager.VERTICAL,false);
        mStepsRecyclerView.setLayoutManager(layoutManager);
        mStepsRecyclerView.setHasFixedSize(true);
        mStepsAdapter = new StepsAdapter(this);
        mStepsRecyclerView.setAdapter(mStepsAdapter);

        RecipeDetailActivity recipeDetailActivity = (RecipeDetailActivity) getActivity();
        recipeDetailActivity.setStepsAdapter(mStepsAdapter);

        Intent originIntent = recipeDetailActivity.getIntent();
        ButterKnife.bind(this, rootView);
        if(originIntent.hasExtra(RecipesMasterListFragment.RECIPE_SELECTED_KEY)){
            Bundle bundle = originIntent.getBundleExtra(RecipesMasterListFragment.RECIPE_SELECTED_KEY);
            mSelectedRecipe = (Recipe) bundle.getSerializable(RecipesMasterListFragment.RECIPE_SELECTED_KEY);
        }

        loadIngredients();
        loadSteps();
        mStepsRecyclerView.setFocusable(false);


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        BakingWidgetUpdateService.startActionUpdateBakingWidget(getContext());
        BakingWidgetProvider.sendRefreshBroadcast(getContext());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStepClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onStepClick(Step step) {
        if(!twoPaneLayout){
            Intent stepIntent = new Intent(this.getContext(),StepActivity.class);
            Bundle stepSelectedBundle = new Bundle();
            stepSelectedBundle.putSerializable(STEP_SELECTED_KEY,step);
            stepIntent.putExtra(STEP_SELECTED_KEY,stepSelectedBundle);

            startActivity(stepIntent);
        }
        else{
            mListener.onStepSelected(step);
        }

    }

    private void loadIngredients() {
        if(mSelectedRecipe != null){
            for (Ingredient ingredient :
                    mSelectedRecipe.getIngredients()) {
                TableRow ingredientRow = new TableRow(this.getContext());
                ingredientRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                TextView quantityTextView = new TextView(this.getContext());
                //quantityTextView.setHeight(TableLayout.LayoutParams.WRAP_CONTENT);
                quantityTextView.setGravity(Gravity.RIGHT);
                quantityTextView.setPadding(8,8,8,8);
                quantityTextView.setWidth(TableLayout.LayoutParams.WRAP_CONTENT);
                quantityTextView.setText(String.valueOf(ingredient.getQuantity()));

                TextView measureTextView = new TextView(this.getContext());
                //measureTextView.setHeight(TableLayout.LayoutParams.WRAP_CONTENT);
                measureTextView.setGravity(Gravity.CENTER);
                measureTextView.setPadding(8,8,8,8);
                measureTextView.setWidth(TableLayout.LayoutParams.WRAP_CONTENT);
                measureTextView.setText(ingredient.getMeasure());

                TextView nameTextView = new TextView(this.getContext());
                //nameTextView.setHeight(TableLayout.LayoutParams.WRAP_CONTENT);
                nameTextView.setPadding(8,8,8,8);
                nameTextView.setWidth(TableLayout.LayoutParams.WRAP_CONTENT);
                nameTextView.setText(ingredient.getName());

                ingredientRow.addView(quantityTextView);
                ingredientRow.addView(measureTextView);
                ingredientRow.addView(nameTextView);
                mIngredientsTableLayout.addView(ingredientRow);
            }
        }
    }

    private void loadSteps() {
        if(mSelectedRecipe != null){
            mStepsAdapter.setStepsData(mSelectedRecipe.getSteps());
        }
    }


}
