package com.zpz.home.fragment;

import android.os.Bundle;
import android.text.TextUtils;

import com.zpz.common.base.BaseFragment;
import com.zpz.common.base.DataBindingConfig;
import com.zpz.home.BR;
import com.zpz.home.R;
import com.zpz.home.adapter.CommissionAdapter;
import com.zpz.home.vm.CommissionItemViewModel;
import com.zpz.home.vm.ExpireCompanyViewModel;

/**
 * 待办
 */
public class CommissionItemFragment extends BaseFragment<CommissionItemViewModel> {
    //从activity共享过来
    private ExpireCompanyViewModel expireCompanyViewModel;
    public String status;
    public static CommissionItemFragment newInstance(String status) {
        CommissionItemFragment tabFragment = new CommissionItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("status", status);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    protected void initViewModel() {
        super.initViewModel();
        expireCompanyViewModel = getActivityViewModel(ExpireCompanyViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        if (getArguments() != null) {
            status = getArguments().getString("status");
        }else if (!TextUtils.isEmpty(expireCompanyViewModel.status.get())){
            status = expireCompanyViewModel.status.get();
        }
        return new DataBindingConfig(R.layout.fragment_commission_item)
                .addBindingParam(BR.adapter,new CommissionAdapter(status))
                .addBindingParam(BR.vm,viewModel);
    }

    @Override
    protected void init() {
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.onRefresh();
    }

    @Override
    protected void initViewObservable() {

    }

    @Override
    protected void onLoadData() {

    }

    @Override
    protected void onTwRefreshAndLoadMore() {
        viewModel.requesfirstTria(status);
    }
}