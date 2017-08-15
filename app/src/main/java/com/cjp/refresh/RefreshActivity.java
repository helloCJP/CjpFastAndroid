package com.cjp.refresh;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by 蔡金品 on 2017/6/30.
 * email : caijinpin@zhexinit.com
 */
public class RefreshActivity extends Activity{
    public static final String TAG = "RefreshActivity";

    private HelloRefresh<WebView> mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final WebView  webView =  new WebView(this);
        webView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        mainLayout = new HelloRefresh<WebView>(this) {
            @Override
            boolean isTop() {
                return webView.getScrollY() == 0;
            }

            @Override
            boolean isBottom() {
                return webView.getContentHeight()*webView.getScale() -( webView.getHeight()+ webView.getScrollY())==0 ;
            }
        };
        mainLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(mainLayout);

        mainLayout.setContentView(webView);
        webView.loadUrl("http://xbly.xingbook.com/activity/public-number/index.html");

        mainLayout.setOnRefreshListener(new HelloRefresh.OnRefreshListener() {
            @Override
            public void refresh() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mainLayout.refreshComplete();
                    }
                },2000);
            }

            @Override
            public void loadMore() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mainLayout.footerComplete();
                    }
                },2000);
            }
        });
    }
}
