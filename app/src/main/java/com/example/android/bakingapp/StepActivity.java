package com.example.android.bakingapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepActivity extends AppCompatActivity {
    private static final String TAG = StepActivity.class.getSimpleName();
//    private static final String SELECTED_STEP_KEY = "selected_step";
//    private final String STATE_RESUME_WINDOW = "resumeWindow";
//    private final String STATE_RESUME_POSITION = "resumePosition";
//    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";
//    @BindView(R.id.tv_step_description)
//    TextView mStepDescriptionTextView;
//    @BindView(R.id.tv_step_name)
//    TextView mStepNameTextView;
//    @BindView(R.id.btn_next_step)
//    Button mNextStepButton;
//    @BindView(R.id.btn_prev_step)
//    Button mPreviousStepButton;
//    /*------------------EXO PLAYER---------------------*/
//    @BindView(R.id.ep_step_player)
//    PlayerView mPlayerView;
//    @BindView(R.id.tv_no_media_available)
//    TextView mNoMediaTextView;
//
//    private Step mSelectedStep;
//    private SimpleExoPlayer mExoPlayer;
//
//    private MediaSource mVideoSource;
//    private boolean mExoPlayerFullscreen = false;
//    private FrameLayout mFullScreenButton;
//    private ImageView mFullScreenIcon;
//    private Dialog mFullScreenDialog;
//
//    private int mResumeWindow;
//    private long mResumePosition;
//    /*------------------EXO PLAYER---------------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
//        Intent originIntent = getIntent();
//        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        if (savedInstanceState != null) {
//            mResumeWindow = savedInstanceState.getInt(STATE_RESUME_WINDOW);
//            mResumePosition = savedInstanceState.getLong(STATE_RESUME_POSITION);
//            mExoPlayerFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN);
//            if(savedInstanceState.containsKey(SELECTED_STEP_KEY)) {
//                mSelectedStep = (Step) savedInstanceState.getSerializable(SELECTED_STEP_KEY);
//            }
//        }
//        else {
//            if (originIntent.hasExtra(RecipeDetailFragment.STEP_SELECTED_KEY)) {
//                Bundle bundle = originIntent.getBundleExtra(RecipeDetailFragment.STEP_SELECTED_KEY);
//                mSelectedStep = (Step) bundle.getSerializable(RecipeDetailFragment.STEP_SELECTED_KEY);
//            }
//        }
//        mNextStepButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onNextStepClick(view);
//            }
//        });
//        mPreviousStepButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onPreviousStepClick(view);
//            }
//        });
//
//
//
//        if (mSelectedStep != null) {
//            setStepDescription();
//        }
    }
