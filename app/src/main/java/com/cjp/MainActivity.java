package com.cjp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.cjp.activity.PhoneInfoActivity;
import com.cjp.draw.DrawActivity;
import com.cjp.effects.EffectsActivity;
import com.cjp.effects.attrandani.DiscoverActivity;
import com.cjp.effects.ball.AccelerometerBallActivity;
import com.cjp.floatView.FloatActivity;
import com.cjp.lottie.LottieActivity;
import com.cjp.recycleview.RecycleviewActivity;
import com.cjp.refresh.RefreshActivity;
import com.cjp.slidingconflict.SlidingConflictActivity2;
import com.cjp.test.TestActivity;
import com.cjp.test.TouchTestActivity;
import com.cjp.util.NotificationsUtils;
import com.cjp.util.ToastUtil;
import com.cjp.video.gsyvideoplayer.VideoPalyerActivity;
import com.cjp.video.ijkplayerview.IjkPlayerActivity;
import com.cjp.video.jiecao.MyMediaPlayerActivity;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener{
    private final String TAG = "MainActivity";

    WindowManager mWindowManager;
    WindowManager.LayoutParams wmParams;
    LinearLayout mFloatLayout;
    Button mFloatView;


    private ListView listView;
    private List<String> data = new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        listView = new ListView(this);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getData()));
        listView.setOnItemClickListener(this);
        setContentView(listView);

    }

    private void createFloatView()
    {
        //获取LayoutParams对象
        wmParams = new WindowManager.LayoutParams();

        //获取的是LocalWindowManager对象
        //mWindowManager = this.getWindowManager();
        Log.i(TAG, "mWindowManager1--->" + this.getWindowManager());
        //mWindowManager = getWindow().getWindowManager();
        Log.i(TAG, "mWindowManager2--->" + getWindow().getWindowManager());

        //获取的是CompatModeWrapper对象
        mWindowManager = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
        Log.i(TAG, "mWindowManager3--->" + mWindowManager);
        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        wmParams.format = PixelFormat.RGBA_8888;
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        wmParams.x = 0;
        wmParams.y = 0;
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        LayoutInflater inflater = this.getLayoutInflater();//LayoutInflater.from(getApplication());

        mFloatLayout = (LinearLayout) inflater.inflate(R.layout.float_layout, null);
        wmParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        mWindowManager.addView(mFloatLayout, wmParams);
        //setContentView(R.layout.main);
        mFloatView = (Button)mFloatLayout.findViewById(R.id.float_id);

        Log.i(TAG, "mFloatView" + mFloatView);
        Log.i(TAG, "mFloatView--parent-->" + mFloatView.getParent());
        Log.i(TAG, "mFloatView--parent--parent-->" + mFloatView.getParent().getParent());
        //绑定触摸移动监听
        mFloatView.setOnTouchListener(new View.OnTouchListener()
        {

            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                // TODO Auto-generated method stub
                wmParams.x = (int)event.getRawX() - mFloatLayout.getWidth()/2;
                //25为状态栏高度
                wmParams.y = (int)event.getRawY() - mFloatLayout.getHeight()/2 - 40;
                mWindowManager.updateViewLayout(mFloatLayout, wmParams);
                return false;
            }
        });

        //绑定点击监听
        mFloatView.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MainActivity.this, LottieActivity.class);
                startActivity(intent);
            }
        });

    }

    private void close(){
        if(mFloatLayout != null)
        {
            mWindowManager.removeView(mFloatLayout);
            finish();
        }
    }


    private List<String> getData(){

//        List<String> data = new ArrayList<String>();
        data.add("设备信息获取");
        data.add("refresh + recycler");
        data.add("滑动冲突");
        data.add("事件分发测试");
        data.add("图形绘制测试");
        data.add("特效");
        data.add("测试");
        data.add("随便玩");
        data.add("你好");
        data.add("重力感应  小球");
        data.add("视频播放   SurfaceView");
        data.add("lottie   动画");
        data.add("gsy 视频播放器");
        data.add("ijkplayer  test");
        data.add(" 打开 悬浮窗  测试");
        data.add(" 关闭 悬浮窗  测试");
        data.add(" 磁性悬浮窗");
        return data;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = null;
        switch (position){
            case 0 :
                intent = new Intent(this, PhoneInfoActivity.class);
                break;
            case 1 :
                intent = new Intent(this, RecycleviewActivity.class);
                break;
            case 2 :
                intent = new Intent(this, SlidingConflictActivity2.class);
                break;
            case 3 :
                intent = new Intent(this, RefreshActivity.class);
                break;
            case 4 :
                intent = new Intent(this, DrawActivity.class);
                break;
            case 5 :
                intent = new Intent(this, EffectsActivity.class);
                break;
            case 6 :
                intent = new Intent(this, TouchTestActivity.class);
                break;
            case 7 :
                intent = new Intent(this, DiscoverActivity.class);
                break;
            case 8 :
                intent = new Intent(this, TestActivity.class);
                break;
            case 9 :
                intent = new Intent(this, AccelerometerBallActivity.class);
                break;
            case 10 :
                intent = new Intent(this, MyMediaPlayerActivity.class);
                break;
            case 11 :
                intent = new Intent(this, LottieActivity.class);
                break;
            case 12 :
                intent = new Intent(this, VideoPalyerActivity.class);
                break;
            case 13 :
                intent = new Intent(this, IjkPlayerActivity.class);
                break;
            case 14 :
                createFloatView();
                break;
            case 15 :
                close();
                break;
            case 16 :
                intent = new Intent(this, FloatActivity.class);
                break;
            default:
                ToastUtil.showToast(this, "没有设置这个item的点击事件: " + data.get(position));
                break;
        }
        if (intent != null){
            startActivity(intent);
        }
    }
}
