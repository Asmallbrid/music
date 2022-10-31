package com.yt.yunxiaoweimusic.ui.adapter;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kunminx.binding_recyclerview.adapter.SimpleDataBindingAdapter;
import com.yt.yunxiaoweimusic.R;
import com.yt.yunxiaoweimusic.data.bean.GetSongListSelfPayload;
import com.yt.yunxiaoweimusic.data.bean.SearchSongListPayload;
import com.yt.yunxiaoweimusic.databinding.AdapterFavoriteSonglistItemBinding;
import com.yt.yunxiaoweimusic.databinding.AdapterSearchSonglistItemBinding;

import org.jetbrains.annotations.NotNull;

public class FavoriteSongListResultAdapter extends SimpleDataBindingAdapter<GetSongListSelfPayload.SongListInfo, AdapterFavoriteSonglistItemBinding> {

    public FavoriteSongListResultAdapter(Context context) {
        super(context, R.layout.adapter_favorite_songlist_item, new DiffUtil.ItemCallback<GetSongListSelfPayload.SongListInfo>() {
            @Override
            public boolean areItemsTheSame(@NonNull @NotNull GetSongListSelfPayload.SongListInfo oldItem, @NonNull @NotNull GetSongListSelfPayload.SongListInfo newItem) {
                return true;
            }

            @Override
            public boolean areContentsTheSame(@NonNull @NotNull GetSongListSelfPayload.SongListInfo oldItem, @NonNull @NotNull GetSongListSelfPayload.SongListInfo newItem) {
                return true;
            }
        });
    }

    @Override
    protected void onBindItem(AdapterFavoriteSonglistItemBinding binding, GetSongListSelfPayload.SongListInfo item, RecyclerView.ViewHolder holder) {
        binding.setVm(item);
    }
}
