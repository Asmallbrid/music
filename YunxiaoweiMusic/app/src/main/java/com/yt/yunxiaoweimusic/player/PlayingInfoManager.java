package com.yt.yunxiaoweimusic.player;

import android.util.Log;

import com.yt.yunxiaoweimusic.data.bean.SongBean;

import java.util.ArrayList;
import java.util.List;

public class PlayingInfoManager {
    private int mPlayIndex = 0;
    private int mAlbumIndex = 0;
    private Enum<RepeatMode> mRepeatMode;

    public enum RepeatMode {
        SINGLE_CYCLE,
        LIST_CYCLE,
        RANDOM
    }

    private final List<SongBean> mOriginPlayingList = new ArrayList<>();
    private final List<SongBean> mShufflePlayingList = new ArrayList<>();
    private final List<Integer> mOriginPlayingSongIDList = new ArrayList<>();
    private List<SongBean> mMusic;

    void setMusic(List<SongBean> mMusic) {
        this.mMusic = mMusic;
        mOriginPlayingList.clear();
        mOriginPlayingSongIDList.clear();
        mOriginPlayingList.addAll(mMusic);
        for (SongBean songBean : mOriginPlayingList) {
            mOriginPlayingSongIDList.add(songBean.songId);
        }
    }

    void addMusic(SongBean songBean) {
        if(mOriginPlayingSongIDList.contains(songBean.songId)){
            return;
        }
        mOriginPlayingSongIDList.add(songBean.songId);
        mOriginPlayingList.add(songBean);
    }


    SongBean getCurrentPlayingMusic() {
        Log.e("zzq","size:"+getPlayingList()+"  mPlayIndex:"+mPlayIndex);
        if (getPlayingList().isEmpty()) {
            return null;
        }
        return getPlayingList().get(mPlayIndex);
    }

    List<SongBean> getPlayingList() {
        if (mRepeatMode == RepeatMode.RANDOM) {
            return mShufflePlayingList;
        } else {
            return mOriginPlayingList;
        }
    }

    void setAlbumIndex(int albumIndex) {
        mAlbumIndex = albumIndex;
        mPlayIndex = getPlayingList().indexOf(mOriginPlayingList.get(mAlbumIndex));
    }

    void countNextIndex() {
        if (mPlayIndex == (getPlayingList().size() - 1)) {
            mPlayIndex = 0;
        } else {
            ++mPlayIndex;
        }
        mAlbumIndex = mOriginPlayingList.indexOf(getCurrentPlayingMusic());
    }

    void countPreviousIndex() {
        if (mPlayIndex == 0) {
            mPlayIndex = (getPlayingList().size() - 1);
        } else {
            --mPlayIndex;
        }
        mAlbumIndex = mOriginPlayingList.indexOf(getCurrentPlayingMusic());
    }

    Enum<PlayingInfoManager.RepeatMode> getRepeatMode() {
        return mRepeatMode;
    }
}
