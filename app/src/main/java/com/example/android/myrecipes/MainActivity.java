package com.example.android.myrecipes;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.android.myrecipes.adapters.RecipesAdapter;
import com.example.android.myrecipes.api.Builder;
import com.example.android.myrecipes.api.RecipesAPI;
import com.example.android.myrecipes.objects.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipesAdapter.ListItemClickListener{

    @BindView(R.id.rv_home)
    RecyclerView mRecyclerView;

    private static Recipe mRecipe;

    public static Recipe getmRecipe() {
        return mRecipe;
    }

    public static void setmRecipe(Recipe mRecipe) {
        MainActivity.mRecipe = mRecipe;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this, this);
        setmRecyclerView();
    }

    public void setmRecyclerView() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            GridLayoutManager mLayoutManager = new GridLayoutManager(this, 4);
            mRecyclerView.setLayoutManager(mLayoutManager);
        } else {
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
        }
        final RecipesAdapter recipesAdapter = new RecipesAdapter(this, this);
        mRecyclerView.setAdapter(recipesAdapter);
        RecipesAPI api = Builder.implementation();
        Call<List<Recipe>> recipes = api.getRecipe();
        recipes.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                List<Recipe> recipeList = response.body();
                recipesAdapter.setRecipeData(recipeList,getApplicationContext());
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @Nullable Throwable t) {
                Log.e("", "ERROW");
            }
        });
    }

    @Override
    public void onListItemClick(Recipe selectedRecipe) {
        mRecipe = selectedRecipe;
        Intent i = new Intent(MainActivity.this, ItemListActivity.class);
        startActivity(i);
    }
}
