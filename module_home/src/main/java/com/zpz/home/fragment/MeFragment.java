package com.zpz.home.fragment;

import android.view.View;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zpz.common.base.BaseFragment;
import com.zpz.common.base.DataBindingConfig;
import com.zpz.common.base.MyARouter;
import com.zpz.common.dialog.ConfirmDialog;
import com.zpz.common.utils.AppManager;
import com.zpz.common.utils.CleanDataUtils;
import com.zpz.common.utils.UserUtils;
import com.zpz.home.BR;
import com.zpz.home.R;
import com.zpz.home.vm.SetingViewModel;

/*
*我的
 */
@Route(path = MyARouter.MeFragment)
public class MeFragment extends BaseFragment<SetingViewModel> {

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_me)
                .addBindingParam(BR.vm,viewModel)
                .addBindingParam(BR.click,new ClickProxy());
    }
    @Override
    protected void init() {

    }

    @Override
    protected void initViewObservable() {
        viewModel.getLoginstate().observe(this, aBoolean -> {
            if (!aBoolean){
                ARouter.getInstance().build(MyARouter.LoginActivity).navigation(mActivity, new NavCallback() {
                    @Override
                    public void onArrival(Postcard postcard) {
                        //跳转成功后关闭除了登录界面外的所有界面
                        UserUtils.clearRecord();
                        AppManager.getAppManager().finishAllExpectSpecifiedActivity( postcard.getDestination());
                    }
                });
            }
        });
    }

    @Override
    protected void onLoadData() {

    }

    public class ClickProxy{
        //退出
        public void outLogin(View view){
            ConfirmDialog confirmDialog =  ConfirmDialog.newInstance("您确定退出登录吗?");
            confirmDialog.setDialogListener(new ConfirmDialog.DialogListener() {
                @Override
                public void dialogClickListener() {
                    viewModel.requesOutLogin();
                }
            });
            confirmDialog.show(getParentFragmentManager(),"");
        }
        //清除缓存
        public void clearCache(View view){
            ConfirmDialog confirmDialog = ConfirmDialog.newInstance("您确定清除缓存吗?");
            confirmDialog.setDialogListener(new ConfirmDialog.DialogListener() {
                @Override
                public void dialogClickListener() {
                    CleanDataUtils.clearAllCache(mActivity);
                    viewModel.cacheSize.set(CleanDataUtils.getTotalCacheSize(mActivity));
                }
            });
            confirmDialog.show(getParentFragmentManager(),"");
        }

    }
}