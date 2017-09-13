package com.cjp.video.ijkplayerview;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import com.cjp.R;
import com.danikula.videocache.CacheListener;
import com.danikula.videocache.HttpProxyCacheServer;
import com.dl7.player.media.IjkPlayerView;

import java.io.File;

/**
 * Created by 蔡金品 on 2017/8/22.
 * email : caijinpin@zhexinit.com
 */
public class IjkPlayerActivity extends AppCompatActivity implements CacheListener {
    public static final String TAG = "IjkPlayerActivity";

    private static final String VIDEO_URL = "http://flv2.bn.netease.com/videolib3/1611/28/GbgsL3639/SD/movie_index.m3u8";
    private static final String VIDEO_HD_URL = "http://flv2.bn.netease.com/videolib3/1611/28/GbgsL3639/HD/movie_index.m3u8";
    private static final String video = "http://xb-vod.oss-cn-hangzhou.aliyuncs.com/babyclass/720/XQHjxnkQDH4zer22ZR8MD3mhPncQejc6.mp4";
    private static final String video_ss = "http://xb-vod.oss-cn-hangzhou.aliyuncs.com/video/00696123-8037-46a3-b52a-00af1d47a717.mp4?orid=123456";

    IjkPlayerView playerView ;

    CjpPlayerView view;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.ijkplayer_layout);
//        playerView = (IjkPlayerView) findViewById(R.id.ijkplayer);
//        playerView.init()
//                .setTitle("这是个跑马灯TextView，标题要足够长才会跑。-(゜ -゜)つロ 乾杯~")
//                .setSkipTip(1000*60*1)
//                .enableDanmaku(false)
//                .setVideoPath( VIDEO_HD_URL)
//                .setMediaQuality(IjkPlayerView.MEDIA_QUALITY_HIGH)
//                .alwaysFullScreen() ;




        view = new CjpPlayerView(this);
        setContentView(view);
        view.init();//.setVideoPath(VIDEO_URL).start();

        HttpProxyCacheServer proxy = getProxy();
        String proxyUrl = proxy.getProxyUrl(video_ss);
        proxy.registerCacheListener(this, video_ss);
        view.setVideoPath(proxyUrl).start();
    }


    HttpProxyCacheServer proxy;

    private HttpProxyCacheServer getProxy() {
        if (proxy != null ){
            return proxy;
        }
        // should return single instance of HttpProxyCacheServer shared for whole app.
        File file = new File("/sdcard/cjpvideo");
        if (!file.exists()) {
            file.mkdirs();
        }
        HttpProxyCacheServer.Builder builder = new HttpProxyCacheServer.Builder(this);
        builder.cacheDirectory(file);
        builder.fileNameGenerator(new MyFileNameGenerator());
        //cacheFile = file;
        proxy =  builder.build();
        return proxy;
    }


        @Override
    protected void onResume() {
        super.onResume();
            view.mVideoView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        view.mVideoView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        view.mVideoView.destroy();
        proxy.unregisterCacheListener(this);
    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (playerView.handleVolumeKey(keyCode)) {
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (playerView.onBackPressed()) {
//            return;
//        }
//        super.onBackPressed();
//    }

    @Override
    public void onCacheAvailable(File cacheFile, String url, int percentsAvailable) {
        Log.i("cjp","    cacheFile = " + cacheFile.getAbsolutePath() + "    url = " + url + "    percent = " + percentsAvailable);
    }
}
