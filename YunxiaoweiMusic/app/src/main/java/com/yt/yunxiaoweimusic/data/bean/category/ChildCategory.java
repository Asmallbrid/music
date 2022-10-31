package com.yt.yunxiaoweimusic.data.bean.category;

public class ChildCategory extends CategoryItem {
    public int id;
    public String name;

    @Override
    public int getType() {
        return TYPE_CHILD;
    }
}
