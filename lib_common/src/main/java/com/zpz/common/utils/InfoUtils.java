package com.zpz.common.utils;

import com.zpz.common.base.AppConfig;
import com.zpz.common.base.BaseApplication;

//储存用户信息处理
public class InfoUtils {
    public static String getToken(){
        return SPUtils.getSharedStringData(BaseApplication.getInstance(), AppConfig.LOGIN_TOKEN);
    }
}
