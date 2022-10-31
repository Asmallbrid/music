package com.yt.yunxiaoweimusic.ui.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kunminx.binding_recyclerview.adapter.SimpleDataBindingAdapter;
import com.yt.yunxiaoweimusic.R;
import com.yt.yunxiaoweimusic.data.bean.GetSongListSelfPayload;
import com.yt.yunxiaoweimusic.databinding.AdapterCategorySonglistItemBinding;
import com.yt.yunxiaoweimusic.databinding.AdapterFavoriteSonglistItemBinding;

import org.jetbrains.annotations.NotNull;

public class CateGorySongListResultAdapter extends SimpleDataBindingAdapter<GetSongListSelfPayload.SongListInfo, AdapterCategorySonglistItemBinding> {

    public CateGorySongListResultAdapter(Context context) {
        super(context, R.layout.adapter_category_songlist_item, new DiffUtil.ItemCallback<GetSongListSelfPayload.SongListInfo>() {
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
    protected void onBindItem(AdapterCategorySonglistItemBinding binding, GetSongListSelfPayload.SongListInfo item, RecyclerView.ViewHolder holder) {
        binding.setVm(item);
    }
}
