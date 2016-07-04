package com.sunpeng.scrollviewdemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sunpeng.scrollviewdemo.R;
import com.sunpeng.scrollviewdemo.customview.PullLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        Button btn_horizontal = (Button) findViewById(R.id.btn_horizontal);
        Button btn_vertical = (Button) findViewById(R.id.btn_vertical);
        Button btn_refresh_lv = (Button) findViewById(R.id.btn_refreshLv);
        Button btn_pull_layout = (Button) findViewById(R.id.btn_pull_layout);

        btn_horizontal.setOnClickListener(this);
        btn_vertical.setOnClickListener(this);
        btn_refresh_lv.setOnClickListener(this);
        btn_pull_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_horizontal:
                Intent intent = new Intent(MainActivity.this,HorizontalScrollViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_vertical:
                Intent intent1 = new Intent(MainActivity.this,VerticalScrollViewActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_refreshLv:
                Intent intent2 = new Intent(MainActivity.this,StickyLayoutActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_pull_layout:
                Intent intent3 = new Intent(MainActivity.this,PullLayoutActivity.class);
                startActivity(intent3);
                break;
            default:
                break;
        }
    }
}
