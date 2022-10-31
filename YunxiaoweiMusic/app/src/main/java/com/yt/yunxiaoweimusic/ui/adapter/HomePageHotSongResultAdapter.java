package com.yt.yunxiaoweimusic.ui.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kunminx.binding_recyclerview.adapter.SimpleDataBindingAdapter;
import com.yt.yunxiaoweimusic.R;
import com.yt.yunxiaoweimusic.data.bean.RecHomePageResponsePayload;
import com.yt.yunxiaoweimusic.data.bean.SingerListResponsePayload;
import com.yt.yunxiaoweimusic.databinding.AdapterSearchHomepageSongHotBinding;
import com.yt.yunxiaoweimusic.databinding.AdapterSearchSingerlistItemBinding;

import org.jetbrains.annotations.NotNull;

public class HomePageHotSongResultAdapter extends SimpleDataBindingAdapter<RecHomePageResponsePayload.Card, AdapterSearchHomepageSongHotBinding> {

    public HomePageHotSongResultAdapter(Context context) {
        super(context, R.layout.adapter_search_homepage_song_hot, new DiffUtil.ItemCallback<RecHomePageResponsePayload.Card>() {
            @Override
            public boolean areItemsTheSame(@NonNull @NotNull RecHomePageResponsePayload.Card oldItem, @NonNull @NotNull RecHomePageResponsePayload.Card newItem) {
                return true;
            }

            @Override
            public boolean areContentsTheSame(@NonNull @NotNull RecHomePageResponsePayload.Card oldItem, @NonNull @NotNull RecHomePageResponsePayload.Card newItem) {
                return true;
            }
        });
    }

    @Override
    protected void onBindItem(AdapterSearchHomepageSongHotBinding binding, RecHomePageResponsePayload.Card item, RecyclerView.ViewHolder holder) {
        binding.setVm(item);
    }
}
