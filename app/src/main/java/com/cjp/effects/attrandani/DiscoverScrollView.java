package com.cjp.effects.attrandani;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by 蔡金品 on 2017/7/14.
 * email : caijinpin@zhexinit.com
 */
public class DiscoverScrollView extends ScrollView{
    public static final String TAG = "DiscoverScrollView";

    public DiscoverScrollView(Context context) {
        this(context, null);
    }

    public DiscoverScrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DiscoverScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LinearLayout layout = (LinearLayout) getChildAt(0);
        for (int i = 0; i<layout.getChildCount(); i++){
            if (layout.getChildAt(i) instanceof DiscoverFrameLayout){
                DiscoverFrameLayout fr = (DiscoverFrameLayout) layout.getChildAt(i);
                Rect rect = new Rect();
                if(fr.getLocalVisibleRect(rect)) {

                    int h = getHeight();
                    Log.i("cjp", "i = " + i + "  rect.top  =" + rect.top + "   rect.bottom = " + rect.bottom + "   h=" + h + "    hei = " + fr.getHeight());

//                float r = test(rect.bottom-rect.top/fr.getHeight());
                    float r = rect.bottom - rect.top / fr.getHeight();
//                Log.i("cjp","r = " + r);
//                if (r == 0f || r == 1f){
//                    fr.endAni();
//                } else {
                    fr.startAni(r);
//                }

                }
            }
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    private float test(float f){
        return Math.min(1f, Math.max(f, 0f));
    }
}
