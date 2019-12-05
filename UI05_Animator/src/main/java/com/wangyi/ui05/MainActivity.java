 package com.wangyi.ui05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wangyi.ui05.animator.LinnerInterpolator;
import com.wangyi.ui05.animator.MyObjectAnimator;

 public class MainActivity extends AppCompatActivity {

     private Button button;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         button = findViewById(R.id.bt);

    }

     public void scale(View view) {
         MyObjectAnimator animator=MyObjectAnimator.
                 ofFloat(button,"scaleX",1f,2f);
         animator.setInterpolator(new LinnerInterpolator());
         animator.setmDuration(3000);
         animator.start();
     }
 }
