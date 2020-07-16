package com.zpz.common.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.zpz.common.api.Api;
import com.zpz.common.api.MyHttp;
import com.zpz.common.base.AppConfig;
import com.zpz.common.base.BaseViewModel;
import com.zpz.common.bean.VersionBean;
//版本信息
public class VersionViewModel extends BaseViewModel {
    private MutableLiveData<VersionBean.DataBean> versionliveData;

    public LiveData<VersionBean.DataBean> getVersionliveData() {
        if (versionliveData==null){
            versionliveData = new MutableLiveData<>();
        }
        return versionliveData;
    }

    public void requesVersionInfo(int versionCode){
        MyHttp.doPost(Api.getDefault().getVersion(AppConfig.apptype, versionCode), new HttpViewModelListener() {
            @Override
            public void onSuccess(JSONObject result) {
                VersionBean versionBean = new Gson().fromJson(result.toString(),VersionBean.class);
                versionliveData.setValue(versionBean.getData());
            }
        });
    }
}
