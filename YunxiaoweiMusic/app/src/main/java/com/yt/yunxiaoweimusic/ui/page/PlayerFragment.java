package com.yt.yunxiaoweimusic.ui.page;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.gson.Gson;
import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.kunminx.architecture.ui.state.State;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.yt.yunxiaoweimusic.BR;
import com.yt.yunxiaoweimusic.R;
import com.yt.yunxiaoweimusic.data.bean.SongBean;
import com.yt.yunxiaoweimusic.databinding.FragmentPlayBinding;
import com.yt.yunxiaoweimusic.domain.event.Messages;
import com.yt.yunxiaoweimusic.domain.message.PageMessenger;
import com.yt.yunxiaoweimusic.domain.request.SearchRequester;
import com.yt.yunxiaoweimusic.player.PlayerEvent;
import com.yt.yunxiaoweimusic.player.PlayerManager;
import com.yt.yunxiaoweimusic.ui.adapter.SongResultAdapter;
import com.yt.yunxiaoweimusic.ui.base.BaseFragment;
import com.yt.yunxiaoweimusic.ui.base.StateHolder;
import com.yt.yunxiaoweimusic.ui.view.PlayerSlideListener;

import java.util.ArrayList;
import java.util.List;

import me.wcy.lrcview.LrcView;

public class PlayerFragment extends BaseFragment {
    private PlayerStates mStates;
    private PageMessenger mMessenger;
    private LrcView lrcView;
    private PlayerSlideListener mListener;
    private PlayerSlideListener.SlideAnimatorStates mAnimatorStates;
    private SearchRequester searchRequester;
    private ExoPlayer mPlayer;
    private SongResultAdapter mAdapter;

    @Override
    protected void initViewModel() {
        mStates = getFragmentScopeViewModel(PlayerStates.class);
        mAnimatorStates = getFragmentScopeViewModel(PlayerSlideListener.SlideAnimatorStates.class);
        mMessenger = getApplicationScopeViewModel(PageMessenger.class);
        searchRequester = getFragmentScopeViewModel(SearchRequester.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        mAdapter = new SongResultAdapter(getContext());
        mPlayer = new ExoPlayer.Builder(getContext()).build();
        return new DataBindingConfig(R.layout.fragment_play, BR.vm, mStates)
                .addBindingParam(BR.click, new ClickProxy())
                .addBindingParam(BR.panelVm, mAnimatorStates)
                .addBindingParam(BR.songAdapter, mAdapter)
                .addBindingParam(BR.listener, new ListenerHandler());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lrcView = view.findViewById(R.id.lrcview);
        getLifecycle().addObserver(searchRequester);
        searchRequester.getSearchLyricResult().observe(this, dataResult -> {
            lrcView.loadLrc(dataResult.getResult().lyric);
        });

        mMessenger.output(this, messages -> {
            switch (messages.eventId) {
                case Messages.EVENT_ADD_SLIDE_LISTENER:
                    if (view.getParent().getParent() instanceof SlidingUpPanelLayout) {
                        SlidingUpPanelLayout sliding = (SlidingUpPanelLayout) view.getParent().getParent();
                        mListener = new PlayerSlideListener(mAnimatorStates);
                        sliding.addPanelSlideListener(mListener);
                    }
                    break;
                case Messages.EVENT_CLOSE_SLIDE_PANEL_IF_EXPANDED:
                    // 按下返回键，如果此时 slide 面板是展开的，那么只对面板进行 slide down
                    if (view.getParent().getParent() instanceof SlidingUpPanelLayout) {
                        SlidingUpPanelLayout sliding = (SlidingUpPanelLayout) view.getParent().getParent();
                        if (sliding.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
                            sliding.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                        } else {
                            mMessenger.input(new Messages(Messages.EVENT_CLOSE_ACTIVITY_IF_ALLOWED));
                        }
                    } else {
                        mMessenger.input(new Messages(Messages.EVENT_CLOSE_ACTIVITY_IF_ALLOWED));
                    }
                    break;
            }
        });

        PlayerManager.getInstance().getDispatcher().output(this, playerEvent -> {
            switch (playerEvent.eventId) {
                case PlayerEvent.EVENT_CHANGE_MUSIC:
                    SongBean songBean = playerEvent.songBean;
                    mStates.title.set(songBean.songName);
                    mStates.artist.set(songBean.singerName);
                    mStates.coverImg.set(songBean.albumPic300x300);
                    searchLyric(songBean.songMId);
                    break;
                case PlayerEvent.EVENT_PROGRESS:
                    mStates.maxSeekDuration.set(playerEvent.playingMusic.getDuration());
                    mStates.maxSeekDurationStr.set(playerEvent.playingMusic.getAllTime());
                    mStates.currentSeekPosition.set(playerEvent.playingMusic.getPlayerPosition());
                    mStates.currentSeekPositionStr.set(playerEvent.playingMusic.getNowTime());
                    break;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.clearMediaItems();
        }
    }

    public class ClickProxy {
        public void start() {
            PlayerManager.getInstance().start();
        }

        public void next() {
            PlayerManager.getInstance().next();
        }

        public void playPrevious() {
            PlayerManager.getInstance().playPrevious();
        }

        public void togglePlay() {
            PlayerManager.getInstance().togglePlay();
        }

        public void showPlayList() {
            Log.e("zzq", "showPlayList click0-----------");
            mStates.playingListResult.set(PlayerManager.getInstance().getPlayingList());
            showPlayingListDialog();
        }
    }

    public class ListenerHandler implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            lrcView.updateTime(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            PlayerManager.getInstance().setSeek(seekBar.getProgress());
        }
    }

    public static class PlayerStates extends StateHolder {
        public final State<String> title = new State<>("");
        public final State<String> artist = new State<>("");
        public final State<String> coverImg = new State<>("");
        public final State<Integer> maxSeekDuration = new State<>(0);
        public final State<String> maxSeekDurationStr = new State<>("");
        public final State<Integer> currentSeekPosition = new State<>(0);
        public final State<String> currentSeekPositionStr = new State<>("");
        public final State<List<SongBean>> playingListResult = new State<>(new ArrayList<>());
    }

    public void searchLyric(String songMid) {
        searchRequester.serchLyric(getContext(), songMid);
    }

    public void showPlayingListDialog() {
        PopupWindow popupWindow = new PopupWindow();
        popupWindow.setOutsideTouchable(true);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_play_list, null);
        RecyclerView playList = view.findViewById(R.id.playList);
        mAdapter.submitList(mStates.playingListResult.get());
        playList.setAdapter(mAdapter);
        popupWindow.setContentView(view);
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }
}
