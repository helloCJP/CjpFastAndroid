package com.cjp.refresh;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;


/**
 * Created by 蔡金品 on 2017/6/29.
 * email : caijinpin@zhexinit.com
 */
public abstract class HelloRefresh<T extends View> extends LinearLayout {
    public static final String TAG = "HelloRefresh";

    public static final int STATUS_LOADING = 1;
    public static final int STATUS_RELEASE_TO_REFRESH = 2;
    public static final int STATUS_PULL_TO_REFRESH = 3;
    public static final int STATUS_RELEASE_TO_LOAD_MORE = 7;
    public static final int STATUS_PULL_TO_LOAD_MORE = 6;


    public static final int STATUS_IDLE = 4;
    public static final int STATUS_LOAD_MORE =5;
    private static int SCROLL_DURATION =500;

    private RelativeLayout headLayout;
    private RelativeLayout footLayout;

    private T contentView;

    private boolean isFistTouch = true;

    protected int currentStatus = STATUS_IDLE;

    private int mScreenWidth;
    private int mScreenHeight;
    private int mLastXIntercepted;
    private int mLastYIntercepted;
    private int mLastX;
    private int mLastY;
//    protected int mInitScrollY = 0;
    private int mTouchSlop;

    private Scroller mScoller;
    private OnRefreshListener mOnRefreshListener;

    private Context context;


    /**
     * 下拉刷新偏移 表示当滑动距离小于该值时， 不进行刷新处理
     */
    private int refreshDy = 100;
    /**
     * 上拉加载偏移 表示当滑动距离小于该值时， 不进行加载更多
     */
    private int loadMoreDy = 100;
    private boolean needRefresh = true;
    private boolean needLoadMore = true;

    private boolean refresh = false;


    public HelloRefresh(Context context) {
        this(context, null);
    }

    public HelloRefresh(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HelloRefresh(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setOrientation(VERTICAL);
        mScoller = new Scroller(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        initView();
    }

    public OnRefreshListener getOnRefreshListener() {
        return mOnRefreshListener;
    }

    public void setOnRefreshListener(OnRefreshListener mOnRefreshListener) {
        this.mOnRefreshListener = mOnRefreshListener;
    }


    private void initView(){
        initHeadLayout();
        initFootLayout();
    }

    private int dp2px(int dp) {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }

    private void initHeadLayout() {
        headLayout = new RelativeLayout(context);
        headLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(200)));
        headLayout.setBackgroundColor(Color.RED);
        addView(headLayout);
    }

    private void initFootLayout() {
        footLayout = new RelativeLayout(context);
        footLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(100)));
        footLayout.setBackgroundColor(Color.BLUE);
        addView(footLayout);
    }

    public void setContentView(T view) {
        contentView = view;
        addView(contentView, 1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int finalHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            finalHeight += child.getMeasuredHeight();
        }

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            widthSize = getChildAt(0).getMeasuredWidth();
            setMeasuredDimension(widthSize, finalHeight);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = getChildAt(0).getMeasuredWidth();
            setMeasuredDimension(widthSize, height);
        } else {
            setMeasuredDimension(widthSize, finalHeight);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int topOffset = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.layout(getPaddingLeft(), getPaddingTop() + topOffset, r, getPaddingTop() + child.getMeasuredHeight() + topOffset);
            topOffset += child.getMeasuredHeight();
        }
