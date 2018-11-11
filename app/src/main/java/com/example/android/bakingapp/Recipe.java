package com.example.android.bakingapp;

import android.content.res.Resources;
import com.example.android.bakingapp.R;

import java.io.Serializable;
import java.util.List;

public class Recipe implements Serializable {
    public static final String BAKING_RECIPE_DEFAULT_IMAGE_URL = "https://source.unsplash.com/MkFTAO4lROs";
    public static final String BAKING_RECIPE_DEFAULT_NUTELLA_PIE_IMAGE_URL = "https://source.unsplash.com/PqYvDBwpXpU";
    public static final String BAKING_RECIPE_DEFAULT_BROWNIES_IMAGE_URL = "https://source.unsplash.com/h1CQFYfo5lk";
    public static final String BAKING_RECIPE_DEFAULT_CHEESECAKE_IMAGE_URL = "https://source.unsplash.com/s8_7AqkzCWY";
    public static final String BAKING_RECIPE_DEFAULT_YELLOW_CAKE_IMAGE_URL = "https://source.unsplash.com/rotFzR9lX0E";

    private int mId;

    private String mName;

    private int mServings;

    private List<Ingredient> mIngredients;

    private List<Step> mSteps;

    private String mImage;

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public int getServings() {
        return mServings;
    }

    public List<Ingredient> getIngredients() {
        return mIngredients;
    }

    public List<Step> getSteps() {
        return mSteps;
    }

    public String getImage() {

        return mImage;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public void setIngredients(List<Ingredient> mIngredients) {
        this.mIngredients = mIngredients;
    }

    public void setSteps(List<Step> mSteps) {
        this.mSteps = mSteps;
    }

    public void setServings(int mServings) {
        this.mServings = mServings;
    }

    public void setImage(String image) {
        this.mImage = image;
    }
}
