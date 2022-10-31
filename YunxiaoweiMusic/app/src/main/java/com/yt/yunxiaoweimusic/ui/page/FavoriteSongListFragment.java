package com.yt.yunxiaoweimusic.ui.page;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.kunminx.architecture.ui.state.State;
import com.yt.yunxiaoweimusic.BR;
import com.yt.yunxiaoweimusic.R;
import com.yt.yunxiaoweimusic.data.bean.AccessToken;
import com.yt.yunxiaoweimusic.data.bean.BaseResponse;
import com.yt.yunxiaoweimusic.data.bean.GetSongListDetailResponsePayload;
import com.yt.yunxiaoweimusic.data.bean.GetSongListSelfPayload;
import com.yt.yunxiaoweimusic.data.bean.MusicRequest;
import com.yt.yunxiaoweimusic.data.bean.MusicRequestPayload;
import com.yt.yunxiaoweimusic.data.bean.RequestHeader;
import com.yt.yunxiaoweimusic.data.bean.SearchCustomPayload;
import com.yt.yunxiaoweimusic.data.bean.SearchSongListPayload;
import com.yt.yunxiaoweimusic.data.bean.SongBean;
import com.yt.yunxiaoweimusic.domain.request.SearchRequester;
import com.yt.yunxiaoweimusic.player.PlayerManager;
import com.yt.yunxiaoweimusic.ui.adapter.FavoriteSongListResultAdapter;
import com.yt.yunxiaoweimusic.ui.adapter.SongListResultAdapter;
import com.yt.yunxiaoweimusic.ui.adapter.SongResultAdapter;
import com.yt.yunxiaoweimusic.ui.base.BaseFragment;
import com.yt.yunxiaoweimusic.utils.AssetsUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 我的歌单
 */
public class FavoriteSongListFragment extends BaseFragment {

    private MainStates mStates;
    private FavoriteSongListResultAdapter mSongListResultAdapter;
    private SearchRequester searchRequester;

    @Override
    protected void initViewModel() {
        mStates = getFragmentScopeViewModel(MainStates.class);
        searchRequester = getFragmentScopeViewModel(SearchRequester.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        mSongListResultAdapter = new FavoriteSongListResultAdapter(getContext());
        mSongListResultAdapter.setOnItemClickListener((viewId, item, position) -> {
            Bundle bundle = new Bundle();
            bundle.putLong("songListId", item.songListId);
            nav().navigate(R.id.action_favoriteSongListFragment_to_songListFragment, bundle);
        });

        return new DataBindingConfig(R.layout.fragment_favorite_song_list, BR.vm, mStates)
                .addBindingParam(BR.songListAdapter, mSongListResultAdapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getLifecycle().addObserver(searchRequester);
        searchRequester.getSongListFavResult().observe(this, dataResult -> {
            mSongListResultAdapter.notifyDataSetChanged();
            mStates.songListResult.set(dataResult.getResult());
        });
        getSongListFav();
    }

    public static class MainStates extends ViewModel {
        public final State<List<GetSongListSelfPayload.SongListInfo>> songListResult = new State<>(new ArrayList<>());
    }

    public void getSongListFav() {
        searchRequester.getSongListFav(getContext());
    }

}
