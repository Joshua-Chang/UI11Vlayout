package com.wangyi.ui07;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

public class Density {
    private static final float WIDTH=320;
    private static float appDensity;
    private static float appScaleDensity;
    public static void setDensity(final Application application, Activity activity){
        DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();
        if (appDensity ==0) {
            appDensity =displayMetrics.density;
            appScaleDensity =displayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(@NonNull Configuration newConfig) {
                    if (newConfig!=null&&newConfig.fontScale>0) {
                        appScaleDensity=application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });


        }
        float targetDensity = displayMetrics.widthPixels / WIDTH;
        float targetScaleDensity = targetDensity * (appScaleDensity / appDensity);
        int targetDensityDpi = (int) (targetDensity * 160);

        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        dm.density=targetDensity;
        dm.scaledDensity=targetScaleDensity;
        dm.densityDpi=targetDensityDpi;
    }
}
