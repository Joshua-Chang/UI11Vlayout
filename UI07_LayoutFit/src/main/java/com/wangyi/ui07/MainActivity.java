package com.wangyi.ui07;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Density.setDensity(getApplication(),this);
        setContentView(R.layout.activity_density);
    }
}
