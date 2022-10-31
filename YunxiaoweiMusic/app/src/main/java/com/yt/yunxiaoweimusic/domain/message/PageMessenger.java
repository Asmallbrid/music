package com.yt.yunxiaoweimusic.domain.message;

import com.kunminx.architecture.domain.dispatch.MviDispatcher;
import com.yt.yunxiaoweimusic.domain.event.Messages;

public class PageMessenger extends MviDispatcher<Messages> {
    @Override
    protected void onHandle(Messages event) {

      /* TODO 于唯一可信源中统一鉴权处理业务逻辑，并通过 sendResult 回推结果至表现层

         switch (event.eventId) {
            case Messages.EVENT_ADD_SLIDE_LISTENER:
                //... 业务逻辑处理
                //... 末端消息回推:
                // event.result.xxx = xxx;
                // sendResult(event);
                break;
            case Messages.EVENT_CLOSE_ACTIVITY_IF_ALLOWED:
                break;
            case Messages.EVENT_CLOSE_SLIDE_PANEL_IF_EXPANDED:
                break;
            case Messages.EVENT_OPEN_DRAWER:
                break;
          }
       */

        sendResult(event);
    }
}
