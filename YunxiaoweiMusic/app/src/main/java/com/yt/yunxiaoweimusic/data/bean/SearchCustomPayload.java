package com.yt.yunxiaoweimusic.data.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SearchCustomPayload implements Serializable {
    @SerializedName("curNum")
    public int curNum;

    @SerializedName("curPage")
    public String curPage;

    @SerializedName("list")
    public List<SongBean> list;

    @SerializedName("msg")
    public String msg;

    @SerializedName("ret")
    public int ret;

    @SerializedName("sessionId")
    public String sessionId;

    @SerializedName("totalNum")
    public int totalNum;
}
