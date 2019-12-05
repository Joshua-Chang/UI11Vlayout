package com.wangyi.ui03;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

public class BezierView extends View {
    private Paint mPaint,mLinePointPaint;
    private Path mPath;

    private ArrayList<PointF> mControlPoints;

    public BezierView(Context context) {
        super(context);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);

        mLinePointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePointPaint.setStrokeWidth(4);
        mLinePointPaint.setStyle(Paint.Style.STROKE);
        mLinePointPaint.setColor(Color.GRAY);

        mPath = new Path();
        mControlPoints = new ArrayList<>();
        init();
    }





    private void init() {
        mControlPoints.clear();
        Random random=new Random();
        for (int i = 0; i < 9; i++) {
            int x=random.nextInt(800)+200;
            int y=random.nextInt(800)+200;
            PointF pointF=new PointF(x,y);
            mControlPoints.add(pointF);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int size = mControlPoints.size();
        PointF point;
        for (int i = 0; i < size; i++) {
            point=mControlPoints.get(i);
            if (i>0) {
                canvas.drawLine(mControlPoints.get(i-1).x,mControlPoints.get(i-1).y,point.x,point.y,mLinePointPaint);
            }
            canvas.drawCircle(point.x,point.y,12,mLinePointPaint);
        }
        buildBezierPoints();
        canvas.drawPath(mPath,mPaint);
    }

    private void buildBezierPoints() {
        ArrayList<PointF>points=new ArrayList<>();
        int order = mControlPoints.size() - 1;
        float delta = 1.0f / 1000;
        for (int t = 0; t < 1; t+=delta) {
            PointF pointf=new PointF(deCastejauX(order,0,t),deCastejauY(order,0,t));
            points.add(pointf);
            if (points.size()==1) {
                mPath.moveTo(points.get(0).x,points.get(0).y);
            }else {
                mPath.lineTo(pointf.x,pointf.y);
            }
        }
    }

    /**
     * p(i,j)=(1-t)*p(i-1,j)+t*p(i-1,i+1)
     * @param i 阶数
     * @param j 控制点
     * @param t 时间
     * @return
     */
    private float deCastejauX(int i, int j, int t) {
        if (i==1) {
            return (1-t)*mControlPoints.get(j).x+t*mControlPoints.get(j+1).x;
        }else {
            return (1-t)*deCastejauX(i-1,j,t)+deCastejauX(i-1,j+1,t);
        }
    }

    private float deCastejauY(int i, int j, int t) {
        if (i==1) {
            return (1-t)*mControlPoints.get(j).y+t*mControlPoints.get(j+1).y;
        }else {
            return (1-t)*deCastejauY(i-1,j,t)+deCastejauY(i-1,j+1,t);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()== MotionEvent.ACTION_DOWN) {
            init();
            invalidate();
        }
        return true;
    }
}
