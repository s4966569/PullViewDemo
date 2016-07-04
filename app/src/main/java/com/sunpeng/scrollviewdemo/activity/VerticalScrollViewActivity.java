package com.sunpeng.scrollviewdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sunpeng.scrollviewdemo.R;

import java.util.ArrayList;

/**
 * author:  sunpeng
 * date:    2016/5/20
 * 此类定义：
 */
public class VerticalScrollViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_scroll_view);

        ListView listView = (ListView)findViewById(R.id.lv);
        ArrayList<String> datas = new ArrayList<>();
        for(int i =0;i<50;i++){
            datas.add("name "+i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.content_list_item,R.id.name,datas);
        listView.setAdapter(adapter);
    }
}
