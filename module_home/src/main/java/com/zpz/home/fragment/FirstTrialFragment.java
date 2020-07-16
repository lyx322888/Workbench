package com.zpz.home.fragment;

import android.os.Bundle;

import androidx.databinding.library.baseAdapters.BR;

import com.zpz.common.base.BaseFragment;
import com.zpz.common.base.DataBindingConfig;
import com.zpz.home.R;
import com.zpz.home.adapter.FirstTrialAdapter;
import com.zpz.home.vm.FirstTrialFmViewModel;

/**
 * 初审列表
 */
public class FirstTrialFragment extends BaseFragment<FirstTrialFmViewModel> {
    public int status;
    public static FirstTrialFragment newInstance(int status) {
        FirstTrialFragment tabFragment = new FirstTrialFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("status", status);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }
    @Override
    protected DataBindingConfig getDataBindingConfig() {
        if (getArguments() != null) {
            status = getArguments().getInt("status");
        }
        return new DataBindingConfig(R.layout.fragment_first_trial)
                .addBindingParam(BR.vm,viewModel)
                .addBindingParam(BR.adapter,new FirstTrialAdapter(status));
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