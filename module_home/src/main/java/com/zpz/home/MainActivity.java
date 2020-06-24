package com.zpz.home;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.yanzhenjie.sofia.Sofia;
import com.zpz.common.base.BaseActivity;
import com.zpz.common.base.DataBindingConfig;
import com.zpz.common.base.MyARouter;
import com.zpz.home.databinding.HomeBinding;
import com.zpz.home.vm.MainViewModel;

@Route(path = MyARouter.MainActivity)
public class MainActivity extends BaseActivity<MainViewModel> {

    @Override
    protected void init() {

    }

    @Override
    protected void initViewObservable() {

    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_home)
                .addBindingParam(BR.vm,viewModel)
                .addBindingParam(BR.click, new ClickProxy());
    }

    @Override
    protected void setStatubarColor() {
        if (((HomeBinding)getBinding())!=null){
            Sofia.with(this).invasionStatusBar().fitsStatusBarView(((HomeBinding)getBinding()).llAddress).statusBarBackgroundAlpha(0).statusBarDarkFont();
        }
    }

    //点击事件
    public  class ClickProxy{
        //企业初审
        public void jumpQycs(View view){
            ARouter.getInstance().build(MyARouter.FirstTrialActivity).navigation();

        }

        //设置
        public void jumpSetting(){
            ARouter.getInstance().build(MyARouter.SetingActivity).navigation();
        }

        //建档
        public void bookbuilding(){
            ARouter.getInstance().build(MyARouter.CreateProcedureActivity).navigation();
        }
    }
}
