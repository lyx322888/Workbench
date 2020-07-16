package com.zpz.home.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.zpz.common.api.Api;
import com.zpz.common.api.MyHttp;
import com.zpz.common.base.BaseViewModel;
import com.zpz.common.utils.UserUtils;
import com.zpz.home.baen.FirstAssessCountBean;

public class FirstTrialViewModel extends BaseViewModel {


    public String[] mTitles = new String[]{"审核中", "已通过", "未通过"};

    private MutableLiveData<FirstAssessCountBean> firstAssessCountLiveData;

    public LiveData<FirstAssessCountBean> getFirstAssessCountLiveData() {
        if (firstAssessCountLiveData ==null){
            firstAssessCountLiveData =  new MutableLiveData<>();
        }
        return firstAssessCountLiveData;
    }

    public void requesFirstAssessCount(){
        MyHttp.doPost(Api.getDefault().getFirstAssessCount(UserUtils.getToken()), new HttpViewModelListener() {
            @Override
            public void onSuccess(JSONObject result) {
                FirstAssessCountBean firstAssessCountBean = new Gson().fromJson(result.toString(),FirstAssessCountBean.class);
                firstAssessCountLiveData.setValue(firstAssessCountBean);
                showSuccess();
            }
        });
    }
}
