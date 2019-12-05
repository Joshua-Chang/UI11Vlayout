package com.wangyi.ui12player;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    private List<View> mDiscLayouts;

    public ViewPagerAdapter(List<View> mDiscLayouts) {
        this.mDiscLayouts = mDiscLayouts;
    }

    @Override
    public int getCount() {
        return mDiscLayouts.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View discLayout = mDiscLayouts.get(position);
        container.addView(discLayout);
        return discLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(mDiscLayouts.get(position));
    }
}
