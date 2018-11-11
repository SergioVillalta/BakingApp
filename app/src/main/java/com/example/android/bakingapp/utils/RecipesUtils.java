package com.example.android.bakingapp.utils;

import android.net.Uri;
import android.util.Log;

import com.example.android.bakingapp.Ingredient;
import com.example.android.bakingapp.Recipe;
import com.example.android.bakingapp.Step;

import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RecipesUtils {
    private static final String TAG = RecipesUtils.class.getSimpleName();

    private static final String BAKING_RECIPES_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    private final static String BAKING_RECIPES_RESULTS_ATTR = "results";
    private final static String BAKING_RECIPES_ID_ATTR = "id";
    private final static String BAKING_RECIPES_NAME_ATTR = "name";
    private final static String BAKING_RECIPES_INGREDIENTS_ATTR = "ingredients";
    private final static String BAKING_RECIPES_QUANTITY_ATTR = "quantity";
    private final static String BAKING_RECIPES_MEASURE_ATTR = "measure";
    private final static String BAKING_RECIPES_INGREDIENT_ATTR = "ingredient";
    private final static String BAKING_RECIPES_STEPS_ATTR = "steps";
    private final static String BAKING_RECIPES_STEP_ID_ATTR = "id";
    private final static String BAKING_RECIPES_SHORT_DESC_ATTR = "shortDescription";
    private final static String BAKING_RECIPES_DESC_ATTR = "description";
    private final static String BAKING_RECIPES_VIDEO_URL_ATTR = "videoURL";
    private final static String BAKING_RECIPES_THUMBNAIL_URL_ATTR = "thumbnailURL";
    private final static String BAKING_RECIPES_SERVINGS_ATTR = "servings";
    private final static String BAKING_RECIPES_IMAGE_ATTR = "image";

    private static String mUrl = BAKING_RECIPES_URL;

    public static List<Recipe> getRecipes() throws Exception {
        if (mUrl == null) {
            throw new Exception("No URL has been set");
        }
        List<Recipe> recipes = new ArrayList<>();
        URL url = buildRecipesUrl();
        String response = NetworkUtils.getResponseFromHttpUrl(url);
        recipes = translateJsonToRecipes(response);

        return recipes;
    }

    //Method to convert a JSON result into a Movie object
    private static List<Recipe> translateJsonToRecipes(String jsonString) throws JSONException {
        List<Recipe> recipes = new ArrayList<>();
        try {

            JSONArray jsonResults = new JSONArray(jsonString);

            for (int i = 0; i < jsonResults.length(); i++) {
                JSONObject result = jsonResults.getJSONObject(i);
                Recipe recipe = new Recipe();
                recipe.setId(result.getInt(BAKING_RECIPES_ID_ATTR));
                recipe.setName(result.getString(BAKING_RECIPES_NAME_ATTR));
                recipe.setImage(result.getString(BAKING_RECIPES_IMAGE_ATTR));
                recipe.setServings(result.getInt(BAKING_RECIPES_SERVINGS_ATTR));

                JSONArray jsonIngredients = result.getJSONArray(BAKING_RECIPES_INGREDIENTS_ATTR);
                List <Ingredient> ingredients = new ArrayList<>();
                for(int j = 0; j<jsonIngredients.length(); j++){
                    JSONObject ingredientResult = jsonIngredients.getJSONObject(j);
                    Ingredient ingredient = new Ingredient();
                    ingredient.setQuantity(ingredientResult.getInt(BAKING_RECIPES_QUANTITY_ATTR));
                    ingredient.setMeasure(ingredientResult.getString(BAKING_RECIPES_MEASURE_ATTR));
                    ingredient.setName(ingredientResult.getString(BAKING_RECIPES_INGREDIENT_ATTR));
                    ingredients.add(ingredient);
                }
                recipe.setIngredients(ingredients);

                JSONArray jsonSteps = result.getJSONArray(BAKING_RECIPES_STEPS_ATTR);
                List<Step> steps = new ArrayList<>();

                for(int j = 0; j<jsonSteps.length(); j++){
                    JSONObject stepResult = jsonSteps.getJSONObject(j);
                    Step step = new Step();
                    step.setId(stepResult.getInt(BAKING_RECIPES_STEP_ID_ATTR));
                    step.setShortDescription(stepResult.getString(BAKING_RECIPES_SHORT_DESC_ATTR));
                    step.setDescription(stepResult.getString(BAKING_RECIPES_DESC_ATTR));
                    step.setVideoUrl(stepResult.getString(BAKING_RECIPES_VIDEO_URL_ATTR));
                    step.setThumbnailUrl(stepResult.getString(BAKING_RECIPES_THUMBNAIL_URL_ATTR));

                    steps.add(step);
                }
                recipe.setSteps(steps);
                recipes.add(recipe);
            }

            return recipes;

        } catch (JSONException e) {
            throw e;
        }

    }

    //Method to build the URL before the Request
    private static URL buildRecipesUrl() throws Exception {
        String param = "";

        mUrl = BAKING_RECIPES_URL;
        Uri builtUri = Uri.parse(mUrl).buildUpon()
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

}
