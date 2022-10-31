package com.yt.yunxiaoweimusic.ui.adapter;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kunminx.binding_recyclerview.adapter.SimpleDataBindingAdapter;
import com.yt.yunxiaoweimusic.R;
import com.yt.yunxiaoweimusic.data.bean.SingerListResponsePayload;
import com.yt.yunxiaoweimusic.data.bean.TopListResponsePayload;
import com.yt.yunxiaoweimusic.databinding.AdapterSearchSingerlistItemBinding;
import com.yt.yunxiaoweimusic.databinding.AdapterSearchToplistItemBinding;

import org.jetbrains.annotations.NotNull;

public class TopListResultAdapter extends SimpleDataBindingAdapter<TopListResponsePayload.Group, AdapterSearchToplistItemBinding> {

    public TopListResultAdapter(Context context) {
        super(context, R.layout.adapter_search_toplist_item, new DiffUtil.ItemCallback<TopListResponsePayload.Group>() {
            @Override
            public boolean areItemsTheSame(@NonNull @NotNull TopListResponsePayload.Group oldItem, @NonNull @NotNull TopListResponsePayload.Group newItem) {
                return true;
            }

            @Override
            public boolean areContentsTheSame(@NonNull @NotNull TopListResponsePayload.Group oldItem, @NonNull @NotNull TopListResponsePayload.Group newItem) {
                return true;
            }
        });
    }

    @Override
    protected void onBindItem(AdapterSearchToplistItemBinding binding, TopListResponsePayload.Group item, RecyclerView.ViewHolder holder) {
        binding.setVm(item);
    }
}
