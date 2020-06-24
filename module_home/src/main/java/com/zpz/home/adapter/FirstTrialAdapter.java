package com.zpz.home.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zpz.home.R;
import com.zpz.home.baen.FirstTriaBean;

import java.util.List;

public class FirstTrialAdapter extends BaseQuickAdapter<FirstTriaBean.DataBean, BaseViewHolder> {
    public FirstTrialAdapter() {
        super(R.layout.item_first_trial);
    }


    @Override
    protected void convert(BaseViewHolder helper, FirstTriaBean.DataBean item) {
        
    }
}
