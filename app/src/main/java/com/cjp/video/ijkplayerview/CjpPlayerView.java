package com.cjp.video.ijkplayerview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cjp.R;
import com.dl7.player.media.IjkVideoView;
import com.dl7.player.media.MediaPlayerParams;
import com.dl7.player.utils.NetWorkUtils;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

import static com.dl7.player.utils.StringUtils.generateTime;

/**
 * Created by 蔡金品 on 2017/8/22.
 * email : caijinpin@zhexinit.com
 */
public class CjpPlayerView extends RelativeLayout implements View.OnClickListener {
    public static final String TAG = "CjpPlayerView";

    // 进度条最大值
    private static final int MAX_VIDEO_SEEK = 1000;
    // 默认隐藏控制栏时间
    private static final int DEFAULT_HIDE_TIMEOUT = 5000;
    // 更新进度消息
    private static final int MSG_UPDATE_SEEK = 10086;
    // 尝试重连消息
    private static final int MSG_TRY_RELOAD = 10088;
    // 无效变量
    private static final int INVALID_VALUE = -1;
    // 达到文件时长的允许误差值，用来判断是否播放完成
    private static final int INTERVAL_TIME = 1000;



    // 原生的IjkPlayer
    public IjkVideoView mVideoView;
    // 播放键
    private ImageView mIvPlay;
    // 进度条
    private SeekBar mPlayerSeek;
    // 时间
    private TextView time;
    // 音量
    private ImageView mIvVolume;


    // 锁屏
    private boolean mIsForbidTouch = false;
    // 是否播放结束
    private boolean mIsPlayComplete = false;
    // 是否正在拖拽进度条
    private boolean mIsSeeking;
    // 目标进度
    private long mTargetPosition = INVALID_VALUE;
    // 当前进度
    private int mCurPosition = INVALID_VALUE;
    // 异常中断时的播放进度
    private int mInterruptPosition;
    private boolean mIsReady = false;
    // 进来还未播放
    private boolean mIsNeverPlay = true;



