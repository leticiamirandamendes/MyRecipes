package com.example.android.myrecipes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.example.android.myrecipes.adapters.IngredientsAdapter;
import com.example.android.myrecipes.adapters.StepsAdapter;
import com.example.android.myrecipes.objects.Recipe;
import com.example.android.myrecipes.objects.Step;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ItemListActivity extends AppCompatActivity implements StepsAdapter.ListStepClickListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_ingredients)
    RecyclerView recyclerViewIngredients;

    @BindView(R.id.rv_steps)
    RecyclerView recyclerViewSteps;

    private boolean mTwoPane;
    private Recipe mRecipe;
    private static Step mStep;

    public static Step getmStep() {
        return mStep;
    }

    public static void setmStep(Step mStep) {
        ItemListActivity.mStep = mStep;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        ButterKnife.bind(this, this);
        mRecipe = MainActivity.getmRecipe();
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);
        toolbar.setTitle(mRecipe.getName());

        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
            FrameLayout container = findViewById(R.id.item_detail_container);
            getLayoutInflater().inflate(R.layout.item_detail, container, true);
        }

        setupRecyclerViewIngredients(recyclerViewIngredients);
        setupRecyclerViewSteps(recyclerViewSteps);
    }

    private void setupRecyclerViewIngredients(@NonNull RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(this);
        recyclerView.setAdapter(ingredientsAdapter);
        ingredientsAdapter.setIngredientsData(mRecipe.getIngredient());
    }

    private void setupRecyclerViewSteps(@NonNull RecyclerView recyclerView){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        StepsAdapter stepsAdapter = new StepsAdapter(this, this);
        recyclerView.setAdapter(stepsAdapter);
        stepsAdapter.setStepsData(mRecipe.getStep());
    }

    @Override
    public void onListStepClick(Step selectedStep) {
        mStep = selectedStep;
        if (mTwoPane) {
            ItemDetailFragment fragment = new ItemDetailFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, fragment).commit();

        } else {
            Intent detailIntent = new Intent(this, ItemDetailActivity.class);
            startActivity(detailIntent);
        }
    }
}
