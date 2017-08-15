package com.cjp.zhihu;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by 蔡金品 on 2017/4/19.
 * email : caijinpin@zhexinit.com
 */
public class MyScrollView extends ScrollView {
    public static final String TAG = "MyScrollView";

    private BottomListener bottomListener;

    private OnScrollListener scrollListener;

    public MyScrollView(Context context) {
        this(context, null);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (getScrollY() + getHeight() >= computeVerticalScrollRange()) {

            if (null != bottomListener) {
                bottomListener.onBottom();
            }
        }

        if (null != scrollListener) {
            scrollListener.onScrollChanged(l, t, oldl, oldt);
        }
    }

    public void setBottomListener(BottomListener bottomListener) {
        this.bottomListener = bottomListener;
    }

    public void setScrollListener(OnScrollListener scrollListener) {

        this.scrollListener = scrollListener;

    }


    public interface OnScrollListener {

        public void onScrollChanged(int l, int t, int oldl, int oldt);

    }


    public interface BottomListener {

        public void onBottom();

    }


}


