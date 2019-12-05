package com.wangyi.ui12player.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.viewpager.widget.ViewPager;

import com.wangyi.ui12player.R;
import com.wangyi.ui12player.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class DiscView extends RelativeLayout {
    private List<View> mDiscLayouts = new ArrayList<>();
    private List<Integer> mMusicDatas = new ArrayList<>();
    private List<ObjectAnimator> mDiscAnimator = new ArrayList<>();
    ImageView musicNeedle;
    ImageView musicCircle;
    private ViewPagerAdapter mViewPagerAdapter;
    private ObjectAnimator mNeedleAnimator;
    private ViewPager viewPager;

    public DiscView(Context context) {
        super(context);
    }

    public DiscView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DiscView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setmMusicDataList(List<Integer> mMusicDataList) {
        if (mMusicDataList.isEmpty()) return;
        mDiscLayouts.clear();
        mMusicDatas.clear();
        mDiscAnimator.clear();
        mMusicDatas.addAll(mMusicDataList);
        for (Integer resd : mMusicDatas) {
            View centerContainer = LayoutInflater.from(getContext()).inflate(R.layout.layout_disc, viewPager, false);
            ImageView centerImage = (ImageView) centerContainer.findViewById(R.id.music_img);
            Drawable drawable = BitmapUtil.getMusicItemDrawable(getContext(), resd);
            ViewCalculateUtil.setViewLinearLayoutParam(centerImage, 800, 800, (850 - 800) / 2 + 190, 0, 0, 0);
            centerImage.setImageDrawable(drawable);
            mDiscLayouts.add(centerContainer);


            ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(centerImage, View.ROTATION, 0, 360);
            rotateAnimator.setRepeatCount(ValueAnimator.INFINITE);
            rotateAnimator.setDuration(20 * 1000);
            rotateAnimator.setInterpolator(new LinearInterpolator());
            mDiscAnimator.add(rotateAnimator);

        }
        mViewPagerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //onFinishInflate后 才可以找到view,构造方法时找不到
        initView();
        initObjectAnimator();
        mViewPagerAdapter = new ViewPagerAdapter(mDiscLayouts);
        viewPager.setAdapter(mViewPagerAdapter);
    }

    private void initObjectAnimator() {
        mNeedleAnimator=ObjectAnimator.ofFloat(musicNeedle,View.ROTATION,-30,0);
        mNeedleAnimator.setDuration(500);
        mNeedleAnimator.setInterpolator(new AccelerateInterpolator());
        mNeedleAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                int index = viewPager.getCurrentItem();
                //开启唱片动画
                playDiscAnimator(index);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void playDiscAnimator(int index) {
        ObjectAnimator objectAnimator = mDiscAnimator.get(index);
        if (objectAnimator.isPaused()) {
            objectAnimator.resume();
        }else {
            objectAnimator.start();
        }
        if (musicListener != null) {
            musicListener.onMusicPicChanged(mMusicDatas.get(index));
        }
    }

    private void initView() {
        musicNeedle = findViewById(R.id.musicNeedle);
        viewPager = findViewById(R.id.viewPager);
        musicCircle = findViewById(R.id.musicCircle);
        Bitmap bitmapDisc = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_disc_blackground), 813, 813, false);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmapDisc);
        musicCircle.setImageDrawable(roundedBitmapDrawable);
        ViewCalculateUtil.setViewLayoutParam(musicCircle, 850, 850, 190, 0, 0, 0);

        Bitmap originBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_needle);
        Bitmap bitmap = Bitmap.createScaledBitmap(originBitmap, UIUtils.getInstance().getWidth(276), UIUtils.getInstance().getHeight(276), false);
        ViewCalculateUtil.setViewLayoutParam(musicNeedle, 276, 413, 43, 0, 500, 0);
        musicNeedle.setPivotX(UIUtils.getInstance().getWidth(43));
        musicNeedle.setPivotY(UIUtils.getInstance().getHeight(43));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int currentItem = 0;

            /**
             *
             * @param position
             * @param positionOffset 0-1 滑动的比例
             * @param positionOffsetPixels
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentItem = position;
            }

            /**
             * @param state 0end 1press 2up
             */
            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE://0什么都么做

                        break;
                    case ViewPager.SCROLL_STATE_SETTLING://2滑动结束
                        playAnimator();
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING://1开始滑动
                        pauseAnimator();
                        break;
                }
            }
        });

    }
    //暂停唱片 重置唱针
    private void pauseAnimator() {
        mDiscAnimator.get(viewPager.getCurrentItem()).pause();
        mNeedleAnimator.reverse();
    }

    private void playAnimator() {
        mNeedleAnimator.start();
    }

    private MusicListener musicListener;

    public void setMusicListener(MusicListener musicListener) {
        this.musicListener = musicListener;
    }
}
