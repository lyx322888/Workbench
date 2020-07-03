package com.zpz.home.fragment;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zpz.common.base.BaseFragment;
import com.zpz.common.base.DataBindingConfig;
import com.zpz.common.base.MyARouter;
import com.zpz.home.BR;
import com.zpz.home.R;
import com.zpz.home.vm.HomeViewModel;

@Route(path = MyARouter.HomeFragment)
public class HomeFragment extends BaseFragment<HomeViewModel> {

    @Override
    protected void init() {

    }

    @Override
    protected void initViewObservable() {

    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_home)
                .addBindingParam(BR.click, new ClickProxy())
                .addBindingParam(BR.vm,viewModel);
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
            ARouter.getInstance().build(MyARouter.FileListActivity).navigation();
        }
    }
}