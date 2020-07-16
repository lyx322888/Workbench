package com.zpz.home.vm;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.zpz.common.api.Api;
import com.zpz.common.api.MyHttp;
import com.zpz.common.base.BaseViewModel;
import com.zpz.common.utils.UserUtils;
import com.zpz.home.baen.ListBonusBean;

import java.util.HashMap;
import java.util.List;

//分红明细
public class DetailAccountViewModel extends BaseViewModel {
    public ObservableField<String> all_bonus = new ObservableField<>();
    private MutableLiveData<List<ListBonusBean.DataBean.WorkerUserMoneyLogBean>> listLiveData;

    public MutableLiveData<List<ListBonusBean.DataBean.WorkerUserMoneyLogBean>> getListLiveData() {
        if (listLiveData ==null){
            listLiveData = new MutableLiveData<>();
        }
        return listLiveData;
    }

    public void requesListBonus( ){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("page",page);
        hashMap.put("login_token", UserUtils.getToken());
        hashMap.put("num",10);
        MyHttp.doPost(Api.getDefault().listBonus(hashMap), new HttpViewModelListener() {
            @Override
            public void onSuccess(JSONObject result) {
                ListBonusBean fileListBean = new Gson().fromJson(result.toString(),ListBonusBean.class);
                listLiveData.setValue(manageList(listLiveData.getValue(),fileListBean.getData().getWorker_user_money_log()));
                all_bonus.set(fileListBean.getData().getAll_bonus());
            }
        });
    }
}
