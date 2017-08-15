package com.cjp.recycleview;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cjp.R;

import java.util.ArrayList;

/**
 * Created by 蔡金品 on 2017/4/17.
 * email : caijinpin@zhexinit.com
 */

public class RecycleviewActivity extends Activity {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    private RecycleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_refresh_layout);
        recyclerView = (RecyclerView)findViewById(R.id.listview);
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        initRecycler();
    }

    private void initRecycler(){
        adapter = new RecycleAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        refreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉进度的主题颜色
        refreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);

        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // 开始刷新，设置当前为刷新状态
                //swipeRefreshLayout.setRefreshing(true);

                // 这里是主线程
                // 一些比较耗时的操作，比如联网获取数据，需要放到子线程去执行
                // TODO 获取数据
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 加载完数据设置为不刷新状态，将下拉进度收起来
                        data.clear();
                        getData();
                        refreshLayout.setRefreshing(false);
                    }
                }, 6000);

                // System.out.println(Thread.currentThread().getName());

                // 这个不能写在外边，不然会直接收起来
                //swipeRefreshLayout.setRefreshing(false);
            }
        });
        getData();
        adapter.setData(data);
    }


    private ArrayList<String> data = new ArrayList<>();
    private void getData(){
        int size = data.size();
        for (int i = 0; i < 5; i++){
            data.add("这是第" + (size + i) + "个数据");
        }
    }
}
