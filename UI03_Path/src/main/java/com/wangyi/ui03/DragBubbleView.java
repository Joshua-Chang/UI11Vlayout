package com.wangyi.ui03;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.PointFEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.Nullable;


public class DragBubbleView extends View {
    private final int BUBBLE_STATE_DEFAULT = 0;
    private final int BUBBLE_STATE_CONNECT = 1;
    private final int BUBBLE_STATE_APART = 2;
    private final int BUBBLE_STATE_DISMISS = 3;
    private PointF mBubFixedCenter;
    private PointF mBubMovableCenter;

    private int mBubbleState = BUBBLE_STATE_DEFAULT;
    private boolean mIsBurstAnimStart = false;

    private int[] mBurstDrawablesArray = {R.mipmap.ic_launcher,  R.mipmap.pic};
    private Rect mBurstRect;
    private Path mBezierPath;
    private float MOVE_OFFSET = 0;
    private float mDist, mMaxDist;
    private float mTextSize;
    private String mBubbleStr;
    private int mBubbleColor, mTextColor;
    private float mBubbleRadius, mBubFixedRadius, mBubbleMovableRadius;
    private Paint mBubblePaint;
    private Paint mTextPaint;
    private Rect mTextRect;
    private Paint mBurstPaint;
    private Bitmap[] mBurstBitmapsArray;
    private int mCurDrawableIndex;

    public DragBubbleView(Context context) {
        this(context, null);
    }

