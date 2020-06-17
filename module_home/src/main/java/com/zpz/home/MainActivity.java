package com.zpz.home;
import android.view.View;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.yanzhenjie.sofia.Sofia;
import com.zpz.common.base.BaseActivity;
import com.zpz.common.base.DataBindingConfig;
import com.zpz.common.base.MyARouter;
import com.zpz.common.bean.UserInfoBean;
import com.zpz.common.utils.InfoUtils;
import com.zpz.common.utils.ToastUitl;
import com.zpz.common.vm.UserViewModel;
import com.zpz.home.databinding.HomeBinding;
import com.zpz.home.vm.MainViewModel;

@Route(path = MyARouter.MainActivity)
public class MainActivity extends BaseActivity {
    private MainViewModel mainViewModel;

    @Override
    protected void initViewModel() {
        mainViewModel = getActivityViewModel(MainViewModel.class);
        UserViewModel userViewModel = getActivityViewModel(UserViewModel.class);
        userViewModel.getUserInfo().observe(this, new Observer<UserInfoBean>() {
            @Override
            public void onChanged(UserInfoBean userInfoBean) {
                mainViewModel.mutableLiveData.setValue(userInfoBean);
            }
        });
        userViewModel.requesUserInfo(InfoUtils.getToken());
        mainViewModel.requesIndexNotice();
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_home)
                .addBindingParam(BR.vm,mainViewModel)
                .addBindingParam(BR.click,new ClickProxy());
    }

    @Override
    protected void setStatubarColor() {
        if (((HomeBinding)getBinding())!=null){
            Sofia.with(this).invasionStatusBar().fitsStatusBarView(((HomeBinding)getBinding()).llAddress).statusBarBackgroundAlpha(0).statusBarDarkFont();
        }
    }

    //点击事件
    public class ClickProxy{
        //企业初审
        public void jumpQycs(View view){
            ToastUitl.showShort("vd");
        }
        //设置
        public void jumpSetting(){
            ARouter.getInstance().build(MyARouter.SetingActivity).navigation();
        }
    }
}
