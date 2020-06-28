package com.zpz.home.vm;

import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.zpz.common.api.Api;
import com.zpz.common.api.HttpListener;
import com.zpz.common.api.MyHttp;
import com.zpz.common.base.ToolBarViewModel;
import com.zpz.common.utils.UserUtils;
import com.zpz.home.baen.CreteProceddureBean;

import java.util.HashMap;

public class CreateProcedureViewModel extends ToolBarViewModel {
    @Override
    protected void setTitle() {
        title.set("企业建档");
    }
    private MutableLiveData<CreteProceddureBean> creteProceddure;

    public MutableLiveData<CreteProceddureBean> getCreteProceddure() {
        if (creteProceddure ==null){
            creteProceddure = new MutableLiveData<>();
        }
        return creteProceddure;
    }

    public void requesCreteProceddure(long company_id,long first_assess_id){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("company_id",company_id);
        hashMap.put("first_assess_id",first_assess_id);
        hashMap.put("login_token", UserUtils.getToken());
        new MyHttp().doPost(Api.getDefault().getCreateRecordInfo(hashMap), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                CreteProceddureBean creteProceddureBean = new Gson().fromJson(result.toString(),CreteProceddureBean.class);
                creteProceddure.setValue(creteProceddureBean);
            }
        });
    }
}
