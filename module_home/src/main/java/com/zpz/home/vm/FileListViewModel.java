package com.zpz.home.vm;

import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.zpz.common.api.Api;
import com.zpz.common.api.MyHttp;
import com.zpz.common.base.BaseViewModel;
import com.zpz.common.utils.UserUtils;
import com.zpz.home.baen.FileListBean;

import java.util.HashMap;
import java.util.List;

public class FileListViewModel extends BaseViewModel {

    private MutableLiveData<List<FileListBean.DataBean>> filelistlivedata;

    public MutableLiveData<List<FileListBean.DataBean>> getFilelistlivedata() {
        if (filelistlivedata ==null){
            filelistlivedata = new MutableLiveData<>();
        }
        return filelistlivedata;
    }

    public void requesListCompanyRecord(String type){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("page",page);
        hashMap.put("type",type);
        hashMap.put("login_token", UserUtils.getToken());
        hashMap.put("num",10);
        MyHttp.doPost(Api.getDefault().listCompanyRecord(hashMap), new HttpViewModelListener() {
            @Override
            public void onSuccess(JSONObject result) {
                FileListBean fileListBean = new Gson().fromJson(result.toString(),FileListBean.class);
                filelistlivedata.setValue(manageList(filelistlivedata.getValue(),fileListBean.getData()));
            }
        });
    }
}
