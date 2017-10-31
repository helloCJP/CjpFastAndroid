package com.cjp.floatView;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.cjp.R;

/**
 * Created by 蔡金品 on 2017/10/30.
 * email : caijinpin@zhexinit.com
 */
public class FloatView extends ImageView implements View.OnClickListener {
    public static final String TAG = "FloatView";

    float uiScale = 1;

    public FloatView(Activity context, float uiScale) {
        super(context);
        this.setImageResource(R.drawable.ball);
        this.setOnClickListener(this);
        this.uiScale = uiScale;
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);

        Rect rect = new Rect();
        context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);

        softbarHeight = metrics.heightPixels - rect.bottom;
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        Log.i("cjp"," screenWidth = " + screenWidth + "    screenHeight = " + screenHeight);
    }

    int softbarHeight = 0;
    int screenWidth = 0 ;
    int screenHeight = 0;

    int dx = 0;
    int dy = 0;

    int mr = 0;
    int mb = 0;

    boolean move = false;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                dx = rawX;
                dy = rawY;
                FrameLayout.LayoutParams fp = (FrameLayout.LayoutParams) getLayoutParams();
                mr = fp.rightMargin;
                mb = fp.bottomMargin;
                move = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(rawX - dx) > 100 || Math.abs(rawY - dy) >100){
                    move = true;
                    upDateLayoutParams(rawX, rawY);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (move){
                    move = false;
                    anima();
                } else {
                    onClick(this);
                }
                break;
        }
        return true;//super.onTouchEvent(event);
    }

    private void anima(){
        if ( getLeft() + getRight() > screenWidth) {
            // 右移
            startAnimaRight((screenWidth - getRight()),0);
        } else {
            startAnimaRight((screenWidth - getRight()),screenWidth - getWidth());
        }
    }

    private void startAnimaRight(int from,int to){
        ObjectAnimator oa= ObjectAnimator.ofInt(new LayoutWapper(this),"rightMargin",from,to);
        oa.setDuration(500);
        oa.setInterpolator(new DecelerateInterpolator());
        oa.setStartDelay(100);
        oa.start();
    }
    private class LayoutWapper {
        private View target;
        public LayoutWapper(View target) {
            this.target = target;
        }
        public void setRightMargin(int value) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)getLayoutParams();
            layoutParams.rightMargin = value;
            target.setLayoutParams(layoutParams);
        }
    }


    @Override
    public void onClick(View v) {
        Toast.makeText(getContext(), "点击了  FLOAT VIEW ", Toast.LENGTH_SHORT).show();
    }


    private void upDateLayoutParams(int rawX, int rawY){
        FrameLayout.LayoutParams fp = (FrameLayout.LayoutParams) getLayoutParams();
        fp.rightMargin = getRealRightMargin( rawX);
        fp.bottomMargin = getRealBottomMargin(rawY);
        setLayoutParams(fp);
    }

    private int getRealBottomMargin( int rawY) {
        int bottom = mb + dy - rawY;
        if (bottom < (int)(150 * uiScale)){
            bottom = (int)(150 * uiScale);
        } else if (bottom > screenHeight - getHeight() - softbarHeight){
            bottom = screenHeight - getHeight() - softbarHeight;
        }
        return bottom;
    }

    private int getRealRightMargin( int rawX) {
        int right = mr - rawX + dx;
        if ( right + getWidth() > screenWidth){
            right = screenWidth - getWidth();
        } else if (right < 0){
            right = 0;
        }
        return right;
    }


    public static FloatView setup(Activity activity){
        float uiScale = 1;
        FloatView ui = new FloatView(activity, uiScale);
        int padding = (int) (12 * uiScale);
        ui.setPadding(padding, padding, padding, padding);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams((int) (192 * uiScale) + 2 * padding, (int) (192 * uiScale) + 2 * padding);
        params.gravity = Gravity.BOTTOM | Gravity.RIGHT ;
        params.bottomMargin = (int) (200 * uiScale);
        ui.setLayoutParams(params);

        FrameLayout decor = (FrameLayout) activity.getWindow().getDecorView();
        decor.addView(ui);
        return ui;
    }
}
