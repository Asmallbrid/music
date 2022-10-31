package com.yt.yunxiaoweimusic.data.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GetLyricPayload implements Serializable {
    @SerializedName("lyric")
    public String lyric;

    @SerializedName("msg")
    public String msg;

    @SerializedName("ret")
    public int ret;

    @SerializedName("sessionId")
    public String sessionId;

    @SerializedName("songId")
    public int songId;
}
