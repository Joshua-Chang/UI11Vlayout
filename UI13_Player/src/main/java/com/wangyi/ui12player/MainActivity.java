package com.wangyi.ui12player;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.wangyi.ui12player.view.BackgourndAnimationRelativeLayout;
import com.wangyi.ui12player.view.DiscView;
import com.wangyi.ui12player.view.MusicListener;
import com.wangyi.ui12player.view.UIUtils;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class MainActivity extends AppCompatActivity implements MusicListener {
    private List<Integer> mMusicDatas = new ArrayList<>();
    private BackgourndAnimationRelativeLayout backgourndAnimationRelativeLayout;
    DiscView mDisc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIUtils.getInstance(this);
        setContentView(R.layout.activity_main);
        backgourndAnimationRelativeLayout=findViewById(R.id.rootLayout);
        mDisc=findViewById(R.id.discview);
        mDisc.setMusicListener(this);
        mMusicDatas.add(R.drawable.ic_music1);
        mMusicDatas.add(R.drawable.ic_music2);
        mMusicDatas.add(R.drawable.ic_music3);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDisc.setmMusicDataList(mMusicDatas);
    }

    @Override
    public void onMusicPicChanged(int resId) {
        Glide.with(this)
                .load(resId)
                .crossFade(500)
                .bitmapTransform(new BlurTransformation(this,200,3))
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        backgourndAnimationRelativeLayout.setForeground(resource);
                    }
                });
    }
}
