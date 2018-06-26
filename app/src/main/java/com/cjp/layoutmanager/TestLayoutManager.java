package com.cjp.layoutmanager;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 蔡金品 on 2018/3/9.
 * email : caijinpin@zhexinit.com
 */

public class TestLayoutManager extends LinearLayoutManager{

    int totalHeight =0;

    public TestLayoutManager(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        // 先把所有的View先从RecyclerView中detach掉，然后标记为"Scrap"状态，表示这些View处于可被重用状态(非显示中)。
        // 实际就是把View放到了Recycler中的一个集合中。
        detachAndScrapAttachedViews(recycler);
        calculateChildrenSite(recycler);
    }

    private void calculateChildrenSite(RecyclerView.Recycler recycler) {
        totalHeight = 0;
        for (int i = 0; i < getItemCount(); i++) {
            // 遍历Recycler中保存的View取出来
            View view = recycler.getViewForPosition(i);
            addView(view); // 因为刚刚进行了detach操作，所以现在可以重新添加
            measureChildWithMargins(view, 0, 0); // 通知测量view的margin值
            int width = getDecoratedMeasuredWidth(view); // 计算view实际大小，包括了ItemDecorator中设置的偏移量。
            int height = getDecoratedMeasuredHeight(view);

            Rect mTmpRect = new Rect();
            //调用这个方法能够调整ItemView的大小，以除去ItemDecorator。
            calculateItemDecorationsForChild(view, mTmpRect);

            // 调用这句我们指定了该View的显示区域，并将View显示上去，此时所有区域都用于显示View，
            //包括ItemDecorator设置的距离。
            layoutDecorated(view, 0, totalHeight, width, totalHeight + height);
            totalHeight += height;
        }
    }
}
