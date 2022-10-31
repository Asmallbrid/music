package com.yt.yunxiaoweimusic.data.bean;

public class MusicRequest {
    public RequestHeader header;
    public MusicRequestPayload payload;

    public RequestHeader getHeader() {
        return header;
    }

    public void setHeader(RequestHeader header) {
        this.header = header;
    }

    public MusicRequestPayload getPayload() {
        return payload;
    }

    public void setPayload(MusicRequestPayload payload) {
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
