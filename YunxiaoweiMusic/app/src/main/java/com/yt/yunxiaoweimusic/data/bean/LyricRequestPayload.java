package com.yt.yunxiaoweimusic.data.bean;

public class LyricRequestPayload {
    public String songMId;
    public int songId;

    private LyricRequestPayload(Builder builder) {
        this.songMId = builder.songMId;
        this.songId = builder.songId;
    }

    public void setSongMId(String songMId) {
        this.songMId = songMId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public static class Builder {
        public String songMId;
        public int songId;

        public Builder setSongMid(String songMId) {
            this.songMId = songMId;
            return this;
        }

        public Builder setSingId(int songId) {
            this.songId = songId;
            return this;
        }

        public LyricRequestPayload build() {
            return new LyricRequestPayload(this);
        }
    }
}
