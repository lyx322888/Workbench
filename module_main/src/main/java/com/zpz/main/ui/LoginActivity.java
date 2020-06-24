package com.zpz.main.ui;

import android.text.TextUtils;
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
import com.zpz.common.utils.UserUtils;
import com.zpz.common.view.dalog.LoadingDialog;
import com.zpz.main.BR;
import com.zpz.main.R;
import com.zpz.main.vm.LoginViewModel;

//登录
@Route(path = MyARouter.LoginActivity)
public class LoginActivity extends BaseActivity<LoginViewModel>  {

    @Override
    protected void init() {

    }

    @Override
    protected void initViewObservable() {
        viewModel.getLoginBeanMutableLiveData().observe(this, new Observer<LoginBean>() {
            @Override
            public void onChanged(LoginBean loginBean) {
                UserUtils.saveLoginState(true);
                UserUtils.saveToken(loginBean.getData().getLogin_token());
                UserUtils.saveMobile(viewModel.name.get());
                finish();
                ARouter.getInstance().build(MyARouter.MainActivity).navigation();
            }
        });
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_login)
                .addBindingParam(BR.vm,viewModel)
                .addBindingParam(BR.click,new ClickProxy());
    }

    @Override
    protected void setStatubarColor( ) {
        Sofia.with(this).invasionStatusBar().statusBarBackgroundAlpha(0).statusBarDarkFont();

    }

    public class ClickProxy{
        //登录
        public void doLogin(View view){
            if (TextUtils.isEmpty(viewModel.name.get())) {
                ToastUitl.showShort("请输入账号");
                return;
            }
            if (TextUtils.isEmpty(viewModel.password.get()) ) {
                ToastUitl.showShort("请输入账号密码");
                return;
            }
            LoadingDialog.showDialogForLoading(mActivity,"登录中...",false);
            viewModel.requesLoginBean();
        }
    }

}