//
//    private void setStepDescription() {
//        mStepDescriptionTextView.setText(mSelectedStep.getDescription());
//        mStepNameTextView.setText(mSelectedStep.getShortDescription());
//
//    }
//
//
//    private MediaSource buildMediaSource(Uri uri) {
//        String userAgent = Util.getUserAgent(StepActivity.this, getApplicationContext().getApplicationInfo().packageName);
//        DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory(userAgent, null, DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS, DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS, true);
//        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(StepActivity.this, null, httpDataSourceFactory);
//        return new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
//
//    }
//
//    public void onNextStepClick(View view) {
//        Step nextStep = RecipeDetailFragment.mStepsAdapter.getNextStep(mSelectedStep.getId());
//        if (nextStep != null) {
//            mSelectedStep = nextStep;
//            releasePlayer();
//            setStepDescription();
//            resumeExoPlayer();
//
//        }
//    }
//
//    public void onPreviousStepClick(View view) {
//        Step previousStep = RecipeDetailFragment.mStepsAdapter.getPreviousStep(mSelectedStep.getId());
//        if (previousStep != null) {
//            mSelectedStep = previousStep;
//            releasePlayer();
//            setStepDescription();
//            resumeExoPlayer();
//        }
//    }
//
//    /**
//     * Release ExoPlayer.
//     */
//    private void releasePlayer() {
//        if (mExoPlayer != null) {
//
//            mExoPlayer.release();
//            mExoPlayer = null;
//        }
//    }
//
//    private void initFullscreenDialog() {
//
//        mFullScreenDialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
//            public void onBackPressed() {
//                if (mExoPlayerFullscreen)
//                    closeFullscreenDialog();
//                super.onBackPressed();
//            }
//        };
//    }
//
//
//    private void openFullscreenDialog() {
//
//        ((ViewGroup) mPlayerView.getParent()).removeView(mPlayerView);
//        mFullScreenDialog.addContentView(mPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(StepActivity.this, R.drawable.ic_fullscreen_skrink));
//        mExoPlayerFullscreen = true;
//        mFullScreenDialog.show();
//    }
//
//
//    private void closeFullscreenDialog() {
//
//        ((ViewGroup) mPlayerView.getParent()).removeView(mPlayerView);
//        ((FrameLayout) findViewById(R.id.main_media_frame)).addView(mPlayerView);
//        mExoPlayerFullscreen = false;
//        mFullScreenDialog.dismiss();
//        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(StepActivity.this, R.drawable.ic_fullscreen_expand));
//    }
//
//
//    private void initFullscreenButton() {
//
//        PlayerControlView controlView = mPlayerView.findViewById(R.id.exo_controller);
//        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
//        mFullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button);
//        mFullScreenButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!mExoPlayerFullscreen)
//                    openFullscreenDialog();
//                else
//                    closeFullscreenDialog();
//            }
//        });
//    }
//
//    private void initExoPlayer() {
//
//        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
//        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
//        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
//        LoadControl loadControl = new DefaultLoadControl();
//        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this), trackSelector, loadControl);
//        mPlayerView.setPlayer(player);
//
//        boolean haveResumePosition = mResumeWindow != C.INDEX_UNSET;
//
//        ((SimpleExoPlayer) mPlayerView.getPlayer()).prepare(mVideoSource);
//        if (haveResumePosition) {
//            mPlayerView.getPlayer().seekTo(mResumeWindow, mResumePosition);
//        }
//        mPlayerView.getPlayer().setPlayWhenReady(true);
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//
//        outState.putInt(STATE_RESUME_WINDOW, mResumeWindow);
//        outState.putLong(STATE_RESUME_POSITION, mResumePosition);
//        outState.putBoolean(STATE_PLAYER_FULLSCREEN, mExoPlayerFullscreen);
//        outState.putSerializable(SELECTED_STEP_KEY, mSelectedStep);
//        super.onSaveInstanceState(outState);
//    }
//
//    /**
//     * Release the player when the activity is destroyed.
//     */
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        releasePlayer();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        //hideSystemUi();
//        resumeExoPlayer();
//
//    }
//
//    private void resumeExoPlayer() {
//        mPlayerView.setVisibility(View.VISIBLE);
//        mNoMediaTextView.setVisibility(View.GONE);
//        if (mSelectedStep != null) {
//            if (!mSelectedStep.getVideoUrl().isEmpty()) {
//                try {
//                    URL videoURL = new URL(mSelectedStep.getVideoUrl());
//                } catch (MalformedURLException ex) {
//                    mPlayerView.setVisibility(View.GONE);
//                    mNoMediaTextView.setVisibility(View.VISIBLE);
//                    return;
//                }
//
//                if (mExoPlayer == null) {
//                    mPlayerView = (PlayerView) findViewById(R.id.ep_step_player);
//                    initFullscreenDialog();
//                    initFullscreenButton();
//                    mVideoSource = buildMediaSource(Uri.parse(mSelectedStep.getVideoUrl()));
//                }
//                initExoPlayer();
//                int orientation = getResources().getConfiguration().orientation;
//                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
//                    openFullscreenDialog();
//                } else {
//                    closeFullscreenDialog();
//                }
//
//                if (mExoPlayerFullscreen) {
//                    ((ViewGroup) mPlayerView.getParent()).removeView(mPlayerView);
//                    mFullScreenDialog.addContentView(mPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//                    mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(StepActivity.this, R.drawable.ic_fullscreen_skrink));
//                    mFullScreenDialog.show();
//                }
//            }
//            else {
//                mPlayerView.setVisibility(View.GONE);
//                mNoMediaTextView.setVisibility(View.VISIBLE);
//            }
//
//        }
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        if (Util.SDK_INT <= 23) {
//            releasePlayer();
//        }
//        if (mPlayerView != null && mPlayerView.getPlayer() != null) {
//            mResumeWindow = mPlayerView.getPlayer().getCurrentWindowIndex();
//            mResumePosition = Math.max(0, mPlayerView.getPlayer().getContentPosition());
//
//            mPlayerView.getPlayer().release();
//        }
//
//        if (mFullScreenDialog != null) {
//            mFullScreenDialog.dismiss();
//        }
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (Util.SDK_INT > 23) {
//            releasePlayer();
//        }
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            // Respond to the action bar's Up/Home button
//            case android.R.id.home:
//                NavUtils.navigateUpFromSameTask(this);
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

}
