package com.cjp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cjp.activity.PhoneInfoActivity;
import com.cjp.draw.DrawActivity;
import com.cjp.effects.EffectsActivity;
import com.cjp.effects.attrandani.DiscoverActivity;
import com.cjp.effects.ball.AccelerometerBallActivity;
import com.cjp.lottie.LottieActivity;
import com.cjp.recycleview.RecycleviewActivity;
import com.cjp.refresh.RefreshActivity;
import com.cjp.slidingconflict.SlidingConflictActivity2;
import com.cjp.test.TestActivity;
import com.cjp.test.TouchTestActivity;
import com.cjp.util.ToastUtil;
import com.cjp.video.MyMediaPlayerActivity;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener{
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
            default:
                ToastUtil.showToast(this, "没有设置这个item的点击事件: " + data.get(position));
                break;
        }
        if (intent != null){
            startActivity(intent);
        }
    }
}
