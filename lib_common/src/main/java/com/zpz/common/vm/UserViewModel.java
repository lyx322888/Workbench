package com.zpz.common.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.zpz.common.api.Api;
import com.zpz.common.api.HttpListener;
import com.zpz.common.api.MyHttp;
import com.zpz.common.base.BaseViewModel;
import com.zpz.common.bean.UserInfoBean;

public class UserViewModel extends BaseViewModel {
    private MutableLiveData<UserInfoBean> userLiveData;
    public LiveData<UserInfoBean> getUserInfo(){
        if (userLiveData==null){
            userLiveData = new MutableLiveData<>();
        }
        return userLiveData;
    }
    public void requesUserInfo(String loginToken){
        new MyHttp().doPost(Api.getDefault().getUserInfo(loginToken), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                UserInfoBean userInfoBean = new Gson().fromJson(result.toString(),UserInfoBean.class);
                userLiveData.setValue(userInfoBean);
            }
        });
    }
}