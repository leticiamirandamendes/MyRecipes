package com.example.android.myrecipes.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.myrecipes.R;
import com.example.android.myrecipes.objects.Step;

import java.util.ArrayList;
import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepViewHolder>{

    private List<Step> mStepsList;
    private final LayoutInflater mInflater;
    private static ListStepClickListener mOnClickListener;

        public StepsAdapter(Context context, ListStepClickListener listener) {
        this.mStepsList = new ArrayList<>();
        mOnClickListener = listener;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.step, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        Step step = mStepsList.get(position);
        holder.shortDescription.setText(step.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return mStepsList != null ? mStepsList.size() : 0;
    }

    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView shortDescription;

        private StepViewHolder(View itemView) {
            super(itemView);
            shortDescription = itemView.findViewById(R.id.textViewNameIn);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            Step step = mStepsList.get(clickedPosition);
            mOnClickListener.onListStepClick(step);

        }
    }
    public void setStepsData(List<Step> stepsIn) {
        mStepsList = stepsIn;
        notifyDataSetChanged();
    }

    public interface ListStepClickListener{
        void onListStepClick(Step selectedStep);
    }
}
