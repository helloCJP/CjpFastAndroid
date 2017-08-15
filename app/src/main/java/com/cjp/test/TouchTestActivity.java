package com.cjp.test;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by 蔡金品 on 2017/6/29.
 * email : caijinpin@zhexinit.com
 */
public class TouchTestActivity extends Activity{
    public static final String TAG = "TouchTestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TestRl test = new TestRl(this);
        test.setLayoutParams( new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        test.setBackgroundColor(Color.BLUE);
        setContentView(test);

        TextView t = new TextView(this);
        t.setLayoutParams( new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        t.setBackgroundColor(Color.GREEN);
        test.addView(t);

        ViewGroup v = new ViewGroup(this) {
            @Override
            protected void onLayout(boolean changed, int l, int t, int r, int b) {

            }
        };
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i("cjp","TouchTestActivity   dispatchTouchEvent");

        return super.dispatchTouchEvent(event);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("cjp","TouchTestActivity   onTouchEvent = " + event.getAction());
        return super.onTouchEvent(event);
    }

}
