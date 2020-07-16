package com.zpz.home.adapter;

import androidx.recyclerview.widget.RecyclerView;

import com.zpz.common.base.adapter.SimpleBaseBindingAdapter;
import com.zpz.home.R;
import com.zpz.home.baen.ListBonusBean;
import com.zpz.home.databinding.ItemDetailAccountBinding;

public class ListBounusAdapter extends SimpleBaseBindingAdapter<ListBonusBean.DataBean.WorkerUserMoneyLogBean, ItemDetailAccountBinding> {
    public ListBounusAdapter() {
        super(R.layout.item_detail_account);
    }

    @Override
    protected void onSimpleBindItem(ItemDetailAccountBinding binding, ListBonusBean.DataBean.WorkerUserMoneyLogBean item, RecyclerView.ViewHolder holder) {
        binding.setVm(item);
    }
}
