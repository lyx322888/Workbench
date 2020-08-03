package com.zpz.home.fragment;

import androidx.core.content.ContextCompat;
import androidx.databinding.library.baseAdapters.BR;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zpz.common.base.BaseFragment;
import com.zpz.common.base.DataBindingConfig;
import com.zpz.common.base.MyARouter;
import com.zpz.home.R;
import com.zpz.home.databinding.FragmentCommissionBinding;
import com.zpz.home.vm.CommissionViewModel;

import java.util.ArrayList;
//待办
@Route(path = MyARouter.CommissionFragment)
public class CommissionFragment extends BaseFragment<CommissionViewModel> {
    private FragmentCommissionBinding binding;
    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_commission)
                .addBindingParam(BR.vm,viewModel);
    }

    @Override
    protected void init() {
        binding = (FragmentCommissionBinding) getBinding();
        ArrayList<Fragment> arrayList = new ArrayList<>();
        arrayList.add(CommissionItemFragment.newInstance("1"));
        arrayList.add(CommissionItemFragment.newInstance("2"));
        arrayList.add(CommissionItemFragment.newInstance("3"));

        binding.labLayout.setViewPager(binding.orderViewPager,viewModel.mTitles, mActivity,arrayList);

    }

    @Override
    protected void initViewObservable() {
        viewModel.getCountBeanLiveData().observe(this, commissionCountBean -> {
            if (commissionCountBean.getCount_type_one()!=0){
                binding.labLayout.showMsg(0,commissionCountBean.getCount_type_one());
                binding.labLayout.setMsgMargin(0,30,10);
                binding.labLayout.getMsgView(0).setBackgroundColor(ContextCompat.getColor(mActivity,R.color.appColor));

            }else {
                binding.labLayout.hideMsg(0);
            }
            if (commissionCountBean.getCount_type_two()!=0){
                binding.labLayout.showMsg(1,commissionCountBean.getCount_type_two());
                binding.labLayout.setMsgMargin(1,30,10);
                binding.labLayout.getMsgView(1).setBackgroundColor(ContextCompat.getColor(mActivity,R.color.appColor));
            }else {
                binding.labLayout.hideMsg(1);
            }
            if (commissionCountBean.getCount_type_three()!=0){
                binding.labLayout.showMsg(2,commissionCountBean.getCount_type_three());
                binding.labLayout.setMsgMargin(2,30,10);
                binding.labLayout.getMsgView(2).setBackgroundColor(ContextCompat.getColor(mActivity,R.color.appColor));
            }else {
                binding.labLayout.hideMsg(2);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.onRefresh();
    }

    @Override
    protected void onTwRefreshAndLoadMore() {
        viewModel.requesBacklogCount();
    }

    @Override
    protected void onLoadData() {

    }
}