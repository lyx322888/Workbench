package com.zpz.home.ui;

import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.zpz.common.base.BaseActivity;
import com.zpz.common.base.DataBindingConfig;
import com.zpz.common.base.MyARouter;
import com.zpz.home.BR;
import com.zpz.home.R;
import com.zpz.home.baen.FirstAssessCountBean;
import com.zpz.home.databinding.ActivityFirstTrialBinding;
import com.zpz.home.fragment.FirstTrialFragment;
import com.zpz.home.vm.FirstTrialViewModel;
import java.util.ArrayList;
//初审
@Route(path = MyARouter.FirstTrialActivity)
public class FirstTrialActivity extends BaseActivity<FirstTrialViewModel> {
    private ActivityFirstTrialBinding binding;
    @Override
    protected void init() {
        binding = (ActivityFirstTrialBinding) getBinding();
        ArrayList<Fragment> arrayList = new ArrayList<>();
        arrayList.add(FirstTrialFragment.newInstance(0));
        arrayList.add(FirstTrialFragment.newInstance(1));
        arrayList.add(FirstTrialFragment.newInstance(2));
        binding.labLayout.setViewPager(binding.orderViewPager,viewModel.mTitles,this,arrayList);
        //分享
        binding.commonToolbar.titleRightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("dfdf", "onClick: " );
            }
        });
    }

    @Override
    protected void initViewObservable() {

        viewModel.getFirstAssessCountLiveData().observe(this, new Observer<FirstAssessCountBean>() {
            @Override
            public void onChanged(FirstAssessCountBean firstAssessCountBean) {
                if (firstAssessCountBean.getData().getCount_status_zero()!=0){
                    binding.labLayout.showMsg(0,firstAssessCountBean.getData().getCount_status_zero());
                    binding.labLayout.setMsgMargin(0,30,10);
                }else {
                    binding.labLayout.hideMsg(0);
                }
                if (firstAssessCountBean.getData().getCount_status_one()!=0){
                    binding.labLayout.showMsg(1,firstAssessCountBean.getData().getCount_status_one());
                    binding.labLayout.setMsgMargin(1,30,10);
                }else {
                    binding.labLayout.hideMsg(1);
                }
                if (firstAssessCountBean.getData().getCount_status_two()!=0){
                    binding.labLayout.showMsg(2,firstAssessCountBean.getData().getCount_status_two());
                    binding.labLayout.setMsgMargin(2,30,10);
                }else {
                    binding.labLayout.hideMsg(2);
                }
            }
        });
        viewModel.requesFirstAssessCount();
    }


    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_first_trial)
                .addBindingParam(BR.vm,viewModel);
    }


}