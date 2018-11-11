package com.example.android.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesAdapterViewHolder> {

    private final RecipeOnClickHandler mClickHandler;
    private List<Recipe> mRecipesData;
    private Context viewHolderContext;

    public RecipesAdapter(RecipeOnClickHandler mClickHandler) {
        this.mClickHandler = mClickHandler;
    }

    public void setRecipesData(List<Recipe> recipesData) {
        mRecipesData = recipesData;
        notifyDataSetChanged();
    }

    public List<Recipe> getRecipesData(){        
        return mRecipesData;
    }
    @NonNull
    @Override
    public RecipesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutId = R.layout.recipes_cell_layout;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        viewHolderContext = context;
        View view = inflater.inflate(layoutId,viewGroup,shouldAttachToParentImmediately);
        return new RecipesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesAdapterViewHolder recipesAdapterViewHolder, int i) {
        Recipe recipe;
        recipe = mRecipesData.get(i);
        String recipeImage = recipe.getImage();
        if(recipeImage.isEmpty()){

                if(recipe.getName().equals(viewHolderContext.getString(R.string.str_nutella_pie))){
                    recipeImage = Recipe.BAKING_RECIPE_DEFAULT_NUTELLA_PIE_IMAGE_URL;
                }
                else if(recipe.getName().equals(viewHolderContext.getString(R.string.str_brownies))){
                    recipeImage = Recipe.BAKING_RECIPE_DEFAULT_BROWNIES_IMAGE_URL;
                }
                else if(recipe.getName().equals(viewHolderContext.getString(R.string.str_cheesecake))){
                    recipeImage = Recipe.BAKING_RECIPE_DEFAULT_CHEESECAKE_IMAGE_URL;
                }
                else if(recipe.getName().equals(viewHolderContext.getString(R.string.str_yellow_cake))){
                    recipeImage = Recipe.BAKING_RECIPE_DEFAULT_YELLOW_CAKE_IMAGE_URL;
                }
                else {
                    recipeImage = Recipe.BAKING_RECIPE_DEFAULT_IMAGE_URL;

            }


            URL posterUrl = null;
            try {
                posterUrl = new URL(recipeImage);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            //Use of Picasso library to set the ImageView
            Picasso.with(recipesAdapterViewHolder.itemView.getContext())
                    .load(posterUrl.toString())
                    .into(recipesAdapterViewHolder.mRecipeImageView);
        }

        recipesAdapterViewHolder.mRecipeNameTextView.setText(recipe.getName());
    }

    @Override
    public int getItemCount() {
        if (null == mRecipesData) return 0;
        return mRecipesData.size();
    }

    public interface RecipeOnClickHandler{
        void onRecipeClick(Recipe recipe);
    }

    public class RecipesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_recipe_name) TextView mRecipeNameTextView;
        @BindView(R.id.iv_recipe) ImageView mRecipeImageView;

        public RecipesAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Recipe recipeSelected = mRecipesData.get(adapterPosition);
            mClickHandler.onRecipeClick(recipeSelected);
        }
    }
}
