package com.zpz.common.initializer;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.zpz.common.BuildConfig;
import com.zpz.common.base.AppConfig;

import java.util.Collections;
import java.util.List;

//图片选择框架启动器
public class UmengInitializer implements Initializer<UMConfigure> {
    @NonNull
    @Override
    public UMConfigure create(@NonNull Context context) {
        UMConfigure.setLogEnabled(BuildConfig.IS_DEBUG);
        UMConfigure.init(context, AppConfig.ThirdParty.UMENGKEY, "Umeng", UMConfigure.DEVICE_TYPE_PHONE, null);
        PlatformConfig.setWeixin(AppConfig.WX_APP_ID, AppConfig.WX_APP_SECRET);
        return null;
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }
}
