package com.zpz.home.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zpz.common.base.MyARouter;
import com.zpz.common.utils.CommonUtils;
import com.zpz.common.utils.GlideUtils;
import com.zpz.home.R;
import com.zpz.home.baen.FirstTriaBean;


public class FirstTrialAdapter extends BaseQuickAdapter<FirstTriaBean.DataBean, BaseViewHolder> {
    private int status;
    public FirstTrialAdapter(int status) {
        super(R.layout.item_first_trial);
        this.status = status;
    }


    @Override
    protected void convert(BaseViewHolder helper, final FirstTriaBean.DataBean item) {
        helper.setText(R.id.tv_name,item.getCompany_name())
                .setText(R.id.tv_time,"提交时间："+item.getCreate_time())
                .setText(R.id.tv_phone,"联系电话："+item.getMobile());
        GlideUtils.showSmallPic((ImageView) helper.getView(R.id.iv_photo),item.getLogo());
        TextView tvJd = helper.getView(R.id.tv_qjd);

       switch (status){
           case 0:
               helper.setText(R.id.tv_state,"审核中");
               tvJd.setVisibility(View.GONE);
               break;
           case 1:
               helper.setText(R.id.tv_state,"已通过");
               if (item.getCompany_id()==0){
                   tvJd.setVisibility(View.VISIBLE);
               }else {
                   tvJd.setVisibility(View.GONE);
               }
               break;
           case 2:
               helper.setText(R.id.tv_state,"未通过");
               tvJd.setVisibility(View.GONE);
               break;
       }
        //建档
       helper.getView(R.id.tv_qjd).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ARouter.getInstance().build(MyARouter.CreateProcedureActivity)
                       .withLong("company_id",item.getCompany_id())
                       .withLong("first_assess_id",item.getFirst_assess_id())
                       .navigation();
           }
       });
       //电话
       helper.getView(R.id.tv_phone).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               CommonUtils.callPhone((Activity) mContext,item.getMobile());
           }
       });
    }
}
