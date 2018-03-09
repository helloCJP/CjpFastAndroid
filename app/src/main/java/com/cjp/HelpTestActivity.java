package com.cjp;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.airbnb.lottie.LottieAnimationView;

/**
 * Created by 蔡金品 on 2017/12/6.
 * email : caijinpin@zhexinit.com
 */
public class HelpTestActivity extends AppCompatActivity{
    public static final String TAG = "HelpTestActivity";

    LottieAnimationView lottie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_test);


        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_main);

        lottie = new LottieAnimationView(this);
        lottie.setLayoutParams(new RelativeLayout.LayoutParams(400, 400));
        relativeLayout.addView(lottie);
        lottie.setAnimation("app/ting.json");


        lottie.addAnimatorUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (lottie.getProgress() > 0.83333333f){
                    lottie.pauseAnimation();
                    lottie.setProgress(0.43f);
                    lottie.resumeAnimation();
                }
//                Log.i("cjp","dur  = " + lottie.isAnimating()  + "   pro  = " + lottie.getProgress() + "  isRunning " + animation.isRunning() + "  isStarted  = " + animation.isStarted());
//                lottie.getProgress();
//                animation.getDuration();
            }
        });

//        lottie.loop(true);
        lottie.playAnimation();



        final LottieAnimationView lottie2 = new LottieAnimationView(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(400, 400);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lottie2.setLayoutParams(params);
        relativeLayout.addView(lottie2);
        lottie2.setAnimation("app/ting2.json");


        lottie2.addAnimatorUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (lottie2.getProgress() > 0.83333333f){
                    lottie2.pauseAnimation();
                    lottie2.setProgress(0.43f);
                    lottie2.resumeAnimation();
                }
//                Log.i("cjp","dur  = " + lottie2.isAnimating()  + "   pro  = " + lottie2.getProgress() + "  isRunning " + animation.isRunning() + "  isStarted  = " + animation.isStarted());
//                lottie.getProgress();
//                animation.getDuration();
            }
        });

//        lottie.loop(true);
        lottie2.playAnimation();


        final LottieAnimationView lottie3 = new LottieAnimationView(this);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(400, 400);
        params1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lottie3.setLayoutParams(params1);
        relativeLayout.addView(lottie3);
        lottie3.setAnimation("app/ting3.json");
        lottie3.addAnimatorListener(new Animator.AnimatorListener() {
            boolean first = false;
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
//                if (!first){
//                    lottie3.pauseAnimation();
//                    lottie3.setAnimation("app/ting4.json");
//                    lottie3.loop(true);
//                    first = true;
//                    lottie3.playAnimation();
//                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
//                lottie3.setAnimation("app/ting4.json");
//                lottie3.playAnimation();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                if (!first){
                    lottie3.pauseAnimation();
                    lottie3.setAnimation("app/ting4.json");

                    first = true;
                    lottie3.resumeAnimation();
                }
            }
        });
        lottie3.loop(true);
        lottie3.playAnimation();

        final LottieAnimationView lottie5 = new LottieAnimationView(this);
        params1 = new RelativeLayout.LayoutParams(400, 400);
        params1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lottie5.setLayoutParams(params1);
        relativeLayout.addView(lottie5);
        lottie5.setAnimation("app/ting4.json");
        lottie5.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        lottie5.loop(true);
        lottie5.playAnimation();
        lottie5.pauseAnimation();
        lottie5.setVisibility(View.GONE);


        final LottieAnimationView lottie4 = new LottieAnimationView(this);
        params1 = new RelativeLayout.LayoutParams(400, 400);
        params1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lottie4.setLayoutParams(params1);
        relativeLayout.addView(lottie4);
        lottie4.setAnimation("app/ting3.json");
        lottie4.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                lottie5.setVisibility(View.VISIBLE);
                lottie5.resumeAnimation();
                lottie4.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        lottie4.playAnimation();





        // Translucent status bar

//        RelativeLayout rl = (RelativeLayout) findViewById(R.id.rela);
//        rl.setFitsSystemWindows(true);
//        rl.setClipToPadding(true);

//        lottie.setLayoutParams(new RelativeLayout.LayoutParams(1080, 1920));
//        lottie.setFitsSystemWindows(true);
    }
}
