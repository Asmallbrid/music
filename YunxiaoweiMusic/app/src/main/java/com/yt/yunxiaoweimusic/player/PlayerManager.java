package com.yt.yunxiaoweimusic.player;

import android.content.Context;
import android.util.Log;

import com.yt.yunxiaoweimusic.data.bean.SongBean;

import java.util.List;

public class PlayerManager {
    private static final PlayerManager sManager = new PlayerManager();
    private final PlayerController mController;

    private PlayerManager() {
        mController = new PlayerController();
    }

    public static PlayerManager getInstance() {
        return sManager;
    }

    public PlayerInfoDispatcher getDispatcher() {
        return mController.getDispatcher();
    }


    public void init(Context context) {
        mController.init(context);
    }

    public void start() {
        mController.start();
    }

    public void next() {
        mController.playNext();
    }

    public void playPrevious() {
        mController.playPrevious();
    }

    public void addMusic(SongBean songBean) {
        mController.addMusic(songBean);
    }

    public void togglePlay() {
        mController.togglePlay();
    }

    public void setSeek(int progress) {
        mController.setSeek(progress);
    }

    public List<SongBean> getPlayingList() {
        return mController.getPlayingList();
    }
}
