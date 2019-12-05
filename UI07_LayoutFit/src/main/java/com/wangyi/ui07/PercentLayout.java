package com.wangyi.ui07;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class PercentLayout extends RelativeLayout {
    public PercentLayout(Context context) {
        super(context);
    }

    public PercentLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PercentLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            ViewGroup.LayoutParams params = child.getLayoutParams();
//            if (params instanceof LayoutParams) {
            if (checkLayoutParams(params)) {
                LayoutParams lp = (LayoutParams) params;
                float widthPercent=lp.widthPercent;
                float heightPercent=lp.heightPercent;
                float marginLeftPercent=lp.marginLeftPercent;
                float marginTopPercent=lp.marginTopPercent;
                float marginRightPercent=lp.marginRightPercent;
                float marginBottomPercent=lp.marginBottomPercent;
                if (widthPercent>0) { params.width= (int) (widthSize*widthPercent); }
                if (heightPercent>0) { params.height= (int) (heightSize*heightPercent); }
                if (marginLeftPercent>0) { ((LayoutParams) params).leftMargin= (int) (widthSize*marginLeftPercent); }
                if (marginTopPercent>0) { ((LayoutParams) params).topMargin= (int) (heightSize*marginTopPercent); }
                if (marginRightPercent>0) { ((LayoutParams) params).rightMargin= (int) (widthSize*marginRightPercent); }
                if (marginBottomPercent>0) { ((LayoutParams) params).bottomMargin= (int) (heightSize*marginBottomPercent); }
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(),attrs);
    }

    public static class LayoutParams extends RelativeLayout.LayoutParams {
        private float widthPercent;
        private float heightPercent;
        private float marginLeftPercent;
        private float marginTopPercent;
        private float marginRightPercent;
        private float marginBottomPercent;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);

            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.PercentLayout);
            widthPercent=a.getFloat(R.styleable.PercentLayout_widthPercent,0);
            heightPercent=a.getFloat(R.styleable.PercentLayout_heightPercent,0);
            marginLeftPercent=a.getFloat(R.styleable.PercentLayout_MarginLeftPercent,0);
            marginTopPercent=a.getFloat(R.styleable.PercentLayout_MarginTopPercent,0);
            marginRightPercent=a.getFloat(R.styleable.PercentLayout_MarginRightPercent,0);
            marginBottomPercent=a.getFloat(R.styleable.PercentLayout_MarginBottomPercent,0);
            a.recycle();
        }
    }
}
