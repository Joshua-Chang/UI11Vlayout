package com.wangyi.ui10_svg;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.Log;

public class ProviceItem {
    private Path path;
    private int drawColor;
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ProviceItem(Path path) {
        this.path = path;
    }

    public void setDrawColor(int drawColor) {
        this.drawColor = drawColor;
    }

    public void drawItem(Canvas canvas, Paint paint, boolean isSelect) {
        Log.e("xxx",isSelect+"");
        if (isSelect) {
            //省份内部
            paint.clearShadowLayer();
            paint.setStrokeWidth(1);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(0xFFD81B60);
            canvas.drawPath(path,paint);
//省份边界
            paint.setStyle(Paint.Style.STROKE);
            int strokeColor=0xffd0e8f4;
            paint.setColor(strokeColor);
            canvas.drawPath(path,paint);
        }else {
            //省份内部
            paint.setStrokeWidth(2);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL);
            paint.setShadowLayer(89,0,0,0xffffff);
            canvas.drawPath(path,paint);
//省份边界
            paint.clearShadowLayer();
            paint.setColor(drawColor);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPath(path,paint);
        }
    }

    public boolean isTouch(float x, float y) {
        RectF rectF=new RectF();
        path.computeBounds(rectF,true);
        Region region=new Region();
        //区交集
        region.setPath(path,new Region((int)rectF.left,(int)rectF.top,(int)rectF.right,(int)rectF.bottom));
        return region.contains((int)x,(int)y);
    }
}
