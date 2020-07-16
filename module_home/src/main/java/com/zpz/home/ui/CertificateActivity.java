package com.zpz.home.ui;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zpz.common.base.BaseActivity;
import com.zpz.common.base.DataBindingConfig;
import com.zpz.common.base.MyARouter;
import com.zpz.home.BR;
import com.zpz.home.R;
import com.zpz.home.vm.CertificateViewModel;
//证书查询
@Route(path = MyARouter.CertificateActivity)
public class CertificateActivity extends BaseActivity<CertificateViewModel> {
    @Autowired()
    long company_id;

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_certificate)
                .addBindingParam(BR.vm,viewModel);
    }

    @Override
    protected void init() {
        setTitle("证书查询");
        ARouter.getInstance().inject(this);
    }

    @Override
    protected void initViewObservable() {

    }

    @Override
    protected void onLoadData() {
        viewModel.requesCertificate(company_id);
    }

}