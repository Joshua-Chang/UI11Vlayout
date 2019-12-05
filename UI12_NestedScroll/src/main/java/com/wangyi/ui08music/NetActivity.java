package com.wangyi.ui08music;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public class NetActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    Toolbar toolbar;
    private void initView(){
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this,R.mipmap.icon_video_down));
        ViewGroup content = (ViewGroup) findViewById(android.R.id.content);
        View parentView = content.getChildAt(0);
        if (parentView != null) {
            parentView.setFitsSystemWindows(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_music,menu);
        return true;
    }



}
