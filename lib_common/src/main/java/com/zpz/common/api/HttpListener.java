package com.zpz.common.api;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by wushenghui on 2017/6/20.
 */

public abstract class HttpListener {
    public abstract void onSuccess(JSONObject result);
    public   void onError(int code){};
    public void onFinish(){};
}
