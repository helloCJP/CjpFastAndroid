package com.cjp.test;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 蔡金品 on 2017/6/29.
 * email : caijinpin@zhexinit.com
 */
public class TextView extends View{
    public static final String TAG = "TextView";

    public TextView(Context context) {
        super(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i("cjp","TextView   dispatchTouchEvent");

        return super.dispatchTouchEvent(event);
    }


    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);
        Log.i("cjp","text layout");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i("cjp","text onLayout");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i("cjp","text onMeasure");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("cjp","TextView   onTouchEvent");
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i("cjp","TextView   onDraw");
        super.onDraw(canvas);
    }
}
