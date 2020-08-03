package com.zpz.common.vm;

import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.zpz.common.api.Api;
import com.zpz.common.api.MyHttp;
import com.zpz.common.base.BaseViewModel;
import com.zpz.common.bean.FirsTrialShareBean;
import com.zpz.common.utils.UserUtils;

public class FirsTrialViewShareModel extends BaseViewModel {
    public MutableLiveData<FirsTrialShareBean.DataBean> data = new MutableLiveData<>();
    public void requesShare(){
        MyHttp.doPost(Api.getDefault().get_share_first_assess_url(UserUtils.getToken()), new HttpViewModelListener() {
            @Override
            public void onSuccess(JSONObject result) {
                FirsTrialShareBean firsTrialShareBean = new Gson().fromJson(result.toString(),FirsTrialShareBean.class);
                data.setValue(firsTrialShareBean.getData());
            }
        });
    }
}
