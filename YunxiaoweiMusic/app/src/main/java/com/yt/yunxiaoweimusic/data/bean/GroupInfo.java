package com.yt.yunxiaoweimusic.data.bean;

import java.util.List;

/**
* 标签类别
* */
public class GroupInfo {
    public String groupName;
    public List<Label>labelInfo;
    public class Label{
        public int id;
        public String name;
    }
}
