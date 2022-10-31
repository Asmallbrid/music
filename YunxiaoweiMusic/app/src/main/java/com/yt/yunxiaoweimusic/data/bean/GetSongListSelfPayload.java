package com.yt.yunxiaoweimusic.data.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 获取个人歌单目录payload
 **/
public class GetSongListSelfPayload implements Serializable {
    public String msg;
    public int ret;
    public String sessionId;
    public List<SongListInfo> songListInfos;

    public class SongListInfo {
        public String avatarNickName;
        public String avatarPic;
        public long createTime;
        public int favNum;
        public int listenNum;
        public String songListDesc;
        public long songListId;
        public String songListName;
        public String songListPic;
        public int songNum;
        public long updateTime;
    }
}
