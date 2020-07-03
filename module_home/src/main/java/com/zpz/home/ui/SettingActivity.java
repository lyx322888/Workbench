package com.zpz.home.ui;
import android.view.View;

import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zpz.common.base.BaseActivity;
import com.zpz.common.base.DataBindingConfig;
import com.zpz.common.base.MyARouter;
import com.zpz.common.utils.AppManager;
import com.zpz.home.R;
import com.zpz.home.vm.SetingViewModel;
//设置
@Route(path = MyARouter.SetingActivity)
public class SettingActivity extends BaseActivity<SetingViewModel> {

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_setting)
                .addBindingParam(BR.vm,viewModel)
                .addBindingParam(BR.click,new ClickProxy());
    }
    @Override
    protected void init() {

    }

    @Override
    protected void initViewObservable() {
        viewModel.getLoginstate().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (!aBoolean){
                    AppManager.getAppManager().finishAllActivity();
                    ARouter.getInstance().build(MyARouter.LoginActivity).navigation();
                }
            }
        });
    }

    public class ClickProxy{
        //退出
        public void outLogin(View view){
            viewModel.requesOutLogin();
        }

    }
}