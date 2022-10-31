package com.yt.yunxiaoweimusic.data.bean;

import java.util.List;

public class RecHomePageResponsePayload {
    public String ok;
    public List<RcItem> rcItemArr;
    public int ret;
    public String sessionId;
    public List<Shelf> shelfArr;

    public class RcItem {
        public String albumPicUrl;
        public int dirId;
        public String dirName;
        public long songListId;
    }

    public class Shelf {
        public List<Card> cardArr;
    }

    public class Card {
        public long cnt;
        public String cover;
        public String id;
        public String subId;
        public String subTitle;
        public String title;
        public int type;
    }
}
