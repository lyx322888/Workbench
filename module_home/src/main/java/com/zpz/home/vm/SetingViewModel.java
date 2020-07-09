package com.zpz.home.vm;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSONObject;
import com.zpz.common.api.Api;
import com.zpz.common.api.MyHttp;
import com.zpz.common.base.BaseApplication;
import com.zpz.common.base.BaseViewModel;
import com.zpz.common.utils.APPUtil;
import com.zpz.common.utils.CleanDataUtils;
import com.zpz.common.utils.UserUtils;

public class SetingViewModel extends BaseViewModel {
    private MutableLiveData<Boolean> loginState ;
    public ObservableField<String> versionName = new ObservableField<>();
    public ObservableField<String> cacheSize = new ObservableField<>();
    {
        versionName.set(APPUtil.getVersionName(BaseApplication.getInstance()));
        cacheSize.set(CleanDataUtils.getTotalCacheSize(BaseApplication.getInstance()));
    }

    public LiveData<Boolean> getLoginstate(){
        if (loginState==null){
            loginState = new MutableLiveData<>();
        }
        return loginState;
    }

    //退出登录
    public void requesOutLogin(){
        new MyHttp().doPost(Api.getDefault().logout(UserUtils.getToken()), new HttpViewModelListener() {
            @Override
            public void onSuccess(JSONObject result) {
                UserUtils.saveLoginState(false);
                if (loginState!=null){
                    loginState.setValue(false);
                }
            }
        });

    }
}
