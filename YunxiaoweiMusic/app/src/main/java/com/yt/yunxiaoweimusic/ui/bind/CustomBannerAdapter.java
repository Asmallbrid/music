package com.yt.yunxiaoweimusic.ui.bind;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.adapter.BannerAdapter;
import com.yt.yunxiaoweimusic.R;
import com.yt.yunxiaoweimusic.data.bean.RecHomePageResponsePayload;

import java.util.List;

public class CustomBannerAdapter extends BannerAdapter<RecHomePageResponsePayload.Card, CustomBannerHolder> {
    public Context context;
    public OnItemClickListener onItemClickListener;

    public CustomBannerAdapter(Context context, List<RecHomePageResponsePayload.Card> datas) {
        super(datas);
        this.context = context;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public CustomBannerHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_banner_item, parent, false);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(lp);
        return new CustomBannerHolder(view);
    }

    @Override
    public void onBindView(CustomBannerHolder holder, RecHomePageResponsePayload.Card data, int position, int size) {
        holder.textView.setText(data.title);

        Glide.with(holder.itemView.getContext())
                .load(data.cover)
                .apply(new RequestOptions()
                        .transform(new CenterCrop(), new RoundedCorners(24)
                        ))
                .into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(data);
            }
        });
    }
    public interface OnItemClickListener{
        void onItemClick(RecHomePageResponsePayload.Card data);
    }
}
