package com.yt.yunxiaoweimusic.data.bean;

public class RequestHeader {
    public Qua qua;

    public void setQua(Qua qua) {
        this.qua = qua;
    }

    @Override
    public String toString() {
        return "RequestHeader{" +
                "qua=" + qua +
                '}';
    }
}
