package com.example.android.myrecipes;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.myrecipes.objects.Recipe;
import com.example.android.myrecipes.objects.Step;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemDetailFragment extends Fragment {

    @BindView(R.id.textViewStepDescription)
    TextView stepDescription;

    @BindView(R.id.imageButtonPlay)
    ImageButton playButton;

    @BindView(R.id.buttonPrevious)
    Button previous;

    @BindView(R.id.buttonNext)
    Button next;

    SimpleExoPlayerView videoViewStepTutorial;
    private int index;
    private Recipe mRecipe;
    ViewGroup placeholder;
    private Step mStep;

    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this, getActivity());
        mRecipe = MainActivity.getmRecipe();
        mStep = ItemListActivity.getmStep();
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
                getLayoutInflater().inflate(R.layout.item_detail, placeholder, true);
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
                getLayoutInflater().inflate(R.layout.item_detail, placeholder, true);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);
        placeholder = container;
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        MainActivity.setmRecipe(mRecipe);
        ItemListActivity.setmStep(mStep);
    }

    public void setupExoplayer(Uri mediaUri){
        SimpleExoPlayer mPlayerView;
        videoViewStepTutorial = getActivity().findViewById(R.id.videoViewStep);
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        mPlayerView = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
        videoViewStepTutorial.setPlayer(mPlayerView);
        String userAgent = Util.getUserAgent(getContext(), "StepTutorial");
        MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
        mPlayerView.prepare(mediaSource);
        mPlayerView.setPlayWhenReady(true);
    }
}
