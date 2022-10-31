package com.yt.yunxiaoweimusic.data.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/*
 * 账号授权绑定返回体
 * */
public class GetQrCodePayload implements Serializable {
    @SerializedName("ret")
    public int ret;

    @SerializedName("msg")
    public String msg;

    @SerializedName("qrCode")
    public String qrCode;

    @SerializedName("authCode")
    public String authCode;

    @SerializedName("expireSeconds")
    public String expireSeconds;

    @Override
    public String toString() {
        return "GetQrCodePayload{" +
                "ret=" + ret +
                ", msg='" + msg + '\'' +
                ", qrCode='" + qrCode + '\'' +
                ", authCode='" + authCode + '\'' +
                ", expireSeconds='" + expireSeconds + '\'' +
                '}';
    }
}
