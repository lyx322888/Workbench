package com.zpz.home.ui;


import androidx.appcompat.app.ActionBar;
import androidx.databinding.library.baseAdapters.BR;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zpz.common.base.BaseActivity;
import com.zpz.common.base.DataBindingConfig;
import com.zpz.common.base.MyARouter;
import com.zpz.home.R;
import com.zpz.home.vm.SetingModel;

@Route(path = MyARouter.SetingActivity)
public class SettingActivity extends BaseActivity<SetingModel> {


    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_setting)
                .addBindingParam(BR.vm,viewModel)
                .addBindingParam(BR.click,new ClickProxy());
    }
    @Override
    protected void initData() {

    }
    public class ClickProxy{

    }
}