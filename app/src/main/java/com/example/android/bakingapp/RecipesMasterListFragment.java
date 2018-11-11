package com.example.android.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.utils.InternetCheck;
import com.example.android.bakingapp.utils.RecipesUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipesMasterListFragment extends Fragment implements RecipesAdapter.RecipeOnClickHandler {

    private static final String RECIPES_RECYCLER_VIEW_OFFSET_KEY = "recipes_recycler_view_offset";
    public static final String RECIPE_SELECTED_KEY = "recipe_selected";
    private String mErrorMessage;

    private List<Recipe> bakingRecipes;

    @BindView(R.id.tv_error_message_display)
    TextView mErrorMessageDisplay;

    @BindView(R.id.pb_loading_indicator)
    ProgressBar mLoadingIndicator;

    @BindView(R.id.rv_baking_recipes)
    RecyclerView mRecyclerView;

    public static RecipesAdapter mRecipesAdapter;

    private int mRecipesRecyclerViewOffset;

    private OnRecipeClickListener mListener;

    public RecipesMasterListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_recipes_master_list, container, false);
        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);

        GridLayoutManager layoutManager;
        ButterKnife.bind(this, rootView);
        if (tabletSize) {
            layoutManager = new GridLayoutManager(rootView.getContext(), 3);
        } else {
            layoutManager = new GridLayoutManager(rootView.getContext(), 1);
        }

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecipesAdapter = new RecipesAdapter(this);
        mRecyclerView.setAdapter(mRecipesAdapter);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(RECIPES_RECYCLER_VIEW_OFFSET_KEY)) {
                mRecipesRecyclerViewOffset = savedInstanceState.getInt(RECIPES_RECYCLER_VIEW_OFFSET_KEY);
            }
        }

        loadRecipes();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        BakingWidgetService.startActionUpdateBakingWidget(getContext());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnRecipeClickListener {
        void onRecipeSelected(int position);
    }

    private void loadRecipes(){
        mRecipesAdapter.setRecipesData(null);
        new InternetCheck(new InternetCheck.Consumer() {
            @Override
            public void afterInternetCheck(Boolean internet) {
                if (internet) {
                    showRecipesView();
                    //Executing async call to obtain data from TMDB API
                    new FetchRecipesTask().execute();
                } else {
                    mErrorMessageDisplay.setText(R.string.msg_no_internet_connection);
                    showErrorMessage();
                }
            }
        });
    }

    private void showRecipesView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    public static List<Recipe> getImageNameRecipes(Context context){
        List<Recipe> recipes = new ArrayList<>();
        if(mRecipesAdapter!=null){
            List<Recipe> currentRecipes = mRecipesAdapter.getRecipesData();
            if(currentRecipes!=null) {
                for (Recipe recipe :
                        currentRecipes) {
                    Recipe newRecipe = new Recipe();
                    newRecipe.setId(recipe.getId());
                    newRecipe.setName(recipe.getName());
                    String recipeImage;
                    if (recipe.getImage().isEmpty()) {
                        if (recipe.getName().equals(context.getString(R.string.str_nutella_pie))) {
                            recipeImage = Recipe.BAKING_RECIPE_DEFAULT_NUTELLA_PIE_IMAGE_URL;
                        } else if (recipe.getName().equals(context.getString(R.string.str_brownies))) {
                            recipeImage = Recipe.BAKING_RECIPE_DEFAULT_BROWNIES_IMAGE_URL;
                        } else if (recipe.getName().equals(context.getString(R.string.str_cheesecake))) {
                            recipeImage = Recipe.BAKING_RECIPE_DEFAULT_CHEESECAKE_IMAGE_URL;
                        } else if (recipe.getName().equals(context.getString(R.string.str_yellow_cake))) {
                            recipeImage = Recipe.BAKING_RECIPE_DEFAULT_YELLOW_CAKE_IMAGE_URL;
                        } else {
                            recipeImage = Recipe.BAKING_RECIPE_DEFAULT_IMAGE_URL;
                        }
                    } else {
                        recipeImage = recipe.getImage();
                    }
                    newRecipe.setImage(recipeImage);
                    recipes.add(newRecipe);
                }
            }
        }

        return recipes;
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        Intent recipeDetailIntent = new Intent(this.getContext(),RecipeDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(RECIPE_SELECTED_KEY,recipe);
        recipeDetailIntent.putExtra(RECIPE_SELECTED_KEY,bundle);
        startActivity(recipeDetailIntent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        int recyclerViewOffset = mRecyclerView.computeVerticalScrollOffset();
        outState.putInt(RECIPES_RECYCLER_VIEW_OFFSET_KEY, recyclerViewOffset);
    }

    public class FetchRecipesTask extends AsyncTask<Void, Void, List<Recipe>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Recipe> doInBackground(Void... params) {

            try {
                List<Recipe> recipes = RecipesUtils.getRecipes();
                return recipes;

            } catch (Exception e) {
                e.printStackTrace();
                mErrorMessage = e.getMessage();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Recipe> recipesData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (recipesData != null) {
                showRecipesView();
                mRecipesAdapter.setRecipesData(recipesData);
                mRecyclerView.scrollBy(0,mRecipesRecyclerViewOffset);
                BakingWidgetService.startActionUpdateBakingWidget(getContext());
            } else {
                showErrorMessage();
                mErrorMessageDisplay.setText("There was an error retrieving the information. " + mErrorMessage);
            }
        }
    }
}
