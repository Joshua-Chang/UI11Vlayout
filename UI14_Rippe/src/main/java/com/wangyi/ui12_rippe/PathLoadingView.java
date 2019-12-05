package com.wangyi.ui12_rippe;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;


public class PathLoadingView extends View {
    private Path mPath;
    private Paint mPaint;
    private PathMeasure mPathMeasure;
    private float mLength;
    private Path dst;
    private float value;

    public PathLoadingView(Context context) {
        this(context,null);
    }

    public PathLoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#ff4081"));
        mPaint.setStrokeWidth(10f);
        mPaint.setStyle(Paint.Style.STROKE);
        mPath=new Path();
        dst=new Path();
        mPath.addCircle(300f,300f,100f, Path.Direction.CW);
        mPathMeasure=new PathMeasure();
        mPathMeasure.setPath(mPath,false);
        mLength = mPathMeasure.getLength();

        ValueAnimator animator = ObjectAnimator.ofFloat(0, mLength);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                value = (float)animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        dst.reset();
//        mPathMeasure.getSegment(value,value+mLength/3,dst,true);
//        mPathMeasure.getSegment(value,value+mLength/3*(1-(value/mLength)),dst,true);

        float start= (float) (value-((0.5-Math.abs(value/mLength-0.5))*mLength));
        mPathMeasure.getSegment(start,value,dst,true);
        canvas.drawPath(dst,mPaint);
    }
}
