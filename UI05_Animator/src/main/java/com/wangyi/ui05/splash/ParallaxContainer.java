package com.wangyi.ui05.splash;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.nineoldandroids.view.ViewHelper;
import com.wangyi.ui05.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * 总控件（根布局）
 * --子控件是viewpager
 * ----viewpager内放fragment
 */
public class ParallaxContainer extends FrameLayout implements ViewPager.OnPageChangeListener {

    private List<ParallaxFragment>fragments;
    private ImageView iv_man;
    private ParallaxPagerAdapter adapter;

    public void setIv_man(ImageView iv_man) {
        this.iv_man = iv_man;
    }

    public ParallaxContainer(@NonNull Context context) {
        super(context);
    }

    public ParallaxContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ParallaxContainer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setUp(int ...childId){
        fragments=new ArrayList<>();
        for (int i = 0; i < childId.length; i++) {
            ParallaxFragment f=new ParallaxFragment();
            Bundle args=new Bundle();
            args.putInt("layoutId",childId[i]);
            f.setArguments(args);
            fragments.add(f);
        }
        ViewPager vp = new ViewPager(getContext());
        vp.setId(R.id.parallax_pager);

        SplashActivity activity= (SplashActivity) getContext();
        adapter = new ParallaxPagerAdapter(activity.getSupportFragmentManager(),fragments);
        vp.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        vp.setAdapter(adapter);
        vp.setOnPageChangeListener(this);
        addView(vp,0);
    }

    //positionOffset 偏移百分比
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int containerWidth = getWidth();
        ParallaxFragment outerFragment=null;
        ParallaxFragment inerFragment=null;
        try {//第一个没有in，所以异常try catch
            outerFragment= fragments.get(position - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            inerFragment= fragments.get(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (outerFragment != null) {
            List<View> outerViews = outerFragment.getParallaxViews();
            if (outerViews != null) {
                for (View view : outerViews) {
                    ParallaxViewTag tag = (ParallaxViewTag) view.getTag(R.id.parallax_view_tag);
                    if (tag == null) {
                        continue;
                    }
                    ViewHelper.setTranslationY(view,(containerWidth-positionOffsetPixels)*tag.xIn);
                    ViewHelper.setTranslationX(view,(containerWidth-positionOffsetPixels)*tag.yIn);
                }
            }
        }

        if (inerFragment != null) {
            List<View> inerViews = inerFragment.getParallaxViews();
            if (inerViews != null) {
                for (View view : inerViews) {
                    ParallaxViewTag tag = (ParallaxViewTag) view.getTag(R.id.parallax_view_tag);
                    if (tag == null) {
                        continue;
                    }
                    //移出为负数
                    ViewHelper.setTranslationY(view,(0-positionOffsetPixels)*tag.xOut);
                    ViewHelper.setTranslationX(view,(0-positionOffsetPixels)*tag.yOut);
                }
            }
        }

    }

    @Override
    public void onPageSelected(int position) {
        if (position==adapter.getCount()-1) {
            iv_man.setVisibility(INVISIBLE);
        }else {
            iv_man.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        AnimationDrawable animation = (AnimationDrawable) iv_man.getBackground();
        switch (state) {
            case ViewPager.SCROLL_STATE_DRAGGING:
                animation.start();
                break;
            case ViewPager.SCROLL_STATE_IDLE:
                animation.stop();
                break;
            default:break;
        }
    }


}
