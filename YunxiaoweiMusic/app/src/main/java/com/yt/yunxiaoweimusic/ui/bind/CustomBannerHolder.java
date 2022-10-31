package com.yt.yunxiaoweimusic.ui.bind;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yt.yunxiaoweimusic.R;

import org.jetbrains.annotations.NotNull;

public class CustomBannerHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView textView;

    public CustomBannerHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.img_banner);
        textView = itemView.findViewById(R.id.tv_title);
    }
}
