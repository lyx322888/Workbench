package com.zpz.home.fragment;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zpz.common.base.BaseFragment;
import com.zpz.common.base.DataBindingConfig;
import com.zpz.common.base.MyARouter;
import com.zpz.home.BR;
import com.zpz.home.R;
import com.zpz.home.databinding.FragmentHomeBinding;
import com.zpz.home.vm.HomeViewModel;

@Route(path = MyARouter.HomeFragment)
public class HomeFragment extends BaseFragment<HomeViewModel> {

    private FragmentHomeBinding homeBinding;
    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_home)
                .addBindingParam(BR.click, new ClickProxy())
                .addBindingParam(BR.vm,viewModel);
    }
    @Override
    protected void init() {
        homeBinding = (FragmentHomeBinding) getBinding();
        homeBinding.trl.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                viewModel.requesUserInfo();
                viewModel.requesIndexNotice();
            }
        });
    }

    @Override
    protected void initViewObservable() {
        viewModel.getHomeGg().observe(this,homeGg -> homeBinding.trl.finishRefreshing());
        viewModel.getUserInfo().observe(this,userInfoBean -> homeBinding.trl.finishRefreshing());
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
    }
}