package com.zpz.common.initializer;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.kingja.loadsir.core.LoadSir;
import com.zpz.common.callback.loadsir.EmptyCallback;
import com.zpz.common.callback.loadsir.LoadingCallback;
import com.zpz.common.callback.loadsir.TimeoutCallback;

import java.util.Collections;
import java.util.List;

//图片选择框架启动器
public class LoadSirInitializer implements Initializer<LoadSir> {
    @NonNull
    @Override
    public LoadSir create(@NonNull Context context) {
        LoadSir.beginBuilder()
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                .commit();
        return LoadSir.getDefault();
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }
}
