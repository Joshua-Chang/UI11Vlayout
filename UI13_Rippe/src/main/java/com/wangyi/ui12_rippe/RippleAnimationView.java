package com.wangyi.ui12_rippe;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import androidx.core.content.ContextCompat;

import com.wangyi.ui12_rippe.ui.UIUtils;

import java.util.ArrayList;
import java.util.Collections;

public class RippleAnimationView extends RelativeLayout {
    public Paint paint;
    private int strokWidth;
    private int radius;
    private int rippleColor;
    private ArrayList<RippleCircleView>viewList=new ArrayList<>();
    private AnimatorSet animatorSet;
    private boolean animationRunning=false;

    public RippleAnimationView(Context context) {
        super(context);
    }

    public RippleAnimationView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RippleAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RippleAnimationView);
        int rippleType = typedArray.getInt(R.styleable.RippleAnimationView_ripple_anim_type, 0);
        int rippleCount = typedArray.getInteger(R.styleable.RippleAnimationView_ripple_count, 4);
        radius = typedArray.getInteger(R.styleable.RippleAnimationView_radius, 54);
        strokWidth = typedArray.getInteger(R.styleable.RippleAnimationView_strokWidth, 2);
        rippleColor = typedArray.getColor(R.styleable.RippleAnimationView_ripple_anim_color, ContextCompat.getColor(context, R.color.rippleColor));
        paint.setStrokeWidth(UIUtils.getInstance().getWidth(strokWidth));
        if (rippleType==0) {
            paint.setStyle(Paint.Style.FILL);
        }else {
            paint.setStyle(Paint.Style.STROKE);
        }
        paint.setColor(rippleColor);
        LayoutParams rippleParams=new LayoutParams(UIUtils.getInstance().getWidth((radius+strokWidth)*2),UIUtils.getInstance().getWidth((radius+strokWidth)*2));
        rippleParams.addRule(CENTER_IN_PARENT,TRUE);
        float maxScale=UIUtils.getInstance().displayMetricsWidth/ (float) (UIUtils.getInstance().getWidth(radius + strokWidth));
//        float maxScale=UIUtils.getInstance().displayMetricsWidth/ (float) (2*UIUtils.getInstance().getWidth(radius + strokWidth));
        int rippleDuration=3500;//总时间
        int singleDelay=rippleDuration/rippleCount;//间隔时间
        ArrayList<Animator>animatorArrayList=new ArrayList<>();

        for (int i = 0; i < rippleCount; i++) {
            RippleCircleView rippleCircleView=new RippleCircleView(this);
            addView(rippleCircleView,rippleParams);
            viewList.add(rippleCircleView);

            ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(rippleCircleView, "ScaleX", 1.0f,maxScale);
            scaleXAnimator.setRepeatMode(ValueAnimator.RESTART);
            scaleXAnimator.setRepeatCount(ValueAnimator.INFINITE);
            scaleXAnimator.setStartDelay(i*singleDelay);
            scaleXAnimator.setDuration(rippleDuration);
            animatorArrayList.add(scaleXAnimator);

            ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(rippleCircleView, "ScaleY", 1.0f,maxScale);
            scaleYAnimator.setRepeatMode(ValueAnimator.RESTART);
            scaleYAnimator.setRepeatCount(ValueAnimator.INFINITE);
            scaleYAnimator.setStartDelay(i*singleDelay);
            scaleYAnimator.setDuration(rippleDuration);
            animatorArrayList.add(scaleYAnimator);

            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(rippleCircleView, "Alpha", 1.0f,0.1f);
            alphaAnimator.setRepeatMode(ValueAnimator.RESTART);
            alphaAnimator.setRepeatCount(ValueAnimator.INFINITE);
            alphaAnimator.setStartDelay(i*singleDelay);
            alphaAnimator.setDuration(rippleDuration);
            animatorArrayList.add(alphaAnimator);

        }

        animatorSet=new AnimatorSet();
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.playTogether(animatorArrayList);

    }

    public void startRippleAnimation(){
        if (!animationRunning) {
            for (RippleCircleView rippleCircleView : viewList) {
                rippleCircleView.setVisibility(VISIBLE);
            }
            animatorSet.start();
            animationRunning=true;
        }
    }

    public void stopRippleAnimation(){
        if (animationRunning) {
            Collections.reverse(viewList);
            for (RippleCircleView rippleCircleView : viewList) {
                rippleCircleView.setVisibility(INVISIBLE);
            }
            animatorSet.end();
            animationRunning=false;
        }
    }

    public int getStrokWidth() {
        return strokWidth;
    }

    public boolean isAnimationRunning() {
        return animationRunning;
    }
}
