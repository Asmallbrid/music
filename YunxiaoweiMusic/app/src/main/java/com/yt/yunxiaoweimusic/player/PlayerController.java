package com.yt.yunxiaoweimusic.player;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.yt.yunxiaoweimusic.data.bean.SongBean;
import com.yt.yunxiaoweimusic.player.bean.PlayingMusic;

import java.util.List;

public class PlayerController {

    private final PlayingInfoManager mPlayingInfoManager = new PlayingInfoManager();
    private final static Handler mHandler = new Handler();
    private ExoPlayer mPlayer;
    private boolean mIsChangingPlayingMusic;
    private final PlayingMusic mCurrentPlay = new PlayingMusic("00:00", "00:00");
    private final Runnable mProgressAction = this::updateProgress;

    public void init(Context context) {
        mPlayer = new ExoPlayer.Builder(context).build();
    }

    public void loadAlbum(List<SongBean> music, int index) {
        setAlbum(music, index);
        playAudio();
    }

    public void playAudio() {
        if (mIsChangingPlayingMusic) getUrlAndPlay();
        else if (isPaused()) resumeAudio();
    }

    private void getUrlAndPlay() {
        String url;
        SongBean freeMusic = mPlayingInfoManager.getCurrentPlayingMusic();
        if (freeMusic != null) {
            url = freeMusic.songPlayUrl == null ? freeMusic.try30sUrl : freeMusic.songPlayUrl;
            mDispatcher.input(new PlayerEvent(PlayerEvent.EVENT_CHANGE_MUSIC, freeMusic));
            if (TextUtils.isEmpty(url)) {
                pauseAudio();
            } else {
                MediaItem item = MediaItem.fromUri(url);
                mPlayer.setMediaItem(item, true);
                mPlayer.prepare();
                mPlayer.play();
                afterPlay();
            }
        }
    }


    private void setAlbum(List<SongBean> music, int albumIndex) {
        mPlayingInfoManager.setMusic(music);
        mPlayingInfoManager.setAlbumIndex(albumIndex);
        setChangingPlayingMusic(true);
    }

    public List<SongBean> getPlayingList() {
        return mPlayingInfoManager.getPlayingList();
    }

    private final PlayerInfoDispatcher mDispatcher = new PlayerInfoDispatcher();

    public PlayerInfoDispatcher getDispatcher() {
        return mDispatcher;
    }

    public void start() {
        mDispatcher.input(new PlayerEvent(PlayerEvent.EVENT_REPEAT_MODE, false));
    }

    public void playNext() {
        if (mPlayingInfoManager.getPlayingList().size() > 0) {
            mPlayingInfoManager.countNextIndex();
            setChangingPlayingMusic(true);
            playAudio();
        }
    }

    public void playPrevious() {
        mPlayingInfoManager.countPreviousIndex();
        setChangingPlayingMusic(true);
        playAudio();
    }

    public void addMusic(SongBean songBean) {
        if (songBean.Playable == 0 && songBean.tryPlayable == 0) {
            Log.e("zzq", "addMusic failed----------");
            mDispatcher.input(new PlayerEvent(PlayerEvent.EVENT_NOT_FOUND));
        } else {
            mPlayingInfoManager.addMusic(songBean);
        }
    }

    private void afterPlay() {
        setChangingPlayingMusic(false);
        mHandler.post(mProgressAction);
        mDispatcher.input(new PlayerEvent(PlayerEvent.EVENT_PLAY_STATUS, false));
    }

    public void pauseAudio() {
        mPlayer.pause();
        mHandler.removeCallbacks(mProgressAction);
        mDispatcher.input(new PlayerEvent(PlayerEvent.EVENT_PLAY_STATUS, true));
    }

    public boolean isPaused() {
        return !mPlayer.getPlayWhenReady();
    }

    public void resumeAudio() {
        mPlayer.play();
        mHandler.post(mProgressAction);
        mDispatcher.input(new PlayerEvent(PlayerEvent.EVENT_PLAY_STATUS, false));
    }

    public void togglePlay() {
        if (isPlaying()) pauseAudio();
        else playAudio();
    }

    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }

    private void updateProgress() {
        mCurrentPlay.setNowTime(calculateTime(mPlayer.getCurrentPosition() / 1000));
        mCurrentPlay.setAllTime(calculateTime(mPlayer.getDuration() / 1000));
        mCurrentPlay.setDuration((int) mPlayer.getDuration());
        mCurrentPlay.setPlayerPosition((int) mPlayer.getCurrentPosition());
        mDispatcher.input(new PlayerEvent(PlayerEvent.EVENT_PROGRESS, mCurrentPlay));
        if (mCurrentPlay.getAllTime().equals(mCurrentPlay.getNowTime())) {
            if (getRepeatMode() == PlayingInfoManager.RepeatMode.SINGLE_CYCLE) playAgain();
            else playNext();
        }
        mHandler.postDelayed(mProgressAction, 1000);
    }

    private String calculateTime(long _time) {
        int time = (int) _time;
        int minute;
        int second;
        if (time >= 60) {
            minute = time / 60;
            second = time % 60;
            return (minute < 10 ? "0" + minute : "" + minute) + (second < 10 ? ":0" + second : ":" + second);
        } else {
            second = time;
            if (second < 10) return "00:0" + second;
            return "00:" + second;
        }
    }

    public Enum<PlayingInfoManager.RepeatMode> getRepeatMode() {
        return mPlayingInfoManager.getRepeatMode();
    }

    public void playAgain() {
        setChangingPlayingMusic(true);
        playAudio();
    }

    public void setChangingPlayingMusic(boolean changingPlayingMusic) {
        mIsChangingPlayingMusic = changingPlayingMusic;
        if (mIsChangingPlayingMusic) {
            //mChangeMusic.setBaseInfo(mPlayingInfoManager.getMusicAlbum(), getCurrentPlayingMusic());
            //mDispatcher.input(new PlayerEvent(PlayerEvent.EVENT_CHANGE_MUSIC, mChangeMusic));
            //mCurrentPlay.setBaseInfo(mPlayingInfoManager.getMusicAlbum(), getCurrentPlayingMusic());
            mCurrentPlay.setNowTime("00:00");
            mCurrentPlay.setAllTime("00:00");
            mCurrentPlay.setPlayerPosition(0);
            mCurrentPlay.setDuration(0);
        }
    }

    public void setSeek(int progress) {
        mPlayer.seekTo(progress);
    }
}
