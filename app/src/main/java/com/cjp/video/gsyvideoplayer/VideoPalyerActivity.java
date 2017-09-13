package com.cjp.video.gsyvideoplayer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

/**
 * Created by 蔡金品 on 2017/8/18.
 * email : caijinpin@zhexinit.com
 */
public class VideoPalyerActivity extends AppCompatActivity{
    public static final String TAG = "VideoPalyerActivity";

    StandardGSYVideoPlayer player;

    OrientationUtils orientationUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        player = new StandardGSYVideoPlayer(this);
        RelativeLayout rl = new RelativeLayout(this);
        rl.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        //player.setLayoutParams(new RelativeLayout.LayoutParams(200,200));
        //rl.addView(player);
        setContentView(player);
        init();
    }

    private void init() {
        player.setUp("http://video19.ifeng.com/video06/2012/09/28/97b03b63-1133-43d0-a6ff-fb2bc6326ac7.mp4", true, "猜猜我是谁");

        player.getBackButton().setVisibility(View.VISIBLE);
        player.setNeedShowWifiTip(true);

        //开启自动旋转
        player.setRotateViewAuto(true);


        //全屏首先横屏
        player.setLockLand(false);

        //是否需要全屏动画效果
        player.setShowFullAnimation(false);

        player.setRotateWithSystem(true);



        //设置旋转
//        orientationUtils = new OrientationUtils(this, player);
//        orientationUtils.setRotateWithSystem(true);
//
//        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
//        player.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                orientationUtils.resolveByClick();
//            }
//        });

        //videoPlayer.setBottomProgressBarDrawable(getResources().getDrawable(R.drawable.video_new_progress));
        //videoPlayer.setDialogVolumeProgressBar(getResources().getDrawable(R.drawable.video_new_volume_progress_bg));
        //videoPlayer.setDialogProgressBar(getResources().getDrawable(R.drawable.video_new_progress));
        //videoPlayer.setBottomShowProgressBarDrawable(getResources().getDrawable(R.drawable.video_new_seekbar_progress),
        //getResources().getDrawable(R.drawable.video_new_seekbar_thumb));
        //videoPlayer.setDialogProgressColor(getResources().getColor(R.color.colorAccent), -11);

        //是否可以滑动调整
        player.setIsTouchWiget(true);

        //设置返回按键功能
        player.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }



    @Override
    protected void onPause() {
        super.onPause();
        player.onVideoPause();
    }
}
