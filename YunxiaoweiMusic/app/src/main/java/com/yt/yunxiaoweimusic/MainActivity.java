package com.yt.yunxiaoweimusic;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.kunminx.architecture.ui.state.State;
import com.yt.yunxiaoweimusic.domain.event.Messages;
import com.yt.yunxiaoweimusic.domain.message.PageMessenger;
import com.yt.yunxiaoweimusic.player.PlayerEvent;
import com.yt.yunxiaoweimusic.player.PlayerManager;
import com.yt.yunxiaoweimusic.ui.base.BaseActivity;
import com.yt.yunxiaoweimusic.ui.base.StateHolder;

public class MainActivity extends BaseActivity {

    private MainStates mStates;
    private PageMessenger mMessenger;

    @Override
    protected void initViewModel() {
        mStates = getActivityScopeViewModel(MainStates.class);
        mMessenger = getApplicationScopeViewModel(PageMessenger.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_main, BR.vm, mStates);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMessenger.output(this, new Observer<Messages>() {
            @Override
            public void onChanged(Messages messages) {
                switch (messages.eventId) {
                    case Messages.EVENT_CLOSE_ACTIVITY_IF_ALLOWED:
                        NavController nav = Navigation.findNavController(MainActivity.this, R.id.main_fragment_host);
                        if (nav.getCurrentDestination() != null && nav.getCurrentDestination().getId() != R.id.mainFragment) {
                            nav.navigateUp();
                        } else {
                            MainActivity.super.onBackPressed();
                        }
                }
            }
        });
        PlayerManager.getInstance().getDispatcher().output(this, playerEvent -> {
            switch (playerEvent.eventId) {
                case PlayerEvent.EVENT_NOT_FOUND:
                    Toast.makeText(MainActivity.this, "找不到播放资源", Toast.LENGTH_LONG).show();
                    break;
                default:
                    mStates.play.set(true);
                    break;
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mMessenger.input(new Messages(Messages.EVENT_ADD_SLIDE_LISTENER));
    }


    @Override
    public void onBackPressed() {
        mMessenger.input(new Messages(Messages.EVENT_CLOSE_SLIDE_PANEL_IF_EXPANDED));
    }

    public static class MainStates extends StateHolder {
        public final State<Boolean> play = new State<>(false);
    }
}