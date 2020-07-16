package com.zpz.home.ui;

import androidx.databinding.library.baseAdapters.BR;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zpz.common.base.BaseActivity;
import com.zpz.common.base.BaseViewModel;
import com.zpz.common.base.DataBindingConfig;
import com.zpz.common.base.MyARouter;
import com.zpz.home.R;
//设置
@Route(path = MyARouter.SetingActivity)
public class SettingActivity extends BaseActivity<BaseViewModel> {

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_setting)
                .addBindingParam(BR.vm,viewModel);
    }
    @Override
    protected void init() {
        setTitle("设置");
    }

    @Override
    protected void initViewObservable() {

    }

    @Override
    protected void onLoadData() {

    }

}