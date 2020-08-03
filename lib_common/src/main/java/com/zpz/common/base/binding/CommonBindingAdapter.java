package com.zpz.common.base.binding;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingConversion;

import com.google.zxing.WriterException;
import com.jakewharton.rxbinding2.view.RxView;
import com.zpz.common.base.ToolBarViewModel;
import com.zpz.common.utils.EncodingHandler;
import com.zpz.common.utils.GlideUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;
public class CommonBindingAdapter {
    //图片加载
    @BindingAdapter("imageUrl")
    public static void showPic(ImageView imageView, String url) {
        GlideUtils.showSmallPic(imageView,url);
    }

    //二维码
    @BindingAdapter("QRCodeUrl")
    public static void QRCodeUrl(ImageView imageView, String url) {
        try {
            if (!TextUtils.isEmpty(url)){
                imageView.setImageBitmap(EncodingHandler.createQRCode(url,300));
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

     //按钮防抖
    @SuppressLint("CheckResult")
    @BindingAdapter("onAntiShakeClick")
    public static void onAntiShakeClick(final View view, final View.OnClickListener listener) {
        RxView.clicks(view)
                //两秒钟之内只取一个点击事件，防抖操作
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        listener.onClick(view);
                    }
                });
    }


    //color转换Drawable
    @BindingConversion
    public static ColorDrawable convertColorToDrawable(int color) {
        return new ColorDrawable(color);
    }


    //返回键
    @SuppressLint("CheckResult")
    @BindingAdapter("onBackClick")
    public static void onBackClick(final View view, final ToolBarViewModel toolBarViewModel) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toolBarViewModel!=null){
                    toolBarViewModel.getOnBackPressedEvent().postValue(true);
                }
            }
        });
    }

}
