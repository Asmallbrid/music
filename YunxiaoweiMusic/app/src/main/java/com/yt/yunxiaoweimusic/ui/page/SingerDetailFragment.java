package com.yt.yunxiaoweimusic.ui.page;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.viewpager.widget.ViewPager;

import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.kunminx.architecture.ui.state.State;
import com.kunminx.binding_recyclerview.adapter.BaseDataBindingAdapter;
import com.yt.yunxiaoweimusic.BR;
import com.yt.yunxiaoweimusic.R;
import com.yt.yunxiaoweimusic.data.bean.Album;
import com.yt.yunxiaoweimusic.data.bean.GetSingerAlbumResponsePayload;
import com.yt.yunxiaoweimusic.data.bean.SongBean;
import com.yt.yunxiaoweimusic.data.response.DataResult;
import com.yt.yunxiaoweimusic.domain.request.SearchRequester;
import com.yt.yunxiaoweimusic.player.PlayerManager;
import com.yt.yunxiaoweimusic.ui.adapter.AlbumResultAdapter;
import com.yt.yunxiaoweimusic.ui.adapter.CommonViewPagerAdapter;
import com.yt.yunxiaoweimusic.ui.adapter.SongResultAdapter;
import com.yt.yunxiaoweimusic.ui.base.BaseFragment;
import com.yt.yunxiaoweimusic.utils.RefreshHelper;

import java.util.ArrayList;
import java.util.List;

import me.jingbin.library.ByRecyclerView;

public class SingerDetailFragment extends BaseFragment {

    private MainStates mStates;
    private SongResultAdapter mSongAdapter;
    private AlbumResultAdapter mAlbumAdapter;
    private SearchRequester searchRequester;
    private ViewPager viewPager;
    private Button btnSong, btnAlbum;

    @Override
    protected void initViewModel() {
        mStates = getFragmentScopeViewModel(MainStates.class);
        searchRequester = getFragmentScopeViewModel(SearchRequester.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        mSongAdapter = new SongResultAdapter(getContext());
        mAlbumAdapter = new AlbumResultAdapter(getContext());
        return new DataBindingConfig(R.layout.fragment_singer_detail, BR.vm, mStates)
                .addBindingParam(BR.click, new ClickProxy())
                .addBindingParam(BR.songAdapter, mSongAdapter)
                .addBindingParam(BR.albumAdapter, mAlbumAdapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = view.findViewById(R.id.view_pager);
        btnSong = view.findViewById(R.id.btnSong);
        btnAlbum = view.findViewById(R.id.btnAlbum);
        viewPager.setAdapter(new CommonViewPagerAdapter(false, new String[]{"歌曲", "专辑"}));

        ByRecyclerView songRv = view.findViewById(R.id.songRv);
        songRv.setOnItemClickListener((v, position) -> {
            PlayerManager.getInstance().addMusic(mStates.songResult.get().get(position));
            PlayerManager.getInstance().next();
        });
        RefreshHelper.initLinear(songRv, true, 1);
        songRv.setOnRefreshListener(() -> {
            mStates.songResult.set(null);
            mSongAdapter.notifyDataSetChanged();
            songPage = 0;
            getSingerDetail();
        });
        songRv.setOnLoadMoreListener(() -> {
            ++songPage;
            getSingerDetail();
            mSongAdapter.notifyDataSetChanged();
        });
        ByRecyclerView albumRv = view.findViewById(R.id.albumRv);
        RefreshHelper.initLinear(songRv, true, 1);
        albumRv.setOnRefreshListener(() -> {
            mStates.albumResult.set(null);
            mAlbumAdapter.notifyDataSetChanged();
            albumPage = 0;
            getSingerAlbum();
        });
        albumRv.setOnLoadMoreListener(false, () -> {
            ++albumPage;
            SingerDetailFragment.this.getSingerAlbum();
            mAlbumAdapter.notifyDataSetChanged();
        });

        Bundle arguments = getArguments();
        mStates.singerID.set(arguments.getInt("singerID"));
        Log.e("zzq", "singerID:" + arguments.getInt("singerID"));
        searchRequester.getSingerDetailResult().observe(this, getSingerDetailResponsePayloadDataResult -> {
            if (getSingerDetailResponsePayloadDataResult != null && getSingerDetailResponsePayloadDataResult.getResult().songInfos != null) {
                songRv.setRefreshing(false);
                if (songPage == 0) {
                    songList = getSingerDetailResponsePayloadDataResult.getResult().songInfos;
                } else {
                    songList.addAll(getSingerDetailResponsePayloadDataResult.getResult().songInfos);
                }
                songRv.loadMoreComplete();
            } else {
                if (songPage > 1) songPage--;
                songRv.loadMoreEnd();
            }
            mStates.songNum.set(getSingerDetailResponsePayloadDataResult.getResult().songNum);
            mStates.singerPic.set(getSingerDetailResponsePayloadDataResult.getResult().singerPic);
            mStates.songResult.set(songList);
        });

        searchRequester.getSingerAlbumResult().observe(this, new Observer<DataResult<GetSingerAlbumResponsePayload>>() {
            @Override
            public void onChanged(DataResult<GetSingerAlbumResponsePayload> getSingerAlbumResponsePayloadDataResult) {
                if (getSingerAlbumResponsePayloadDataResult != null && getSingerAlbumResponsePayloadDataResult.getResult().albumList != null) {
                    albumRv.setRefreshing(false);
                    if (albumPage == 0) {
                        albumList = getSingerAlbumResponsePayloadDataResult.getResult().albumList;
                    } else {
                        albumList.addAll(getSingerAlbumResponsePayloadDataResult.getResult().albumList);
                    }
                    albumRv.loadMoreComplete();
                } else {
                    if (albumPage > 1) albumPage--;
                    albumRv.loadMoreEnd();
                }
                mStates.albumNum.set(getSingerAlbumResponsePayloadDataResult.getResult().totalNum);
                mStates.albumResult.set(albumList);
            }
        });
        getSingerDetail();
        getSingerAlbum();
    }

    public void getSingerDetail() {
        searchRequester.getSingerDetail(getContext(), mStates.singerID.get(), songPage, 20);
    }

    public void getSingerAlbum() {
        searchRequester.getSingerAlbum(getContext(), mStates.singerID.get(), albumPage, 20);
    }

    public class ClickProxy {
        public void switchViewPager(int page) {
            btnSong.setTextColor(page == 0 ? getContext().getColor(R.color.orange) : getContext().getColor(R.color.black));
            btnAlbum.setTextColor(page == 1 ? getContext().getColor(R.color.orange) : getContext().getColor(R.color.black));
            viewPager.setCurrentItem(page);
        }
    }

    public int songPage = 0;
    public int albumPage = 0;
    public List<SongBean> songList;
    public List<Album> albumList;

    public static class MainStates extends ViewModel {
        public final State<String> content = new State<>("1");
        public final State<Integer> singerID = new State<>(0);
        public final State<Integer> songPage = new State<>(0);
        public final State<String> singerPic = new State<>("");
        public final State<Integer> songNum = new State<>(0);
        public final State<Integer> albumNum = new State<>(0);
        public final State<String> songListName = new State<>("");
        public final State<List<SongBean>> songResult = new State<>(new ArrayList<>());
        public final State<List<Album>> albumResult = new State<>(new ArrayList<>());
    }

}
