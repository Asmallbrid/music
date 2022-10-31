package com.yt.yunxiaoweimusic.data.bean.category;

public abstract class CategoryItem {
    public final int TYPE_GROUP = 0xa01;
    public final int TYPE_CHILD = 0xa02;

    public abstract int getType();
}
