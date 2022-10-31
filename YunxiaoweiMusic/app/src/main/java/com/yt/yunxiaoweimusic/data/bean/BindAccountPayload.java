package com.yt.yunxiaoweimusic.data.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/*
 * 账号授权绑定返回体
 * */
public class BindAccountPayload implements Serializable {
    public String tokenType;

    public int retCode;

    public String errMsg;

    public String accessToken;

    public String refreshToken;

    public int expiresIn;
}
