package com.cjp.test;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by 蔡金品 on 2017/6/29.
 * email : caijinpin@zhexinit.com
 */
public class TestRl extends RelativeLayout{
    public static final String TAG = "TestRl";

    public TestRl(Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("cjp","TestRl   onInterceptTouchEvent");
        return  true;
//        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i("cjp","TestRl   dispatchTouchEvent");

        return super.dispatchTouchEvent(event);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("cjp","TestRl   onTouchEvent  = " + event.getAction());
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN :
//                scrollTo(0,200);
                scrollBy(0,200);
                return true;
            default:
                return true;
        }


//        return super.onTouchEvent(event);
    }


    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        Log.i("cjp","testRl drawChild");
        return super.drawChild(canvas, child, drawingTime);
    }

    @Override
    protected void measureChild(View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {
        Log.i("cjp","testRl measureChild");
        super.measureChild(child, parentWidthMeasureSpec, parentHeightMeasureSpec);
    }

    @Override
    protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        Log.i("cjp","testRl measureChildWithMargins");
        super.measureChildWithMargins(child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
    }

    @Override
    protected void measureChildren(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i("cjp","testRl measureChildren");
        super.measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.i("cjp","testRl onLayout");
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public void addView(View child) {
        Log.i("cjp","testRl addView");
        super.addView(child);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i("cjp","testRl onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
