package com.yt.yunxiaoweimusic.data.bean;

public class BaseRequrst {
    public RequestHeader header;
    public RequestPayload payload;

    public BaseRequrst (){}
    public BaseRequrst (RequestHeader header,RequestPayload payload){
        this.header =header;
        this.payload = payload;
    }

    public RequestHeader getHeader() {
        return header;
    }

    public void setHeader(RequestHeader header) {
        this.header = header;
    }

    public RequestPayload getPayload() {
        return payload;
    }

    public void setPayload(RequestPayload payload) {
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
