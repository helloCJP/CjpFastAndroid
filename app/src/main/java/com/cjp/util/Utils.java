package com.cjp.util;

import android.content.res.Resources;

/**
 * Created by 蔡金品 on 2017/7/4.
 * email : caijinpin@zhexinit.com
 */
public class Utils {
    public static final String TAG = "Utils";
    /**
     * 密度
     */
    public static final float DENSITY = Resources.getSystem().getDisplayMetrics().density;

    public static int dp2px(int dp) {
        return Math.round(dp * DENSITY);
    }
}
