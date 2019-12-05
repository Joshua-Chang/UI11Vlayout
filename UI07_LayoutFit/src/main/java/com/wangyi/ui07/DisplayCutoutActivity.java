package com.wangyi.ui07;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.DisplayCutout;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

public class DisplayCutoutActivity extends AppCompatActivity {

    private int safeInsetTop;

    /**
     * 1.设置全屏
     * (判断是否有刘海)
     * 2.延伸刘海
     * 3.沉浸式
     */

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 第一种手动在oncreate里hide()actionbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        // 第二种最简单
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        // 第三种 直接修改style 缺点 直接全局了（注意继承）
        //<style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        boolean hasDisplayCutout = hasDisplayCutout(window);
        boolean hasDisplayCutout = false;
//        if (hasDisplayCutout) {
//            WindowManager.LayoutParams params = window.getAttributes();
//            /**
//             * LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT
//             * LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
//             * LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER
//             */
//            params.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
//            window.setAttributes(params);
//            int flag = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
//            int visibility = window.getDecorView().getSystemUiVisibility();
//            visibility |= flag;//a|=b就是 a=a|b,
//            window.getDecorView().setSystemUiVisibility(visibility);
//        }


        setContentView(R.layout.activity_display_cutout);

        //11111
//        Button button = findViewById(R.id.button);
//        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) button.getLayoutParams();
//        layoutParams.topMargin=heightForDisplayCutout();
//        button.setLayoutParams(layoutParams);

//22222
//        RelativeLayout layout = findViewById(R.id.container);
//        layout.setPadding(layout.getPaddingLeft(),heightForDisplayCutout(),layout.getPaddingRight(),layout.getPaddingBottom());
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        boolean hasDisplayCutout = hasDisplayCutout(window);
        if (hasDisplayCutout) {
            WindowManager.LayoutParams params = window.getAttributes();
            /**
             * LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT
             * LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
             * LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER
             */
            params.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            window.setAttributes(params);
            int flag = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            int visibility = window.getDecorView().getSystemUiVisibility();
            visibility |= flag;//a|=b就是 a=a|b,
            window.getDecorView().setSystemUiVisibility(visibility);
        }







        //333333
        View rootView = window.getDecorView();
        WindowInsets insets = rootView.getRootWindowInsets();
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.P&&insets != null&&insets.getDisplayCutout()!=null&&insets.getDisplayCutout().getSafeInsetTop()>0){
            safeInsetTop = insets.getDisplayCutout().getSafeInsetTop();
            RelativeLayout layout = findViewById(R.id.container);
            layout.setPadding(layout.getPaddingLeft(),safeInsetTop,layout.getPaddingRight(),layout.getPaddingBottom());
        }






    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private boolean hasDisplayCutout(Window window) {
        DisplayCutout displayCutout;
        View rootView = window.getDecorView();
        WindowInsets insets = rootView.getRootWindowInsets();
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.P&&insets != null) {
            displayCutout = insets.getDisplayCutout();
            if (displayCutout != null) {
                if (displayCutout.getBoundingRects() != null && displayCutout.getBoundingRects().size() > 0 && displayCutout.getSafeInsetTop() > 0) {
                    return true;
                }
            }
        }
        return false;
    }


    public int heightForDisplayCutout(){
        int resId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId>0) {
            return getResources().getDimensionPixelSize(resId);
        }
        return 96;
    }
}
