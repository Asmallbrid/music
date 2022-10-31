package com.yt.yunxiaoweimusic.data.bean;

public class AccessToken {
    private String authorization;
    private static final AccessToken instance = new AccessToken();

    private AccessToken() {
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public static AccessToken getInstance() {
        return instance;
    }
}
