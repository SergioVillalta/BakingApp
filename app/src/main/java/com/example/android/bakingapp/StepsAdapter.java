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

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsAdapterViewHolder>  implements Serializable {

    private final StepOnClickHandler mClickHandler;
    private List<Step> mStepsData;
    private Context viewHolderContext;

    public StepsAdapter(StepOnClickHandler mClickHandler) {
        this.mClickHandler = mClickHandler;
    }

    public void setStepsData(List<Step> stepsData) {
        mStepsData = stepsData;
        notifyDataSetChanged();
    }

    public List<Step> getStepsData(){        
        return mStepsData;
    }

    public Step getNextStep(int currentStepId){
        if(currentStepId <  getItemCount() - 1) {
            return mStepsData.get(currentStepId + 1);
        }
        return null;
    }

    public Step getPreviousStep(int currentStepId){
        if(currentStepId > 0) {
            return mStepsData.get(currentStepId - 1);
        }
        return null;
    }

    @NonNull
    @Override
    public StepsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutId = R.layout.step_cell_layout;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        viewHolderContext = context;
        View view = inflater.inflate(layoutId,viewGroup,shouldAttachToParentImmediately);
        return new StepsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsAdapterViewHolder stepsAdapterViewHolder, int i) {
        Step step;
        step = mStepsData.get(i);
        String stepImage = step.getThumbnailUrl();
        if(!stepImage.isEmpty()){

            URL imageUrl = null;
            try {
                imageUrl = new URL(stepImage);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            //Use of Picasso library to set the ImageView
            Picasso.with(stepsAdapterViewHolder.itemView.getContext())
                    .load(imageUrl.toString())
                    .into(stepsAdapterViewHolder.mStepImageView);
        }
        else{
            stepsAdapterViewHolder.mStepImageView.setImageResource(R.drawable.ic_cake_black_24dp);
        }
        stepsAdapterViewHolder.mStepNumberTextView.setText(String.valueOf((step.getId())+1));
        stepsAdapterViewHolder.mStepNameTextView.setText(step.getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (null == mStepsData) return 0;
        return mStepsData.size();
    }

    public interface StepOnClickHandler{
        void onStepClick(Step step);
    }

    public class StepsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_step_number) TextView mStepNumberTextView;
        @BindView(R.id.tv_step_name) TextView mStepNameTextView;
        @BindView(R.id.iv_step_image) ImageView mStepImageView;

        public StepsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Step stepSelected = mStepsData.get(adapterPosition);
            mClickHandler.onStepClick(stepSelected);
        }
    }
}
