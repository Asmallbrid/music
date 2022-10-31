package com.yt.yunxiaoweimusic.ui.page;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.kunminx.architecture.ui.state.State;
import com.kunminx.binding_recyclerview.adapter.BaseDataBindingAdapter;
import com.yt.yunxiaoweimusic.BR;
import com.yt.yunxiaoweimusic.R;
import com.yt.yunxiaoweimusic.data.bean.SingerBean;
import com.yt.yunxiaoweimusic.data.bean.SingerListResponsePayload;
import com.yt.yunxiaoweimusic.domain.request.SearchRequester;
import com.yt.yunxiaoweimusic.ui.adapter.SingerListResultAdapter;
import com.yt.yunxiaoweimusic.ui.adapter.TabSelectedListener;
import com.yt.yunxiaoweimusic.ui.base.BaseFragment;

import java.util.List;

public class SingerListFragment extends BaseFragment {

    private MainStates mStates;
    private SearchRequester searchRequester;
    public SingerListResultAdapter singerListResultAdapter;
    private TabSelectedListener mTabAreaSelectedListener, mTabTypeSelectedListener, mTabGenreSelectedListener;
    private int[] _areas, _types, _genres;

    @Override
    protected void initViewModel() {
        mStates = getFragmentScopeViewModel(MainStates.class);
        searchRequester = getFragmentScopeViewModel(SearchRequester.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        singerListResultAdapter = new SingerListResultAdapter(getContext());
        singerListResultAdapter.setOnItemClickListener((viewId, item, position) -> {
            Bundle bundle = new Bundle();
            bundle.putInt("singerID", item.singerId);
            nav().navigate(R.id.action_singerListFragment_to_singerDetailFragment, bundle);
        });
        mTabAreaSelectedListener = new TabSelectedListener();
        mTabTypeSelectedListener = new TabSelectedListener();
        mTabGenreSelectedListener = new TabSelectedListener();
        mTabAreaSelectedListener.setOnTabClickListener(position -> {
            mStates.area.set(_areas[position]);
            getSingerList(mStates.area.get(), mStates.type.get(), mStates.genre.get());
        });
        mTabTypeSelectedListener.setOnTabClickListener(position -> {
            mStates.type.set(_types[position]);
            getSingerList(mStates.area.get(), mStates.type.get(), mStates.genre.get());
        });
        mTabGenreSelectedListener.setOnTabClickListener(position -> {
            mStates.genre.set(_genres[position]);
            getSingerList(mStates.area.get(), mStates.type.get(), mStates.genre.get());
        });
        return new DataBindingConfig(R.layout.fragment_singer_list, BR.vm, mStates)
                .addBindingParam(BR.singerListAdapter, singerListResultAdapter)
                .addBindingParam(BR.tabAreaSelectedListener, mTabAreaSelectedListener)
                .addBindingParam(BR.tabTypeSelectedListener, mTabTypeSelectedListener)
                .addBindingParam(BR.tabGenreSelectedListener, mTabGenreSelectedListener);
    }

    @SuppressLint({"NotifyDataSetChanged", "ResourceType"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _areas = getResources().getIntArray(R.array.singer_area);
        _types = getResources().getIntArray(R.array.singer_type);
        _genres = getResources().getIntArray(R.array.singer_genre);
        searchRequester.getSingerListResult().observe(this, dataResult -> {
            mStates.singerList.set(dataResult.getResult());
        });
        getSingerList();
    }

    public void getSingerList() {
        searchRequester.getSingerList(getContext());
    }

    public void getSingerList(int area, int type, int genre) {
        mStates.singerList.set(null);
        searchRequester.getSingerList(getContext(), area, type, genre);
    }

    public static class MainStates extends ViewModel {
        public final State<List<SingerBean>> singerList = new State<>(null);
        public final State<Integer> area = new State<>(-100);
        public final State<Integer> type = new State<>(-100);
        public final State<Integer> genre = new State<>(-100);
    }

}
