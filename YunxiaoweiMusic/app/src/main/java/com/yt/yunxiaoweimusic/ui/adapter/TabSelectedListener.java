package com.yt.yunxiaoweimusic.ui.adapter;

import com.google.android.material.tabs.TabLayout;

public class TabSelectedListener implements TabLayout.OnTabSelectedListener {
    public OnTabClickListener onTabClickListener;

    public void setOnTabClickListener(OnTabClickListener onTabClickListener) {
        this.onTabClickListener = onTabClickListener;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (onTabClickListener != null) {
            onTabClickListener.onTabClick(tab.getPosition());
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public interface OnTabClickListener {
        void onTabClick(int position);
    }
}
