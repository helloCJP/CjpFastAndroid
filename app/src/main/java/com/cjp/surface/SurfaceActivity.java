package com.cjp.surface;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by 蔡金品 on 2017/11/17.
 * email : caijinpin@zhexinit.com
 */
public class SurfaceActivity extends AppCompatActivity{
    public static final String TAG = "SurfaceActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = new DemoSurfaceView(this);
        setContentView(view);
    }
}
