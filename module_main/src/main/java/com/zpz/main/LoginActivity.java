package com.zpz.main;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.yanzhenjie.sofia.Sofia;
import com.zpz.common.base.AppConfig;
import com.zpz.common.base.BaseActivity;
import com.zpz.common.base.DataBindingConfig;
import com.zpz.common.base.MyARouter;
import com.zpz.common.bean.LoginBean;
import com.zpz.common.utils.SPUtils;
import com.zpz.common.utils.ToastUitl;
import com.zpz.common.view.dalog.LoadingDialog;
import com.zpz.main.vm.LoginViewModel;

//登录
@Route(path = MyARouter.LoginActivity)
public class LoginActivity extends BaseActivity  {
    private LoginViewModel loginViewModel;

    @Override
    protected void initViewModel() {
        loginViewModel = getActivityViewModel(LoginViewModel.class);
        loginViewModel.name.set(SPUtils.getSharedStringData(mContext, AppConfig.LOGIN_MOBILE));
        loginViewModel.getLoginBeanMutableLiveData().observe(this, new Observer<LoginBean>() {
            @Override
            public void onChanged(LoginBean loginBean) {
                SPUtils.setSharedBooleanData(mContext, AppConfig.LOGIN_STATE, true);
                SPUtils.setSharedStringData(mContext, AppConfig.LOGIN_TOKEN, loginBean.getData().getLogin_token());
                SPUtils.setSharedStringData(mContext, AppConfig.LOGIN_MOBILE, loginViewModel.name.get());
                finish();
                ARouter.getInstance().build(MyARouter.MainActivity).navigation();
            }
        });

    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_login)
                .addBindingParam(BR.vm,loginViewModel)
                .addBindingParam(BR.click,new ClickProxy());
    }

    @Override
    protected void setStatubarColor( ) {
        Sofia.with(this).invasionStatusBar().statusBarBackgroundAlpha(0).statusBarDarkFont();

    }

    public class ClickProxy{
        //登录
        public void doLogin(View view){
            if (TextUtils.isEmpty(loginViewModel.name.get())) {
                ToastUitl.showShort("请输入账号");
                return;
            }
            if (TextUtils.isEmpty(loginViewModel.password.get()) ) {
                ToastUitl.showShort("请输入账号密码");
                return;
            }
            LoadingDialog.showDialogForLoading(mActivity,"登录中...",false);
            loginViewModel.requesLoginBean();
        }
    }

}
