package com.yt.yunxiaoweimusic.data.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SearchSongListPayload implements Serializable {
    public int curNum;
    public String curPage;
    public List<SongListBean> list;
    public String msg;
    public int ret;
    public String sessionId;
    public int totalNum;

    //歌单实体类
    public class SongListBean{
        public int Playable;
        public int albumId;
        public String avatarNickName;
        public long createTime;
        public int  favNum;
        public String songListDesc;
        public String avatarPic;
        public String avatarUin;
        public int copyright;
        public String docId;
        public int hot;
        public int isDigitalAlbum;
        public int isOnly;
        public long listenNum;
        public int longAudioTag;
        public int opiPlayFlag;
        public int qqMusicFlag;
        public int singerId;
        public int songId;
        public long songListId;
        public String songListName;
        public String songListPic;
        public int songNum;
        public int songPlayTime;
        public int songSize;
        public int songSizeHq;
        public int songSizeSq;
        public int songSizeStandard;
        public int songVersion;
        public int tryBegin;
        public int tryEnd;
        public int tryFileSize;
        public int tryPlayable;
        public int unplayableCode;
        public int userOwnRule;
        public int vip;
        public long updateTime;
    }
}
