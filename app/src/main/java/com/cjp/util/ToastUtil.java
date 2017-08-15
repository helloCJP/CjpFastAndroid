package com.cjp.util;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

/**
 * Toast 优化，在弹出下一个Toast 之前 cancel 前一个Toast
 * Created by 蔡金品 on 2016-07-28.
 *
 * tag : 增加判断是否为主线程，不是主线程的情况下，判断是否为Activity。并做相应处理。2016-09-18 10:47
 */
public class ToastUtil {

    private static String TAG = "ToastUtils";

    private static Toast toast;

    public static void showToast(final Context context, final String toastInfo){
        if ( context == null || toastInfo == null || toastInfo.equals(""))
            return ;
        if (isMainLoop()){
            show(context.getApplicationContext(), toastInfo);
        } else if(context instanceof Activity){
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    show(context.getApplicationContext(), toastInfo);
                }
            });
        } else {
            LogUtil.e(TAG, "Does not run in the UI thread");
        }
    }

    private static void initToast(Context context, String toastInfo) {
        toast = Toast.makeText(context, toastInfo, Toast.LENGTH_SHORT);
    }

    private static void show(Context context, String toastInfo){
        if (toast != null && context != null) {
            toast.cancel();
        }
        initToast(context, toastInfo);
        toast.show();
    }

    private static boolean isMainLoop(){
        if (Looper.myLooper() == Looper.getMainLooper()){
            return true;
        }
        return false;
    }
}