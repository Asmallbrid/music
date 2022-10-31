package com.yt.yunxiaoweimusic.data.bean;

public class BaseResponse<T>{
    public Header header;
    public T payload;

    public class Header{
        public String sessionId;
        public int code;
        public String message;
    }
}
