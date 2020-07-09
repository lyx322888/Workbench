package com.zpz.common.base;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseViewModel extends ViewModel {
    //页数
    public int page = 1;
    //管理RxJava，主要针对RxJava异步操作造成的内存泄漏
    private CompositeDisposable mCompositeDisposable ;
    //是否显示空页面
    public MutableLiveData<Boolean> isShowEmpty = new MutableLiveData<>();
    //是否显示网络延迟页面
    public MutableLiveData<Boolean> isShowTimeout = new MutableLiveData<>();
    //是否显示加载中
    public MutableLiveData<Boolean> isShowLoading = new MutableLiveData<>();
    //显示内容
    public MutableLiveData<Boolean> isShowSuccess = new MutableLiveData<>();

    public void showEmpty(){
        isShowEmpty.setValue(true);
    }

    public void showTimeout(){
        isShowTimeout.setValue(true);
    }

    public void showLoading(){
        isShowLoading.setValue(true);
    }

    public void showSuccess(){
        isShowSuccess.setValue(true);
    }

    //添加disposable
    public void  addDisposable(Disposable disposable){
        if (mCompositeDisposable==null){
            mCompositeDisposable = new CompositeDisposable();
        }else {
            mCompositeDisposable.add(disposable);
        }
    }
    //ViewModel销毁时会执行，同时取消所有异步任务
    protected void onCleared() {
        //ViewModel销毁时会执行，同时取消所有异步任务
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    //管理网络回调列表数据
    protected  <T> List<T> manageList(List<T> oldlist, List<T> newlist){
       if (null==oldlist){
           oldlist = new ArrayList<>();
       }
        if (page==1){
            if (newlist.size()==0){
                showEmpty();
            }else {
                showSuccess();
                oldlist = newlist;
            }
        }else {
            showSuccess();
            oldlist.addAll(newlist);
        }
        return oldlist;

    }

    public abstract class HttpViewModelListener extends com.zpz.common.api.HttpListener {
        public abstract void onSuccess(JSONObject result);
        public void onError(int code){
            if (page!=1){
                page-=1;
            }
        }
        public void onSubscribe(Disposable d){
            addDisposable(d);
        }
        public void onFinish(){}
    }
}
