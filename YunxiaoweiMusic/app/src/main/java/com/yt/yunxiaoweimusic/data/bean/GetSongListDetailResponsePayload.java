package com.yt.yunxiaoweimusic.data.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GetSongListDetailResponsePayload implements Serializable {
    @SerializedName("favNum")
    public int favNum;

    @SerializedName("hot")
    public int hot;

    @SerializedName("listenNum")
    public int listenNum;

    @SerializedName("msg")
    public String msg;

    @SerializedName("ownerFlag")
    public int ownerFlag;

    @SerializedName("ret")
    public int ret;

    @SerializedName("sessionId")
    public String sessionId;

    @SerializedName("songInfos")
    public List<SongBean> songInfos;

    @SerializedName("songListId")
    public long songListId;

    @SerializedName("songListPic")
    public String songListPic;

    @SerializedName("songListTitle")
    public String songListTitle;

    @SerializedName("songNum")
    public int songNum;

    @SerializedName("totalNum")
    public int totalNum;
}
