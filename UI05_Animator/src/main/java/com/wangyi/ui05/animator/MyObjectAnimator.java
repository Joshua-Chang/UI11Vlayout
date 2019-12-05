package com.wangyi.ui05.animator;

import android.view.View;

import java.lang.ref.WeakReference;

public class MyObjectAnimator implements VSYNCManger.AnimationFrameCallback{
    long mStartTime=-1;
    private long mDuration=0;
    private int index;
    private WeakReference<View> mTarget;
    MyFloatPropertyValuesHolder myFloatPropertyValuesHolder;
    private TimeInterpolator interpolator;

    public void setmDuration(long mDuration) {
        this.mDuration = mDuration;
    }

    public void setInterpolator(TimeInterpolator interpolator) {
        this.interpolator = interpolator;
    }

    public MyObjectAnimator(View target, String propertyName, float... values) {
        mTarget=new WeakReference<View>(target);
        myFloatPropertyValuesHolder=new MyFloatPropertyValuesHolder(propertyName,values);
    }

    public static MyObjectAnimator ofFloat(View target, String propertyName, float... values) {
        MyObjectAnimator anim = new MyObjectAnimator(target, propertyName,values);
        return anim;
    }

    public void start(){
        myFloatPropertyValuesHolder.setupSetter(mTarget);
        mStartTime=System.currentTimeMillis();
        VSYNCManger.getInstance().add(this);
    }

    @Override
    public boolean doAnimationFrame(long currentTime) {
        float total = mDuration / 16;
        float fraction = (index++) / total;
        if (interpolator != null) {
            fraction=interpolator.getInterpolation(fraction);
        }
        if (index>=total) {
            index=0;
        }
        myFloatPropertyValuesHolder.setAnimatedValue(mTarget.get(),fraction);
        return false;
    }
}
