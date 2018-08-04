package com.example.android.myrecipes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.myrecipes.objects.Ingredient;
import com.example.android.myrecipes.objects.Recipe;
import com.example.android.myrecipes.objects.Step;
import com.example.android.myrecipes.widget.UpdateRecipesService;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemDetailActivity extends AppCompatActivity {

    private static Step mStep;
    private static Recipe mRecipe;

    SimpleExoPlayerView videoViewStepTutorial;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.textViewStepDescription)
    TextView stepDescription;

    @BindView(R.id.imageButtonPlay)
    ImageButton playButton;

    @BindView(R.id.buttonPrevious)
    Button previous;

    @BindView(R.id.buttonNext)
    Button next;

    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        ButterKnife.bind(this, this);
        setSupportActionBar(toolbar);
        mRecipe = MainActivity.getmRecipe();
        mStep = ItemListActivity.getmStep();
        ArrayList<String> recipeIngredientsForWidgets= new ArrayList<>();
        for(Ingredient ingredient : mRecipe.getIngredient()){
            recipeIngredientsForWidgets.add(ingredient.getName());
        }
        UpdateRecipesService.startBakingService(this,recipeIngredientsForWidgets);
        stepDescription.setText(mStep.getDescription());
        final Uri myUri = Uri.parse(mStep.getVideoUrl());
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            setupExoplayer(myUri);
            playButton.setVisibility(View.GONE);
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = mStep.getId();
                for (Step step : mRecipe.getStep()) {
                    if (step.getId() == index - 1) {
                        ItemListActivity.setmStep(step);
                    }
                }
                Intent i = new Intent(ItemDetailActivity.this, ItemDetailActivity.class);
                startActivity(i);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = mStep.getId();
                for (Step step : mRecipe.getStep()) {
                    if (step.getId() == index + 1) {
                        ItemListActivity.setmStep(step);
                    }
                }
                Intent i = new Intent(ItemDetailActivity.this, ItemDetailActivity.class);
                startActivity(i);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, ItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setupExoplayer(Uri mediaUri){
        SimpleExoPlayer mPlayerView;
        videoViewStepTutorial = findViewById(R.id.videoViewStep);
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        mPlayerView = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
        videoViewStepTutorial.setPlayer(mPlayerView);
        String userAgent = Util.getUserAgent(this, "StepTutorial");
        MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                this, userAgent), new DefaultExtractorsFactory(), null, null);
        mPlayerView.prepare(mediaSource);
        mPlayerView.setPlayWhenReady(true);
    }
}
