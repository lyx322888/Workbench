package com.zpz.common.utils;

import android.text.TextUtils;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;


/**
 * Description : 图片加载工具类 使用glide框架封装
 */
public class GlideUtils {
    public static void showSmallPic(ImageView imageView, String url) {
        if (imageView == null || TextUtils.isEmpty(url)) {
            return;
        }

        if (Util.isOnMainThread()) {
            Glide.with(imageView.getContext()).load(url).into(imageView);
        }

    }

}
