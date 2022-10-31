package com.yt.yunxiaoweimusic.ui.page;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
import com.yt.yunxiaoweimusic.data.bean.SongBean;
import com.yt.yunxiaoweimusic.domain.request.SearchRequester;
import com.yt.yunxiaoweimusic.player.PlayerManager;
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
 * 我喜欢的
 */
public class FavoriteSongFragment extends BaseFragment {

    private MainStates mStates;
    private SongResultAdapter mAdapter;
    private SearchRequester searchRequester;

    @Override
    protected void initViewModel() {
        mStates = getFragmentScopeViewModel(MainStates.class);
        searchRequester = getFragmentScopeViewModel(SearchRequester.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        mAdapter = new SongResultAdapter(getContext());
        mAdapter.setOnItemClickListener((viewId, item, position) -> PlayerManager.getInstance().next());
        mAdapter.setFavoriteListener(new SongResultAdapter.FavoriteListener() {
            @Override
            public void addFavorite() {
                getSongListDetail(mStates.songListID.get());
            }

            @Override
            public void deleteFavorite() {
                getSongListDetail(mStates.songListID.get());
            }
        });
        return new DataBindingConfig(R.layout.fragment_favorite_song, BR.vm, mStates)
                .addBindingParam(BR.songAdapter, mAdapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getLifecycle().addObserver(searchRequester);
        searchRequester.getSongListSelfResult().observe(this, dataResult -> {
            mStates.songListPic.set(dataResult.getResult().get(0).songListPic);
            mStates.songListID.set(dataResult.getResult().get(0).songListId);
            getSongListDetail(dataResult.getResult().get(0).songListId);
        });
        searchRequester.getSongListDetailResult().observe(this, dataResult -> {
            mStates.songResult.set(dataResult.getResult().songInfos);
        });
        getSongListSelf();
    }

    public static class MainStates extends ViewModel {
        public final State<String> content = new State<>("1");
        public final State<String> songListPic = new State<>("");
        public final State<Long> songListID = new State<>(0l);
        public final State<List<SongBean>> songResult = new State<>(new ArrayList<>());
    }

    public void getSongListSelf() {
        searchRequester.getSongListSelf(getContext());
    }

    public void getSongListDetail(long songListId) {
        searchRequester.getSongListDetail(getContext(), songListId);
    }

}
