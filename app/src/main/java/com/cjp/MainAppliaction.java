package com.cjp;

import android.app.Application;
import android.content.Context;

import com.tencent.android.tpush.XGPushConfig;

/**
 * Created by 蔡金品 on 2017/4/11.
 * email : caijinpin@zhexinit.com
 */

public class MainAppliaction extends Application {

    private static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        XGPushConfig.enableDebug(getMainContext(), BuildConfig.DEBUG);
    }

    public static Application getInstance(){
        return instance;
    }
    public static Context getMainContext() {
        if (instance != null) return instance.getApplicationContext();
        return null;
    }
}
