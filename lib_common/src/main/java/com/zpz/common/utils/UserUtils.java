package com.zpz.common.utils;

import com.zpz.common.base.AppConfig;
import com.zpz.common.base.BaseApplication;

//储存用户信息处理
public class UserUtils {
    //获取token
    public static String getToken(){
        return SPUtils.getSharedStringData(BaseApplication.getInstance(), AppConfig.LOGIN_TOKEN);
    }
    //保存token
    public static void saveToken(String token){
        SPUtils.setSharedStringData(BaseApplication.getInstance(), AppConfig.LOGIN_TOKEN, token);

    }

    //获取账号
    public static String getMobile(){
        return SPUtils.getSharedStringData(BaseApplication.getInstance(), AppConfig.LOGIN_MOBILE);
    }
    //保存账号
    public static void saveMobile(String mobile){
        SPUtils.setSharedStringData(BaseApplication.getInstance(), AppConfig.LOGIN_MOBILE, mobile);

    }

    //获取登录状态
    public static Boolean getLoginState(){
        return SPUtils.getSharedBooleanData(BaseApplication.getInstance(), AppConfig.LOGIN_STATE);
    }

    //保存账号
    public static void saveLoginState(boolean state){
        SPUtils.setSharedBooleanData(BaseApplication.getInstance(), AppConfig.LOGIN_STATE, state);
    }
}
