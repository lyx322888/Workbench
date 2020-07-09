package com.zpz.home.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.zpz.common.api.Api;
import com.zpz.common.api.MyHttp;
import com.zpz.common.base.BaseViewModel;
import com.zpz.common.bean.HomeGg;
import com.zpz.common.bean.UserInfoBean;
import com.zpz.common.utils.UserUtils;

public class HomeViewModel extends BaseViewModel {
    private  MutableLiveData<UserInfoBean> mutableLiveData ;
    private MutableLiveData<HomeGg> homeGgMutableLiveData ;

    public LiveData<HomeGg> getHomeGg (){
        if (homeGgMutableLiveData==null){
            homeGgMutableLiveData = new MutableLiveData<>();
            requesIndexNotice();
        }
        return homeGgMutableLiveData;
    }
    public LiveData<UserInfoBean> getUserInfo (){
        if (mutableLiveData==null){
            mutableLiveData = new MutableLiveData<>();
            requesUserInfo();
        }
        return mutableLiveData;
    }

    //获取公告
    public void requesIndexNotice(){
        new MyHttp().doPost(Api.getDefault().getIndexNotice(UserUtils.getToken()), new HttpViewModelListener() {
            @Override
            public void onSuccess(JSONObject result) {
                HomeGg homeGg = new Gson().fromJson(result.toString(),HomeGg.class);
                homeGgMutableLiveData.setValue(homeGg);
            }

            @Override
            public void onError(int code) {

            }
        });
    }

    //用户信息
    public void requesUserInfo(){
        new MyHttp().doPost(Api.getDefault().getUserInfo(UserUtils.getToken()), new HttpViewModelListener() {
            @Override
            public void onSuccess(JSONObject result) {
                UserInfoBean userInfoBean = new Gson().fromJson(result.toString(),UserInfoBean.class);
                mutableLiveData.setValue(userInfoBean);
            }

            @Override
            public void onError(int code) {

            }
        });
    }
}
