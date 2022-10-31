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
import com.yt.yunxiaoweimusic.data.bean.BaseRequrst;
import com.yt.yunxiaoweimusic.data.bean.BaseResponse;
import com.yt.yunxiaoweimusic.data.bean.RequestHeader;
import com.yt.yunxiaoweimusic.data.bean.RequestPayload;
import com.yt.yunxiaoweimusic.data.bean.TopListResponsePayload;
import com.yt.yunxiaoweimusic.domain.request.SearchRequester;
import com.yt.yunxiaoweimusic.ui.adapter.TopListAllResultAdapter;
import com.yt.yunxiaoweimusic.ui.adapter.TopListResultAdapter;
import com.yt.yunxiaoweimusic.ui.base.BaseFragment;
import com.yt.yunxiaoweimusic.utils.AssetsUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class TopListAllFragment extends BaseFragment {

    private MainStates mStates;
    private SearchRequester searchRequester;
    public TopListAllResultAdapter topListAllResultAdapter;

    @Override
    protected void initViewModel() {
        mStates = getFragmentScopeViewModel(MainStates.class);
        searchRequester = getFragmentScopeViewModel(SearchRequester.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        topListAllResultAdapter = new TopListAllResultAdapter(getContext());
        topListAllResultAdapter.setOnItemClickListener(new BaseDataBindingAdapter.OnItemClickListener<TopListResponsePayload.GroupTop>() {
            @Override
            public void onItemClick(int viewId, TopListResponsePayload.GroupTop item, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("topId", item.topId);
                nav().navigate(R.id.action_topListAllFragment_to_topListDetailFragment, bundle);
            }
        });
        return new DataBindingConfig(R.layout.fragment_toplist_all, BR.vm, mStates)
                .addBindingParam(BR.topListAllAdapter, topListAllResultAdapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchRequester.getTopListResult().observe(this, dataResult -> {
            List<TopListResponsePayload.Group> groupList = dataResult.getResult();
            List<TopListResponsePayload.GroupTop> groupTopList = new ArrayList<>();
            for (TopListResponsePayload.Group group : groupList) {
                for (TopListResponsePayload.GroupTop groupTop : group.groupTopList) {
                    groupTopList.add(groupTop);
                }
            }
            mStates.groupTopList.set(groupTopList);
        });
        new ClickProxy().getTopList();
    }

    public class ClickProxy {
        public void getTopList() {
            searchRequester.getTopList(getContext());
        }
    }

    public static class MainStates extends ViewModel {
        public final State<List<TopListResponsePayload.GroupTop>> groupTopList = new State<>(null);
    }

}
