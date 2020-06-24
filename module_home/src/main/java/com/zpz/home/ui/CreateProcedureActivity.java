package com.zpz.home.ui;

import android.view.View;

import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zpz.common.base.BaseActivity;
import com.zpz.common.base.DataBindingConfig;
import com.zpz.common.base.MyARouter;
import com.zpz.common.view.dalog.LoadingDialog;
import com.zpz.home.BR;
import com.zpz.home.R;
import com.zpz.home.vm.CreateProcedureViewModel;
//建档
@Route(path = MyARouter.CreateProcedureActivity)
public class CreateProcedureActivity extends BaseActivity<CreateProcedureViewModel> {

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_create_procedure)
                .addBindingParam(BR.vm,viewModel)
                .addBindingParam(BR.click, new ClickProxy());
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initViewObservable() {
       LoadingDialog.showDialogForLoading(mActivity);
       viewModel.requesCreteProceddure();
    }

    public class ClickProxy{
        //提交
        public void submit(View view){

        }
        //上传视频
        public void uploadAVideo(View view){

        }
        //上传logo
        public void uploadLogo(View view){

        }
        //选择时间
        public void selectTime (View view){

        }
        //定位
        public void location (View view){

        }
    }
}