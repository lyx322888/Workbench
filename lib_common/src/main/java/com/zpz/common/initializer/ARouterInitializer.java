package com.zpz.common.initializer;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zpz.common.BuildConfig;

import java.util.Collections;
import java.util.List;

//图片选择框架启动器
public class ARouterInitializer implements Initializer<ARouter> {
    @NonNull
    @Override
    public ARouter create(@NonNull Context context) {
        if (BuildConfig.IS_DEBUG) {
            //下面两句话必须放到init前面,否则配置无效
            ARouter.openLog();  //打印ARouter日志
            ARouter.openDebug();  //开启ARouter的调试模式(如果在InstantRun模式下运行,必须开启调试模式,线上版本需要关闭，否则有安全风险),
        }
        ARouter.init((Application) context);
        return ARouter.getInstance();
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }
}
