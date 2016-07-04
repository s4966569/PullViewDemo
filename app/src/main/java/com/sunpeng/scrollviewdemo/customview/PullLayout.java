package com.sunpeng.scrollviewdemo.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Scroller;
import android.widget.TextView;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * author:  sunpeng
 * date:    2016/5/20
 * 此类定义：
 */
public class PullLayout extends LinearLayout {

    public interface GiveUpOnTouchEventListener {
        boolean giveUpOnTouchEvent(MotionEvent event);
    }


    private View mHeader;
    private int mHeaderHeight;
    private int mTouchSlop;  //有效滑动的最小距离  nexus4上面测得的是16

    private Scroller mScroller;
    private int mLastX, mLastY;
    private ListView lv;
    int count = 0;
    private GiveUpOnTouchEventListener giveUpOnTouchEventListener;

    public PullLayout(Context context) {
        super(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PullLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public PullLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PullLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init() {
        mScroller = new Scroller(getContext());
        int headerId = getResources().getIdentifier("pull_header", "id", getContext().getPackageName());
        if (headerId != 0) {
            mHeader = findViewById(headerId);
            mHeaderHeight = mHeader.getMeasuredHeight();
            mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        } else {
            throw new NoSuchElementException("Did your view with id \"pull_header\" exists?");
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus && mHeader == null) {
            init();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                    intercepted=true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                if (Math.abs(deltaY) > Math.abs(deltaX)) {
                    if (getScrollY() == mHeaderHeight) {
                        if (deltaY > 0 && giveUpOnTouchEventListener != null && giveUpOnTouchEventListener.giveUpOnTouchEvent(ev)) {
                            intercepted = true;
                        }
                    } else {
                        intercepted = true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }

        mLastX = x;
        mLastY = y;

        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = y - mLastY;
                if (getScrollY() - deltaY > mHeaderHeight) {
                    deltaY = getScrollY() - mHeaderHeight;
//                    requestDisallowInterceptTouchEvent(true);
                }
                scrollBy(0, -deltaY);
                break;
            case MotionEvent.ACTION_UP:
                if (getScrollY() < 0) {
                    smoothScroll(0, -getScrollY());
                }
                break;
            default:
                break;
        }

        mLastX = x;
        mLastY = y;
        return true;
    }

    private void smoothScroll(int dx, int dy) {
        mScroller.startScroll(0, getScrollY(), dx, dy, 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller == null) {
            return;
        }
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    public void setGiveUpOnTouchEventListener(GiveUpOnTouchEventListener giveUpOnTouchEventListener) {
        this.giveUpOnTouchEventListener = giveUpOnTouchEventListener;
    }

    public ListView getLv() {
        return lv;
    }

    public void setLv(ListView lv) {
        this.lv = lv;
    }
}
