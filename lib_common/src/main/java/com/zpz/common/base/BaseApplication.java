package com.zpz.common.base;

import android.app.Application;
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
    }
}
