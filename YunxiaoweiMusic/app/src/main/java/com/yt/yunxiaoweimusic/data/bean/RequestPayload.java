package com.yt.yunxiaoweimusic.data.bean;

public class RequestPayload {
    public String grantType;
    public String version;
    public String refreshToken;
    public String qrCodeType;
    public String authCode;
    public String sn;
    public String type;

    private RequestPayload(Builder builder) {
        this.grantType = builder.grantType;
        this.version = builder.version;
        this.refreshToken = builder.refreshToken;
        this.qrCodeType = builder.qrCodeType;
        this.authCode = builder.authCode;
        this.sn = builder.sn;
        this.type = builder.type;
    }

    public static class Builder {
        private String grantType;//可选
        private String version;//可选
        private String refreshToken;//可选
        private String qrCodeType;//可选
        private String authCode;//可选
        public String sn;
        public String type;

        public Builder setGrantType(String grantType) {
            this.grantType = grantType;
            return this;
        }

        public Builder setVersion(String version) {
            this.version = version;
            return this;
        }

        public Builder setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public Builder setQrCodeType(String qrCodeType) {
            this.qrCodeType = qrCodeType;
            return this;
        }

        public Builder setAuthCode(String authCode) {
            this.authCode = authCode;
            return this;
        }

        public Builder setSn(String sn) {
            this.sn = sn;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public RequestPayload build() {
            return new RequestPayload(this);
        }

    }
}
