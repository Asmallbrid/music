package com.yt.yunxiaoweimusic.data.bean;

public class TestRequrst {
    public RequestHeader header;
    public TestPayload payload;

    public TestRequrst() {
    }

    public TestRequrst(RequestHeader header, TestPayload payload) {
        this.header = header;
        this.payload = payload;
    }

    public RequestHeader getHeader() {
        return header;
    }

    public void setHeader(RequestHeader header) {
        this.header = header;
    }

    public TestPayload getPayload() {
        return payload;
    }

    public void setPayload(TestPayload payload) {
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
