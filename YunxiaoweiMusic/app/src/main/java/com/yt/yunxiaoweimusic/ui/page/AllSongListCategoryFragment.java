package com.yt.yunxiaoweimusic.ui.page;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.kunminx.architecture.ui.state.State;
import com.yt.yunxiaoweimusic.BR;
import com.yt.yunxiaoweimusic.R;
import com.yt.yunxiaoweimusic.data.bean.GetSongListCategory;
import com.yt.yunxiaoweimusic.data.bean.GroupInfo;
import com.yt.yunxiaoweimusic.data.bean.category.ChildCategory;
import com.yt.yunxiaoweimusic.data.bean.category.GroupCategory;
import com.yt.yunxiaoweimusic.data.bean.category.CategoryItem;
import com.yt.yunxiaoweimusic.data.response.DataResult;
import com.yt.yunxiaoweimusic.domain.request.SearchRequester;
import com.yt.yunxiaoweimusic.ui.adapter.AllSongListCategoryAdapter;
import com.yt.yunxiaoweimusic.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class AllSongListCategoryFragment extends BaseFragment {
    private final int TYPE_GROUP = 0xa01;
    private final int TYPE_CHILD = 0xa02;
    private MainStates mStates;
    private SearchRequester searchRequester;
    private List<CategoryItem> list = new ArrayList<>();
    private AllSongListCategoryAdapter mAdapter;

    @Override
    protected void initViewModel() {
        mStates = getFragmentScopeViewModel(MainStates.class);
        searchRequester = getFragmentScopeViewModel(SearchRequester.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_all_song_list_category, BR.vm, mStates);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rv = view.findViewById(R.id.rv);
        searchRequester.getSongListCategoryResult().observe(this, new Observer<DataResult<GetSongListCategory>>() {
            @Override
            public void onChanged(DataResult<GetSongListCategory> getSongListCategoryDataResult) {
                List<GroupInfo> groupInfos = getSongListCategoryDataResult.getResult().groupInfos;
                for (GroupInfo groupInfo : groupInfos) {
                    GroupCategory group = new GroupCategory();
                    group.groupName = groupInfo.groupName;
                    list.add(group);
                    for (GroupInfo.Label label : groupInfo.labelInfo) {
                        ChildCategory child = new ChildCategory();
                        child.id = label.id;
                        child.name = label.name;
                        list.add(child);
                    }
                }
                mAdapter.setData(list);
                mAdapter.notifyDataSetChanged();
            }
        });
        getSongListCategory(1, 0);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4, GridLayoutManager.VERTICAL, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (list.get(position).getType() == TYPE_GROUP) {
                    return 4;   //如果是标题 item 则跨四列
                } else {
                    return 1;   //如果是内容 item 则不夸咧
                }
            }
        });
        mAdapter = new AllSongListCategoryAdapter();
        mAdapter.setOnItemClickListener((viewId, item) -> {
            ChildCategory c = (ChildCategory) item;
            Bundle bundle = new Bundle();
            bundle.putInt("categoryId", c.id);
            nav().navigate(R.id.action_allSongListCategoryFragment_to_allSongListByCategoryFragment, bundle);
        });
        rv.setLayoutManager(gridLayoutManager);
        rv.setAdapter(mAdapter);
    }

    public void getSongListCategory(int cmd, int category) {
        searchRequester.getSongListCategory(getContext(), cmd, category);
    }

    public static class MainStates extends ViewModel {
        public final State<String> content = new State<>("1");
    }

}
