package com.cjp.slidingconflict;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.cjp.recycleview.DividerItemDecoration;
import com.cjp.recycleview.RecycleAdapter;
import com.cjp.util.LogUtil;

import java.util.ArrayList;

/**
 * Created by 蔡金品 on 2017/4/18.
 * email : caijinpin@zhexinit.com
 */
public class SlidingConflictActivity2 extends Activity{
    public static final String TAG = "SlidingConflictActivity2";

    private RefreshLayoutBase<RecyclerView> mainLayout;
    public RecycleAdapter adapter;
    private RecyclerView recyclerView;

    private boolean isBottom,isTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainLayout = new RefreshLayoutBase<RecyclerView>(this) {
            @Override
            boolean isTop() {
                return isTop;
            }

            @Override
            boolean isBottom() {
                return isBottom;
            }
        };

        recyclerView = new RecyclerView(this);
        recyclerView.setLayoutParams(new ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT ));
        adapter = new RecycleAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItemCount = layoutManager.getItemCount();
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

                if ( totalItemCount < lastVisibleItem + 2) {
                    isBottom = true;
                    mainLayout.showFooter();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // 加载完数据设置为不刷新状态，将下拉进度收起来
                            mainLayout.footerComplete();
                        }
                    }, 6000);
                } else {
                    isBottom = false;
                }
                if ( firstVisibleItem == 0 ){
                    isTop = true;
                } else {
                    isTop = false;
                }
                LogUtil.d(TAG, "isTop = " + isTop + "   isBottom = " + isBottom);
            }
        });
        mainLayout.setContentView(recyclerView);
        mainLayout.setOnRefreshListener(new RefreshLayoutBase.OnRefreshListener() {
            @Override
            public void refresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 加载完数据设置为不刷新状态，将下拉进度收起来
                        mainLayout.refreshComplete();
                    }
                }, 6000);

            }
        });

        getData();
        adapter.setData(data);
        setContentView(mainLayout);
    }


    private ArrayList<String> data = new ArrayList<>();
    private void getData(){
        int size = data.size();
        for (int i = 0; i < 20; i++){
            data.add("这是第" + (size + i) + "个数据");
        }
    }
}
