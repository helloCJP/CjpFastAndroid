package com.cjp.lottie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
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
        LottieAnimationView demo7 = (LottieAnimationView)findViewById(R.id.demo7);
        demo7.setAnimation("lottie/demo7.json");
        demo7.loop(true);
        demo7.playAnimation();


        LottieAnimationView test = (LottieAnimationView) findViewById(R.id.eee);
        test.setImageAssetsFolder("lottie/images");
        test.setAnimation("lottie/test.json");
        test.loop(true);
        test.playAnimation();
    }
}
