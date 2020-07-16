package com.zpz.home.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.zpz.common.api.Api;
import com.zpz.common.api.MyHttp;
import com.zpz.common.base.BaseViewModel;
import com.zpz.common.utils.UserUtils;
import com.zpz.home.baen.CertificateBean;

public class CertificateViewModel extends BaseViewModel {
    private MutableLiveData<CertificateBean.DataBean> liveData;

    public LiveData<CertificateBean.DataBean> getLiveData() {
        if (liveData==null){
            liveData = new MutableLiveData<>();
        }
        return liveData;
    }

    public void requesCertificate(long company_id){
        MyHttp.doPost(Api.getDefault().getCertificateInfo(UserUtils.getToken(), company_id), new HttpViewModelListener() {
            @Override
            public void onSuccess(JSONObject result) {
                CertificateBean bean = new Gson().fromJson(result.toString(),CertificateBean.class);
                liveData.setValue(bean.getData());
            }
        });
    }
}
