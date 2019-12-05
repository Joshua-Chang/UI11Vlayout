package com.wangyi.ui09;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private RecyclerView mRecycler;
    private FeedAdapter feedAdapter;
    private RelativeLayout mSuspensionBar;
    private TextView mSuspensionTv;
    private ImageView mSuspensionIv;

    private int mSuspensionHeight;
    private int mCurrentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        mToolbar = findViewById(R.id.toolbar);
        mRecycler = findViewById(R.id.recycler);

//4.4
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//5.0
        immersive();
        setHeightAndPadding(this,mToolbar);
        mSuspensionBar = findViewById(R.id.suspension_bar);
        mSuspensionTv = findViewById(R.id.tv_nickname);
        mSuspensionIv = findViewById(R.id.iv_avatar);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(layoutManager);
        feedAdapter = new FeedAdapter();
        mRecycler.setAdapter(feedAdapter);
        mRecycler.setHasFixedSize(true);
        mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mSuspensionHeight = mSuspensionBar.getHeight();//自身高度
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View view = layoutManager.findViewByPosition(mCurrentPosition + 1);
                if (view != null) {
                    int top = view.getTop();
                    if (top <= mSuspensionHeight) {
                        Log.e("xxx", top + "\t" + mSuspensionHeight + "\t|||||||||"+mCurrentPosition);
                        mSuspensionBar.setY(-(mSuspensionHeight - view.getTop()));
                    } else {
                        Log.e("xxx", top + "\t" + mSuspensionHeight + "\t---------"+mCurrentPosition);
                        mSuspensionBar.setY(0);
                    }
                }

                if (mCurrentPosition != layoutManager.findFirstVisibleItemPosition()) {
                     mCurrentPosition = layoutManager.findFirstVisibleItemPosition();
                    updateSuspensionBar();
                }
            }
        });
        updateSuspensionBar();
    }

    private void immersive() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.KITKAT){
            return;
        }
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);//5.0
            int visibility = window.getDecorView().getSystemUiVisibility();
            visibility|=View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            visibility|=View.SYSTEM_UI_FLAG_LAYOUT_STABLE;//防止内容区域大小发生变化

//            visibility|=View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            window.getDecorView().setSystemUiVisibility(visibility);
        }else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//4.4
        }
    }

    private void updateSuspensionBar() {
        mSuspensionIv.setImageResource(getAvaId(mCurrentPosition));
        mSuspensionTv.setText("NetEase" + mCurrentPosition);
    }


    private int getAvaId(int position) {
        switch (position % 4) {
            case 0:
                return R.mipmap.icon_mypage_like;
            case 1:
                return R.mipmap.icon_mypage_money;
            case 2:
                return R.mipmap.icon_mypage_partner;
            case 3:
                return R.mipmap.icon_mypage_sales;
        }
        return 0;
    }



    public static void main(String[] args) {
//        List<String> list = new ArrayList<>();
//        test1(list);
//        System.out.println(list.size());
//        test2(list);
//        System.out.println(list.size());
//        test3(list);
//        System.out.println(list.size());
    }

//所以如果在方法中改变参数引用（拷贝的引用）指向的内存地址，对方法外的引用是不会产生任何影响的
    public static void test1(List list) {
        list=null;
//        list=new ArrayList();
//        list.add("b");
    }

    public static void test2(List list) {
        list.add("a");
    }

    public static void test3(List list) {
        list.add(1);
    }


    public int getStatusBarHeight(Context context){
        int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (identifier>0) {
            return context.getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public void setHeightAndPadding(Context context,View view){
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height+=getStatusBarHeight(context);
        view.setPadding(view.getPaddingLeft(),view.getPaddingTop()+getStatusBarHeight(context),view.getPaddingRight(),view.getPaddingBottom());
    }



}
