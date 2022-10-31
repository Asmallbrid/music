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
import com.kunminx.binding_recyclerview.adapter.BaseDataBindingAdapter;
import com.yt.yunxiaoweimusic.BR;
import com.yt.yunxiaoweimusic.R;
import com.yt.yunxiaoweimusic.data.bean.AccessToken;
import com.yt.yunxiaoweimusic.data.bean.BaseResponse;
import com.yt.yunxiaoweimusic.data.bean.MusicRequestPayload;
import com.yt.yunxiaoweimusic.data.bean.MusicRequest;
import com.yt.yunxiaoweimusic.data.bean.RequestHeader;
import com.yt.yunxiaoweimusic.data.bean.SearchCustomPayload;
import com.yt.yunxiaoweimusic.data.bean.SearchSongListPayload;
import com.yt.yunxiaoweimusic.data.bean.SongBean;
import com.yt.yunxiaoweimusic.domain.message.PageMessenger;
import com.yt.yunxiaoweimusic.domain.request.SearchRequester;
import com.yt.yunxiaoweimusic.player.PlayerManager;
import com.yt.yunxiaoweimusic.ui.adapter.TabSelectedListener;
import com.yt.yunxiaoweimusic.ui.adapter.SongListResultAdapter;
import com.yt.yunxiaoweimusic.ui.adapter.SongResultAdapter;
import com.yt.yunxiaoweimusic.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SearchFragment extends BaseFragment {

    private MainStates mStates;
    private PageMessenger mMessenger;
    private SearchRequester searchRequester;
    private TabSelectedListener mSearchTabSelectedListener;
    private SongResultAdapter mAdapter;
    private SongListResultAdapter mSongListResultAdapter;

    private final static int VALUE_SEARCH_TYPE_SONG = 0;
    private final static int VALUE_SEARCH_TYPE_SONG_LIST = 3;
    private final static int VALUE_SEARCH_TYPE_ALBUM = 8;

    private final static int INDEX_SEARCH_TYPE_SONG = 0;
    private final static int INDEX_SEARCH_TYPE_SONG_LIST = 1;
    private final static int INDEX_SEARCH_TYPE_ALBUM = 2;
    private int INDEX_SEARCH_TYPE_CURRENT = 0;

    @Override
    protected void initViewModel() {
        mStates = getFragmentScopeViewModel(MainStates.class);
        mMessenger = getApplicationScopeViewModel(PageMessenger.class);
        searchRequester = getFragmentScopeViewModel(SearchRequester.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        mSearchTabSelectedListener = new TabSelectedListener();
        mSearchTabSelectedListener.setOnTabClickListener(position -> {
            switch (position) {
                case INDEX_SEARCH_TYPE_SONG:
                    INDEX_SEARCH_TYPE_CURRENT = INDEX_SEARCH_TYPE_SONG;
                    search(VALUE_SEARCH_TYPE_SONG);
                    break;
                case INDEX_SEARCH_TYPE_SONG_LIST:
                    INDEX_SEARCH_TYPE_CURRENT = INDEX_SEARCH_TYPE_SONG_LIST;
                    search(VALUE_SEARCH_TYPE_SONG_LIST);
                    break;
                case INDEX_SEARCH_TYPE_ALBUM:
                    INDEX_SEARCH_TYPE_CURRENT = INDEX_SEARCH_TYPE_ALBUM;
                    search(VALUE_SEARCH_TYPE_ALBUM);
                    break;
                default:
                    break;
            }
        });

        mAdapter = new SongResultAdapter(getContext());
        mAdapter.setOnItemClickListener((viewId, item, position) -> {
            PlayerManager.getInstance().addMusic(mStates.songResult.get().get(position));
            PlayerManager.getInstance().next();
        });

        mSongListResultAdapter = new SongListResultAdapter(getContext());
        mSongListResultAdapter.setOnItemClickListener((viewId, item, position) -> {
            Bundle bundle = new Bundle();
            bundle.putLong("songListId", item.songListId);
            nav().navigate(R.id.action_searchFragment_to_songListFragment, bundle);
        });
        return new DataBindingConfig(R.layout.fragment_search, BR.vm, mStates)
                .addBindingParam(BR.click, new ClickProxy())
                .addBindingParam(BR.tabSelectedListener, mSearchTabSelectedListener)
                .addBindingParam(BR.songAdapter, mAdapter)
                .addBindingParam(BR.songListAdapter, mSongListResultAdapter);

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getLifecycle().addObserver(searchRequester);
        Bundle arguments = getArguments();
        mStates.searchKeyWord.set(arguments.getString("keyWork"));
        search(VALUE_SEARCH_TYPE_SONG);
        searchRequester.getSearchResult().observe(this, stringDataResult -> {
            parseSearchResult(stringDataResult.getResult());
        });
    }

    public class ClickProxy {
        public void searchClick() {
            search(INDEX_SEARCH_TYPE_CURRENT == INDEX_SEARCH_TYPE_SONG ? VALUE_SEARCH_TYPE_SONG : (INDEX_SEARCH_TYPE_CURRENT == INDEX_SEARCH_TYPE_SONG_LIST ? VALUE_SEARCH_TYPE_SONG_LIST : VALUE_SEARCH_TYPE_ALBUM));
        }
    }

    public static class MainStates extends ViewModel {
        public final State<String> searchKeyWord = new State<>("");
        public final State<String> accessToken = new State<>("");
        public final State<Boolean> initTabAndPage = new State<>(true);
        public final State<List<SongBean>> songResult = new State<>(new ArrayList<>());
        public final State<List<SearchSongListPayload.SongListBean>> songListResult = new State<>(new ArrayList<>());
    }

    public void search(int type) {
        searchRequester.serchCustom(getContext(), mStates.searchKeyWord.get(), type);
    }

    public void parseSearchResult(String result) {
        Gson gson = new Gson();
        BaseResponse baseResponse = gson.fromJson(result, BaseResponse.class);
        String s = gson.toJson(baseResponse.payload);
        if (INDEX_SEARCH_TYPE_CURRENT == INDEX_SEARCH_TYPE_SONG) {
            SearchCustomPayload searchCustomPayload = gson.fromJson(s, SearchCustomPayload.class);
            mStates.songResult.set(searchCustomPayload.list);
            mAdapter.notifyDataSetChanged();
        } else if (INDEX_SEARCH_TYPE_CURRENT == INDEX_SEARCH_TYPE_SONG_LIST) {
            SearchSongListPayload searchSongListPayload = gson.fromJson(s, SearchSongListPayload.class);
            mStates.songListResult.set(searchSongListPayload.list);
            mSongListResultAdapter.notifyDataSetChanged();
        }
    }
}
