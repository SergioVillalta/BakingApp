package com.example.android.bakingapp;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailFragment.OnStepClickListener {



    private StepsAdapter mStepsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void setStepsAdapter(StepsAdapter stepsAdapter){
        mStepsAdapter = stepsAdapter;
    }

    public StepsAdapter getStepsAdapter(){
        return mStepsAdapter;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onStepSelected(Step step) {
        StepFragment stepFragment = ((StepFragment) getSupportFragmentManager().findFragmentById(R.id.step_detail_fragment));
        if(stepFragment != null){
            stepFragment.setSelectedStep(step);
            stepFragment.setStepDescription();
            stepFragment.resumeExoPlayer();
        }
    }
}
