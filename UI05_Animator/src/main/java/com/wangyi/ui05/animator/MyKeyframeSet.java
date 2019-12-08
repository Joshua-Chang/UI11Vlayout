package com.wangyi.ui05.animator;

import android.animation.FloatEvaluator;
import android.animation.TypeEvaluator;


import java.util.Arrays;
import java.util.List;

public class MyKeyframeSet {
    TypeEvaluator mEvaluator ;//类型估值器
    MyFloatKeyframe mFirstKeyframe;
    List<MyFloatKeyframe> mKeyframes;

    public MyKeyframeSet(MyFloatKeyframe ...keyframes) {
        mKeyframes= Arrays.asList(keyframes);
        mFirstKeyframe=keyframes[0];
        mEvaluator=new FloatEvaluator();
    }

    public static MyKeyframeSet ofFloat(float[] values) {
        int numKeyframes = values.length;
        MyFloatKeyframe keyframes[]=new MyFloatKeyframe[numKeyframes];
        keyframes[0]=new MyFloatKeyframe(0,values[0]);
        for (int i = 0; i < numKeyframes; i++) {
            keyframes[i]=new MyFloatKeyframe((float)i/(numKeyframes-1),values[i]);
        }
        return new MyKeyframeSet(keyframes);
    }

    public Object getValue(float fraction) {
        MyFloatKeyframe prevKeyframe=mFirstKeyframe;
        for (int i = 0; i < mKeyframes.size(); i++) {
            MyFloatKeyframe nextKeyframe = mKeyframes.get(i);
            if (fraction<nextKeyframe.getmFraction()){
                return mEvaluator.evaluate(fraction,prevKeyframe.getmValue(),nextKeyframe.getmValue());
                //估值器：根据关键帧状态，估算出每个百分比应有的状态
                //差值器：传入A，返回B，根据传入返回的不同，控制变化速率（例如：在属性动画中传入1%，返回2% 的加速差值器：本应显示1%的状态，结果显示了2%的状态）
            }
            prevKeyframe=nextKeyframe;
        }
        return null;
    }
}
