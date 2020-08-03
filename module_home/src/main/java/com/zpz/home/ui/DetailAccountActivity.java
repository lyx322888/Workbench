package com.zpz.home.ui;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zpz.common.base.BaseActivity;
import com.zpz.common.base.DataBindingConfig;
import com.zpz.common.base.MyARouter;
import com.zpz.home.BR;
import com.zpz.home.R;
import com.zpz.home.adapter.ListBounusAdapter;
import com.zpz.home.vm.DetailAccountViewModel;
//分红明细
@Route(path = MyARouter.DetailAccountActivity)
public class DetailAccountActivity extends BaseActivity<DetailAccountViewModel> {
    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_detail_account)
                .addBindingParam(BR.vm,viewModel)
                .addBindingParam(BR.adapter,new ListBounusAdapter());
    }

    @Override
    protected void init() {
        viewModel.showLoading();
        setTitle("分红明细");
    }

    @Override
    protected void initViewObservable() {

    }

    @Override
    protected void onLoadData() {
        viewModel.requesListBonus();
    }

}