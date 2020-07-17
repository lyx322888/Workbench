package com.zpz.common.base;


public class AppConfig {
    public static final String apptype = "android";
    public static final String LOGIN_STATE = "loginState";//登录状态
    public static final String LOGIN_TOKEN = "token";//token
    public static final String WX_APP_ID = "wxd6b58bada06d588f";
    public static final String WX_APP_SECRET = "d4e18fd38227f81749ee509efa8ac13f";
    public static final String LOGIN_MD5 = "qyxy888";//密码加密所需
    public static final String IMAGES = "http://img.qygs.org.cn/";
    public static final String CSSHARE = "https://qygs.org.cn/wap/firstassess/first_assess_one";
//    public static final String IMAGES = "http://qcoi6tyog.bkt.clouddn.com/";
    public static final String LOGIN_MOBILE = "loginMobile";//保存手机号码
    public interface ERROR_STATE {
        int NULLDATA = 1001;//无数据
        int NO_NETWORK = 1002;//网络错误
        int SERVICE_ERROR = 1003;//加载错误
        int JURISDICTION  = 123;//权限受制
    }

    public static class ThirdParty{
        public static String UMENGKEY = "5f0d49eb978eea08f5a149bb";
    }


}
