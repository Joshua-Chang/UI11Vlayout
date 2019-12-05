package com.wangyi.ui08music;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class StatusBarUtil {
    public static void setStatusBar(Activity activity, View toolbar) {
        setTranslateStateBar(activity);
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        int count = decorView.getChildCount();
        if (count > 0 && decorView.getChildAt(count - 1) instanceof StatusBarView) {
            decorView.getChildAt(count - 1).setBackgroundColor(Color.argb(0, 0, 0, 0));
            return;
        }
        StatusBarView statusBarView = new StatusBarView(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.getInstance().getSystemBarHeight(activity));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(Color.argb(0, 0, 0, 0));
        decorView.addView(statusBarView);


        if (toolbar != null) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) toolbar.getLayoutParams();
            layoutParams.setMargins(0,  UIUtils.getInstance().getSystemBarHeight(activity), 0, 0);
        }
    }

    public static void setTranslateStateBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
