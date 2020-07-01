package com.zpz.common.utils;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zpz.common.R;
import com.zpz.common.base.AppConfig;


/**
 * Description : 图片加载工具类 使用glide框架封装
 */
public class GlideUtils {
    public static void showSmallPic(ImageView imageView, String url) {
        if (imageView == null) {
            return;
        }
        if (TextUtils.isEmpty(url)) {
            Glide.with(imageView.getContext()).load(R.mipmap.loading_logo).into(imageView);
            return;
        }
        if (url.startsWith(AppConfig.IMAGES)) {
            url = url + "?imageView2/0/w/500/h/500";
        }

        RequestOptions options = new RequestOptions().
        error(R.mipmap.loading_logo);
        Glide.with(imageView.getContext()).load(url).apply(options).into(imageView);

    }

}
