package com.wangyi.ui02.transform;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 状态保存和恢复
 */
public class SaveRestoreView extends View {

    private Paint mPaint;

    public SaveRestoreView(Context context) {
        this(context, null);
    }

    public SaveRestoreView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SaveRestoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 1.canvas内部对于状态的保存存放在栈中
         * 2.可以多次调用save保存canvas的状态，并且可以通过getSaveCount方法获取保存的状态个数。默认为1，save加1，restore减1.
         * 3.可以通过restore方法返回最近一次save前的状态，也可以通过restoreToCount返回指定save状态。指定save状态之后的状态全部被清除
         * 4.saveLayer可以创建新的图层，之后的绘制都会在这个图层之上绘制，直到调用restore方法
         * 注意：绘制的坐标系不能超过图层的范围， saveLayerAlpha对图层增加了透明度信息
         */
//        canvas.drawRect(200,200,700,700,mPaint);
//        canvas.translate(50,50);
//        mPaint.setColor(Color.GRAY);
//        canvas.drawRect(0,0,500,500,mPaint);
//        canvas.translate(-50,-50);
//        mPaint.setColor(Color.BLUE);
//        canvas.drawLine(0,0,550,550,mPaint);


//        canvas.drawRect(200,200,700,700,mPaint);
//        canvas.save();
//        canvas.translate(50,50);
//        mPaint.setColor(Color.GRAY);
//        canvas.drawRect(0,0,500,500,mPaint);
//        canvas.restore();
//        mPaint.setColor(Color.BLUE);
//        canvas.drawLine(0,0,550,550,mPaint);

//        mPaint.setColor(Color.RED);
//        Log.e("xxx","org\t"+canvas.getSaveCount());
//        canvas.drawRect(200,200,700,700,mPaint);
//        int save = canvas.save();//返回值为count值，canvas.restoreToCount(save)
//        Log.e("xxx","00 \t"+canvas.getSaveCount());
//
//        canvas.translate(50,50);
//        mPaint.setColor(Color.GRAY);
//        canvas.drawRect(0,0,500,500,mPaint);
//        canvas.save();
//        Log.e("xxx","50 \t"+canvas.getSaveCount());
////
//        canvas.translate(50,50);
//        mPaint.setColor(Color.GREEN);
//        canvas.drawRect(0,0,500,500,mPaint);
//
//        canvas.restore();
//        Log.e("xxx","50 \t"+canvas.getSaveCount());
//        mPaint.setColor(Color.BLUE);
//        canvas.drawLine(0,0,550,550,mPaint);
//
//        canvas.restore();//等同于canvas.restoreToCount(save);
//        Log.e("xxx","00 \t"+canvas.getSaveCount());
//        mPaint.setColor(Color.BLACK);
//        canvas.drawLine(0,0,550,0,mPaint);


//        if (canvas.getSaveCount()>1) {
//            canvas.restore();
//        }
        mPaint.setColor(Color.RED);
        canvas.drawRect(200,200,700,700,mPaint);
        int layerId = canvas.saveLayer(0, 0, 700, 700, mPaint);//可指定图层大小
        mPaint.setColor(Color.YELLOW);
        Matrix matrix=new Matrix();
        matrix.setTranslate(100,100);//超出图层大小，绘制不完全
        canvas.setMatrix(matrix);
        canvas.drawRect(0,0,700,700,mPaint);
        canvas.restoreToCount(layerId);

        mPaint.setColor(Color.MAGENTA);
        canvas.drawRect(0,0,100,100,mPaint);
    }
}
