package com.wangyi.ui05.animator;

public class MyFloatKeyframe {
    float mFraction;
    Class mValueType;
    float mValue;

    public MyFloatKeyframe(float mFraction, float mValue) {
        this.mFraction = mFraction;
        this.mValue = mValue;
        mValueType=float.class;
    }

    public float getmFraction() {
        return mFraction;
    }

    public Class getmValueType() {
        return mValueType;
    }

    public float getmValue() {
        return mValue;
    }
}
