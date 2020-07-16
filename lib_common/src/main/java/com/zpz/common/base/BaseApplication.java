package com.zpz.common.base;

import android.app.Application;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.BallPulseView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;

//基础app
public class BaseApplication extends Application {
    private static long appInitTime = System.currentTimeMillis();
    private static BaseApplication instance;
    //单例模式中获取唯一的MyApplication实例
    public static BaseApplication getInstance() {
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init();
        initTwinkingRefreshlayout();
    }

    private void init() {
    }

    //设置刷新样式
    private void initTwinkingRefreshlayout(){
        TwinklingRefreshLayout.setDefaultHeader(SinaRefreshView.class.getName());
        TwinklingRefreshLayout.setDefaultFooter(BallPulseView.class.getName());
    }

}
