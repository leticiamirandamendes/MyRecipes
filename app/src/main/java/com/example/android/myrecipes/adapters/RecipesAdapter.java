package com.example.android.myrecipes.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.myrecipes.R;
import com.example.android.myrecipes.objects.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {

    private List<Recipe> mRecipeList;
    private final LayoutInflater mInflater;
    private Context mContext;
    private static ListItemClickListener mOnClickListener;

    public RecipesAdapter(Context context, ListItemClickListener listener) {
        this.mRecipeList = new ArrayList<>();
        mOnClickListener = listener;
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecipesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipesAdapter.ViewHolder holder, int position) {
        Recipe recipe = mRecipeList.get(position);
        holder.textView.setText(recipe.getName());
        holder.portion.setText(recipe.getServings().toString());
        holder.portion.setCompoundDrawablesWithIntrinsicBounds(R.drawable.plate, 0, 0, 0);

        int image;
        switch (recipe.getId()){
            case 1:
                image = R.drawable.nutella_pie;
                break;
            case 2:
                image = R.drawable.brownie;
                break;
            case 3:
                image = R.drawable.yellow_cake;
                break;
            case 4:
                image = R.drawable.cheesecake;
                break;
            default:
                image = R.drawable.recipe_example;
                break;
        }
        Picasso.with(mContext)
                .load(image)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mRecipeList != null ? mRecipeList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final ImageView imageView;
        private final TextView textView;
        private final TextView portion;

        private ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.recipe_title);
            imageView = itemView.findViewById(R.id.recipe_picture);
            portion = itemView.findViewById(R.id.portion);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            Recipe selectedRecipe = mRecipeList.get(clickedPosition);
            mOnClickListener.onListItemClick(selectedRecipe);

        }
    }
    public void setRecipeData(List<Recipe> recipesIn, Context context) {
        mRecipeList = recipesIn;
        mContext=context;
        notifyDataSetChanged();
    }

    public interface ListItemClickListener{
        void onListItemClick(Recipe selectedRecipe);
    }

}