    public DragBubbleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragBubbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DragBubbleView, defStyleAttr, 0);
        mBubbleRadius = array.getDimension(R.styleable.DragBubbleView_bubble_radius, mBubbleRadius);
        mBubbleColor = array.getColor(R.styleable.DragBubbleView_bubble_color, Color.RED);
        mBubbleStr = array.getString(R.styleable.DragBubbleView_text);
        mTextSize = array.getDimension(R.styleable.DragBubbleView_textSize, mTextSize);
        mTextColor = array.getColor(R.styleable.DragBubbleView_text_color, Color.WHITE);
        array.recycle();

        mBubFixedRadius = mBubbleRadius;
        mBubbleMovableRadius = mBubFixedRadius;
        mMaxDist = 8 * mBubbleRadius;
        MOVE_OFFSET = mMaxDist / 4;

        mBubblePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBubblePaint.setColor(mBubbleColor);
        mBubblePaint.setStyle(Paint.Style.FILL);
        mBezierPath = new Path();

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
        mTextRect = new Rect();

        mBurstPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBurstPaint.setFilterBitmap(true);
        mBurstRect = new Rect();
        mBurstBitmapsArray = new Bitmap[mBurstDrawablesArray.length];
        for (int i = 0; i < mBurstDrawablesArray.length; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mBurstDrawablesArray[i]);
            mBurstBitmapsArray[i]=bitmap;
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mBubFixedCenter == null) {
            mBubFixedCenter = new PointF(w / 2, h / 2);
        } else {
            mBubFixedCenter.set(w / 2, h / 2);
        }

        if (mBubMovableCenter == null) {
            mBubMovableCenter = new PointF(w / 2, h / 2);
        } else {
            mBubMovableCenter.set(w / 2, h / 2);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mBubbleState == BUBBLE_STATE_CONNECT) {
            canvas.drawCircle(mBubFixedCenter.x, mBubFixedCenter.y, mBubFixedRadius, mBubblePaint);//不动球
            int iAnchorX = (int) (mBubFixedCenter.x + mBubMovableCenter.x) / 2;
            int iAnchorY = (int) (mBubFixedCenter.y + mBubMovableCenter.y) / 2;

            float sin = (mBubMovableCenter.y - mBubFixedCenter.y) / mDist;
            float cos = (mBubMovableCenter.x - mBubFixedCenter.x) / mDist;

            float iBubMovableStartX = mBubMovableCenter.x + sin * mBubbleMovableRadius;
            float iBubMovableStartY = mBubMovableCenter.y - cos * mBubbleMovableRadius;

            float iBubFixedEndX = mBubFixedCenter.x + sin * mBubFixedRadius;
            float iBubFixedEndY = mBubFixedCenter.y - cos * mBubFixedRadius;


            float iBubFixedStartX = mBubFixedCenter.x - sin * mBubFixedRadius;
            float iBubFixedStartY = mBubFixedCenter.y + cos * mBubFixedRadius;

            float iBubMovableEndX = mBubMovableCenter.x - sin * mBubbleMovableRadius;
            float iBubMovableEndY = mBubMovableCenter.y + cos * mBubbleMovableRadius;


            mBezierPath.reset();
            mBezierPath.moveTo(iBubFixedStartX, iBubFixedStartY);
            mBezierPath.quadTo(iAnchorX, iAnchorY, iBubMovableEndX, iBubMovableEndY);
            mBezierPath.lineTo(iBubMovableStartX, iBubMovableStartY);
            mBezierPath.quadTo(iAnchorX, iAnchorY, iBubFixedEndX, iBubFixedEndY);
            mBezierPath.close();
            canvas.drawPath(mBezierPath, mBubblePaint);
        }
        if (mBubbleState != BUBBLE_STATE_DISMISS) {
            canvas.drawCircle(mBubMovableCenter.x, mBubMovableCenter.y, mBubbleMovableRadius, mBubblePaint);
            mTextPaint.getTextBounds(mBubbleStr, 0, mBubbleStr.length(), mTextRect);
            canvas.drawText(mBubbleStr, mBubMovableCenter.x - mTextRect.width() / 2, mBubMovableCenter.y + mTextRect.height() / 2, mTextPaint);//drawText接近左下角
        }
        if (mBubbleState == BUBBLE_STATE_DISMISS&&mCurDrawableIndex<mBurstBitmapsArray.length) {
            mBurstRect.set(
                    (int)(mBubMovableCenter.x-mBubbleMovableRadius),
                    (int)(mBubMovableCenter.y-mBubbleMovableRadius),
                    (int)(mBubMovableCenter.x+mBubbleMovableRadius),
                    (int)(mBubMovableCenter.y+mBubbleMovableRadius)
                    );
            canvas.drawBitmap(mBurstBitmapsArray[mCurDrawableIndex],null,mBurstRect,mBubblePaint);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mBubbleState != BUBBLE_STATE_DISMISS) {
                    mDist = (float) Math.hypot(event.getX() - mBubFixedCenter.x, event.getY() - mBubFixedCenter.y);//求斜边
                    if (mDist < mBubbleRadius + MOVE_OFFSET) {
                        mBubbleState = BUBBLE_STATE_CONNECT;
                    } else {
                        mBubbleState = BUBBLE_STATE_DEFAULT;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mBubbleState != BUBBLE_STATE_DEFAULT) {
                    mDist = (float) Math.hypot(event.getX() - mBubFixedCenter.x, event.getY() - mBubFixedCenter.y);//求斜边
//                    mBubMovableCenter.set(event.getX(),event.getY());
                    mBubMovableCenter.x = event.getX();
                    mBubMovableCenter.y = event.getY();

                    if (mBubbleState == BUBBLE_STATE_CONNECT) {
                        if (mDist < mMaxDist - MOVE_OFFSET) {//拖拽范围内时，不动气泡变径变化
                            mBubFixedRadius = mBubbleRadius - mDist / 8;
                        } else {
                            mBubbleState = BUBBLE_STATE_APART;//拖拽范围外时，
                        }
                    }
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mBubbleState != BUBBLE_STATE_CONNECT) {
                    startBubbleRestAnim();
                }else if (mBubbleState != BUBBLE_STATE_APART) {
                    if (mDist<2*mBubFixedRadius){
                        startBubbleRestAnim();
                    }else {
                        startBubbleBurstAnim();
                    }
                }
                break;
        }
        return true;
    }

    private void startBubbleBurstAnim() {
        ValueAnimator anim=ValueAnimator.ofObject(new PointFEvaluator(),new PointF(mBubMovableCenter.x,mBubMovableCenter.y),new PointF(mBubFixedCenter.x,mBubFixedCenter.y));
        anim.setDuration(200);
        anim.setInterpolator(new OvershootInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mBubMovableCenter= (PointF) animation.getAnimatedValue();
                invalidate();
            }
        });
        anim.addListener(new AnimatorListenerAdapter(){
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mBubbleState=BUBBLE_STATE_DEFAULT;
            }
        });
        anim.start();
    }

    private void startBubbleRestAnim() {
        mBubbleState=BUBBLE_STATE_DISMISS;
        ValueAnimator animator=ValueAnimator.ofInt(0,mBurstBitmapsArray.length);
        animator.setDuration(500);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurDrawableIndex= (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }
}
