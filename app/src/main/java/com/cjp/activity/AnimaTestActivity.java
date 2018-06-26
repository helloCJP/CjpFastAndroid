package com.cjp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.cjp.R;

/**
 * Created by 蔡金品 on 2018/6/23.
 * email : caijinpin@zhexinit.com
 */

public class AnimaTestActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anima_test);
        final ImageView imageView = (ImageView) findViewById(R.id.anima_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScaleAnimation scaleAnimation =new ScaleAnimation(1.0f,0.5f, 1.0f, 0.5f, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(10000);
                scaleAnimation.setFillAfter(true);
                imageView.startAnimation(scaleAnimation);
            }
        });
    }
}
