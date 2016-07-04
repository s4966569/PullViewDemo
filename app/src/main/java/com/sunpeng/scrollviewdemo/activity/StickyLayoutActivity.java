package com.sunpeng.scrollviewdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sunpeng.scrollviewdemo.R;
import com.sunpeng.scrollviewdemo.customview.StickyLayout;

import java.util.ArrayList;

/**
 * author:  sunpeng
 * date:    2016/5/20
 * 此类定义：
 */
public class StickyLayoutActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_layout);
        StickyLayout stickyLayout = (StickyLayout) findViewById(R.id.sticky);
        final ListView lv = (ListView) findViewById(R.id.sticky_content);
        ArrayList<String> datas = new ArrayList<>();
        for(int i =0;i<50;i++){
            datas.add("name "+i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.content_list_item,R.id.name,datas);
        lv.setAdapter(adapter);

        stickyLayout.setOnGiveUpTouchEventListener(new StickyLayout.OnGiveUpTouchEventListener() {
            @Override
            public boolean giveUpTouchEvent(MotionEvent event) {
                if(lv.getFirstVisiblePosition()==0){
                    View view = lv.getChildAt(0);
                    if(view !=null && view.getTop() >= 0 ){
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
