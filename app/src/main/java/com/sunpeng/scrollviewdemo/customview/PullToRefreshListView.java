package com.sunpeng.scrollviewdemo.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.Scroller;

/**
 * author:  sunpeng
 * date:    2016/5/19
 * 此类定义：
 */
public class PullToRefreshListView extends ListView {

    private Scroller mScroller;
    private boolean headerVisible=false;
    private int mLastX=0;
    private int mLastY=0;
    public PullToRefreshListView(Context context) {
        super(context);
        init();
    }

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PullToRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mScroller = new Scroller(getContext());
        LayoutInflater inflater = LayoutInflater.from(getContext());

    }

//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//
//        return super.onTouchEvent(ev);
//    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y -mLastY;
                if(Math.abs(deltaY)>Math.abs(deltaX)){
                    if(isTop() && deltaY > 0 ){
                        scrollBy(0,-deltaY);
                        mLastX=x;
                        mLastY=y;
                        return true;
                    }
                    if(isBottom() && deltaY<0){
                        scrollBy(0,-deltaY);
                        mLastX=x;
                        mLastY=y;
                        return true;
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                if(getCount()!=0){
                    if(getChildAt(0)!=null && getChildAt(0).getTop()>0){
                        smoothScroll(0,-getScrollY());
                        return true;
                    }
                    if(getChildAt(getCount()-1) !=null && getChildAt(getCount()-1).getBottom()>0){
                        smoothScroll(0,-getScrollY());
                        return true;
                    }
                }

                break;
            default:
                break;
        }
        mLastX=x;
        mLastY=y;

        return super.dispatchTouchEvent(ev);
    }

    private boolean interceptTouchEvent(){
        if(headerVisible){
            return true;
        }else{
            if(getFirstVisiblePosition()==0){}
        }
        return false;
    }

    private void smoothScroll(int dx,int dy){
        mScroller.startScroll(0,getScrollY(),0,dy,500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }

    private boolean isTop(){
        if(getFirstVisiblePosition()==0){
            View view =getChildAt(0);
            if(view !=null && view.getTop()>=0){
                return true;
            }
        }

        return false;
    }

    private boolean isBottom(){
        if(getLastVisiblePosition()==getCount()-1){
            View view =getChildAt(getCount()-1);
            if(view !=null && view.getBottom()<=0){
                return true;
            }
        }

        return false;
    }

}
