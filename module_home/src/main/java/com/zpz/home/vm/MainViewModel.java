package com.zpz.home.vm;

import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.zpz.common.api.Api;
import com.zpz.common.api.HttpListener;
import com.zpz.common.api.MyHttp;
import com.zpz.common.base.BaseViewModel;
import com.zpz.common.bean.HomeGg;
import com.zpz.common.bean.UserInfoBean;
import com.zpz.common.utils.InfoUtils;


public class MainViewModel extends BaseViewModel {
    public MutableLiveData<UserInfoBean> mutableLiveData = new MutableLiveData<>();
    public MutableLiveData<HomeGg> homeGgMutableLiveData = new MutableLiveData<>();

    public void requesIndexNotice(){
        new MyHttp().doPost(Api.getDefault().getIndexNotice(InfoUtils.getToken()), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                HomeGg homeGg = new Gson().fromJson(result.toString(),HomeGg.class);
                homeGgMutableLiveData.setValue(homeGg);
            }
        });
    }
}
