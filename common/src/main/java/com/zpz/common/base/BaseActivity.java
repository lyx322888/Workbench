package com.zpz.common.base;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.os.Bundle;

public abstract class BaseActivity<V extends ViewDataBinding> extends AppCompatActivity {
    protected V bingding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bingding = DataBindingUtil.setContentView(this,getLayoutId());
        setContentView(bingding.getRoot());
        this.initView();
        this.fillData();
    }

    //获取布局文件
    public abstract int getLayoutId();
    //初始化view
    public abstract void initView();
    //加载网络数据
    public abstract void fillData();
}
