package com.wangyi.ui06;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.lang.reflect.Field;

public class UIUtils {
    private static UIUtils instance;
    public static final float STANDARD_WIDTH = 1080f;
    public static final float STANDARD_HEIGHT = 1920f;


    public static float displayMetricsWidth;
    public static float displayMetricsHeight;
    private static int systemBarHeight;

    public static UIUtils getInstance(Context context) {
        if (instance == null) {
            instance = new UIUtils(context);
        }
        return instance;
    }

    public static UIUtils notifyInstance(Context context) {
            instance = new UIUtils(context);
        return instance;
    }

    public static UIUtils getInstance() {
        if (instance == null) {
            throw new RuntimeException("");
        }
        return instance;
    }

    private UIUtils(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (displayMetricsWidth == 0.0f || displayMetricsHeight == 0.0f) {
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            systemBarHeight = getSystemBarHeight(context);
            if (displayMetrics.widthPixels>displayMetrics.heightPixels) {
                this.displayMetricsWidth= ((float) displayMetrics.heightPixels);
                this.displayMetricsHeight= ((float) displayMetrics.widthPixels- systemBarHeight);
            }else {
                this.displayMetricsWidth= ((float) displayMetrics.widthPixels);
                this.displayMetricsHeight= ((float) displayMetrics.heightPixels- systemBarHeight);
            }
        }
    }


    public float getHorizontalScaleValue(){
        return ((float) (displayMetricsWidth / STANDARD_WIDTH));
    }
    public float getVerticalScaleValue(){
        return ((float) (displayMetricsHeight / (STANDARD_HEIGHT-systemBarHeight)));
    }

    public int getWidth(int width){
        return Math.round((float) width*this.displayMetricsWidth/STANDARD_WIDTH);
    }

    public int getHeight(int height){
        return Math.round((float) height*this.displayMetricsHeight/(STANDARD_HEIGHT-systemBarHeight));
    }

    private int getSystemBarHeight(Context context){
//        return getValue(context,"com.android.internal.R$dimen","system_bar_height",48);


        int id = context.getResources().getIdentifier("system_bar_height", "dimen", "android");
        if (id >0) {
            return context.getResources().getDimensionPixelSize(id);
        }
        return 48;
    }

    private int getValue(Context context, String dimeClass, String system_bar_height, int defValue) {
        try {//com.android.internal.R$dimen system_bar_height
            Class<?> clz = Class.forName(dimeClass);
            Object object = clz.newInstance();
            Field field = clz.getField(system_bar_height);
            int id = Integer.parseInt(field.get(object).toString());
            return context.getResources().getDimensionPixelSize(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }
}
