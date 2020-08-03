package com.zpz.home.ui;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zpz.common.base.BaseActivity;
import com.zpz.common.base.DataBindingConfig;
import com.zpz.common.base.MyARouter;
import com.zpz.common.dialog.FirstTrialShareDialog;
import com.zpz.home.BR;
import com.zpz.home.R;
import com.zpz.home.databinding.ActivityFirstTrialBinding;
import com.zpz.home.fragment.FirstTrialFragment;
import com.zpz.home.vm.FirstTrialViewModel;

import java.util.ArrayList;
//初审
@Route(path = MyARouter.FirstTrialActivity)
public class FirstTrialActivity extends BaseActivity<FirstTrialViewModel> {
    private ActivityFirstTrialBinding binding;

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_first_trial)
                .addBindingParam(BR.vm,viewModel);
    }

    @Override
    protected void init() {
        setTitle("企业初审");
        binding = (ActivityFirstTrialBinding) getBinding();
        ArrayList<Fragment> arrayList = new ArrayList<>();
        arrayList.add(FirstTrialFragment.newInstance(0));
        arrayList.add(FirstTrialFragment.newInstance(1));
        arrayList.add(FirstTrialFragment.newInstance(2));
        binding.labLayout.setViewPager(binding.orderViewPager,viewModel.mTitles,this,arrayList);
        //分享
        baseTitleBinding.titleRightTv.setText("分享初审表");
        baseTitleBinding.titleRightTv.setOnClickListener(v -> {
            FirstTrialShareDialog.newInstance().show(getSupportFragmentManager(),"");
        });
    }

    @Override
    protected void initViewObservable() {
        viewModel.getFirstAssessCountLiveData().observe(this, firstAssessCountBean -> {
            if (firstAssessCountBean.getData().getCount_status_zero()!=0){
                binding.labLayout.showMsg(0,firstAssessCountBean.getData().getCount_status_zero());
                binding.labLayout.setMsgMargin(0,30,10);
                binding.labLayout.getMsgView(0).setBackgroundColor(ContextCompat.getColor(mContext,R.color.appColor));
            }else {
                binding.labLayout.hideMsg(0);
            }
            if (firstAssessCountBean.getData().getCount_status_one()!=0){
                binding.labLayout.showMsg(1,firstAssessCountBean.getData().getCount_status_one());
                binding.labLayout.setMsgMargin(1,30,10);
                binding.labLayout.getMsgView(1).setBackgroundColor(ContextCompat.getColor(mContext,R.color.appColor));

            }else {
                binding.labLayout.hideMsg(1);
            }
            if (firstAssessCountBean.getData().getCount_status_two()!=0){
                binding.labLayout.showMsg(2,firstAssessCountBean.getData().getCount_status_two());
                binding.labLayout.setMsgMargin(2,30,10);
                binding.labLayout.getMsgView(2).setBackgroundColor(ContextCompat.getColor(mContext,R.color.appColor));
            }else {
                binding.labLayout.hideMsg(2);
            }
        });
    }

    @Override
    protected void onLoadData() {
        viewModel.requesFirstAssessCount();
    }



}