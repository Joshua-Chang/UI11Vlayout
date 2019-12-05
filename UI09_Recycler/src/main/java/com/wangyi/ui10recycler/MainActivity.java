package com.wangyi.ui10recycler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView2 recyclerView=findViewById(R.id.table);
        recyclerView.setAdapter(new RecyclerView2.Adapter() {
            @Override
            public View onCreateViewHolder(int position, View convertView, ViewGroup parent) {
                convertView=MainActivity.this.getLayoutInflater().inflate(R.layout.item_table,parent,false);
                TextView textView = (TextView) convertView.findViewById(R.id.text1);
                textView.setText("create: "+position);
                return convertView;
            }

            @Override
            public View onBinderViewHolder(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) convertView.findViewById(R.id.text1);
                textView.setText("binder:"+position);
                return convertView;
            }

            @Override
            public int getItemViewType(int row) {
                return 0;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public int getCount() {
                return 30;
            }

            @Override
            public int getHeight(int i) {
                return 100;
            }
        });
    }
}
