package com.yt.yunxiaoweimusic.player;

import com.kunminx.architecture.domain.dispatch.MviDispatcher;

public class PlayerInfoDispatcher extends MviDispatcher<PlayerEvent> {
    @Override
    protected void onHandle(PlayerEvent intent) {
        super.onHandle(intent);
        sendResult(intent);
    }
}
