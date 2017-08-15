package com.cjp.draw;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;

/**
 * Created by 蔡金品 on 2017/7/4.
 * email : caijinpin@zhexinit.com
 */
public class DrawActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyDrawView view = new MyDrawView(this);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(view);
    }

    public static final String TAG = "DrawActivity";
}
