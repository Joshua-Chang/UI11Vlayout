package com.wangyi.ui10recycler;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理滑动及
 */
public class RecyclerView2 extends ViewGroup {
    private Adapter adapter;
    private List<View> viewList;
    private int currentY;
    private int rowCount;
    private int firstRow;
    private int scrollY;
    private boolean needRelayout;
    private int width, height;
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
            recycler = new Recycler(adapter.getViewTypeCount());
            scrollY = 0;
            firstRow = 0;
            needRelayout = true;
            requestLayout();//触发onMeasure onLayout
        }
    }

    public RecyclerView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    //一、初始化
    private void init(Context context, AttributeSet attrs) {
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();//最小滑动距离
        viewList = new ArrayList<>();
        needRelayout = true;
    }

    //    三、整体摆放
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (needRelayout || changed) {
            needRelayout = false;
//            1、清空
            viewList.clear();
            removeAllViews();
//            2、摆放
            width = r - l;
            height = b - t;
            int top = 0, bottom;
            for (int i = 0; i < rowCount && top < height; i++) {//填充满屏
                bottom = top + heights[i];
//                3、生成并摆放view
                View view=makeAndStep(i, 0, top, width, bottom);
                viewList.add(view);
                top = bottom;
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
        View view = obtainView(row, r - l, bottom - top);//3.1生成
        view.layout(l, top, r, bottom);//3.2摆放
        return view;
    }


    private View obtainView(int row, int w, int h) {
        int itemType = adapter.getItemViewType(row);
        View recyclerCache = this.recycler.get(itemType);
        View item = null;
        if (recyclerCache == null) {//新建
            item = adapter.onCreateViewHolder(row, recyclerCache, this);
            if (item == null) {
                throw new RuntimeException("onCreateViewHolder must be init");
            }
        } else {//从回收池取
            item = adapter.onBinderViewHolder(row, recyclerCache, this);
        }
        item.setTag(R.id.tag_type_view, itemType);
        item.measure(MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY));
        addView(item, 0);
        return item;
    }

    //    二、整体测量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int h = 0;
        if (adapter != null) {
            rowCount = adapter.getCount();
            heights = new int[rowCount];
            for (int i = 0; i < heights.length; i++) {
                heights[i] = adapter.getHeight(i);
            }
        }
        h = Math.min(heightSize, sumArray(heights, 0, heights.length));//数据高度与recyclerview高度的最小值
        setMeasuredDimension(widthSize, h);//设置
    }

    private int sumArray(int[] arr, int firstIndex, int count) {
        int sum = 0;
        count += firstIndex;
        for (int i = firstIndex; i < count; i++) {
            sum += arr[i];
        }
        return sum;
    }

    //A、拦截处理：拦截触摸滑动事件、不拦截点击事件
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                currentY = (int) ev.getRawY();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (Math.abs(currentY - (int) ev.getRawY()) > touchSlop) {
                    intercept = true;
                }
            }
        }
        return intercept;
    }

    //B、消费滑动事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE: {
                int rawY = (int) event.getRawY();
                int diffY = currentY - rawY;//上滑正 下滑负
                scrollBy(0, diffY);//画布移动、并不影响子控件位置
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void scrollBy(int x, int y) {
//        scrollY: 第一个可见item顶点 与离屏幕顶点的距离
        scrollY += y;
        scrollY=scrollBounds(scrollY);
        Log.e("xxx","scrollY:"+scrollY+"----"+y);
        //上滑正
        if (scrollY > 0) {
            //上滑顶部移除item ： scrollY>第一个可见item的高度时
            while (scrollY > heights[firstRow]) {
                //上滑顶部移除item:1.viewgroup->removeView; 2.viewList移除; 3.添加到回收池
                removeView(viewList.remove(0));
                //顶部每移除一个item时，scrollY重置，并且第一个item在数据中位置++
                scrollY-=heights[firstRow];//scrollY=0;
                firstRow++;
            }
            //上滑底部添加item：recyclerView屏幕高度>显示区域高度时（此时底部尚未添加）
            while (getFillHeight()<height){
                int addLast = firstRow + viewList.size();//应该被添加的item的索引
                View view=obtainView(addLast,width,heights[addLast]);
                viewList.add(viewList.size(),view);//添加到viewList显示区域集合最末端
            }
        }
        //下滑负
        else if (scrollY < 0) {
            //下滑顶部添加item
            while (scrollY<0){
                int firstAddRow = firstRow - 1;//顶部添加的索引
                View view=obtainView(firstAddRow,width,heights[firstAddRow]);
                viewList.add(0,view);
                //顶部每添加一个item时，scrollY重置，并且第一个item在数据中位置--
                firstRow--;
                scrollY+=heights[firstRow+1];
            }
            //下滑底部移除item
            //viewList显示区域集合总高度-scrollY-最底部item高度>=recyclerview高度时
            //例如:三个item显示,顶部半个,底部半个,中间一个
            // TODO: 19-12-2
            while (sumArray(heights,firstRow,viewList.size())-scrollY-heights[firstRow+viewList.size()-1]>=height){
                removeView(viewList.remove(viewList.size()-1));
            }
        } else {
        }
        rePositionViews();
    }

    /**
     * 极限修正
     * @param scrollY
     * @return
     */
    private int scrollBounds(int scrollY) {
        if (scrollY>0) {//上滑
            scrollY=Math.min(scrollY,sumArray(heights,firstRow,heights.length-firstRow)-height);//当底部没有更多元素时
        }else {//下滑
            scrollY=Math.max(scrollY,-sumArray(heights,0,firstRow));//当顶部没有更多元素时,在下滑scrollY为负值,求和没有数据为0
        }
        return scrollY;
    }

    /**
     * 移除或增加后,重新摆放item;
     * 此时scrollY就时firstRow的top;bottom以此类推,其他不变.
     */
    private void rePositionViews() {
        int left,top,right,bottom,i;
        top=-scrollY;
        i=firstRow;
        for (View view : viewList) {
            bottom=top+heights[i++];
            view.layout(0,top,width,bottom);
            top=bottom;
        }
    }

    private int getFillHeight() {
//        显示区域（整体显示和部分显示数据）的数据的高度之和-scrollY的高度
//        例如整体6个item在一屏，顶端显示半个：6个减去划出的scrollY高度
        return sumArray(heights,firstRow,viewList.size())-scrollY;//viewList显示区域集合
    }


    @Override
    public void removeView(View view) {
        super.removeView(view);
        int key = (int) view.getTag(R.id.tag_type_view);
        recycler.put(view, key);
    }

    interface Adapter {
        View onCreateViewHolder(int position, View convertView, ViewGroup parent);

        View onBinderViewHolder(int position, View convertView, ViewGroup parent);

        int getItemViewType(int row);

        int getViewTypeCount();

        int getCount();

        int getHeight(int i);
    }
}
