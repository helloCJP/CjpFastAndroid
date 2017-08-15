package com.cjp.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;


public class MyMediaPlayerActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JCVideoPlayerStandard jcVideoPlayerStandard = new JCVideoPlayerStandard(this);
        setContentView(jcVideoPlayerStandard);
        jcVideoPlayerStandard.setUp("http://video.jiecao.fm/11/23/xin/%E5%81%87%E4%BA%BA.mp4"
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "嫂子不信");
        Picasso.with(this)
                .load("http://img4.jiecaojingxuan.com/2016/11/23/00b026e7-b830-4994-bc87-38f4033806a6.jpg@!640_360")
                .into(jcVideoPlayerStandard.thumbImageView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
}