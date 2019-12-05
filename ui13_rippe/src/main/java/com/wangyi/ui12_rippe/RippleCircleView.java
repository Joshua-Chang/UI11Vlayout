package com.wangyi.ui12_rippe;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class RippleCircleView extends View {
    private RippleAnimationView rippleAnimationView;
    public RippleCircleView(RippleAnimationView rippleAnimationView) {
        this(rippleAnimationView.getContext(), null);
        this.rippleAnimationView = rippleAnimationView;
        this.setVisibility(View.INVISIBLE);
    }

    public RippleCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RippleCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int radius = Math.min(getWidth(), getHeight()) / 2;
        canvas.drawCircle(radius, radius, radius - rippleAnimationView.getStrokWidth(), rippleAnimationView.paint);
    }
}