//        mInitScrollY = headLayout.getMeasuredHeight();
//        scrollTo(0, mInitScrollY);
        scrollTo(0, headLayout.getMeasuredHeight());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastXIntercepted = x;
                mLastYIntercepted = y;
                break;
            case MotionEvent.ACTION_MOVE:
                final int deltaY = y - mLastYIntercepted;
                if (needRefresh && isTop() && deltaY > 0 && Math.abs(deltaY) > mTouchSlop) {
                    /*下拉*/
                    refresh = true;
                    intercepted = true;
                    Log.i(TAG,"cjp  babab");
                } else if (needLoadMore && isBottom() &&  deltaY < 0 && Math.abs(deltaY) > mTouchSlop ){
                    Log.i(TAG,"cjp");
                    refresh = false;
                    intercepted = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        mLastXIntercepted = x;
        mLastYIntercepted = y;
        return intercepted;
    }

    private void doRefresh() {
        if (currentStatus == STATUS_RELEASE_TO_REFRESH) {
            mScoller.startScroll(0, getScrollY(), 0, headLayout.getMeasuredHeight() - getScrollY(), SCROLL_DURATION);
            currentStatus = STATUS_IDLE;
        } else if (currentStatus == STATUS_PULL_TO_REFRESH) {
            mScoller.startScroll(0,getScrollY(),0,0-getScrollY(),SCROLL_DURATION);
            if (null != mOnRefreshListener) {
                currentStatus = STATUS_LOADING;
                mOnRefreshListener.refresh();
            }
        }
        invalidate();
    }

    private void doLoadMore() {
        Log.i(TAG, "doLoadMore: ");
        if (currentStatus == STATUS_RELEASE_TO_LOAD_MORE) {
            Log.i(TAG, "doLoadMore: 11111");
            mScoller.startScroll(0, getScrollY(), 0, headLayout.getMeasuredHeight() - getScrollY()  , SCROLL_DURATION);
            currentStatus = STATUS_IDLE;
        } else if (currentStatus == STATUS_PULL_TO_LOAD_MORE) {
            Log.i(TAG, "doLoadMore: 22222");
//            showFooter();
//            scrollTo(0,mInitScrollY + footLayout.getMeasuredHeight());
            mScoller.startScroll(0, getScrollY(), 0, headLayout.getMeasuredHeight()-getScrollY() + footLayout.getMeasuredHeight(), SCROLL_DURATION);
            if (null != mOnRefreshListener) {
                currentStatus = STATUS_LOADING;
                mOnRefreshListener.loadMore();
            }
        }
        invalidate();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScoller.isFinished()) {
                    mScoller.abortAnimation();
                }
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                if (isFistTouch) {
                    isFistTouch = false;
                    mLastX = x;
                    mLastY = y;
                }
                final int deltaY = y - mLastY;
                if (currentStatus != STATUS_LOADING) {
                    changeScrollY(deltaY);
                }
                break;
            case MotionEvent.ACTION_UP:
                isFistTouch = true;
                if (refresh) {
                    doRefresh();
                } else {
                    doLoadMore();
                }
                break;
        }

        mLastX = x;
        mLastY = y;
        return true;
    }

    private void changeScrollY(int deltaY) {
        int curY = getScrollY();
        if (refresh) {
            if (deltaY > 0) {
            /*下拉*/
//                if (curY - deltaY > getPaddingTop()) {
                    scrollBy(0, -deltaY);
//                }
            } else {
            /*上拉*/
                if (curY - deltaY <= headLayout.getMeasuredHeight()) {
                    scrollBy(0, -deltaY);
                }
            }

            if (curY > 0 && curY <= refreshDy) {
                currentStatus = STATUS_PULL_TO_REFRESH;
            } else if (curY > 0 && curY >= refreshDy) {
                currentStatus = STATUS_RELEASE_TO_REFRESH;
            }

        } else {
            if (deltaY > 0) {
            /*下拉*/
                if (curY - deltaY >= headLayout.getMeasuredHeight() + getPaddingTop()) {
                    scrollBy(0, -deltaY);
                }
            } else {
            /*上拉*/
//                if (curY - deltaY >= mInitScrollY && curY-deltaY <= mInitScrollY*2) {
                    scrollBy(0, -deltaY);
//                }
            }

            if (curY >headLayout.getMeasuredHeight() && curY <= headLayout.getMeasuredHeight() + loadMoreDy){
                currentStatus = STATUS_RELEASE_TO_LOAD_MORE;
            } else if (curY > headLayout.getMeasuredHeight() + loadMoreDy ) {
                currentStatus = STATUS_PULL_TO_LOAD_MORE ;
            }
        }
//        scrollBy(0, -deltaY);
        Log.i(TAG, "changeScrollY:     scrollY = " + getScrollY() + "   deltay = " + deltaY + "  paddingTop = " + getPaddingTop() + "   mInitScrollY = " + headLayout.getMeasuredHeight());
//        int slop = mInitScrollY / 2;

    }

    @Override
    public void computeScroll() {
//        Log.i(TAG,"computeScroll");
        if (mScoller.computeScrollOffset()) {
            scrollTo(mScoller.getCurrX(), mScoller.getCurrY());
            postInvalidate();
        }
    }

    /**
     * 加载完成调用这个方法
     */
    public void refreshComplete() {
        mScoller.startScroll(0, getScrollY(), 0, headLayout.getMeasuredHeight() - getScrollY(), SCROLL_DURATION);
        currentStatus = STATUS_IDLE;
        invalidate();
    }

    /**
     * 显示 Footer
     */
    public void showFooter() {
        if(currentStatus==STATUS_LOAD_MORE) return ;
        currentStatus = STATUS_LOAD_MORE ;
        mScoller.startScroll(0, getScrollY(), 0, footLayout.getMeasuredHeight()
                , SCROLL_DURATION);
        invalidate();

    }


    /**
     * loadMore完成之后调用
     */
    public void footerComplete() {
        mScoller.startScroll(0, getScrollY(), 0, headLayout.getMeasuredHeight() - getScrollY(), SCROLL_DURATION);
        invalidate();
        currentStatus = STATUS_IDLE;
    }


    public interface OnRefreshListener {
        void refresh();
        void loadMore();
    }

    abstract boolean isTop();

    abstract boolean isBottom();

}
