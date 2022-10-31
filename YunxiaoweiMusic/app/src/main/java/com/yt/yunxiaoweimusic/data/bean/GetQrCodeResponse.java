package com.yt.yunxiaoweimusic.data.bean;

public class GetQrCodeResponse {
    private Header header;
    private GetQrCodePayload payload;

    public Header getHeader() {
        return header;
    }

    public GetQrCodePayload getPayload() {
        return payload;
    }

    public class Header {
        private String sessionId;
        private int code;
        private String message;

        public String getSessionId() {
            return sessionId;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return "Header{" +
                    "sessionId='" + sessionId + '\'' +
                    ", code=" + code +
                    ", message='" + message + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "header=" + header +
                ", payload=" + payload +
                '}';
    }
}
