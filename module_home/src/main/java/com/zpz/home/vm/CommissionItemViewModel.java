package com.zpz.home.vm;

import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.zpz.common.api.Api;
import com.zpz.common.api.MyHttp;
import com.zpz.common.base.BaseViewModel;
import com.zpz.common.utils.UserUtils;
import com.zpz.home.baen.CommissionItemBean;

import java.util.HashMap;
import java.util.List;

//初审
public class CommissionItemViewModel extends BaseViewModel {

    private MutableLiveData<List<CommissionItemBean.DataBean>> comissionlivedta;

    public MutableLiveData<List<CommissionItemBean.DataBean>> getComissionlivedta() {
        if (comissionlivedta ==null){
            comissionlivedta = new MutableLiveData<>();
        }
        return comissionlivedta;
    }

    public void requesfirstTria(String status){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("page",page);
        hashMap.put("login_token", UserUtils.getToken());
        hashMap.put("num",10);
        hashMap.put("type",status);
        MyHttp.doPost(Api.getDefault().listBacklog(hashMap), new HttpViewModelListener() {
            @Override
            public void onSuccess(JSONObject result) {
                CommissionItemBean firstTriaBean = new Gson().fromJson(result.toString(),CommissionItemBean.class);
                comissionlivedta.setValue(manageList(comissionlivedta.getValue(),firstTriaBean.getData()));
            }
        });
    }
}
