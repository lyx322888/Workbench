package com.zpz.home.vm;

import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.zpz.common.api.Api;
import com.zpz.common.api.MyHttp;
import com.zpz.common.base.BaseViewModel;
import com.zpz.common.utils.UserUtils;
import com.zpz.home.baen.FirstTriaBean;

import java.util.HashMap;
import java.util.List;
//初审
public class FirstTrialFmViewModel extends BaseViewModel {
    private MutableLiveData<List<FirstTriaBean.DataBean>> firstTriaLiveData;
    public MutableLiveData<List<FirstTriaBean.DataBean>> getFirstTriaBeanMutableLiveData() {
        if (firstTriaLiveData ==null){
            firstTriaLiveData = new MutableLiveData<>();
        }
        return firstTriaLiveData;
    }

    public void requesfirstTria(int status){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("page",page);
        hashMap.put("status",status);
        hashMap.put("login_token", UserUtils.getToken());
        hashMap.put("num",10);
        MyHttp.doPost(Api.getDefault().listFirstAssess(hashMap), new HttpViewModelListener() {
            @Override
            public void onSuccess(JSONObject result) {
                FirstTriaBean firstTriaBean = new Gson().fromJson(result.toString(),FirstTriaBean.class);
                    firstTriaLiveData.setValue(manageList(firstTriaLiveData.getValue(),firstTriaBean.getData()));
            }
        });
    }

}
