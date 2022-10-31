package com.yt.yunxiaoweimusic.ui.bind;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class TextAdapter {
    @BindingAdapter(value = "txt")
    public static void setText(TextView tv,String s){
        tv.setText(s);
    }
}
