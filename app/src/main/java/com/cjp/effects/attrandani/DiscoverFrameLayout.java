package com.cjp.effects.attrandani;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

/**
 * Created by 蔡金品 on 2017/7/14.
 * email : caijinpin@zhexinit.com
 */
public class DiscoverFrameLayout extends FrameLayout{
    public static final String TAG = "DiscoverFrameLayout";

    private static final int LEFT = 0x01;
    private static final int RIGHT = 0x02;
    private static final int TOP = 0x04;
    private static final int BOTTOM = 0X08;

    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();


    private boolean discoverScaleX = false;
    private boolean discoverScaleY = false;
    private boolean discoverAlpha = false;
    private int discoverBgColorFrom = -1;
    private int discoverBgColorTo = -1;
    private int discoverTranslate = -1;

    public boolean isDiscoverScaleX() {
        return discoverScaleX;
    }

    public void setDiscoverScaleX(boolean discoverScaleX) {
        this.discoverScaleX = discoverScaleX;
    }

    public boolean isDiscoverScaleY() {
        return discoverScaleY;
    }

    public void setDiscoverScaleY(boolean discoverScaleY) {
        this.discoverScaleY = discoverScaleY;
    }

    public boolean isDiscoverAlpha() {
        return discoverAlpha;
    }

    public void setDiscoverAlpha(boolean discoverAlpha) {
        this.discoverAlpha = discoverAlpha;
    }

    public int getDiscoverBgColorFrom() {
        return discoverBgColorFrom;
    }

    public void setDiscoverBgColorFrom(int discoverBgColorFrom) {
        this.discoverBgColorFrom = discoverBgColorFrom;
    }

    public int getDiscoverBgColorTo() {
        return discoverBgColorTo;
    }

    public void setDiscoverBgColorTo(int discoverBgColorTo) {
        this.discoverBgColorTo = discoverBgColorTo;
    }

    public int getDiscoverTranslate() {
        return discoverTranslate;
    }

    public void setDiscoverTranslate(int discoverTranslate) {
        this.discoverTranslate = discoverTranslate;
    }



    public DiscoverFrameLayout(Context context) {
        this(context, null);
    }

    public DiscoverFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DiscoverFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private int width = 0;
    private int height = 0;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;
        super.onSizeChanged(w, h, oldw, oldh);
    }


    public void startAni(float s){
        Log.i("cjp","startAni = " + s);
        if (isDiscoverScaleX()) {
            setScaleX(s);
        }
        if (isDiscoverScaleY()) {
            setScaleY(s);
        }
        if (isDiscoverAlpha()) {
            setAlpha(s);
        }
        if (getDiscoverBgColorFrom() != -1 && getDiscoverBgColorTo() != -1){
            setBackgroundColor((int)argbEvaluator.evaluate(s, getDiscoverBgColorFrom(), getDiscoverBgColorTo()));
        }

        if (judgeOrientation(LEFT)){
            setTranslationX(width * (1 - s));
        }
        if (judgeOrientation(RIGHT)){
            setTranslationX(-width * (1 - s));
        }
        if (judgeOrientation(TOP)){
            setTranslationY(-height * (1 - s));
        }
        if (judgeOrientation(BOTTOM)){
            setTranslationY(height * (1 - s));
        }

    }

    public void endAni(){
        if (isDiscoverScaleX()) {
            setScaleX(0);
        }
        if (isDiscoverScaleY()) {
            setScaleY(0);
        }
        if (isDiscoverAlpha()) {
            setAlpha(0);
        }
        if (getDiscoverBgColorFrom() != -1 && getDiscoverBgColorTo() != -1){
            setBackgroundColor( getDiscoverBgColorTo());
        }

        if (judgeOrientation(LEFT)){
            setTranslationX(0);
        }
        if (judgeOrientation(RIGHT)){
            setTranslationX(0);
        }
        if (judgeOrientation(TOP)){
            setTranslationY(0);
        }
        if (judgeOrientation(BOTTOM)){
            setTranslationY(0);
        }
    }

    private boolean judgeOrientation(int orientation) {
        return (getDiscoverTranslate() & orientation) == orientation;
    }


}
