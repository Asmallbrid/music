package com.yt.yunxiaoweimusic.ui.page;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.kunminx.architecture.ui.state.State;
import com.kunminx.binding_recyclerview.adapter.BaseDataBindingAdapter;
import com.yt.yunxiaoweimusic.BR;
import com.yt.yunxiaoweimusic.R;
import com.yt.yunxiaoweimusic.data.bean.SongBean;
import com.yt.yunxiaoweimusic.data.bean.TopListDetailResponsePayload;
import com.yt.yunxiaoweimusic.data.bean.TopListResponsePayload;
import com.yt.yunxiaoweimusic.data.response.DataResult;
import com.yt.yunxiaoweimusic.domain.request.SearchRequester;
import com.yt.yunxiaoweimusic.player.PlayerManager;
import com.yt.yunxiaoweimusic.ui.adapter.SongResultAdapter;
import com.yt.yunxiaoweimusic.ui.adapter.TopListAllResultAdapter;
import com.yt.yunxiaoweimusic.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class TopListDetailFragment extends BaseFragment {

    private MainStates mStates;
    private SearchRequester searchRequester;
    private SongResultAdapter mAdapter;
    public TopListAllResultAdapter topListAllResultAdapter;

    @Override
    protected void initViewModel() {
        mStates = getFragmentScopeViewModel(MainStates.class);
        searchRequester = getFragmentScopeViewModel(SearchRequester.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        mAdapter = new SongResultAdapter(getContext());
        mAdapter.setOnItemClickListener((viewId, item, position) -> PlayerManager.getInstance().next());
        return new DataBindingConfig(R.layout.fragment_top_list_detail, BR.vm, mStates)
                .addBindingParam(BR.songAdapter, mAdapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchRequester.getTopListDetailResult().observe(this, topListDetailResponsePayloadDataResult -> mStates.songResult.set(topListDetailResponsePayloadDataResult.getResult().songInfos));
        getTopListDetail(getArguments().getInt("topId"));
    }

    public void getTopListDetail(int topID) {
        searchRequester.getTopListDetail(getContext(), topID);
    }

    public static class MainStates extends ViewModel {
        public final State<List<SongBean>> songResult = new State<>(new ArrayList<>());
    }

}
