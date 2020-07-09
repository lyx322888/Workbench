package com.zpz.home.fragment;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.lifecycle.Observer;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zpz.common.base.BaseFragment;
import com.zpz.common.base.DataBindingConfig;
import com.zpz.home.BR;
import com.zpz.home.R;
import com.zpz.home.adapter.CommissionAdapter;
import com.zpz.home.baen.CommissionItemBean;
import com.zpz.home.databinding.FragmentCommissionItemBinding;
import com.zpz.home.vm.CommissionItemViewModel;
import com.zpz.home.vm.ExpireCompanyViewModel;

import java.util.List;

/**
 * 待办
 */
public class CommissionItemFragment extends BaseFragment<CommissionItemViewModel> {
    private FragmentCommissionItemBinding commissionItemBinding;
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
                .addBindingParam(BR.adapter,new CommissionAdapter(mActivity,R.layout.item_commission,status))
                .addBindingParam(BR.vm,viewModel);
    }

    @Override
    protected void init() {

        commissionItemBinding = (FragmentCommissionItemBinding) getBinding();
        //刷新加载
        commissionItemBinding.trl.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                viewModel.page=1;
                viewModel.requesfirstTria(status);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                viewModel.page+=1;
                viewModel.requesfirstTria(status);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.requesfirstTria(status);
    }

    @Override
    protected void initViewObservable() {
        viewModel.getComissionlivedta().observe(this, new Observer<List<CommissionItemBean.DataBean>>() {
            @Override
            public void onChanged(List<CommissionItemBean.DataBean> dataBeans) {
                commissionItemBinding.trl.finishRefreshing();
                commissionItemBinding.trl.finishLoadmore();
            }
        });
        viewModel.requesfirstTria(status);
    }



}