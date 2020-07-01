package com.zpz.home.ui;

import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zpz.common.base.BaseActivity;
import com.zpz.common.base.DataBindingConfig;
import com.zpz.common.base.MyARouter;
import com.zpz.home.BR;
import com.zpz.home.R;
import com.zpz.home.adapter.FileListAdapter;
import com.zpz.home.baen.FileListBean;
import com.zpz.home.databinding.ActivityFileListBinding;
import com.zpz.home.vm.FileListViewModel;

import java.util.List;

@Route(path = MyARouter.FileListActivity)
public class FileListActivity extends BaseActivity<FileListViewModel> {
    private ActivityFileListBinding fileListBinding;
    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_file_list)
                .addBindingParam(BR.vm,viewModel)
                .addBindingParam(BR.adapter,new FileListAdapter(mActivity));
    }

    @Override
    protected void init() {
        fileListBinding = (ActivityFileListBinding) getBinding();
        //刷新加载
        fileListBinding.trl.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                viewModel.page=1;
                viewModel.requesListCompanyRecord();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                viewModel.page+=1;
                viewModel.requesListCompanyRecord();
            }
        });
    }

    @Override
    protected void initViewObservable() {
        viewModel.getFilelistlivedata().observe(this, new Observer<List<FileListBean.DataBean>>() {
            @Override
            public void onChanged(List<FileListBean.DataBean> dataBeans) {
                fileListBinding.trl.finishRefreshing();
                fileListBinding.trl.finishLoadmore();
            }
        });
        viewModel.requesListCompanyRecord();
    }

}