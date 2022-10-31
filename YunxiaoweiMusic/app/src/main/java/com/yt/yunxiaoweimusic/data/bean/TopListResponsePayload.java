package com.yt.yunxiaoweimusic.data.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 排行耪
 */
public class TopListResponsePayload implements Serializable {
    public List<Group> groupList;

    public class Group  {
        public int groupId;
        public String groupName;
        public List<GroupTop> groupTopList;
        public String msg;
        public int ret;
        public String sessionId;
    }

    public class GroupTop {
        public int listenNum;
        public String showTime;
        public List<SongInfo> songInfos;
        public String topBannerPic;
        public String topHeaderPic;
        public int topId;
        public String topName;
        public int topType;
        public int totalNum;
    }

    public class SongInfo {
        public int singerId;
        public String singerMId;
        public String singerName;
        public int songId;
        public String songMId;
        public String songName;
    }
}
