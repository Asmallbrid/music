package com.yt.yunxiaoweimusic.data.bean;

public class MusicRequestPayload {
    public String keyword;
    public int cmd;
    public int page;
    public int type;
    public int pageSize;
    public long songListId;
    public int categoryId;
    public int area;
    public int genre;
    public int singerId;
    public int pageIndex;
    public int order;
    public String songIds;
    public int  topId;

    private MusicRequestPayload(Builder builder) {
        this.keyword = builder.keyword;
        this.cmd = builder.cmd;
        this.page = builder.page;
        this.type = builder.type;
        this.pageSize = builder.pageSize;
        this.songListId = builder.songListId;
        this.categoryId = builder.categoryId;
        this.area = builder.area;
        this.genre = builder.genre;
        this.singerId = builder.singerId;
        this.pageIndex = builder.pageIndex;
        this.order = builder.order;
        this.songIds = builder.songIds;
        this.topId = builder.topId;
    }

    public static class Builder {
        public String keyword;
        public int cmd;
        public int page;
        public int type;
        public int pageSize;
        public long songListId;
        public int categoryId;
        public int area;
        public int genre;
        public int singerId;
        public int pageIndex;
        public int order;
        public String songIds;
        public int  topId;

        public Builder setKeyword(String keyword) {
            this.keyword = keyword;
            return this;
        }

        public Builder setCmd(int cmd) {
            this.cmd = cmd;
            return this;
        }


        public Builder setPage(int page) {
            this.page = page;
            return this;
        }

        public Builder setType(int type) {
            this.type = type;
            return this;
        }

        public Builder setPageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder setSongListId(long songListId) {
            this.songListId = songListId;
            return this;
        }

        public Builder setCategoryId(int categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public Builder setArea(int area) {
            this.area = area;
            return this;
        }

        public Builder setGenre(int genre) {
            this.genre = genre;
            return this;
        }

        public Builder setSingerId(int singerId) {
            this.singerId = singerId;
            return this;
        }

        public Builder setPageIndex(int pageIndex) {
            this.pageIndex = pageIndex;
            return this;
        }

        public Builder setOrder(int order) {
            this.order = order;
            return this;
        }

        public Builder setSongIds(String songIds) {
            this.songIds = songIds;
            return this;
        }

        public Builder setTopId(int topId) {
            this.topId = topId;
            return this;
        }

        public MusicRequestPayload build() {
            return new MusicRequestPayload(this);
        }
    }
}
