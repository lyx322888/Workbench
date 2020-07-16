package com.zpz.home.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.zpz.common.api.Api;
import com.zpz.common.api.MyHttp;
import com.zpz.common.base.BaseViewModel;
import com.zpz.common.bean.CommissionCountBean;
import com.zpz.common.utils.UserUtils;

public class CommissionViewModel extends BaseViewModel {
    public String[] mTitles = new String[]{"待建档", "即将到期", "已到期"};

    private MutableLiveData<CommissionCountBean.DataBean> commissionCountLiveData;

    public LiveData<CommissionCountBean.DataBean> getCountBeanLiveData() {
        if (commissionCountLiveData ==null){
            commissionCountLiveData =  new MutableLiveData<>();
        }
        return commissionCountLiveData;
    }
    //统计
    public void requesBacklogCount(){
        MyHttp.doPost(Api.getDefault().getBacklogCount(UserUtils.getToken()), new HttpViewModelListener() {
            @Override
            public void onSuccess(JSONObject result) {
                CommissionCountBean firstAssessCountBean = new Gson().fromJson(result.toString(),CommissionCountBean.class);
                commissionCountLiveData.setValue(firstAssessCountBean.getData());
                showSuccess();
            }

            @Override
            public void onError(int code) {

            }
        });
    }
}
