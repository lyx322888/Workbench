package com.zpz.home.adapter;

import android.app.Activity;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zpz.common.base.MyARouter;
import com.zpz.common.base.adapter.SimpleBaseBindingAdapter;
import com.zpz.common.utils.CommonUtils;
import com.zpz.home.R;
import com.zpz.home.baen.FirstTriaBean;
import com.zpz.home.databinding.ItemFirstTrialBinding;

public class FirstTrialAdapter extends SimpleBaseBindingAdapter<FirstTriaBean.DataBean, ItemFirstTrialBinding> {
    private int status;
    public FirstTrialAdapter(int status) {
        super(R.layout.item_first_trial);
        this.status = status;
    }

    @Override
    protected void onSimpleBindItem(ItemFirstTrialBinding binding, FirstTriaBean.DataBean item, RecyclerView.ViewHolder holder) {
        binding.setList(item);
        switch (status){
            case 0:
                binding.tvState.setText("审核中");
                binding.tvQjd.setVisibility(View.GONE);
                break;
            case 1:
                binding.tvState.setText("已通过");
                if (item.getCompany_id()==0){
                    binding.tvQjd.setVisibility(View.VISIBLE);
                }else {
                    binding.tvQjd.setVisibility(View.GONE);
                }
                break;
            case 2:
                binding.tvState.setText("未通过");
                binding.tvQjd.setVisibility(View.GONE);
                break;
        }
        //建档
       binding.tvQjd.setOnClickListener(v -> ARouter.getInstance().build(MyARouter.CreateProcedureActivity)
               .withLong("company_id",item.getCompany_id())
               .withLong("first_assess_id",item.getFirst_assess_id())
               .navigation());
        //电话
        binding.tvPhone.setOnClickListener(v -> CommonUtils.callPhone((Activity) mContext,item.getMobile()));
    }

}
