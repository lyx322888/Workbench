package com.zpz.common.base.binding;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zpz.common.base.BaseViewModel;

public class TwinklingRefreshBindingAdapter {

    @BindingAdapter("binding")
    public static void bingding(final TwinklingRefreshLayout trl, final BaseViewModel baseViewModel){
        trl.setHeaderHeight(50);
        trl.setMaxHeadHeight(70);
        trl.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                baseViewModel.onRefresh();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                baseViewModel.onLoadMore();
            }
        });

        baseViewModel.finishTw.observe((LifecycleOwner) trl.getContext(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                trl.finishRefreshing();
                trl.finishLoadmore();
            }
        });
    }
}
