package com.yt.yunxiaoweimusic.ui.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kunminx.binding_recyclerview.adapter.SimpleDataBindingAdapter;
import com.yt.yunxiaoweimusic.R;
import com.yt.yunxiaoweimusic.data.bean.SearchSongListPayload;
import com.yt.yunxiaoweimusic.databinding.AdapterSearchSonglistItemBinding;

import org.jetbrains.annotations.NotNull;

public class SongListResultAdapter extends SimpleDataBindingAdapter<SearchSongListPayload.SongListBean, AdapterSearchSonglistItemBinding> {

    public SongListResultAdapter(Context context) {
        super(context, R.layout.adapter_search_songlist_item, new DiffUtil.ItemCallback<SearchSongListPayload.SongListBean>() {
            @Override
            public boolean areItemsTheSame(@NonNull @NotNull SearchSongListPayload.SongListBean oldItem, @NonNull @NotNull SearchSongListPayload.SongListBean newItem) {
                return true;
            }

            @Override
            public boolean areContentsTheSame(@NonNull @NotNull SearchSongListPayload.SongListBean oldItem, @NonNull @NotNull SearchSongListPayload.SongListBean newItem) {
                return true;
            }
        });
    }

    @Override
    protected void onBindItem(AdapterSearchSonglistItemBinding binding, SearchSongListPayload.SongListBean item, RecyclerView.ViewHolder holder) {
        binding.setVm(item);
    }
}
