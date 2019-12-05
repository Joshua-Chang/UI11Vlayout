package com.wangyi.ui09;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;

public class ScaleBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    public ScaleBehavior() {
    }

    public ScaleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        Log.e("aaa","dyConsumed\t"+dyConsumed+"\tisRunning\t"+isRunning+"\t"+child.getVisibility());

        if (dyConsumed > 0 && !isRunning && child.getVisibility() == View.VISIBLE) {
            Log.e("aaa","hide");
            scaleHide(child);
        } else if (dyConsumed < 0 && !isRunning && child.getVisibility() == View.INVISIBLE) {
            Log.e("aaa","show");
            scaleShow(child);
        }
    }


    private LinearOutSlowInInterpolator linearOutSlowInInterpolator = new LinearOutSlowInInterpolator();
    private FastOutLinearInInterpolator fastOutLinearInInterpolator = new FastOutLinearInInterpolator();

    private void scaleShow(final V child) {

        ViewCompat.animate(child).scaleX(1).scaleY(1).setDuration(500).setInterpolator(linearOutSlowInInterpolator).setListener(new ViewPropertyAnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(View view) {
                Log.e("aaa","start");
                super.onAnimationStart(view);
                child.setVisibility(View.VISIBLE);
                isRunning = true;
            }

            @Override
            public void onAnimationEnd(View view) {
                Log.e("aaa","end");
                super.onAnimationEnd(view);
                isRunning = false;
            }

            @Override
            public void onAnimationCancel(View view) {
                Log.e("aaa","cancel");
                super.onAnimationCancel(view);
                isRunning = false;
            }
        });
//        animateChildTo(child,1,500, AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
    }

    private void scaleHide(final V child) {
//        animateChildTo(child,0,500, AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR);
        ViewCompat.animate(child).scaleX(0).scaleY(0).setDuration(500).setInterpolator(fastOutLinearInInterpolator).setListener(new ViewPropertyAnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(View view) {
                super.onAnimationStart(view);
                isRunning = true;
            }

            @Override
            public void onAnimationEnd(View view) {
                super.onAnimationEnd(view);
                isRunning = false;
                child.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(View view) {
                super.onAnimationCancel(view);
                isRunning = false;
            }
        });
    }

    private void animateChildTo(final V child, final int targetY, long duration, TimeInterpolator interpolator) {
        child.animate().scaleX((float) targetY).scaleY((float) targetY).setInterpolator(interpolator).setDuration(duration).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isRunning = true;
            }

            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isRunning = false;
                if (targetY == 0) {
                    child.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                isRunning = false;
            }
        });
    }

    private boolean isRunning;
}
