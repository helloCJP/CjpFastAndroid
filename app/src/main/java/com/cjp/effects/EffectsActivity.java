package com.cjp.effects;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cjp.activity.PhoneInfoActivity;
import com.cjp.draw.DrawActivity;
import com.cjp.effects.particle.explosion.ParticleExplosionActivity;
import com.cjp.recycleview.RecycleviewActivity;
import com.cjp.refresh.RefreshActivity;
import com.cjp.slidingconflict.SlidingConflictActivity2;
import com.cjp.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蔡金品 on 2017/7/4.
 * email : caijinpin@zhexinit.com
 */
public class EffectsActivity extends Activity implements AdapterView.OnItemClickListener{
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
        data.add("离子破碎效果");
//        data.add("refresh + recycler");
//        data.add("滑动冲突");
//        data.add("事件分发测试");
//        data.add("图形绘制测试");

        return data;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = null;
        switch (position){
            case 0 :
                intent = new Intent(this, ParticleExplosionActivity.class);
                break;
//            case 1 :
//                intent = new Intent(this, RecycleviewActivity.class);
//                break;
//            case 2 :
//                intent = new Intent(this, SlidingConflictActivity2.class);
//                break;
//            case 3 :
//                intent = new Intent(this, RefreshActivity.class);
//                break;
//            case 4 :
//                intent = new Intent(this, DrawActivity.class);
//                break;
            default:
                ToastUtil.showToast(this, "没有设置这个item的点击事件: " + data.get(position));
                break;
        }
        if (intent != null){
            startActivity(intent);
        }
    }
}
