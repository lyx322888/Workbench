package com.zpz.home.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zpz.common.base.BaseFragment;
import com.zpz.common.base.DataBindingConfig;
import com.zpz.home.R;
import com.zpz.home.adapter.FirstTrialAdapter;
import com.zpz.home.baen.FirstTriaBean;
import com.zpz.home.databinding.FragmentFirstTrialBinding;
import com.zpz.home.vm.FirstTrialFmViewModel;

import java.util.List;

/**
 * 初审列表
 */
public class FirstTrialFragment extends BaseFragment<FirstTrialFmViewModel> {
    public FirstTrialAdapter firstTrialAdapter;
    private FragmentFirstTrialBinding firstTrialBinding;
    public int status;
    public static FirstTrialFragment newInstance(int status) {
        FirstTrialFragment tabFragment = new FirstTrialFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("status", status);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    protected void init() {
        if (getArguments() != null) {
            status = getArguments().getInt("status");
        }
        firstTrialBinding = (FragmentFirstTrialBinding) getBinding();
        firstTrialAdapter =new FirstTrialAdapter();
        firstTrialBinding.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        firstTrialBinding.rv.setAdapter(firstTrialAdapter);
        firstTrialBinding.trl.setOnRefreshListener(new RefreshListenerAdapter() {
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
    protected void initViewObservable() {
        viewModel.getFirstTriaBeanMutableLiveData().observe(this, new Observer<List<FirstTriaBean.DataBean>>() {
            @Override
            public void onChanged(List<FirstTriaBean.DataBean> dataBeans) {
                firstTrialAdapter.setNewData(dataBeans);
                firstTrialBinding.trl.finishRefreshing();
                firstTrialBinding.trl.finishLoadmore();

            }
        });
        viewModel.requesfirstTria(status);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_first_trial);
    }



}