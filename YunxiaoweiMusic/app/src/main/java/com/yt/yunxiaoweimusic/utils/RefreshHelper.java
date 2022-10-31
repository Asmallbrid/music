package com.yt.yunxiaoweimusic.utils;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.yt.yunxiaoweimusic.R;
import com.yt.yunxiaoweimusic.ui.view.NeteaseLoadMoreView;
import com.yt.yunxiaoweimusic.ui.view.NeteaseRefreshHeaderView;

import me.jingbin.library.ByRecyclerView;
import me.jingbin.library.decoration.GridSpaceItemDecoration;
import me.jingbin.library.decoration.SpacesItemDecoration;

public class RefreshHelper {

    public static void initStaggeredGrid(ByRecyclerView recyclerView, int spanCount, int spacing) {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL));
        // 如果每个item高度一致设置后效率更高
        recyclerView.setHasFixedSize(true);
//        recyclerView.setItemAnimator(null);
        recyclerView.addItemDecoration(new GridSpaceItemDecoration(spacing));
        recyclerView.setRefreshHeaderView(new NeteaseRefreshHeaderView(recyclerView.getContext()));
        recyclerView.setLoadingMoreView(new NeteaseLoadMoreView(recyclerView.getContext()));
    }

    public static ByRecyclerView initLinear(ByRecyclerView recyclerView, boolean isDivider, int headerNoShowSize) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
//        recyclerView.setItemAnimator(null);
        if (isDivider) {
            recyclerView.addItemDecoration(new SpacesItemDecoration(recyclerView.getContext(), SpacesItemDecoration.VERTICAL, headerNoShowSize).setDrawable(R.drawable.shape_line));
        }
        recyclerView.setRefreshHeaderView(new NeteaseRefreshHeaderView(recyclerView.getContext()));
        recyclerView.setLoadingMoreView(new NeteaseLoadMoreView(recyclerView.getContext()));
        return recyclerView;
    }

    public static ByRecyclerView setDefaultAnimator(ByRecyclerView recyclerView) {
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return recyclerView;
    }
}
