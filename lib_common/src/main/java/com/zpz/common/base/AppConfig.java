package com.zpz.common.base;


public class AppConfig {
    public static final String apptype = "android";
    public static final String PUSH_APP_ID = "loginToken";//保存登录Token
    public interface ERROR_STATE {
        int NULLDATA = 1001;//无数据
        int NO_NETWORK = 1002;//网络错误
        int SERVICE_ERROR = 1003;//加载错误
        int JURISDICTION  = 123;//权限受制
    }


}
