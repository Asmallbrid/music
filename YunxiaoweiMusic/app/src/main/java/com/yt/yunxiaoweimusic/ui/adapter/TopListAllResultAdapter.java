package com.yt.yunxiaoweimusic.ui.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kunminx.binding_recyclerview.adapter.SimpleDataBindingAdapter;
import com.yt.yunxiaoweimusic.R;
import com.yt.yunxiaoweimusic.data.bean.TopListResponsePayload;
import com.yt.yunxiaoweimusic.databinding.AdapterSearchToplistAllItemBinding;

import org.jetbrains.annotations.NotNull;

public class TopListAllResultAdapter extends SimpleDataBindingAdapter<TopListResponsePayload.GroupTop, AdapterSearchToplistAllItemBinding> {

    public TopListAllResultAdapter(Context context) {
        super(context, R.layout.adapter_search_toplist_all_item, new DiffUtil.ItemCallback<TopListResponsePayload.GroupTop>() {
            @Override
            public boolean areItemsTheSame(@NonNull @NotNull TopListResponsePayload.GroupTop oldItem, @NonNull @NotNull TopListResponsePayload.GroupTop newItem) {
                return true;
            }

            @Override
            public boolean areContentsTheSame(@NonNull @NotNull TopListResponsePayload.GroupTop oldItem, @NonNull @NotNull TopListResponsePayload.GroupTop newItem) {
                return true;
            }
        });
    }

    @Override
    protected void onBindItem(AdapterSearchToplistAllItemBinding binding, TopListResponsePayload.GroupTop item, RecyclerView.ViewHolder holder) {
        binding.setVm(item);
    }
}
