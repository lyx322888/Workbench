package com.zpz.home.ui;


import com.zpz.common.base.BaseActivity;
import com.zpz.common.base.DataBindingConfig;
import com.zpz.home.BR;
import com.zpz.home.R;
import com.zpz.home.vm.CertificateViewModel;
//证书查询
public class CertificateActivity extends BaseActivity<CertificateViewModel> {

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_certificate)
                .addBindingParam(BR.vm,viewModel);
    }

    @Override
    protected void init() {
        setTitle("证书查询");
    }

    @Override
    protected void initViewObservable() {

    }




}