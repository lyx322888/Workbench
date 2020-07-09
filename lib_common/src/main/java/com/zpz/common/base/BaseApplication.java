package com.zpz.common.base;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kingja.loadsir.core.LoadSir;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.BallPulseView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.zpz.common.BuildConfig;
import com.zpz.common.callback.loadsir.EmptyCallback;
import com.zpz.common.callback.loadsir.LoadingCallback;
import com.zpz.common.callback.loadsir.TimeoutCallback;
import com.zpz.common.utils.MediaLoader;

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
        initARouter();
        initAlbum();
        initLoadSir();
    }

    //状态页框架
    private void initLoadSir() {
        LoadSir.beginBuilder()
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                .commit();
    }

    //图片选择框架
    private void initAlbum() {
        Album.initialize(AlbumConfig.newBuilder(this)
                .setAlbumLoader(new MediaLoader()).build());
    }
    //设置刷新样式
    private void initTwinkingRefreshlayout(){
        TwinklingRefreshLayout.setDefaultHeader(SinaRefreshView.class.getName());
        TwinklingRefreshLayout.setDefaultFooter(BallPulseView.class.getName());
    }
    private void initARouter() {
        //是否进行ARouter调试(可以通过AppConfig.isDebug=true/false来判断日志的是否开启)
        if (BuildConfig.IS_DEBUG) {
            //下面两句话必须放到init前面,否则配置无效
            ARouter.openLog();  //打印ARouter日志
            ARouter.openDebug();  //开启ARouter的调试模式(如果在InstantRun模式下运行,必须开启调试模式,线上版本需要关闭，否则有安全风险),
        }
        //官方建议在Application里面进行初始化(使用该注解路径至少两级)
        ARouter.init(this);
    }

}
