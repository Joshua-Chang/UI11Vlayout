package com.wangyi.ui05.animator;

import android.view.View;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyFloatPropertyValuesHolder {
    String mPropertyName;
    Class mValueType;
    Method mSetter=null;
    MyKeyframeSet mKeyframes;

    public MyFloatPropertyValuesHolder(String propertyName, float... values) {
        this.mPropertyName=propertyName;
        mValueType=float.class;
        mKeyframes=MyKeyframeSet.ofFloat(values);
    }


    public void setupSetter(WeakReference<View> mTarget) {
        char firstLetter=Character.toUpperCase(mPropertyName.charAt(0));
        String substring = mPropertyName.substring(1);
        String methodName="set"+firstLetter+substring;
        try {
            mSetter=View.class.getMethod(methodName,float.class);
//            mSetter=mTarget.getClass().getMethod(methodName,float.class);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void setAnimatedValue(View view, float fraction) {
        Object value=mKeyframes.getValue(fraction);
        try {
            mSetter.invoke(view,value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
