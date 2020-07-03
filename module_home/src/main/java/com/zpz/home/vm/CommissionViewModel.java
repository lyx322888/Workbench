package com.zpz.home.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.zpz.common.api.Api;
import com.zpz.common.api.HttpListener;
import com.zpz.common.api.MyHttp;
import com.zpz.common.base.ToolBarViewModel;
import com.zpz.common.utils.UserUtils;
import com.zpz.home.baen.CommissionCountBean;

public class CommissionViewModel extends ToolBarViewModel {
    public String[] mTitles = new String[]{"待建档", "即将到期", "已到期"};

    @Override
    protected void setTitle() {
        title.set("待办");
        backvisibility.set(false);
    }

    private MutableLiveData<CommissionCountBean> commissionCountLiveData;

    public LiveData<CommissionCountBean> getCountBeanLiveData() {
        if (commissionCountLiveData ==null){
            commissionCountLiveData =  new MutableLiveData<>();
        }
        return commissionCountLiveData;
    }

    public void requesFirstAssessCount(){
        new MyHttp().doPost(Api.getDefault().getBacklogCount(UserUtils.getToken()), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                CommissionCountBean firstAssessCountBean = new Gson().fromJson(result.toString(),CommissionCountBean.class);
                commissionCountLiveData.setValue(firstAssessCountBean);
            }
        });
    }
}
