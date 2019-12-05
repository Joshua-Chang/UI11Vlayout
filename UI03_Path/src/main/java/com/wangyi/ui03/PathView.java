package com.wangyi.ui03;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;


public class PathView extends View {

    private Paint mPaint;
    private Path mPath;


    public PathView(Context context) {
        super(context);
        init();
    }

    private void init() {
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 基本一阶贝瑟尔曲线
         */
//        mPaint.setStyle(Paint.Style.FILL);
//        mPath.moveTo(100, 70);
//        mPath.lineTo(140, 800);//等同于相对链接 mPath.rLineTo(40,730);
//        mPath.lineTo(250, 600);
//        mPath.close();
        /**
         * add方法添加子图形
         * cw顺时针 ccw逆时针
         */
//        mPath.addArc(200, 200, 400, 400, -255, 255);//弧
//        mPath.addRect(500, 500, 900, 900, Path.Direction.CW);
//        RectF rectF=new RectF(500, 500, 900, 900);
//        mPath.addRect(rectF, Path.Direction.CCW);
//        mPath.addRoundRect(rectF,20,20, Path.Direction.CCW);
//        mPath.addCircle(700, 700, 200, Path.Direction.CW);
//        mPath.addOval(0, 0, 500, 300, Path.Direction.CCW);//椭圆

        /**
         * to方法追加子图形
         * forceMoveTo:true起点移动后开始画，false链接上一点画
         */
//        mPath.moveTo(0,0);
//        mPath.lineTo(100,100);
//        mPath.arcTo(400,200,600,400,0,270,false);

        /**
         *添加路径
         */
        mPath.moveTo(100,70);
        mPath.lineTo(140,180);
        mPath.lineTo(250,330);
        mPath.lineTo(400,630);
        mPath.lineTo(100,830);


        Path newPath=new Path();
        newPath.moveTo(100,1000);
        newPath.lineTo(600,1300);
        newPath.lineTo(400,1700);
        mPath.addPath(newPath);

        /**
         * 多阶贝瑟尔曲线
         */
//        mPath.moveTo(300,500);
//        mPath.quadTo(500,100,800,500);
//        mPath.rQuadTo(200,-400,500,0);//相对

//        mPath.moveTo(300,500);
//        mPath.cubicTo(500,100,600,1200,800,500);//相对mPath.rCubicTo();


        canvas.drawPath(mPath, mPaint);
    }
}
