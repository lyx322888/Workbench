package com.zpz.home.adapter;

import android.app.Activity;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zpz.common.base.MyARouter;
import com.zpz.common.base.adapter.SimpleBaseBindingAdapter;
import com.zpz.common.utils.CommonUtils;
import com.zpz.home.R;
import com.zpz.home.baen.CommissionItemBean;
import com.zpz.home.databinding.ItemCommissionBinding;

public class CommissionAdapter extends SimpleBaseBindingAdapter<CommissionItemBean.DataBean, ItemCommissionBinding> {
    private String status;
    public CommissionAdapter( String status) {
        super(R.layout.item_commission);
        this.status = status;
    }

    @Override
    protected void onSimpleBindItem(ItemCommissionBinding binding, final CommissionItemBean.DataBean item, RecyclerView.ViewHolder holder) {
        binding.setVm(item);
        switch (status){
            case "1":
                binding.tvTime.setText(String.format("提交时间:%s",item.getCreate_time()));
                binding.tvState.setText("已通过");
                binding.tvQjd.setText("去建档");
                break;
            case "2":
                binding.tvTime.setText(String.format("到期时间:%s",item.getCreate_time()));
                binding.tvState.setText("即将过期");
                binding.tvQjd.setText("去年审");
                break;
            case "3":
                binding.tvTime.setText(String.format("到期时间:%s",item.getCreate_time()));
                binding.tvState.setText("已过期");
                binding.tvQjd.setText("重新建档");
                break;
            default:break;
        }
        binding.tvQjd.setOnClickListener(v -> ARouter.getInstance().build(MyARouter.CreateProcedureActivity)
                .withLong("company_id",item.getCompany_id())
                .withLong("first_assess_id",item.getFirst_assess_id())
                .navigation());
        //电话
        binding.tvPhone.setOnClickListener(v -> CommonUtils.callPhone((Activity) mContext,item.getMobile()));
    }
}
