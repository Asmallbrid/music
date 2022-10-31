package com.yt.yunxiaoweimusic.ui.adapter;

import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yt.yunxiaoweimusic.R;
import com.yt.yunxiaoweimusic.data.bean.category.ChildCategory;
import com.yt.yunxiaoweimusic.data.bean.category.GroupCategory;
import com.yt.yunxiaoweimusic.data.bean.category.CategoryItem;

import java.util.ArrayList;
import java.util.List;

public class AllSongListCategoryAdapter extends RecyclerView.Adapter<AllSongListCategoryAdapter.ItemVH> {
    private static final int TYPE_GROUP = 0xa01;
    private static final int TYPE_CHILD = 0xa02;
    private List<CategoryItem> list = new ArrayList<>();
    protected OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setData(List<CategoryItem> list){
        this.list = list;
    }

    @Override
    public ItemVH onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemVH itemVH = null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_category_name_item, parent, false);
        switch (viewType) {
            case TYPE_GROUP:
                itemVH = new GroupVH(view);
                break;

            case TYPE_CHILD:
                itemVH = new ChildVH(view);
                break;
        }
        return itemVH;
    }

    @Override
    public void onBindViewHolder(ItemVH holder, int position) {
        CategoryItem item = list.get(position);
        switch (getItemViewType(position)) {
            case TYPE_GROUP:
                GroupCategory g = (GroupCategory) item;
                GroupVH groupVH = (GroupVH) holder;
                groupVH.text.setText(g.groupName);
                break;

            case TYPE_CHILD:
                ChildCategory c = (ChildCategory) item;
                ChildVH childVH = (ChildVH) holder;
                childVH.text.setText(c.name);
                holder.itemView.setOnClickListener(v -> {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(holder.itemView.getId(), item);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    public class GroupVH extends ItemVH {
        public TextView text;

        public GroupVH(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.tv_name);
            text.setTextSize(25);
            text.setGravity(Gravity.CENTER_VERTICAL);
            text.setTextColor(Color.BLACK);
            text.setPadding(20, 20, 0, 0);
        }

        @Override
        public int getType() {
            return TYPE_GROUP;
        }
    }

    public class ChildVH extends ItemVH {
        public TextView text;

        public ChildVH(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.tv_name);
            text.setTextSize(12);
            text.setGravity(Gravity.CENTER);
            text.setTextColor(Color.LTGRAY);
        }

        @Override
        public int getType() {
            return TYPE_CHILD;
        }
    }

    public abstract class ItemVH extends RecyclerView.ViewHolder {
        public ItemVH(View itemView) {
            super(itemView);
        }

        public abstract int getType();
    }

    public interface OnItemClickListener {
        void onItemClick(int viewId, CategoryItem item);
    }

}
