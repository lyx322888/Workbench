package com.zpz.home.vm;

import androidx.lifecycle.MutableLiveData;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.zpz.common.api.Api;
import com.zpz.common.api.HttpListener;
import com.zpz.common.api.MyHttp;
import com.zpz.common.base.BaseViewModel;
import com.zpz.common.utils.UserUtils;
import com.zpz.home.baen.FirstTriaBean;

import java.util.HashMap;
import java.util.List;
//初审
public class FirstTrialFmViewModel extends BaseViewModel {
    public int page = 1;
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
        hashMap.put("login_token", UserUtils.getToken());
        hashMap.put("num",10);
        hashMap.put("status",status);
        new MyHttp().doPost(Api.getDefault().listFirstAssess(hashMap), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                FirstTriaBean firstTriaBean = new Gson().fromJson(result.toString(),FirstTriaBean.class);
                    if (page==1){
                        firstTriaLiveData.setValue(firstTriaBean.getData());
                    }else {
                       List<FirstTriaBean.DataBean>list = firstTriaLiveData.getValue();
                       list.addAll(firstTriaBean.getData());
                        firstTriaLiveData.setValue(list);
                    }
            }
        });
    }
}
