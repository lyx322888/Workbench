package com.zpz.common.base;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.zpz.common.utils.ActivityManager;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseActivity<V extends ViewDataBinding> extends AppCompatActivity {
    protected V mbinding;
    private CompositeDisposable compositeDisposable ;//统一管理Rx的Disposable
    public Context mContext;
    protected Activity mActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mbinding = DataBindingUtil.setContentView(this,getLayoutId());
        mContext = this;
        mActivity = this;
        ActivityManager.addActivity(this);
        this.initView();
        this.fillData();
    }
    //获取布局文件
    public abstract int getLayoutId();
    //初始化view
    public abstract void initView();
    //加载网络数据
    public abstract void fillData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.removeActivity(this);
        //注销
        if (compositeDisposable!=null&&!compositeDisposable.isDisposed()){
            compositeDisposable.dispose();
        }
        //解绑
        if (mbinding !=null){
            mbinding.unbind();
        }
    }


    //添加disposable
    public void  addDisposable(Disposable disposable){
        if (compositeDisposable==null){
            compositeDisposable = new CompositeDisposable();
        }else {
            compositeDisposable.add(disposable);
        }
    }
}
