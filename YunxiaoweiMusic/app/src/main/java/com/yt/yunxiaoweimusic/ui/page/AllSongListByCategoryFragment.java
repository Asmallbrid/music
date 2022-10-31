package com.yt.yunxiaoweimusic.ui.page;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.kunminx.architecture.ui.state.State;
import com.yt.yunxiaoweimusic.BR;
import com.yt.yunxiaoweimusic.R;
import com.yt.yunxiaoweimusic.data.bean.GetSongListCategory;
import com.yt.yunxiaoweimusic.data.bean.GetSongListSelfPayload;
import com.yt.yunxiaoweimusic.data.response.DataResult;
import com.yt.yunxiaoweimusic.domain.request.SearchRequester;
import com.yt.yunxiaoweimusic.ui.adapter.CateGorySongListResultAdapter;
import com.yt.yunxiaoweimusic.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class AllSongListByCategoryFragment extends BaseFragment {

    private MainStates mStates;
    private SearchRequester searchRequester;
    public CateGorySongListResultAdapter mCateGorySongListResultAdapter;

    @Override
    protected void initViewModel() {
        mStates = getFragmentScopeViewModel(MainStates.class);
        searchRequester = getFragmentScopeViewModel(SearchRequester.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        mCateGorySongListResultAdapter = new CateGorySongListResultAdapter(getContext());
        mCateGorySongListResultAdapter.setOnItemClickListener((viewId, item, position) -> {
            Bundle bundle = new Bundle();
            bundle.putLong("songListId", item.songListId);
            nav().navigate(R.id.action_allSongListByCategoryFragment_to_songListFragment, bundle);
        });
        return new DataBindingConfig(R.layout.fragment_all_song_list_bycategory, BR.vm, mStates)
                .addBindingParam(BR.songListCategoryAdapter, mCateGorySongListResultAdapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchRequester.getSongListCategoryResult().observe(this, dataResult -> mStates.categorySongList.set(dataResult.getResult().songListInfos));
        getSongListCategory(2, getArguments().getInt("categoryId"));

    }

    public void getSongListCategory(int cmd, int category) {
        searchRequester.getSongListCategory(getContext(), cmd, category);

    }

    public static class MainStates extends ViewModel {
        public final State<List<GetSongListSelfPayload.SongListInfo>> categorySongList = new State<>(new ArrayList<>());
    }

}
