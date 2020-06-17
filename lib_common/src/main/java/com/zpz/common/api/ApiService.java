package com.zpz.common.api;



import com.alibaba.fastjson.JSONObject;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * des:ApiService
 * Created by wushenghui on 2017/4/23.
 */
public interface ApiService {
    //获取版本号
    @FormUrlEncoded
    @POST("/sjapi/version/get_version_info")
    Observable<JSONObject> getVersion(@Field("app_type") String appType, @Field("version_code") String versionCode);

    //登录
    @FormUrlEncoded
    @POST("/workerapi/user/login")
    Observable<JSONObject> login(@FieldMap Map<String, Object> params);

    //获取用户登录信息
    @FormUrlEncoded
    @POST("/workerapi/user/get_user_info")
    Observable<JSONObject> getUserInfo(@Field ("login_token") String loginToken);

    //获取公告
    @FormUrlEncoded
    @POST("/workerapi/user/get_index_notice")
    Observable<JSONObject> getIndexNotice(@Field ("login_token") String loginToken);

}
