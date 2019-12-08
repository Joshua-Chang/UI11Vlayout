package com.wangyi.ui05.splash;

import androidx.annotation.NonNull;

public class ParallaxViewTag {
    protected int index;
    protected float xIn;
    protected float yIn;
    protected float xOut;
    protected float yOut;
    protected float alphaIn;
    protected float alphaOut;

    @NonNull
    @Override
    public String toString() {
        return "ParallaxViewTag [index="+index+",xIn="+xIn+",xOut="+xOut+",yIn="+yIn+",yOut="+yOut+
                ",alphaIn="+alphaIn+",alphaOut="+alphaOut+"]";
    }
}
