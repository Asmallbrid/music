/*
 * Copyright 2018-present KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yt.yunxiaoweimusic.ui.view;

import android.animation.ArgbEvaluator;
import android.animation.FloatEvaluator;
import android.animation.IntEvaluator;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import com.kunminx.architecture.ui.state.State;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.yt.yunxiaoweimusic.databinding.FragmentPlayBinding;
import com.yt.yunxiaoweimusic.ui.base.StateHolder;
import com.yt.yunxiaoweimusic.utils.DisplayUtils;
import com.yt.yunxiaoweimusic.utils.ScreenUtils;

public class PlayerSlideListener implements SlidingUpPanelLayout.PanelSlideListener {

    private final SlideAnimatorStates mStates;

    private int mArtistNormalEndTranslationY;
    private int mSummaryContentPadding;
    private int mContentNormalEndTranslationX;
    private int mContentNormalEndTranslationY;
    private int mSongTxtSize;
    private int mSingTxtSize;

    private final int mPlayPauseStartX;
    private final int mPlayQueueStartX;
    private final int mPlayPauseEndX;
    private final int mPreviousEndX;
    private final int mModeEndX;
    private final int mNextEndX;
    private final int mPlayQueueEndX;
    private final int mIconContainerStartY;
    private int mIconContainerEndY;

    private final int SCREEN_WIDTH;
    private final int SCREEN_HEIGHT;

    private final IntEvaluator INT_EVALUATOR = new IntEvaluator();
    private final FloatEvaluator FLOAT_EVALUATOR = new FloatEvaluator();
    private final ArgbEvaluator COLOR_EVALUATOR = new ArgbEvaluator();

    private final int NOW_PLAYING_CARD_COLOR;
    private final int PLAY_PAUSE_DRAWABLE_COLOR;
    private Status mStatus = Status.COLLAPSED;

    public enum Status {
        EXPANDED,
        COLLAPSED,
    }

    public PlayerSlideListener(SlideAnimatorStates states) {
        mStates = states;
        SCREEN_WIDTH = ScreenUtils.getScreenWidth();
        SCREEN_HEIGHT = ScreenUtils.getScreenHeight();
        PLAY_PAUSE_DRAWABLE_COLOR = Color.BLACK;
        NOW_PLAYING_CARD_COLOR = Color.WHITE;
        calculateTitleAndArtist();
        mPlayPauseStartX = 415;
        mPlayQueueStartX = 512;
        int size = DisplayUtils.dp2px(36);
        int gap = (SCREEN_WIDTH - 5 * (size)) / 6;
        mPlayPauseEndX = (SCREEN_WIDTH / 2) - (size / 2);
        mPreviousEndX = mPlayPauseEndX - gap - size;
        mModeEndX = mPreviousEndX - gap - size;
        mNextEndX = mPlayPauseEndX + gap + size;
        mPlayQueueEndX = mNextEndX + gap + size;
        mIconContainerStartY = 20;
        int tempImgSize = DisplayUtils.dp2px(55);
        mStates.albumArtSize.set(new Pair<>(tempImgSize, tempImgSize));
        mIconContainerEndY = 20;
        mStates.playPauseDrawableColor.set(PLAY_PAUSE_DRAWABLE_COLOR);
        mStates.playCircleAlpha.set(INT_EVALUATOR.evaluate(0, 0, 255));
        mStates.modeX.set(0);
        mStates.previousX.set(0);
        mStates.iconContainerY.set(mIconContainerStartY);
    }

    @Override
    public void onPanelSlide(View panel, float slideOffset) {
        calculateTitleAndArtist();
        int imgWidth = INT_EVALUATOR.evaluate(slideOffset, DisplayUtils.dp2px(55), SCREEN_WIDTH);
        int imgHeight = INT_EVALUATOR.evaluate(slideOffset, DisplayUtils.dp2px(55), SCREEN_HEIGHT);
        mStates.albumArtSize.set(new Pair<>(imgWidth, imgHeight));
        mStates.artistTranslationY.set(FLOAT_EVALUATOR.evaluate(slideOffset, 0, mArtistNormalEndTranslationY));
        mStates.summaryContentPadding.set(FLOAT_EVALUATOR.evaluate(slideOffset, 30, mSummaryContentPadding));
        mStates.summaryTranslationX.set(FLOAT_EVALUATOR.evaluate(slideOffset, 128, mContentNormalEndTranslationX));
        mStates.summaryTranslationY.set(FLOAT_EVALUATOR.evaluate(slideOffset, 20, mContentNormalEndTranslationY));
        mStates.singTxtSize.set(INT_EVALUATOR.evaluate(slideOffset, 28, mSingTxtSize));
        mStates.songTxtSize.set(INT_EVALUATOR.evaluate(slideOffset, 28, mSongTxtSize));

        mStates.playPauseX.set(INT_EVALUATOR.evaluate(slideOffset, mPlayPauseStartX, mPlayPauseEndX));
        mStates.playCircleAlpha.set(INT_EVALUATOR.evaluate(slideOffset, 0, 255));
        mStates.playPauseDrawableColor.set((int) COLOR_EVALUATOR.evaluate(slideOffset, PLAY_PAUSE_DRAWABLE_COLOR, NOW_PLAYING_CARD_COLOR));
        mStates.previousX.set(INT_EVALUATOR.evaluate(slideOffset, SCREEN_WIDTH, mPreviousEndX));
        mStates.modeX.set(INT_EVALUATOR.evaluate(slideOffset, SCREEN_WIDTH, mModeEndX));
        mStates.nextX.set(INT_EVALUATOR.evaluate(slideOffset, SCREEN_WIDTH, mNextEndX));
        mStates.icPlayListX.set(INT_EVALUATOR.evaluate(slideOffset, mPlayQueueStartX, mPlayQueueEndX));
        mStates.modeAlpha.set(FLOAT_EVALUATOR.evaluate(slideOffset, 0, 1));
        mStates.previousAlpha.set(FLOAT_EVALUATOR.evaluate(slideOffset, 0, 1));
        mStates.iconContainerY.set(INT_EVALUATOR.evaluate(slideOffset, mIconContainerStartY, mIconContainerEndY));
    }

    @Override
    public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
        if (previousState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
            mStates.songProgressNormalVisibility.set(false);
            mStates.modeVisibility.set(true);
            mStates.previousVisibility.set(true);
        }
        if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
            mStatus = Status.EXPANDED;
            mStates.lyricVisibility.set(true);
        } else if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
            mStatus = Status.COLLAPSED;
            mStates.songProgressNormalVisibility.set(true);
            mStates.modeVisibility.set(false);
            mStates.previousVisibility.set(false);
        } else if (newState == SlidingUpPanelLayout.PanelState.DRAGGING) {
            mStates.customToolbarVisibility.set(false);
        }
    }

    public void calculateTitleAndArtist() {
        mIconContainerEndY = 1320;
        mArtistNormalEndTranslationY = DisplayUtils.dp2px(12);
        mSummaryContentPadding = DisplayUtils.dp2px(35);
        mContentNormalEndTranslationX = DisplayUtils.dp2px(32);
        mContentNormalEndTranslationY = DisplayUtils.dp2px(68);
        mSongTxtSize = DisplayUtils.px2sp(96);
        mSingTxtSize = DisplayUtils.px2sp(64);
    }

    public static class SlideAnimatorStates extends StateHolder {
        public final State<Float> artistTranslationY = new State<>(0f);
        public final State<Float> summaryContentPadding = new State<>(0f);
        public final State<Float> summaryTranslationX = new State<>(0f);
        public final State<Float> summaryTranslationY = new State<>(0f);

        public final State<Integer> songTxtSize = new State<>(0);
        public final State<Integer> singTxtSize = new State<>(0);

        public final State<Integer> playPauseX = new State<>(0);
        public final State<Integer> playCircleAlpha = new State<>(0);
        public final State<Integer> playPauseDrawableColor = new State<>(0);
        public final State<Integer> previousX = new State<>(0);
        public final State<Integer> modeX = new State<>(0);
        public final State<Integer> nextX = new State<>(0);
        public final State<Integer> icPlayListX = new State<>(0);
        public final State<Float> modeAlpha = new State<>(0f);
        public final State<Float> previousAlpha = new State<>(0f);
        public final State<Integer> iconContainerY = new State<>(0);
        public final State<Boolean> songProgressNormalVisibility = new State<>(false);
        public final State<Boolean> modeVisibility = new State<>(false);
        public final State<Boolean> previousVisibility = new State<>(false);
        public final State<Boolean> customToolbarVisibility = new State<>(false);
        public final State<Pair<Integer, Integer>> albumArtSize = new State<>(new Pair<>(0, 0));

        public final State<Boolean> lyricVisibility = new State<>(false);
    }
}
