package com.yt.yunxiaoweimusic.data.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 获取分类歌单payload 请求码为1：获取所有的分类标签，2：获取标签下歌单列表
 **/
public class GetSongListCategory {
    public String msg;
    public int ret;
    public String sessionId;
    public List<GetSongListSelfPayload.SongListInfo> songListInfos;
    public List<GroupInfo> groupInfos;

}
