package com.example.android.bakingapp;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class RecipeTest {

    private static final int NUTELLA_PIE_POSITION = 0;
    private static final int BROWNIES_POSITION = 1;
    private static final int BROWNIES_MEDIA_STEP_POSITION = 0;
    private static final String BROWNIES_MEDIA_STEP_TEXT = "Recipe Introduction";
    private static final int YELLOW_CAKE_POSITION = 2;
    private static final int CHEESECAKE_POSITION = 3;
    private static final int CHEESECAKE_NO_MEDIA_STEP_POSITION = 4;

    @Rule
    public ActivityTestRule<RecipesActivity> mActivityTestRule =
            new ActivityTestRule<>(RecipesActivity.class);

    @Test
    public void clickGridViewItem_OpensRecipeDetailActivity() {



        onView(withId(R.id.rv_baking_recipes))
                .perform(actionOnItemAtPosition(0, click()));

        onView(withText("Ingredients")).check(matches(withText("Ingredients")));

        onView(withId(R.id.ll_recipe_detail));
    }

    @Test
    public void clickNextStepAtPositionZero(){
        onView(withId(R.id.rv_baking_recipes))
                .perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.rv_recipe_steps))
                .perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.btn_next_step))
                .perform(click());

        onView(withId(R.id.tv_step_name)).check(matches(withText("Starting prep")));
    }

    @Test
    public void clickPreviousStepAtPositionZero(){
        onView(withId(R.id.rv_baking_recipes))
                .perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.rv_recipe_steps))
                .perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.btn_prev_step))
                .perform(click());

        onView(withId(R.id.tv_step_name)).check(matches(withText("Recipe Introduction")));
    }

    @Test
    public void noMediaAvailableForStep(){
        //Cheesecake
        onView(withId(R.id.rv_baking_recipes))
                .perform(actionOnItemAtPosition(CHEESECAKE_POSITION, click()));

        //offset 3
        onView(withId(R.id.rv_recipe_steps))
                .perform(RecyclerViewActions.scrollToPosition(CHEESECAKE_POSITION + 3));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.rv_recipe_steps)).perform(actionOnItemAtPosition(CHEESECAKE_NO_MEDIA_STEP_POSITION, click()));
        onView(withId(R.id.ep_step_player)).check(matches(not(isDisplayed())));
        onView(withId(R.id.tv_no_media_available)).check(matches(withText(mActivityTestRule.getActivity().getString(R.string.msg_no_media_available))));
    }

    @Test
    public void mediaAvailableForStep(){
        onView(withId(R.id.rv_baking_recipes))
                .perform(actionOnItemAtPosition(BROWNIES_POSITION, click()));

        onView(withId(R.id.rv_recipe_steps)).perform(actionOnItemAtPosition(BROWNIES_MEDIA_STEP_POSITION, click()));

        onView(withId(R.id.ep_step_player)).check(matches(isDisplayed()));
    }

    @Test
    public void goToFullscreenWhenLandscape(){
        onView(withId(R.id.rv_baking_recipes))
                .perform(actionOnItemAtPosition(BROWNIES_POSITION, click()));

        onView(withId(R.id.rv_recipe_steps)).perform(actionOnItemAtPosition(BROWNIES_MEDIA_STEP_POSITION, click()));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.ep_step_player)).check(matches(isDisplayed()));
        if(mActivityTestRule.getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            onView(withId(R.id.exo_fullscreen_icon)).check(matches(EspressoTestsMatchers.withDrawable(R.drawable.ic_fullscreen_skrink)));
        }
    }

    @Test
    public void returnFromFullscreenWhenPortrait(){
        goToFullscreenWhenLandscape();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(mActivityTestRule.getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            onView(withId(R.id.exo_fullscreen_icon)).check(matches(EspressoTestsMatchers.withDrawable(R.drawable.ic_fullscreen_expand)));
        }
    }

    @Test
    public void twoPaneWhenTablet(){
        boolean twoPaneLayout = mActivityTestRule.getActivity().getResources().getBoolean(R.bool.isTablet);
        if(twoPaneLayout){
            onView(withId(R.id.rv_baking_recipes))
                    .perform(actionOnItemAtPosition(0, click()));
            onView(withId(R.id.rv_recipe_steps)).check(matches(isDisplayed()));
            onView(withId(R.id.tl_recipe_ingredients)).check(matches(isDisplayed()));
            onView(withId(R.id.tv_step_description)).check(matches(isDisplayed()));
        }
    }

    @Test
    public void singlePaneWhenPhone(){
        boolean twoPaneLayout = mActivityTestRule.getActivity().getResources().getBoolean(R.bool.isTablet);
        if(!twoPaneLayout){
            onView(withId(R.id.rv_baking_recipes))
                    .perform(actionOnItemAtPosition(0, click()));
            onView(withId(R.id.rv_recipe_steps)).check(matches(isDisplayed()));
            onView(withId(R.id.tl_recipe_ingredients)).check(matches(isDisplayed()));
            onView(withId(R.id.tv_step_description)).check(doesNotExist());
        }
    }

}
