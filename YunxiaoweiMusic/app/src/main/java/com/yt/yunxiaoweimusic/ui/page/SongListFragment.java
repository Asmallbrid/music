/*
 * Copyright 2018-present KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yt.yunxiaoweimusic.ui.page;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
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
import com.yt.yunxiaoweimusic.data.response.DataResult;
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
 * 歌单详情
 */
public class SongListFragment extends BaseFragment {

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
        mAdapter.setOnItemClickListener((viewId, item, position) -> {
            PlayerManager.getInstance().addMusic(mStates.songResult.get().get(position));
            PlayerManager.getInstance().next();
        });

        return new DataBindingConfig(R.layout.fragment_songlist, BR.vm, mStates)
                .addBindingParam(BR.click, new ClickProxy())
                .addBindingParam(BR.songAdapter, mAdapter);

    }

    public TextView tvSongListHot;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getLifecycle().addObserver(searchRequester);
        tvSongListHot = view.findViewById(R.id.tvSongListHot);
        searchRequester.getSongListDetailResult().observe(this, dataResult -> {
            mStates.songListId.set(dataResult.getResult().songListId);
            mStates.songListPic.set(dataResult.getResult().songListPic);
            mStates.songListHotValue.set(dataResult.getResult().hot);
            mStates.songListName.set(dataResult.getResult().songListTitle);
            mStates.songResult.set(dataResult.getResult().songInfos);
            tvSongListHot.setText(dataResult.getResult().hot == 0 ? getString(R.string.song_list_collect) : getString(R.string.song_list_collected));
            tvSongListHot.setCompoundDrawablesWithIntrinsicBounds(dataResult.getResult().hot == 0 ? getResources().getDrawable(R.mipmap.icon_songlist_love_n)
                            : getResources().getDrawable(R.mipmap.icon_songlist_love_s),
                    null, null, null);
        });

        searchRequester.getSongListFavResult().observe(this, new Observer<DataResult<List<GetSongListSelfPayload.SongListInfo>>>() {
            @Override
            public void onChanged(DataResult<List<GetSongListSelfPayload.SongListInfo>> listDataResult) {

            }
        });
        getSongListDetail(getArguments().getLong("songListId"));
    }

    public class ClickProxy {
        public void getSongListFav(int isHot, long songListId) {
            searchRequester.getSongListFav(getContext());
            mStates.songListHotValue.set(isHot == 2 ? 0 : 1);
            tvSongListHot.setText(isHot == 2 ? getString(R.string.song_list_collect) : getString(R.string.song_list_collected));
            tvSongListHot.setCompoundDrawablesWithIntrinsicBounds(isHot == 2 ? getResources().getDrawable(R.mipmap.icon_songlist_love_n)
                            : getResources().getDrawable(R.mipmap.icon_songlist_love_s),
                    null, null, null);
        }
    }

    public static class MainStates extends ViewModel {
        public final State<String> accessToken = new State<>("");
        public final State<Long> songListId = new State<>(0l);
        public final State<Integer> songListHotValue = new State<>(0);
        public final State<String> songListPic = new State<>("");
        public final State<String> songListName = new State<>("");
        public final State<List<SongBean>> songResult = new State<>(new ArrayList<>());
    }

    public void getSongListDetail(long songListId) {
        searchRequester.getSongListDetail(getContext(), songListId);
    }

}
