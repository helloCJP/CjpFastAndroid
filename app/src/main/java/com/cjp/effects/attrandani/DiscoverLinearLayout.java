package com.cjp.effects.attrandani;

import android.content.Context;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cjp.R;

/**
 * Created by 蔡金品 on 2017/7/14.
 * email : caijinpin@zhexinit.com
 */
public class DiscoverLinearLayout extends LinearLayout{
    public static final String TAG = "DiscoverLinearLayout";

    public DiscoverLinearLayout(Context context) {
        this(context, null);
    }

    public DiscoverLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DiscoverLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
    }


    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        DiscoverLayoutParams p = (DiscoverLayoutParams) params;
        if ( hasAni( p ) ){
            DiscoverFrameLayout f = new DiscoverFrameLayout(getContext());
            f.setDiscoverAlpha(p.alpha);
            f.setDiscoverScaleX(p.scaleX);
            f.setDiscoverScaleY(p.scaleY);
            f.setDiscoverBgColorFrom(p.bgColorFrom);
            f.setDiscoverBgColorTo(p.bgColorTo);
            f.setDiscoverTranslate(p.translate);
            f.addView(child, params);
            super.addView(f, index, params);
        } else {
            super.addView(child, index,params);
        }
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new DiscoverLayoutParams(getContext(), attrs);
    }

    private boolean hasAni(DiscoverLayoutParams params){
        return params.scaleY ||
                params.scaleX ||
                params.alpha ||
                (params.translate != -1) ||
                (params.bgColorTo != -1 && params.bgColorFrom != -1);
    }


    private static class DiscoverLayoutParams extends LinearLayout.LayoutParams{

        public boolean scaleX = false;
        public boolean scaleY = false;
        public boolean alpha = false;
        public int bgColorFrom = -1;
        public int bgColorTo = -1;
        public int translate = -1;

        public DiscoverLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.DiscoverLinearLayout);
            scaleX = a.getBoolean(R.styleable.DiscoverLinearLayout_discover_scaleX , false);
            scaleY = a.getBoolean(R.styleable.DiscoverLinearLayout_discover_scaleY , false);
            alpha = a.getBoolean(R.styleable.DiscoverLinearLayout_discover_alpha, false);
            bgColorFrom = a.getInt(R.styleable.DiscoverLinearLayout_discover_bgColorFrom, -1);
            bgColorTo = a.getInt(R.styleable.DiscoverLinearLayout_discover_bgColorTo , -1);
            translate = a.getInt(R.styleable.DiscoverLinearLayout_discover_translate , -1);
        }
    }
}
