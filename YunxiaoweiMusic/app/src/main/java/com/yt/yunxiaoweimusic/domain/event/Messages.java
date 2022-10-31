package com.yt.yunxiaoweimusic.domain.event;

public class Messages<T> {
    public final static int EVENT_CLOSE_SLIDE_PANEL_IF_EXPANDED = 1;
    public final static int EVENT_CLOSE_ACTIVITY_IF_ALLOWED = 2;
    public final static int EVENT_ADD_SLIDE_LISTENER = 3;

    public final int eventId;
    public T data;

    public Messages(int eventId) {
        this.eventId = eventId;
    }
}
