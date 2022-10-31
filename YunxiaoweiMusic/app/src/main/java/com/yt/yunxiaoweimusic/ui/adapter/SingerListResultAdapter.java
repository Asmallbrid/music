package com.yt.yunxiaoweimusic.ui.adapter;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kunminx.binding_recyclerview.adapter.SimpleDataBindingAdapter;
import com.yt.yunxiaoweimusic.R;
import com.yt.yunxiaoweimusic.data.bean.SearchSongListPayload;
import com.yt.yunxiaoweimusic.data.bean.SingerBean;
import com.yt.yunxiaoweimusic.data.bean.SingerListResponsePayload;
import com.yt.yunxiaoweimusic.databinding.AdapterSearchSingerlistItemBinding;
import com.yt.yunxiaoweimusic.databinding.AdapterSearchSonglistItemBinding;

import org.jetbrains.annotations.NotNull;

public class SingerListResultAdapter extends SimpleDataBindingAdapter<SingerBean, AdapterSearchSingerlistItemBinding> {

    public SingerListResultAdapter(Context context) {
        super(context, R.layout.adapter_search_singerlist_item, new DiffUtil.ItemCallback<SingerBean>() {
            @Override
            public boolean areItemsTheSame(@NonNull @NotNull SingerBean oldItem, @NonNull @NotNull SingerBean newItem) {
                return true;
            }

            @Override
            public boolean areContentsTheSame(@NonNull @NotNull SingerBean oldItem, @NonNull @NotNull SingerBean newItem) {
                return true;
            }
        });
    }

    @Override
    protected void onBindItem(AdapterSearchSingerlistItemBinding binding, SingerBean item, RecyclerView.ViewHolder holder) {
        binding.setVm(item);
    }
}