    // 关联的Activity
    private AppCompatActivity mAttachActivity;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_UPDATE_SEEK) {
                final int pos = _setProgress();
                if (!mIsSeeking && mVideoView.isPlaying()) {
                    // 这里会重复发送MSG，已达到实时更新 Seek 的效果
                    msg = obtainMessage(MSG_UPDATE_SEEK);
                    sendMessageDelayed(msg, 1000 - (pos % 1000));
                }
            } else if (msg.what == MSG_TRY_RELOAD) {
                if (mIsNetConnected) {
                    reload();
                }
                msg = obtainMessage(MSG_TRY_RELOAD);
                sendMessageDelayed(msg, 3000);
            }
        }
    };

    public CjpPlayerView(Context context) {
        this(context, null);
    }

    public CjpPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        _initView(context);
    }

    private void _initView(Context context) {
        if (context instanceof AppCompatActivity) {
            mAttachActivity = (AppCompatActivity) context;
        } else {
            throw new IllegalArgumentException("Context must be AppCompatActivity");
        }
        View.inflate(context, R.layout.video_layout, this);
        mVideoView = (IjkVideoView)findViewById(R.id.ijkvideo);
        mIvPlay = (ImageView)findViewById(R.id.video_play);
        mPlayerSeek = (SeekBar)findViewById(R.id.player_seek);
        mIvVolume = (ImageView)findViewById(R.id.volume_image);
        time = (TextView)findViewById(R.id.time);

        mVideoView.setOnClickListener(this);
        mIvPlay.setOnClickListener(this);
        mIvVolume.setOnClickListener(this);
    }

    /**
     * 初始化
     */
    private void _initMediaPlayer() {
        // 加载 IjkMediaPlayer 库
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

//        // 亮度
//        try {
//            int e = Settings.System.getInt(mAttachActivity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
//            float progress = 1.0F * (float) e / 255.0F;
//            WindowManager.LayoutParams layout = mAttachActivity.getWindow().getAttributes();
//            layout.screenBrightness = progress;
//            mAttachActivity.getWindow().setAttributes(layout);
//        } catch (Settings.SettingNotFoundException var7) {
//            var7.printStackTrace();
//        }
        // 进度
        mPlayerSeek.setMax(MAX_VIDEO_SEEK);
        mPlayerSeek.setOnSeekBarChangeListener(mSeekListener);
        // 视频监听
        mVideoView.setOnInfoListener(mInfoListener);
        // 触摸控制
//        mGestureDetector = new GestureDetector(mAttachActivity, mPlayerGestureListener);
//        mFlVideoBox.setClickable(true);
//        mFlVideoBox.setOnTouchListener(mPlayerTouchListener);
//        // 屏幕翻转控制
//        mOrientationListener = new OrientationEventListener(mAttachActivity) {
//            @Override
//            public void onOrientationChanged(int orientation) {
//                _handleOrientation(orientation);
//            }
//        };
//        if (mIsForbidOrientation) {
//            // 禁止翻转
//            mOrientationListener.disable();
//        }
    }


    /**
     * 初始化，必须要先调用
     *
     * @return
     */
    public CjpPlayerView init() {
        _initMediaPlayer();
        return this;
    }

    /**
     * 切换视频
     *
     * @param url
     * @return
     */
    public CjpPlayerView switchVideoPath(String url) {
        return switchVideoPath(Uri.parse(url));
    }

    /**
     * 切换视频
     *
     * @param uri
     * @return
     */
    public CjpPlayerView switchVideoPath(Uri uri) {
//        if (mQualityData != null) {
//            mQualityData.clear();
//            mQualityData = null;
//        }
        reset();
        return setVideoPath(uri);
    }



    /**
     * 设置播放资源
     *
     * @param url
     * @return
     */
    public CjpPlayerView setVideoPath(String url) {
        return setVideoPath(Uri.parse(url));
    }

    /**
     * 设置播放资源
     *
     * @param uri
     * @return
     */
    public CjpPlayerView setVideoPath(Uri uri) {
        mVideoView.setVideoURI(uri);
        if (mCurPosition != INVALID_VALUE) {
            seekTo(mCurPosition);
            mCurPosition = INVALID_VALUE;
        } else {
            seekTo(0);
        }
        return this;
    }


    /**
     * 开始播放
     *
     * @return
     */
    public void start() {
        if (mIsPlayComplete) {
            mIsPlayComplete = false;
        }
        if (!mVideoView.isPlaying()) {
            mIvPlay.setSelected(true);
//            if (mInterruptPosition > 0) {
//                mLoadingView.setVisibility(VISIBLE);
//                mHandler.sendEmptyMessage(MSG_TRY_RELOAD);
//            } else {
            mVideoView.start();
            // 更新进度
            mHandler.sendEmptyMessage(MSG_UPDATE_SEEK);
//            }
        }
        if (mIsNeverPlay) {
            mIsNeverPlay = false;
        }
        // 视频播放时开启屏幕常亮
        mAttachActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    /**
     * 重新开始
     */
    public void reload() {
        if (mIsReady) {
            // 确保网络正常时
            if (NetWorkUtils.isNetworkAvailable(mAttachActivity)) {
                mVideoView.reload();
                mVideoView.start();
//                start();
                if (mInterruptPosition > 0) {
                    seekTo(mInterruptPosition);
                    mInterruptPosition = 0;
                }
            }
        } else {
            mVideoView.release(false);
            mVideoView.setRender(IjkVideoView.RENDER_TEXTURE_VIEW);
            start();
        }
        // 更新进度
        mHandler.removeMessages(MSG_UPDATE_SEEK);
        mHandler.sendEmptyMessage(MSG_UPDATE_SEEK);
    }

    /**
     * 是否正在播放
     *
     * @return
     */
    public boolean isPlaying() {
        return mVideoView.isPlaying();
    }

    /**
     * 暂停
     */
    public void pause() {
        mIvPlay.setSelected(false);
        if (mVideoView.isPlaying()) {
            mVideoView.pause();
        }
        // 视频暂停时关闭屏幕常亮
        mAttachActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    /**
     * 跳转
     *
     * @param position 位置
     */
    public void seekTo(int position) {
        mVideoView.seekTo(position);
    }

    /**
     * 停止
     */
    public void stop() {
        pause();
        mVideoView.stopPlayback();
    }

    /**
     * 重置状态
     */
    public void reset() {
        mIsNeverPlay = true;
        mCurPosition = 0;
        stop();
        mVideoView.setRender(IjkVideoView.RENDER_TEXTURE_VIEW);
    }

    /**
     * 更新进度条
     *
     * @return
     */
    private int _setProgress() {
        if (mVideoView == null || mIsSeeking) {
            return 0;
        }
        // 视频播放的当前进度
        int position = Math.max(mVideoView.getCurrentPosition(), mInterruptPosition);
        // 视频总的时长
        int duration = mVideoView.getDuration();
        if (duration > 0) {
            // 转换为 Seek 显示的进度值
            long pos = (long) MAX_VIDEO_SEEK * position / duration;
            mPlayerSeek.setProgress((int) pos);

        }
        // 获取缓冲的进度百分比，并显示在 Seek 的次进度
        int percent = mVideoView.getBufferPercentage();
        mPlayerSeek.setSecondaryProgress(percent * 10);
        // 更新播放时间
        time.setText(generateTime(position) + "/" + generateTime(duration));
        // 返回当前播放进度
        return position;
    }



    /**
     * ============================ 播放状态控制 ============================
     */

    // 这个用来控制弹幕启动和视频同步
    private boolean mIsRenderingStart = false;
    // 缓冲开始，这个用来控制弹幕启动和视频同步
    private boolean mIsBufferingStart = false;

    // 视频播放状态监听
    private IMediaPlayer.OnInfoListener mInfoListener = new IMediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(IMediaPlayer iMediaPlayer, int status, int extra) {
            _switchStatus(status);
//            if (mOutsideInfoListener != null) {
//                mOutsideInfoListener.onInfo(iMediaPlayer, status, extra);
//            }
            return true;
        }
    };

    /**
     * 视频播放状态处理
     *
     * @param status
     */
    private void _switchStatus(int status) {
        Log.i("IjkPlayerView", "status " + status);
        switch (status) {
            case IMediaPlayer.MEDIA_INFO_BUFFERING_START:
                mIsBufferingStart = true;
                if (!mIsNeverPlay) {
//                    mLoadingView.setVisibility(View.VISIBLE);
                }
                mHandler.removeMessages(MSG_TRY_RELOAD);
            case MediaPlayerParams.STATE_PREPARING:
                break;

            case MediaPlayerParams.STATE_PREPARED:
                mIsReady = true;
                break;

            case IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                mIsRenderingStart = true;
            case IMediaPlayer.MEDIA_INFO_BUFFERING_END:
                mIsBufferingStart = false;
//                mLoadingView.setVisibility(View.GONE);
//                mPlayerThumb.setVisibility(View.GONE);
                // 更新进度
                mHandler.removeMessages(MSG_UPDATE_SEEK);
                mHandler.sendEmptyMessage(MSG_UPDATE_SEEK);
                if (mVideoView.isPlaying() && mIsNetConnected) {
                    mInterruptPosition = 0;
                    if (!mIvPlay.isSelected()) {
                        // 这里处理断网重连后不会播放情况
                        mVideoView.start();
                        mIvPlay.setSelected(true);
                    }
                }
                break;

            case MediaPlayerParams.STATE_PLAYING:
                mHandler.removeMessages(MSG_TRY_RELOAD);
                break;
            case MediaPlayerParams.STATE_ERROR:
                mInterruptPosition = Math.max(mVideoView.getInterruptPosition(), mInterruptPosition);
                pause();
                if (mVideoView.getDuration() == -1 && !mIsReady) {
//                    mLoadingView.setVisibility(View.GONE);
//                    mPlayerThumb.setVisibility(View.GONE);
//                    mIvPlayCircle.setVisibility(GONE);
//                    mFlReload.setVisibility(VISIBLE);
                } else {
                    //mLoadingView.setVisibility(VISIBLE);
                    mHandler.sendEmptyMessage(MSG_TRY_RELOAD);
                }
                break;

            case MediaPlayerParams.STATE_COMPLETED:
                pause();
                if (mVideoView.getDuration() == -1 ||
                        (mVideoView.getInterruptPosition() + INTERVAL_TIME < mVideoView.getDuration())) {
                    mInterruptPosition = Math.max(mVideoView.getInterruptPosition(), mInterruptPosition);
                    Toast.makeText(mAttachActivity, "网络异常", Toast.LENGTH_SHORT).show();
                } else {
                    mIsPlayComplete = true;
                    if (mCompletionListener != null) {
                        mCompletionListener.onCompletion(mVideoView.getMediaPlayer());
                    }
                }
                break;
        }
    }


    private IMediaPlayer.OnCompletionListener mCompletionListener;
    public void setOnCompletionListener(IMediaPlayer.OnCompletionListener l) {
        mCompletionListener = l;
//        mVideoView.setOnCompletionListener(l);
    }



    /**============================ 控制栏处理 ============================*/

    /**
     * SeekBar监听
     */
    private final SeekBar.OnSeekBarChangeListener mSeekListener = new SeekBar.OnSeekBarChangeListener() {


        @Override
        public void onStartTrackingTouch(SeekBar bar) {
            mIsSeeking = true;
            mHandler.removeMessages(MSG_UPDATE_SEEK);
        }

        @Override
        public void onProgressChanged(SeekBar bar, int progress, boolean fromUser) {
            if (!fromUser) {
                // We're not interested in programmatically generated changes to
                // the progress bar's position.
                return;
            }
            long duration = mVideoView.getDuration();
            // 计算目标位置
            mTargetPosition = (duration * progress) / MAX_VIDEO_SEEK;
        }

        @Override
        public void onStopTrackingTouch(SeekBar bar) {
            mIsSeeking = false;
            // 视频跳转
            seekTo((int) mTargetPosition);
            mTargetPosition = INVALID_VALUE;
            _setProgress();
        }
    };





    @Override
    public void onClick(View v) {
        if (v.equals(mVideoView)) {
            // TODO: 2017/8/22  实现全屏/非全屏切换
        } else if (v.equals(mIvPlay)) {
            // TODO: 2017/8/22  实现播放暂停图标切换
        } else if (v.equals(mIvVolume)) {
            // TODO: 2017/8/22  显示声音调节seekbar
        }
    }

    private boolean mIsNetConnected;

    public class NetBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // 如果相等的话就说明网络状态发生了变化
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                mIsNetConnected = NetWorkUtils.isNetworkAvailable(mAttachActivity);
            }
        }
    }
}
