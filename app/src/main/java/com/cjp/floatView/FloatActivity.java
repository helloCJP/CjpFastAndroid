package com.cjp.floatView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.cjp.R;

/**
 * Created by 蔡金品 on 2017/10/30.
 * email : caijinpin@zhexinit.com
 */
public class FloatActivity extends AppCompatActivity{
    public static final String TAG = "FloatActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float);
        FloatView.setup(this);
    }
}
