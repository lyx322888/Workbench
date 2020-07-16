package com.zpz.home.ui;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zpz.common.base.BaseActivity;
import com.zpz.common.base.DataBindingConfig;
import com.zpz.common.base.MyARouter;
import com.zpz.common.callback.loadsir.LoadingCallback;
import com.zpz.home.BR;
import com.zpz.home.R;
import com.zpz.home.adapter.FileListAdapter;
import com.zpz.home.vm.FileListViewModel;

@Route(path = MyARouter.FileListActivity)
//档案管理
public class FileListActivity extends BaseActivity<FileListViewModel> {
    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_file_list)
                .addBindingParam(BR.vm,viewModel)
                .addBindingParam(BR.adapter,new FileListAdapter());
    }

    @Override
    protected void init() {
        setTitle("档案管理");
        loadService.showCallback(LoadingCallback.class);
    }

    @Override
    protected void initViewObservable() {

    }

    @Override
    protected void onLoadData() {
        viewModel.requesListCompanyRecord("1");
    }

}