package com.zpz.main;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;


import androidx.annotation.NonNull;

import com.zpz.common.base.BaseActivity;
import com.zpz.common.utils.PermissioinSettingPage;
import com.zpz.common.utils.RxUtils;
import com.zpz.main.databinding.ActivitySplashBinding;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

//启动页
@RuntimePermissions
public class SplashActivity extends BaseActivity<ActivitySplashBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        RxUtils.countdown(2, new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                addDisposable(d);
            }

            @Override
            public void onNext(Long aLong) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                SplashActivityPermissionsDispatcher.showMainContentWithPermissionCheck(SplashActivity.this);
            }
        });
    }

    @Override
    public void fillData() {

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SplashActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showMainContent() {
        startActivity(new Intent(mContext, MainActivity.class));
        finish();
    }

    @OnShowRationale({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showPermissionDialog(final PermissionRequest request) {
        new AlertDialog.Builder(this, R.style.SplashDialog).setTitle("提示").setMessage("请授予相关权限，否则微百姓无法正常工作")
                .setCancelable(false).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                request.cancel();
                finish();
            }
        }).setPositiveButton("授权", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                request.proceed();
            }
        }).create().show();
    }

    @OnPermissionDenied({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void permissionDeny() {
        SplashActivityPermissionsDispatcher.showMainContentWithPermissionCheck(this);
    }

    @OnNeverAskAgain({Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void permissionNeverAskAgain() {
        new AlertDialog.Builder(this, R.style.SplashDialog).setTitle("提示").setMessage("为保证软件的正常工作，请在设置中授予相关权限")
                .setCancelable(false).setNegativeButton("暂不", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        }).setPositiveButton("去设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                PermissioinSettingPage.start(SplashActivity.this, true);
            }
        }).create().show();
    }
}
