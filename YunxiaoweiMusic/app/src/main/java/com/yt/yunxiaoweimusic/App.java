package com.yt.yunxiaoweimusic;

import android.app.Application;

import com.yt.yunxiaoweimusic.player.PlayerManager;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PlayerManager.getInstance().init(this);
    }
}
