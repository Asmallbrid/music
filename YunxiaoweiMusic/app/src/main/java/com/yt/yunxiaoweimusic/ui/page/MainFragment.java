package com.yt.yunxiaoweimusic.ui.page;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
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
import com.youth.banner.Banner;
import com.youth.banner.indicator.RectangleIndicator;
import com.yt.yunxiaoweimusic.BR;
import com.yt.yunxiaoweimusic.R;
import com.yt.yunxiaoweimusic.data.bean.AccessToken;
import com.yt.yunxiaoweimusic.data.bean.GetSongListDetailResponsePayload;
import com.yt.yunxiaoweimusic.data.bean.GetSongListSelfPayload;
import com.yt.yunxiaoweimusic.data.bean.RecHomePageResponsePayload;
import com.yt.yunxiaoweimusic.data.bean.SingerBean;
import com.yt.yunxiaoweimusic.data.bean.SingerListResponsePayload;
import com.yt.yunxiaoweimusic.data.bean.TopListResponsePayload;
import com.yt.yunxiaoweimusic.data.response.DataResult;
import com.yt.yunxiaoweimusic.domain.request.BindAccountRequester;
import com.yt.yunxiaoweimusic.domain.request.SearchRequester;
import com.yt.yunxiaoweimusic.player.PlayerManager;
import com.yt.yunxiaoweimusic.ui.adapter.CateGorySongListResultAdapter;
import com.yt.yunxiaoweimusic.ui.adapter.HomePageHotSongResultAdapter;
import com.yt.yunxiaoweimusic.ui.adapter.SingerListResultAdapter;
import com.yt.yunxiaoweimusic.ui.adapter.TabSelectedListener;
import com.yt.yunxiaoweimusic.ui.adapter.TopListResultAdapter;
import com.yt.yunxiaoweimusic.ui.base.BaseFragment;
import com.yt.yunxiaoweimusic.ui.bind.CustomBannerAdapter;
import com.yt.yunxiaoweimusic.utils.QREncode;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends BaseFragment {

    private MainStates mStates;
    private BindAccountRequester bindAccountRequester;
    private SearchRequester searchRequester;
    public Banner banner;
    public SingerListResultAdapter singerListResultAdapter;
    public HomePageHotSongResultAdapter homepageHotSongAdapter;
    public TopListResultAdapter topListResultAdapter;
    public CateGorySongListResultAdapter mCateGorySongListResultAdapter;
    private TabSelectedListener mTabSelectedListener;
    public ClickProxy clickProxy;

    @Override
    protected void initViewModel() {
        mStates = getFragmentScopeViewModel(MainStates.class);
        bindAccountRequester = getFragmentScopeViewModel(BindAccountRequester.class);
        searchRequester = getFragmentScopeViewModel(SearchRequester.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        clickProxy = new ClickProxy();
        mTabSelectedListener = new TabSelectedListener();
        mTabSelectedListener.setOnTabClickListener(new TabSelectedListener.OnTabClickListener() {
            @Override
            public void onTabClick(int position) {
                Log.e("zzq", "position:" + position);
                switch (position) {
                    case 0:
                        clickProxy.getSongListCategory(2, 68);
                        break;
                    case 1:
                        clickProxy.getSongListCategory(2, 4);
                        break;
                    case 2:
                        clickProxy.getSongListCategory(2, 49);
                        break;
                    case 3:
                        clickProxy.getSongListCategory(2, 33);
                        break;
                    case 4:
                        clickProxy.getSongListCategory(2, 41);
                        break;
                }
                mStates.categorySongList.set(null);
            }
        });
        singerListResultAdapter = new SingerListResultAdapter(getContext());
        singerListResultAdapter.setOnItemClickListener((viewId, item, position) -> {
            Bundle bundle = new Bundle();
            bundle.putInt("singerID", item.singerId);
            nav().navigate(R.id.action_mainFragment_to_singerDetailFragment, bundle);
        });
        topListResultAdapter = new TopListResultAdapter(getContext());
        topListResultAdapter.setOnItemClickListener(new BaseDataBindingAdapter.OnItemClickListener<TopListResponsePayload.Group>() {
            @Override
            public void onItemClick(int viewId, TopListResponsePayload.Group item, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("topId", item.groupTopList.get(0).topId);
                nav().navigate(R.id.action_mainFragment_to_topListDetailFragment, bundle);
            }
        });
        mCateGorySongListResultAdapter = new CateGorySongListResultAdapter(getContext());
        mCateGorySongListResultAdapter.setOnItemClickListener((viewId, item, position) -> {
            Bundle bundle = new Bundle();
            bundle.putLong("songListId", item.songListId);
            nav().navigate(R.id.action_mainFragment_to_songListFragment, bundle);
        });
        homepageHotSongAdapter = new HomePageHotSongResultAdapter(getContext());
        homepageHotSongAdapter.setOnItemClickListener((viewId, item, position) -> clickProxy.getSongDetailBatch(String.valueOf(item.id)));
        return new DataBindingConfig(R.layout.fragment_main, BR.vm, mStates)
                .addBindingParam(BR.singerListAdapter, singerListResultAdapter)
                .addBindingParam(BR.topListAdapter, topListResultAdapter)
                .addBindingParam(BR.songListCategoryAdapter, mCateGorySongListResultAdapter)
                .addBindingParam(BR.tabSelectedListener, mTabSelectedListener)
                .addBindingParam(BR.hotSongAdapter, homepageHotSongAdapter)
                .addBindingParam(BR.click, clickProxy);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getLifecycle().addObserver(bindAccountRequester);
        getLifecycle().addObserver(searchRequester);
        banner = view.findViewById(R.id.banner);
        banner.setIndicator(new RectangleIndicator(getContext()));
        banner.isAutoLoop(true);

        bindAccountRequester.getTokenResult().observe(this, dataResult -> {
            mStates.accessToken.set(dataResult.getResult());
            AccessToken.getInstance().setAuthorization(dataResult.getResult());
            clickProxy.getQrCode();
            clickProxy.recSongListHomepage("ddd");
            clickProxy.recSongHomepage("ddd");
            clickProxy.getSingerList();
            clickProxy.getTopList();
            clickProxy.getSongListCategory(2, 68);
        });

        bindAccountRequester.getQrCodeResult().observe(this, dataResult -> {
            Bitmap bmp = QREncode.createQRCodeBitmap(dataResult.getResult(), 100, 100);
            mStates.bitMap.set(bmp);
            mStates.isLogin.set(true);
        });
        searchRequester.getRcvSongListHomePageResult().observe(this, dataResult -> {
            mStates.recSongListHomePage.set(dataResult.getResult());
            CustomBannerAdapter bannerAdapter = new CustomBannerAdapter(getContext(), mStates.recSongListHomePage.get());
            bannerAdapter.setOnItemClickListener(data -> {
                Bundle bundle = new Bundle();
                bundle.putLong("songListId", Long.parseLong(data.id));
                nav().navigate(R.id.action_mainFragment_to_songListFragment, bundle);
            });
            banner.setAdapter(bannerAdapter);
        });

        searchRequester.getRcvSongHomePageResult().observe(this, dataResult -> {
            mStates.recSongHomePage.set(dataResult.getResult());
        });

        searchRequester.getSingerListResult().observe(this, dataResult -> {
            mStates.singerList.set(dataResult.getResult());
        });
        searchRequester.getTopListResult().observe(this, dataResult -> {
            List<TopListResponsePayload.Group> groupList = dataResult.getResult();
            mStates.topListAll.set(groupList);
            //返回四个，首页只取三个
            groupList.remove(groupList.size() - 1);
            mStates.topList.set(groupList);
        });
        searchRequester.getSongListCategoryResult().observe(this, dataResult -> {
            mStates.categorySongList.set(dataResult.getResult().songListInfos);
        });
        searchRequester.getSongDetailBatchResult().observe(this, new Observer<DataResult<GetSongListDetailResponsePayload>>() {
            @Override
            public void onChanged(DataResult<GetSongListDetailResponsePayload> dataResult) {
                PlayerManager.getInstance().next();
            }
        });
    }

    public class ClickProxy {
        public void bindAccount() {
            bindAccountRequester.bindAccount(getContext());
        }

        public void getQrCode() {
            bindAccountRequester.getQrCode(getContext());
        }

        public void search() {
            if (!mStates.searchKeyWord.get().equals("")) {
                Bundle bundle = new Bundle();
                bundle.putString("keyWork", mStates.searchKeyWord.get());
                nav().navigate(R.id.action_mainFragment_to_searchFragment, bundle);
            }
        }

        public void recSongListHomepage(String sn) {
            searchRequester.recSongListHomepage(getContext(), sn);
        }

        public void recSongHomepage(String sn) {
            searchRequester.recSongHomepage(getContext(), sn);
        }

        public void getSingerList() {
            searchRequester.getSingerList(getContext());
        }

        public void getTopList() {
            searchRequester.getTopList(getContext());
        }

        public void getSongListCategory(int cmd, int category) {
            searchRequester.getSongListCategory(getContext(), cmd, category);
        }

        public void getSongDetailBatch(String songId) {
            searchRequester.getSongDetailBatch(getContext(), songId);
        }

        public void gotoFavoriteSong() {
            nav().navigate(R.id.action_mainFragment_to_favoriteSongFragment);
        }

        public void gotoFavoriteSongList() {
            nav().navigate(R.id.action_mainFragment_to_favoriteSongListFragment);
        }

        public void gotoTopListDetail() {
            nav().navigate(R.id.action_mainFragment_to_topListAllFragment);
        }

        public void gotoSingerListDetail() {
            nav().navigate(R.id.action_mainFragment_to_singerListFragment);
        }
        public void gotoAllSongListCategory() {
            nav().navigate(R.id.action_mainFragment_to_allSongListCategoryFragment);
        }
    }

    public static class MainStates extends ViewModel {
        public final State<String> searchKeyWord = new State<>("");
        public final State<String> accessToken = new State<>("");
        public final State<Bitmap> bitMap = new State<>(null);
        public final State<Boolean> isLogin = new State<>(false);
        public final State<List<RecHomePageResponsePayload.Card>> recSongListHomePage = new State<>(null);
        public final State<List<RecHomePageResponsePayload.Card>> recSongHomePage = new State<>(null);
        public final State<List<SingerBean>> singerList = new State<>(null);
        public final State<List<TopListResponsePayload.Group>> topList = new State<>(null);
        public final State<List<TopListResponsePayload.Group>> topListAll = new State<>(null);
        public final State<List<GetSongListSelfPayload.SongListInfo>> categorySongList = new State<>(new ArrayList<>());
    }
}
