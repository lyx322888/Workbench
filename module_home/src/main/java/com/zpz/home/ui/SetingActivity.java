package com.zpz.home.ui;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zpz.common.base.BaseActivity;
import com.zpz.common.base.DataBindingConfig;
import com.zpz.common.base.MyARouter;
import com.zpz.home.R;
@Route(path = MyARouter.SetingActivity)
public class SetingActivity extends BaseActivity {


    @Override
    protected void initViewModel() {

    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_seting);
    }
}