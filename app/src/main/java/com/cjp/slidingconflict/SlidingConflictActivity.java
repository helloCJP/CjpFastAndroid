package com.cjp.slidingconflict;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.cjp.util.LogUtil;

import java.util.ArrayList;

/**
 * Created by 蔡金品 on 2017/4/17.
 * email : caijinpin@zhexinit.com
 */

public class SlidingConflictActivity extends Activity {

    public static final String TAG = "SlidingConflictActivity";

    private HorizontalEx mainlayout;
    private ViewPager viewPager;
    private ArrayList<View> viewList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        RelativeLayout rl = new RelativeLayout(this);
//        rl.setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT, ViewGroup.MarginLayoutParams.MATCH_PARENT));
//        mainlayout = new HorizontalEx(this);
//        setContentView(mainlayout);

//        mainlayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        View view1 = new View(this);
        view1.setBackgroundColor(Color.BLACK);
        view1.setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT, ViewGroup.MarginLayoutParams.MATCH_PARENT));

        View view2 = new View(this);
        view2.setBackgroundColor(Color.BLUE);
        view2.setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT, ViewGroup.MarginLayoutParams.MATCH_PARENT));

        viewList.add(view1);
        viewList.add(view2);

        viewPager = new ViewPager(this);
        viewPager.setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT, ViewGroup.MarginLayoutParams.MATCH_PARENT));
        viewPager.setAdapter( new PagerAdapter() {
            @Override
            public int getCount() {
                return viewList.size();
            }

            //滑动切换的时候销毁当前的组件
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                ((ViewPager) container).removeView(viewList.get(position));
            }

            //每次滑动的时候生成的组件
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ((ViewPager) container).addView(viewList.get(position));
                return viewList.get(position);
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == (object);
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int arg0) {
                LogUtil.d(TAG, "--------changed:" + arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                LogUtil.d(TAG, "-------scrolled arg0:" + arg0);
                LogUtil.d(TAG, "-------scrolled arg1:" + arg1);
                LogUtil.d(TAG, "-------scrolled arg2:" + arg2);
            }

            @Override
            public void onPageSelected(int arg0) {
                LogUtil.d(TAG, "------selected:" + arg0);
            }
        });

        setContentView(viewPager);
//        mainlayout.addView(viewPager);
    }
}
