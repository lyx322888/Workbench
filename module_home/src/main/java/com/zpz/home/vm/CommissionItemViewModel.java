package com.zpz.home.vm;

import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.zpz.common.api.Api;
import com.zpz.common.api.HttpListener;
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

    public void requesfirstTria(int status){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("page",page);
        hashMap.put("login_token", UserUtils.getToken());
        hashMap.put("num",10);
        hashMap.put("type",status);
        new MyHttp().doPost(Api.getDefault().listBacklog(hashMap), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                CommissionItemBean firstTriaBean = new Gson().fromJson(result.toString(),CommissionItemBean.class);
                if (page==1){
                    comissionlivedta.setValue(firstTriaBean.getData());
                }else {
                    List<CommissionItemBean.DataBean>list = comissionlivedta.getValue();
                    list.addAll(firstTriaBean.getData());
                    comissionlivedta.setValue(list);
                }
            }

            @Override
            public void onError(int code) {
                super.onError(code);
                if (page!=1){
                    page-=1;
                }
            }
        });
    }
}
