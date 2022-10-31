package com.yt.yunxiaoweimusic.data.bean.category;

public class GroupCategory extends CategoryItem {
    public String groupName;

    @Override
    public int getType() {
        return TYPE_GROUP;
    }
}
