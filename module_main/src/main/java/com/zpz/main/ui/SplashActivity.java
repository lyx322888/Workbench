package com.zpz.main.ui;

import android.Manifest;
import android.app.AlertDialog;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zpz.common.base.BaseActivity;
import com.zpz.common.base.BaseViewModel;
import com.zpz.common.base.DataBindingConfig;
import com.zpz.common.base.MyARouter;
import com.zpz.common.utils.PermissioinSettingPage;
import com.zpz.common.utils.UserUtils;
import com.zpz.main.R;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

//启动页
@RuntimePermissions
public class SplashActivity extends BaseActivity<BaseViewModel> {

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_splash);
    }

    @Override
    protected void setStatubarColor( ) {
    }

    @Override
    protected void init() {
        hideTitleBar();
        SplashActivityPermissionsDispatcher.showMainContentWithPermissionCheck(SplashActivity.this);
    }

    @Override
    protected void initViewObservable() {

    }

    @Override
    protected void onLoadData() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SplashActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,})
    void showMainContent() {
        //懒加载
        if (UserUtils.getLoginState()){
            ARouter.getInstance().build(MyARouter.MainActivity).navigation();
        }else {
            ARouter.getInstance().build(MyARouter.LoginActivity).navigation();
        }
        finish();
    }

    @OnShowRationale({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,})
    void showPermissionDialog(final PermissionRequest request) {
        new AlertDialog.Builder(this, R.style.SplashDialog).setTitle("提示").setMessage("请授予相关权限，否则无法正常工作")
                .setCancelable(false).setNegativeButton("取消", (dialogInterface, i) -> {
                    request.cancel();
                    finish();
                }).setPositiveButton("授权", (dialogInterface, i) -> request.proceed()).create().show();
    }

    @OnPermissionDenied({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,})
    void permissionDeny() {
        SplashActivityPermissionsDispatcher.showMainContentWithPermissionCheck(this);
    }

    @OnNeverAskAgain({Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,})
    void permissionNeverAskAgain() {
        new AlertDialog.Builder(this, R.style.SplashDialog).setTitle("提示").setMessage("为保证软件的正常工作，请在设置中授予相关权限")
                .setCancelable(false).setNegativeButton("暂不", (dialogInterface, i) -> finish()).setPositiveButton("去设置", (dialogInterface, i) -> PermissioinSettingPage.start(SplashActivity.this, true)).create().show();
    }
}
