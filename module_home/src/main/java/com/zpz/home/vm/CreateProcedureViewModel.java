package com.zpz.home.vm;

import android.text.TextUtils;
import androidx.lifecycle.MutableLiveData;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.zpz.common.api.Api;
import com.zpz.common.api.HttpListener;
import com.zpz.common.api.MyHttp;
import com.zpz.common.base.ToolBarViewModel;
import com.zpz.common.utils.ToastUitl;
import com.zpz.common.utils.UserUtils;
import com.zpz.home.baen.CreteProceddureBean;

import java.util.HashMap;

public class CreateProcedureViewModel extends ToolBarViewModel {
    @Override
    protected void setTitle() {
        title.set("企业建档");
    }
    private MutableLiveData<CreteProceddureBean.DataBean> creteProceddure;

    public MutableLiveData<CreteProceddureBean.DataBean> getCreteProceddure() {
        if (creteProceddure ==null){
            creteProceddure = new MutableLiveData<>();
            creteProceddure.setValue(new CreteProceddureBean.DataBean());
        }
        return creteProceddure;
    }

    //获取档案信息
    public void requesCreteProceddure(long company_id,long first_assess_id){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("company_id",company_id);
        hashMap.put("first_assess_id",first_assess_id);
        hashMap.put("login_token", UserUtils.getToken());
        new MyHttp().doPost(Api.getDefault().getCreateRecordInfo(hashMap), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {
                CreteProceddureBean creteProceddureBean = new Gson().fromJson(result.toString(),CreteProceddureBean.class);
                creteProceddure.setValue(creteProceddureBean.getData());

            }

            @Override
            public void onError(int code) {
                super.onError(code);
            }
        });
    }

    //建档
    public void requesSubmit(long company_id,long first_assess_id){
        if (creteProceddure.getValue()==null){
            ToastUitl.showShort("请填写公司信息");
            return;
        }else if (TextUtils.isEmpty(creteProceddure.getValue().getCompany_name())){
            ToastUitl.showShort("请填写公司名称");
            return;
        }else if (TextUtils.isEmpty(creteProceddure.getValue().getLogo())){
            ToastUitl.showShort("请上传logo");
            return;
        }else if (TextUtils.isEmpty(creteProceddure.getValue().getLegal_person())){
            ToastUitl.showShort("请填写法定代表人");
            return;
        }else if (TextUtils.isEmpty(creteProceddure.getValue().getRegistered_capital())){
            ToastUitl.showShort("请填写注册资本");
            return;
        } else if (TextUtils.isEmpty(creteProceddure.getValue().getEstablish_date())){
            ToastUitl.showShort("请填写成立日期");
            return;
        }else if (TextUtils.isEmpty(creteProceddure.getValue().getAddress())){
            ToastUitl.showShort("请填写公司地址");
            return;
        } else if (TextUtils.isEmpty(creteProceddure.getValue().getIntroduce_text())){
            ToastUitl.showShort("请填写公司介绍");
            return;
        }else if (TextUtils.isEmpty(creteProceddure.getValue().getIntroduce_video())){
            ToastUitl.showShort("请上传公司介绍视频");
            return;
        }

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("login_token",UserUtils.getToken());
        hashMap.put("company_id",company_id);
        hashMap.put("first_assess_id",first_assess_id);
        hashMap.put("company_name",creteProceddure.getValue().getCompany_name());
        hashMap.put("legal_person",creteProceddure.getValue().getLegal_person());
        hashMap.put("logo",creteProceddure.getValue().getLogo());
        hashMap.put("registered_capital",creteProceddure.getValue().getRegistered_capital());
        hashMap.put("establish_date",creteProceddure.getValue().getEstablish_date());
        hashMap.put("address",creteProceddure.getValue().getAddress());
        hashMap.put("lat",creteProceddure.getValue().getLat());
        hashMap.put("lng",creteProceddure.getValue().getLng());
        hashMap.put("introduce_video",creteProceddure.getValue().getIntroduce_video());
        hashMap.put("introduce_text",creteProceddure.getValue().getIntroduce_text());
        //工作环境
        StringBuilder work_environment_img = new StringBuilder();
        for (int i = 0; i < creteProceddure.getValue().getWork_environment_img().size(); i++) {
            work_environment_img.append(creteProceddure.getValue().getWork_environment_img().get(i)).append(",");
        }
        if (work_environment_img.length()>1){
            work_environment_img.deleteCharAt(work_environment_img.length()-1);
        }
        hashMap.put("work_environment_img",work_environment_img);
        //荣誉
        StringBuilder enterprise_honor_img = new StringBuilder();
        for (int i = 0; i < creteProceddure.getValue().getEnterprise_honor_img().size(); i++) {
            enterprise_honor_img.append(creteProceddure.getValue().getEnterprise_honor_img().get(i)).append(",");
        }
        if (enterprise_honor_img.length()>1){
            enterprise_honor_img.deleteCharAt(enterprise_honor_img.length()-1);
        }
        hashMap.put("enterprise_honor_img",enterprise_honor_img);
        //合约
        StringBuilder keep_faith_contract_img = new StringBuilder();
        for (int i = 0; i < creteProceddure.getValue().getKeep_faith_contract_img().size(); i++) {
            keep_faith_contract_img.append(creteProceddure.getValue().getKeep_faith_contract_img().get(i)).append(",");
        }
        if (keep_faith_contract_img.length()>1){
            keep_faith_contract_img.deleteCharAt(keep_faith_contract_img.length()-1);
        }
        hashMap.put("keep_faith_contract_img",keep_faith_contract_img);
        new MyHttp().doPost(Api.getDefault().createRecord(hashMap), new HttpListener() {
            @Override
            public void onSuccess(JSONObject result) {

            }
        });
    }
}
