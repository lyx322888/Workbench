package com.zpz.home.vm;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.zpz.common.base.BaseApplication;
import com.zpz.common.base.ToolBarViewModel;
import com.zpz.common.utils.APPUtil;
import com.zpz.common.utils.UserUtils;

public class SetingModel extends ToolBarViewModel {
    private MutableLiveData<Boolean> loginState ;
    public ObservableField<String> versionName = new ObservableField<>();
    {
        versionName.set(APPUtil.getVersionName(BaseApplication.getInstance()));
        title.set("设置");
    }

    public LiveData<Boolean> getLoginstate(){
        if (loginState==null){
            loginState = new MutableLiveData<>();
        }
        return loginState;
    }
    //退出登录
    public void requesOutLogin(){
        UserUtils.saveLoginState(false);
        if (loginState!=null){
            loginState.setValue(false);
        }
    }

}
