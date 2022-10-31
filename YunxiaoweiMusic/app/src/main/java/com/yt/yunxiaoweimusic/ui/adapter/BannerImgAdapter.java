package com.yt.yunxiaoweimusic.ui.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.youth.banner.adapter.BannerAdapter;
import com.yt.yunxiaoweimusic.data.bean.SearchCustomPayload;
import com.yt.yunxiaoweimusic.data.bean.SongBean;

import java.util.List;

public class BannerImgAdapter extends BannerAdapter<SongBean, BannerImgAdapter.BannerViewHolder> {


    public BannerImgAdapter(List<SongBean> datas) {
        super(datas);
    }

    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        RoundedImageView roundedImageView = new RoundedImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        roundedImageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        roundedImageView.setScaleType(ImageView.ScaleType.FIT_START);
        return new BannerViewHolder(roundedImageView);
    }

    @Override
    public void onBindView(BannerViewHolder holder, SongBean data, int position, int size) {
        Glide.with(holder.imageView)
                .load(data.albumPic)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                .into(holder.imageView);
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView imageView;

        public BannerViewHolder(@NonNull RoundedImageView view) {
            super(view);
            this.imageView = view;
        }
    }
}
