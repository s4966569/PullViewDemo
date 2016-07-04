package com.sunpeng.scrollviewdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.sunpeng.scrollviewdemo.R;
import com.sunpeng.scrollviewdemo.customview.PullLayout;
import com.sunpeng.scrollviewdemo.customview.StickyLayout;

import java.util.ArrayList;

/**
 * author:  sunpeng
 * date:    2016/5/20
 * 此类定义：
 */
public class PullLayoutActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_layout);
        PullLayout pullLayout = (PullLayout) findViewById(R.id.pull_layout);
        final ListView lv = (ListView) findViewById(R.id.sticky_content);
        ArrayList<String> datas = new ArrayList<>();
        for(int i =0;i<50;i++){
            datas.add("name "+i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.content_list_item,R.id.name,datas);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(PullLayoutActivity.this,"点我！",Toast.LENGTH_SHORT).show();
                Log.i("tag","current:"+view.getTop());
                Log.i("tag","top:"+parent.getChildAt(0).getTop());
            }
        });

        pullLayout.setGiveUpOnTouchEventListener(new PullLayout.GiveUpOnTouchEventListener() {
            @Override
            public boolean giveUpOnTouchEvent(MotionEvent event) {
                if(lv.getFirstVisiblePosition()==0){
                    View view = lv.getChildAt(0);
                    if(view !=null && view.getTop() >= 0 ){
                        return true;
                    }
                }
                return false;
            }
        });

//        setListViewHeight(lv);
    }


    public void setListViewHeight(ListView listView) {

        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;

        for (int i = 0; i < listAdapter.getCount(); i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);

    }


}
