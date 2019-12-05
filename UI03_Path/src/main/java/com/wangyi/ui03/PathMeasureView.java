package com.wangyi.ui03;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.Log;
import android.view.View;

public class PathMeasureView extends View {
    private Paint mPaint, mLinePaint;
    private Path mPath;
    private Bitmap mBitmap;
    private float mFloat;

    public PathMeasureView(Context context) {
        super(context);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);

        mLinePaint = new Paint();
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setColor(Color.RED);
        mLinePaint.setStrokeWidth(6);
        mPath = new Path();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.pic, options);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, mLinePaint);
        canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight(), mLinePaint);
        canvas.translate(getWidth() / 2, getHeight() / 2);


//        mPath.lineTo(0,200);
//        mPath.lineTo(200,200);
//        mPath.lineTo(200,0);
//
//        PathMeasure pathMeasure=new PathMeasure(mPath,false);
//        Log.d("xxx", "pathMeasure.getLength():" + pathMeasure.getLength());
//        mPath.lineTo(200,-200);
//        pathMeasure.setPath(mPath,false);//path更改后要重新set
//        Log.d("xxx", "pathMeasure.getLength():" + pathMeasure.getLength());

//        mPath.addRect(-200,-200,200,200, Path.Direction.CW);
//
//        Path dst=new Path();
//        dst.lineTo(-300,-300);
//        PathMeasure pathMeasure=new PathMeasure(mPath,false);
////        截取一部分存入dst
//        pathMeasure.getSegment(200,1000,dst,false);//是否移到上一点 然后画
//
//
//        canvas.drawPath(mPath,mPaint);
//        canvas.drawPath(dst,mLinePaint);


//        mPath.addRect(-100, -100, 100, 100, Path.Direction.CW);
//        mPath.addOval(-200, -200, 200, 200, Path.Direction.CW);
//        canvas.drawPath(mPath, mPaint);
//        PathMeasure pathMeasure = new PathMeasure(mPath, false);
//        //pathMeasure.getLength()为当前曲线长度
//        Log.d("xxx", "pathMeasure.getLength():" + pathMeasure.getLength());
//        pathMeasure.nextContour();//下一段曲线
//        Log.d("xxx", "pathMeasure.getLength():" + pathMeasure.getLength());

        mFloat += 0.01;
        if (mFloat >= 1) {
            mFloat = 0;
        }

        float[] pos = new float[2];
        float[] tan = new float[2];
        mPath.reset();
        mPath.addCircle(0, 0, 200, Path.Direction.CW);
        canvas.drawPath(mPath, mPaint);

//        PathMeasure pathMeasure = new PathMeasure(mPath, false);
//        /**
//         *
//         * 返回值(boolean）：判断获取是否成功（true表示成功，数据会存入 pos 和 tan 中，false 表示失败，pos 和 tan 不会改变）；
//         * float distance：距离 Path 起点的长度 取值范围: 0 <= distance <= getLength；
//         * float[] pos：该点的坐标值，坐标值: (x==[0], y==[1])；
//         * float[] tan：该点的正切值，正切值: (x==[0], y==[1])；
//         */
//        pathMeasure.getPosTan(pathMeasure.getLength()*mFloat,pos,tan);
//        Log.d("PathMeasureView", "pos[0]:" + pos[0]+"pos[1]:" + pos[1]);
//        Log.d("PathMeasureView", "tan[0]:" + tan[0]+"tan[1]:" + tan[1]);
//
//        /**
//         * 函数返回点(x,y)和原点(0,0)之间直线的倾斜角
//         */
//        double degree = Math.atan2(tan[1], tan[0]) * 180 / Math.PI;//切线与x轴角度
//        Log.d("PathMeasureView", "degree" + degree);
//
        Matrix matrix=new Matrix();
//        matrix.reset();
//        matrix.postRotate((float) degree,mBitmap.getWidth()/2,mBitmap.getHeight()/2);
//        matrix.postTranslate(pos[0]-mBitmap.getWidth()/2,pos[1]-mBitmap.getHeight()/2);//绘制中心与当前点重合
////                matrix.postRotate((float) degree,pos[0],pos[1]);


        PathMeasure pathMeasure = new PathMeasure(mPath, false);
        /**
         * 返回值(boolean)：判断获取是否成功（true表示成功，数据会存入matrix中，false 失败，matrix内容不会改变）；
         * float distance：距离 Path 起点的长度（取值范围: 0 <= distance <= getLength）；
         * Matrix matrix：根据 falgs 封装好的matrix，会根据 flags 的设置而存入不同的内容；
         * int flags：规定哪些内容会存入到matrix中（可选择POSITION_MATRIX_FLAG位置 、ANGENT_MATRIX_FLAG正切 ）；
         */
        pathMeasure.getMatrix(pathMeasure.getLength()*mFloat,matrix,PathMeasure.TANGENT_MATRIX_FLAG|PathMeasure.POSITION_MATRIX_FLAG);
        matrix.preTranslate(-mBitmap.getWidth()/2,-mBitmap.getHeight()/2);
        canvas.drawBitmap(mBitmap,matrix,mPaint);
        invalidate();
    }


}
