package com.zpz.common.api;

import android.content.Intent;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSONObject;
import com.zpz.common.base.AppConfig;
import com.zpz.common.base.BaseApplication;
import com.zpz.common.base.MyARouter;
import com.zpz.common.utils.NetWorkUtils;
import com.zpz.common.utils.ToastUitl;
import com.zpz.common.utils.UserUtils;
import com.zpz.common.view.dalog.LoadingDialog;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 */

public class MyHttp {

    public static void doPost(Observable<JSONObject> observable, final HttpListener listener) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        listener.onSubscribe(d);
                    }
                    @Override
                    public void onNext(JSONObject result) {
                        switch (result.getInteger("state")){
                            case 0:
                                //失败
                                ToastUitl.showShort(result.getString("msg"));
                                listener.onError(0);
                                break;
                            case 1:
                                //成功
                                listener.onSuccess(result);
                                break;
                            case 2:
                                //请先登录
                                ToastUitl.showShort("请重新登录");
                                UserUtils.clearRecord();
                                ARouter.getInstance().build(MyARouter.LoginActivity).withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK).navigation();
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //请求错误
                        LoadingDialog.cancelDialogForLoading();
                        if (!NetWorkUtils.isNetConnected(BaseApplication.getInstance())) {
                            //网络
                            listener.onError(AppConfig.ERROR_STATE.NO_NETWORK);
                        } else {
                            //服务器
                            listener.onError(AppConfig.ERROR_STATE.SERVICE_ERROR);
                            ToastUitl.showShort("网络错误，请重试！");
                        }
                    }

                    @Override
                    public void onComplete() {
                        LoadingDialog.cancelDialogForLoading();
                        listener.onFinish();
                    }
                });
    }


}
