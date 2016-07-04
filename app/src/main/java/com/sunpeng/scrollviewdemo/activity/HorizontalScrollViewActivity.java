package com.sunpeng.scrollviewdemo.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sunpeng.scrollviewdemo.R;
import com.sunpeng.scrollviewdemo.customview.HorizontalScrollViewEx;

import java.util.ArrayList;

/**
 * author:  sunpeng
 * date:    2016/5/20
 * 此类定义：
 */
public class HorizontalScrollViewActivity extends Activity {

    private HorizontalScrollViewEx horizontalScrollViewEx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_scroll_view_ex);
        initView();
    }

    private void initView(){
        LayoutInflater inflater = getLayoutInflater();
        horizontalScrollViewEx= (HorizontalScrollViewEx) findViewById(R.id.container);
        final int screenWidth = getScreenMetrics(this).widthPixels;
        for(int i =0;i<3;i++){
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.content_layout,horizontalScrollViewEx,false);
            layout.getLayoutParams().width=screenWidth;
            TextView textView= (TextView) layout.findViewById(R.id.title);
            textView.setText("page"+(i+1));
            layout.setBackgroundColor(Color.rgb(255/(i+1),255/(i+1),0));
            initListView(layout);
            horizontalScrollViewEx.addView(layout);

        }
    }

    private void initListView(ViewGroup layout){
        ListView listView = (ListView) layout.findViewById(R.id.list);
        ArrayList<String> datas = new ArrayList<>();
        for(int i =0;i<50;i++){
            datas.add("name "+i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.content_list_item,R.id.name,datas);
        listView.setAdapter(adapter);
    }

    public static DisplayMetrics getScreenMetrics(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm;
    }
}
