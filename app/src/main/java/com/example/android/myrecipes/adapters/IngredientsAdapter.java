package com.example.android.myrecipes.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.myrecipes.R;
import com.example.android.myrecipes.objects.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder>{


    private List<Ingredient> mIngredientsList;
    private final LayoutInflater mInflater;

    public IngredientsAdapter(Context context) {
        this.mIngredientsList = new ArrayList<>();
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.ingredients, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        Ingredient ingredient = mIngredientsList.get(position);
        holder.nameIn.setText(ingredient.getName());
        holder.quantity.setText(ingredient.getQuantity());
        holder.measure.setText(ingredient.getMeasure());
    }

    @Override
    public int getItemCount() {
            return mIngredientsList != null ? mIngredientsList.size() : 0;
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder{

        private final TextView nameIn;
        private final TextView quantity;
        private final TextView measure;

        private IngredientViewHolder(View itemView) {
            super(itemView);
            nameIn = itemView.findViewById(R.id.textViewNameIn);
            quantity = itemView.findViewById(R.id.textViewQuantity);
            measure = itemView.findViewById(R.id.textViewMeasure);
        }

    }

    public void setIngredientsData(List<Ingredient> ingredientsIn) {
        mIngredientsList = ingredientsIn;
        notifyDataSetChanged();
    }

}
