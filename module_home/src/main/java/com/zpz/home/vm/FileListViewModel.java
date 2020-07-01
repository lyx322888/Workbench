package com.zpz.home.vm;

import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.zpz.common.api.Api;
import com.zpz.common.api.HttpListener;
import com.zpz.common.api.MyHttp;
import com.zpz.common.base.ToolBarViewModel;
import com.zpz.common.utils.UserUtils;
import com.zpz.home.baen.FileListBean;

import java.util.HashMap;
import java.util.List;

public class FileListViewModel extends ToolBarViewModel {
    @Override
    protected void setTitle() {
        title.set("档案管理");
    }
    private MutableLiveData<List<FileListBean.DataBean>> filelistlivedata;

    public MutableLiveData<List<FileListBean.DataBean>> getFilelistlivedata() {
        if (filelistlivedata ==null){
            filelistlivedata = new MutableLiveData<>();
        }
        return filelistlivedata;
    }

    public void requesListCompanyRecord(){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("page",page);
        hashMap.put("login_token", UserUtils.getToken());
        hashMap.put("num",10);
        new MyHttp().doPost(Api.getDefault().listCompanyRecord(hashMap), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                FileListBean fileListBean = new Gson().fromJson(result.toString(),FileListBean.class);
                if (page==1){
                    filelistlivedata.setValue(fileListBean.getData());
                }else {
                    List<FileListBean.DataBean>list = filelistlivedata.getValue();
                    list.addAll(fileListBean.getData());
                    filelistlivedata.setValue(list);
                }
            }

            @Override
            public void onError(int code) {
                super.onError(code);
                if (page!=1){
                    page-=1;
                }
            }
        });
    }
}
