package com.zpz.home.ui;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zpz.common.base.BaseActivity;
import com.zpz.common.base.DataBindingConfig;
import com.zpz.common.base.MyARouter;
import com.zpz.home.BR;
import com.zpz.home.R;
import com.zpz.home.vm.ExpireCompanyViewModel;
//到期企业 企业年审
@Route(path = MyARouter.ExpireCompanyActivity)
public class ExpireCompanyActivity extends BaseActivity<ExpireCompanyViewModel> {
    @Autowired(name = "title")
    public String title;
    @Autowired(name = "status")
    public String status;

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        ARouter.getInstance().inject(this);
        viewModel.status.set(status);
        return new DataBindingConfig(R.layout.activity_expire_company)
                .addBindingParam(BR.vm,viewModel);
    }

    @Override
    protected void init() {
        setTitle(title);
    }

    @Override
    protected void initViewObservable() {

    }
    @Override
    protected void onLoadData() {

    }

}