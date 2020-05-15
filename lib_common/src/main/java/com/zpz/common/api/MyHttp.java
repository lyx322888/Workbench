package com.zpz.common.api;

import android.content.Intent;
import android.util.Log;


import com.zpz.common.base.AppConfig;
import com.zpz.common.base.BaseApplication;
import com.zpz.common.utils.NetWorkUtils;
import com.zpz.common.utils.ToastUitl;

import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by wushenghui on 2017/6/20.
 */

public class MyHttp {
    private int opNum;

    public MyHttp() {
    }
    public void doPost(Observable<JSONObject> observable, final HttpListener listener) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("dfdf", "onError: "+e );
                        //请求错误
//                LoadingDialog.cancelDialogForLoading();
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
                        //请求完成
//                LoadingDialog.cancelDialogForLoading();
                    }
                });
    }
}
