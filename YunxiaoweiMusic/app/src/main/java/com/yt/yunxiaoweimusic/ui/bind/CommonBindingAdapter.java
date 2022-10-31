package com.yt.yunxiaoweimusic.ui.bind;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.List;

import jp.wasabeef.glide.transformations.CropTransformation;
import me.jingbin.library.ByRecyclerView;

public class CommonBindingAdapter {
    @BindingAdapter(value = {"bitmap", "placeHolder"}, requireAll = false)
    public static void setBitmap(ImageView view, Bitmap bitmap, Drawable placeHolder) {
        if (bitmap != null) {
            Glide.with(view.getContext()).load(bitmap).placeholder(placeHolder).into(view);
        }
    }

    @BindingAdapter(value = {"url", "category", "corners", "crop"}, requireAll = false)
    public static void setImage(ImageView view, String url, String category, int corners, boolean crop) {
        if (category != null) {
            if (category.equals("circle")) {
                //圆形
                Glide.with(view.getContext()).load(url)
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(view);
            }
        } else if (corners == 0) {
            if (crop) {
                //裁剪图片
                Glide.with(view.getContext())
                        .load(url)
                        .apply(RequestOptions.bitmapTransform(new CropTransformation(100, 270, CropTransformation.CropType.CENTER)))
                        .into(view);
            } else {
                Glide.with(view.getContext()).load(url)
                        .into(view);
            }
        } else {
            //圆角
            Glide.with(view.getContext()).load(url)
                    .apply(new RequestOptions()
                            .transform(new CenterCrop(), new RoundedCorners(corners)
                            ))
                    .into(view);
        }
    }

    @BindingAdapter(value = {"panelShow"}, requireAll = false)
    public static void setPanelShow(SlidingUpPanelLayout view, boolean show) {
        if (show) view.setPanelHeight(100);
        else view.setPanelHeight(0);
    }

    @BindingAdapter(value = {"visible"}, requireAll = false)
    public static void visible(View view, boolean visible) {
        if (visible && view.getVisibility() == View.GONE) {
            view.setVisibility(View.VISIBLE);
        } else if (!visible && view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
        }
    }

    @BindingAdapter(value = {"size"}, requireAll = false)
    public static void size(View view, Pair<Integer, Integer> size) {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) view.getLayoutParams();
        params.width = size.first;
        params.height = size.second;
        view.setLayoutParams(params);
    }

    @BindingAdapter(value = {"transX"}, requireAll = false)
    public static void translationX(View view, float translationX) {
        view.setTranslationX(translationX);
    }

    @BindingAdapter(value = {"transY"}, requireAll = false)
    public static void translationY(View view, float translationY) {
        view.setTranslationY(translationY);
    }

    @BindingAdapter(value = {"x"}, requireAll = false)
    public static void x(View view, float x) {
        view.setX(x);
    }

    @BindingAdapter(value = {"y"}, requireAll = false)
    public static void y(View view, float y) {
        view.setY(y);
    }

    @BindingAdapter(value = {"alpha"}, requireAll = false)
    public static void alpha(View view, float alpha) {
        view.setAlpha(alpha);
    }

    @BindingAdapter(value = {"spanCount", "orientation"})
    public static void setLayoutManager(RecyclerView view, int spanCount, int orientation) {
        view.setLayoutManager(new StaggeredGridLayoutManager(spanCount, orientation));
    }
}
