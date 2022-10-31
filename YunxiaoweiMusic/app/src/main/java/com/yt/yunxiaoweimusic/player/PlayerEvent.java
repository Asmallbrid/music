package com.yt.yunxiaoweimusic.player;

import com.yt.yunxiaoweimusic.data.bean.SongBean;
import com.yt.yunxiaoweimusic.player.bean.PlayingMusic;

public class PlayerEvent {
    public final static int EVENT_CHANGE_MUSIC = 1;
    public final static int EVENT_PROGRESS = 2;
    public final static int EVENT_PLAY_STATUS = 3;
    public final static int EVENT_REPEAT_MODE = 4;
    public final static int EVENT_NOT_FOUND = 5;
    public final Enum<PlayingInfoManager.RepeatMode> repeatMode;
    public final PlayingMusic playingMusic;

    public final int eventId;
    public final SongBean songBean;
    public final boolean toPause;

    public PlayerEvent(int eventId) {
        this.eventId = eventId;
        this.songBean = null;
        this.toPause = false;
        this.repeatMode = null;
        this.playingMusic = null;
    }

    public PlayerEvent(int eventId, boolean toPause) {
        this.eventId = eventId;
        this.songBean = null;
        this.toPause = toPause;
        this.repeatMode = null;
        this.playingMusic = null;
    }

    public PlayerEvent(int eventId, PlayingMusic playingMusic) {
        this.eventId = eventId;
        this.playingMusic = playingMusic;
        this.toPause = false;
        this.repeatMode = null;
        this.songBean = null;
    }

    public PlayerEvent(int eventId, SongBean songBean) {
        this.eventId = eventId;
        this.songBean = songBean;
        this.toPause = false;
        this.repeatMode = null;
        this.playingMusic = null;
    }
}
