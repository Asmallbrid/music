package com.yt.yunxiaoweimusic.data.bean;

public class LyricRequest {
    public RequestHeader header;
    public LyricRequestPayload payload;

    public RequestHeader getHeader() {
        return header;
    }

    public void setHeader(RequestHeader header) {
        this.header = header;
    }

    public LyricRequestPayload getPayload() {
        return payload;
    }

    public void setPayload(LyricRequestPayload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "BaseRequrst{" +
                "header=" + header +
                ", payload=" + payload +
                '}';
    }
}
