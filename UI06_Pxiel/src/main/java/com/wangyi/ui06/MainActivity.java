package com.wangyi.ui06;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private UIRelativeLayout mLine1;
    private TextView mText1;
    private TextView mText2;
    private TextView mText3;
    private TextView mText4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLine1 = findViewById(R.id.line1);
        mText1 = findViewById(R.id.text1);
        mText2 = findViewById(R.id.text2);
        mText3 = findViewById(R.id.text3);
        mText4 = findViewById(R.id.text4);
        ViewCalculateUtil.setViewLayoutParam(mText3,1080,50,0,0,0,0);
        ViewCalculateUtil.setViewLayoutParam(mText4,540,50,0,0,0,0);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
            UIUtils.notifyInstance(this);
    }
}
