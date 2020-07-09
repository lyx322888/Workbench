package com.zpz.main.vm;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.zpz.common.api.Api;
import com.zpz.common.api.MyHttp;
import com.zpz.common.base.AppConfig;
import com.zpz.common.base.BaseViewModel;
import com.zpz.common.bean.LoginBean;
import com.zpz.common.utils.UserUtils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import static com.zpz.common.utils.CommonUtils.EncoderByMd5;

public class LoginViewModel extends BaseViewModel {
    private MutableLiveData<LoginBean> loginBeanMutableLiveData ;
    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> password = new ObservableField<>();

    public MutableLiveData<LoginBean> getLoginBeanMutableLiveData() {
        if (loginBeanMutableLiveData==null){
            loginBeanMutableLiveData  = new MutableLiveData<>();
        }
        return loginBeanMutableLiveData;
    }
    {
        name.set(UserUtils.getMobile());
    }
    //登录请求
    public void requesLoginBean( ){
        HashMap<String, Object> params = new HashMap<>();
        String passwordParams = password.get();
        try {
            passwordParams = EncoderByMd5(passwordParams+ AppConfig.LOGIN_MD5);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        params.put("account",name.get());
        params.put("password",passwordParams);
        new MyHttp().doPost(Api.getDefault().login(params), new HttpViewModelListener() {
            @Override
            public void onSuccess(JSONObject result) {
                LoginBean loginBean = new Gson().fromJson(result.toString(), LoginBean.class);
                loginBeanMutableLiveData.setValue(loginBean);
            }
        });
    }
}
