package com.yt.yunxiaoweimusic.data.bean;

import java.util.List;

/**
 * 获取歌手专辑列表信息 返回bean
 * */
public class GetSingerAlbumResponsePayload {
    public List<Album>albumList;
    public String msg;
    public int ret;
    public String sessionId;
    public int totalNum;
}
