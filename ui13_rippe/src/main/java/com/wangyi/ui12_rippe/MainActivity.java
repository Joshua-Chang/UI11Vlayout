package com.wangyi.ui12_rippe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.wangyi.ui12_rippe.ui.UIUtils;

public class MainActivity extends AppCompatActivity {

    RippleAnimationView rippleAnimationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIUtils.getInstance(this);
//        setContentView(R.layout.activity_main);
        setContentView(new PathLoadingView(this));
//        rippleAnimationView=findViewById(R.id.layout_RippleAnimation);
//        findViewById(R.id.ImageView).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (rippleAnimationView.isAnimationRunning()) {
//                    rippleAnimationView.stopRippleAnimation();
//                }else {
//                    rippleAnimationView.startRippleAnimation();
//                }
//            }
//        });
    }
}
