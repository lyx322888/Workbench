package com.zpz.common.base;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSONObject;
import com.zpz.common.utils.ToastUitl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public  class BaseViewModel extends ViewModel {
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
    //结束刷新、加载更多
    public MutableLiveData<Boolean> finishTw = new MutableLiveData<>();
    //加载数据
    public MutableLiveData<Boolean> loadData = new MutableLiveData<>();
    //刷新  loadData.setValue(true);会调用页面的onTwRefreshAndLoadMore
    public void onRefresh(){
        page=1;
        loadData.setValue(true);
    }
    //加载更多  loadData.setValue(true);会调用页面的onTwRefreshAndLoadMore
    public void onLoadMore(){
        page+=1;
        loadData.setValue(true);
    }

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
            if (newlist.size()==0){
                page-=1;
                ToastUitl.showShort("没有更多了");
            }
        }
        return oldlist;

    }

    //接口回调
    public  abstract class HttpViewModelListener extends com.zpz.common.api.HttpListener {
        public abstract void onSuccess(JSONObject result);
        public void onError(int code){
            super.onError(code);
            if (page!=1){
                page-=1;
            }
        }
        public void onSubscribe(Disposable d){
            super.onSubscribe(d);
            addDisposable(d);
        }

        @Override
        public void onFinish() {
            super.onFinish();
            finishTw.setValue(true);
        }

    }
}
