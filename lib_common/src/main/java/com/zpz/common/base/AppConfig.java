package com.zpz.common.base;


public class AppConfig {
    public static final String apptype = "android";
    public static final String LOGIN_STATE = "loginState";//登录状态
    public static final String LOGIN_TOKEN = "token";//token
    public static final String WX_APP_ID = "wxd6b58bada06d588f";
    public static final String WX_APP_SECRET = "d4e18fd38227f81749ee509efa8ac13f";
    public static final String LOGIN_MD5 = "qyxy888";//密码加密所需
    public static final String APPPROVIDER = "com.zpz.common.provider";//provider  7.0读取文件所需
    public static final String IMAGES = "http://img.qygs.org.cn/";
    public static final String STUDYVIDEO = "http://api.qygs.org.cn/workerapi/htmlpage/study_video";//学习课堂
    public static final String MATERIAL = "http://api.qygs.org.cn/workerapi/htmlpage/study_datum";//资料下载
    public static final String COMPANYDETAILS = "http://qygs.org.cn/wap/company/index?company_id=";//企业详情
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
