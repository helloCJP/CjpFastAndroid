package com.cjp.lottie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.cjp.R;

/**
 * Created by 蔡金品 on 2017/8/10.
 * email : caijinpin@zhexinit.com
 */
public class LottieActivity extends AppCompatActivity{
    public static final String TAG = "LottieActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie);
    }
}
