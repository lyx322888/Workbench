package com.zpz.common.base.binding;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingConversion;

import com.jakewharton.rxbinding2.view.RxView;
import com.zpz.common.base.ToolBarViewModel;
import com.zpz.common.utils.GlideUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;
public class CommonBindingAdapter {
    //图片加载
    @BindingAdapter("imageUrl")
    public static void showPic(ImageView imageView, String url) {
        GlideUtils.showSmallPic(imageView,url);
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

    //圆角
    @BindingAdapter({"radius"})
    public static void radius(View view,int radius){
        GradientDrawable drawable ;
        if (view.getBackground()==null){
            drawable = new GradientDrawable();
            drawable.setShape(GradientDrawable.RECTANGLE);
            drawable.setGradientType(GradientDrawable.SWEEP_GRADIENT);
        }else {
            try {
                drawable = (GradientDrawable) view.getBackground();
            } catch (Exception e) {
                drawable = new GradientDrawable();
                drawable.setShape(GradientDrawable.RECTANGLE);
                drawable.setGradientType(GradientDrawable.SWEEP_GRADIENT);
                if (view.getBackground() instanceof ColorDrawable) {
                    drawable.setColor(((ColorDrawable) view.getBackground()).getColor());
                }
            }
        }
        drawable.setCornerRadius(radius);
        view.setBackground(drawable);
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
