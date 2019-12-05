package com.wangyi.ui10recycler;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 测绘、生成并摆放item
 */
public class RecyclerView extends ViewGroup {
    private Adapter adapter;
    private List<View> viewList;
    private int currentY;
    private int rowCount;
    private int firstRow;
    private int scrollY;
    private boolean needRelayout;
    private int width,height;
    private int heights[];
    Recycler recycler;
    private int touchSlop;

    public Adapter getAdapter() {
        return adapter;
    }

    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
        //    四、参数重置
        if (adapter != null) {
            recycler=new Recycler(adapter.getViewTypeCount());
            scrollY=0;
            firstRow=0;
            needRelayout =true;
            requestLayout();//触发onMeasure onLayout
        }
    }

    public RecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    //一、初始化
    private void init(Context context, AttributeSet attrs) {
        touchSlop=ViewConfiguration.get(context).getScaledTouchSlop();//最小滑动距离
        viewList=new ArrayList<>();
        needRelayout =true;
    }

//    三、整体摆放
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (needRelayout ||changed) {
            needRelayout =false;
//            1、清空
            viewList.clear();
            removeAllViews();
//            2、摆放
            width=r-l;
            height=b-t;
            int top=0,bottom;
            for (int i = 0; i < rowCount&&top<height; i++) {//填充满屏
                bottom=top+heights[i];
//                3、生成并摆放view
                makeAndStep(i,0,top,width,bottom);
                top=bottom;
            }


        }
    }

    private View makeAndStep(int row, int l, int top, int r, int bottom) {
        /**
         * 1.生成item 先找回收池，没有在新建
         * 2.根据itemType打上Tag 方便复用
         * 3.measure测量item
         * 3.为viewgroup-》addview
         * 4.layout摆放
         */
        View view=obtainView(row,r-l,bottom-top);//3.1生成
        view.layout(l,top,r,bottom);//3.2摆放
        return view;
    }


    private View obtainView(int row, int w, int h) {
        int itemType = adapter.getItemViewType(row);
        View recyclerCache = this.recycler.get(itemType);
        View item=null;
        if (recyclerCache == null) {//新建
            item=adapter.onCreateViewHolder(row,recyclerCache,this);
            if (item==null) {
                throw new RuntimeException("onCreateViewHolder must be init");
            }
        }else {//从回收池取
            item=adapter.onBinderViewHolder(row,recyclerCache,this);
        }
        item.setTag(R.id.tag_type_view,itemType);
        item.measure(MeasureSpec.makeMeasureSpec(w,MeasureSpec.EXACTLY),MeasureSpec.makeMeasureSpec(h,MeasureSpec.EXACTLY));
        addView(item,0);
        return item;
    }

//    二、整体测量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int h=0;
        if (adapter != null) {
            rowCount=adapter.getCount();
            heights=new int[rowCount];
            for (int i = 0; i < heights.length; i++) {
                heights[i]=adapter.getHeight(i);
            }
        }
        h=Math.min(heightSize,sumArray(heights,0,heights.length));//数据高度与recyclerview高度的最小值
        setMeasuredDimension(widthSize,h);//设置
    }

    private int sumArray(int [] arr,int firstIndex,int count){
        int sum=0;
        count+=firstIndex;
        for (int i = firstIndex; i < count; i++) {
            sum+=arr[i];
        }
        return sum;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void scrollBy(int x, int y) {
        super.scrollBy(x, y);
    }

    interface Adapter{
        View onCreateViewHolder(int position,View convertView,ViewGroup parent);
        View onBinderViewHolder(int position,View convertView,ViewGroup parent);
        int getItemViewType(int row);
        int getViewTypeCount();
        int getCount();
        int getHeight(int i);
    }
}
