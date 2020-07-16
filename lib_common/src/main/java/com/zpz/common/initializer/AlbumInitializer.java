package com.zpz.common.initializer;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.zpz.common.utils.MediaLoader;

import java.util.Collections;
import java.util.List;
//图片选择框架启动器
public class AlbumInitializer implements Initializer<Album> {
    @NonNull
    @Override
    public Album create(@NonNull Context context) {
        Album.initialize(AlbumConfig.newBuilder(context)
                .setAlbumLoader(new MediaLoader()).build());
        return null;
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }
}
