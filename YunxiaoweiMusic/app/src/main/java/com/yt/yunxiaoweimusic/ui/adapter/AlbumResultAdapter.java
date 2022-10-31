package com.yt.yunxiaoweimusic.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.kunminx.binding_recyclerview.adapter.SimpleDataBindingAdapter;
import com.yt.yunxiaoweimusic.R;
import com.yt.yunxiaoweimusic.data.api.RequestUtils;
import com.yt.yunxiaoweimusic.data.bean.AccessToken;
import com.yt.yunxiaoweimusic.data.bean.Album;
import com.yt.yunxiaoweimusic.data.bean.BaseResponse;
import com.yt.yunxiaoweimusic.data.bean.RequestHeader;
import com.yt.yunxiaoweimusic.data.bean.SongBean;
import com.yt.yunxiaoweimusic.data.bean.TestPayload;
import com.yt.yunxiaoweimusic.data.bean.TestRequrst;
import com.yt.yunxiaoweimusic.data.repository.ResponseObserver;
import com.yt.yunxiaoweimusic.databinding.AdapterSearchAlbumItemBinding;
import com.yt.yunxiaoweimusic.databinding.AdapterSearchSongItemBinding;
import com.yt.yunxiaoweimusic.utils.AssetsUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AlbumResultAdapter extends SimpleDataBindingAdapter<Album, AdapterSearchAlbumItemBinding> {

    public Context context;

    public AlbumResultAdapter(Context context) {
        super(context, R.layout.adapter_search_album_item, new DiffUtil.ItemCallback<Album>() {
            @Override
            public boolean areItemsTheSame(Album oldItem, Album newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areContentsTheSame(Album oldItem, Album newItem) {
                return true;
            }
        });
        this.context = context;
    }

    @Override
    protected void onBindItem(AdapterSearchAlbumItemBinding binding, Album item, RecyclerView.ViewHolder holder) {
        binding.setVm(item);
    }
}
