package com.yt.yunxiaoweimusic.data.bean;

import java.util.List;

/**
* 获取歌手歌曲信息 返回bean
* */
public class GetSingerDetailResponsePayload {
    public String area;
    public String msg;
    public int ret;
    public String sessionId;
    public int singerId;
    public String singerMId;
    public String singerName;
    public String singerPic;
    public String singerTranslatorName;
    public List<SongBean>songInfos;
    public int songNum;
}
