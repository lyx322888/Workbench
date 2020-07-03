package com.zpz.common.api;



import com.alibaba.fastjson.JSONObject;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

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

    //退出
    @FormUrlEncoded
    @POST("/workerapi/user/logout")
    Observable<JSONObject> logout(@Field ("login_token") String loginToken);

    //获取用户登录信息
    @FormUrlEncoded
    @POST("/workerapi/user/get_user_info")
    Observable<JSONObject> getUserInfo(@Field ("login_token") String loginToken);

    //获取公告
    @FormUrlEncoded
    @POST("/workerapi/user/get_index_notice")
    Observable<JSONObject> getIndexNotice(@Field ("login_token") String loginToken);

    //建档
    @FormUrlEncoded
    @POST("/workerapi/user/create_record")
    Observable<JSONObject> createRecord(@FieldMap Map<String, Object> params);

    //获取初审列表统计
    @FormUrlEncoded
    @POST("/workerapi/user/get_first_assess_count")
    Observable<JSONObject> getFirstAssessCount(@Field ("login_token") String loginToken);

    //获取建档信息
    @FormUrlEncoded
    @POST("/workerapi/user/get_create_record_info")
    Observable<JSONObject> getCreateRecordInfo(@FieldMap Map<String, Object> params);

    //获取初审列表
    @FormUrlEncoded
    @POST("/workerapi/user/list_first_assess")
    Observable<JSONObject> listFirstAssess(@FieldMap Map<String, Object> params);

    //档案列表
    @FormUrlEncoded
    @POST("/workerapi/user/list_company_record")
    Observable<JSONObject> listCompanyRecord(@FieldMap Map<String, Object> params);

    //待办列表
    @FormUrlEncoded
    @POST("/workerapi/user/list_backlog")
    Observable<JSONObject> listBacklog(@FieldMap Map<String, Object> params);

    //待办列表计数
    @FormUrlEncoded
    @POST("/workerapi/user/get_backlog_count")
    Observable<JSONObject> getBacklogCount(@Field ("login_token") String loginToken);

}
