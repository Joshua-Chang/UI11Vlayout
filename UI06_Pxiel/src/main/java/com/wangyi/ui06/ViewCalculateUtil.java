package com.wangyi.ui06;

import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ViewCalculateUtil {
    public static void setViewLayoutParam(View view, int width, int height, int topM, int bottomM, int leftM, int rightM) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        if (layoutParams != null) {

            if (width != RelativeLayout.LayoutParams.MATCH_PARENT && width != RelativeLayout.LayoutParams.WRAP_CONTENT && width != RelativeLayout.LayoutParams.FILL_PARENT) {
                layoutParams.width = UIUtils.getInstance(view.getContext()).getWidth(width);
            } else {
                layoutParams.width = width;
            }
            if (height != RelativeLayout.LayoutParams.MATCH_PARENT && height != RelativeLayout.LayoutParams.WRAP_CONTENT && height != RelativeLayout.LayoutParams.FILL_PARENT) {
                layoutParams.height = UIUtils.getInstance(view.getContext()).getHeight(height);
            } else {
                layoutParams.height = height;
            }
            layoutParams.leftMargin = UIUtils.getInstance(view.getContext()).getWidth(leftM);
            layoutParams.rightMargin = UIUtils.getInstance(view.getContext()).getWidth(rightM);
            layoutParams.topMargin = UIUtils.getInstance(view.getContext()).getHeight(topM);
            layoutParams.bottomMargin = UIUtils.getInstance(view.getContext()).getHeight(bottomM);
            view.setLayoutParams(layoutParams);
        }else {

        }
    }
    public static void setTextSize(TextView view,int size){
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX,UIUtils.getInstance(view.getContext()).getHeight(size));
    }
}
