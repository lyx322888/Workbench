package com.zpz.home.fragment;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zpz.common.base.AppConfig;
import com.zpz.common.base.BaseFragment;
import com.zpz.common.base.DataBindingConfig;
import com.zpz.common.base.MyARouter;
import com.zpz.home.BR;
import com.zpz.home.R;
import com.zpz.home.vm.HomeViewModel;
//首页
@Route(path = MyARouter.HomeFragment)
public class HomeFragment extends BaseFragment<HomeViewModel> {

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_home)
                .addBindingParam(BR.click, new ClickProxy())
                .addBindingParam(BR.vm,viewModel);
    }
    @Override
    protected void init() {

    }

    @Override
    protected void initViewObservable() {

    }

    @Override
    protected void onLoadData() {
        viewModel.requesUserInfo();
        viewModel.requesIndexNotice();
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

        //到期企业
        public void expireCompany(View view){
            ARouter.getInstance().build(MyARouter.ExpireCompanyActivity).withString("title","到期企业")
                    .withString("status","3").navigation();
        }

        //企业年审
        public void annualCompany(View view){
            ARouter.getInstance().build(MyARouter.ExpireCompanyActivity).withString("title","企业年审")
                    .withString("status","2").navigation();
        }

        //分红明细
        public void detailAccount(View view){
            ARouter.getInstance().build(MyARouter.DetailAccountActivity).navigation();
        }

        //资料下载
        public void learningMaterials(View view){
            ARouter.getInstance().build(MyARouter.BaseWebActivity)
                    .withString("title","资料下载")
                    .withString("url", AppConfig.MATERIAL).navigation();
        }

        //学习课堂
        public void LearningVideo(View view){
            ARouter.getInstance().build(MyARouter.BaseWebActivity)
                    .withString("title","学习课堂")
                    .withString("url",AppConfig.STUDYVIDEO).navigation();
        }
    }
}