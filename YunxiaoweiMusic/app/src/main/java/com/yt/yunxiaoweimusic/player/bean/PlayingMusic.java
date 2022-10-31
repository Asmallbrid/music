package com.yt.yunxiaoweimusic.player.bean;

public class PlayingMusic {
    private String nowTime;
    private String allTime;
    private int duration;
    private int playerPosition;
    public PlayingMusic(String nowTime, String allTime) {
        this.nowTime = nowTime;
        this.allTime = allTime;
    }
    public String getNowTime() {
        return nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    public String getAllTime() {
        return allTime;
    }

    public void setAllTime(String allTime) {
        this.allTime = allTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(int playerPosition) {
        this.playerPosition = playerPosition;
    }
}
