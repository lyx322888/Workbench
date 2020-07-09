package com.zpz.home.ui;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zpz.common.base.BaseActivity;
import com.zpz.common.base.DataBindingConfig;
import com.zpz.common.base.MyARouter;
import com.zpz.common.callback.loadsir.EmptyCallback;
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
        //fragment公用 ExpireCompanyViewModel 所以要在fragment需要时 提交设置好参数
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

}