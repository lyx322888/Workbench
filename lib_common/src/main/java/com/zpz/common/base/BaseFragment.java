package com.zpz.common.base;

import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.zpz.common.callback.loadsir.EmptyCallback;
import com.zpz.common.callback.loadsir.LoadingCallback;
import com.zpz.common.callback.loadsir.TimeoutCallback;
import com.zpz.common.utils.ToastUitl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseFragment<VM extends BaseViewModel>extends Fragment {
    private ViewModelProvider mFragmentProvider;
    private ViewModelProvider mActivityProvider;
    private ViewDataBinding mBinding;
    protected VM viewModel;
    protected LoadService loadService;

    protected abstract DataBindingConfig getDataBindingConfig();
    protected abstract void init();
    protected abstract void initViewObservable();
    protected AppCompatActivity mActivity;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (AppCompatActivity) context;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViewModel();
        DataBindingConfig dataBindingConfig = getDataBindingConfig();
        //TODO tip: DataBinding 严格模式：
        // 将 DataBinding 实例限制于 base 页面中，默认不向子类暴露，
        // 通过这样的方式，来彻底解决 视图调用的一致性问题，
        // 如此，视图刷新的安全性将和基于函数式编程的 Jetpack Compose 持平。

        // 如果这样说还不理解的话，详见 https://xiaozhuanlan.com/topic/9816742350 和 https://xiaozhuanlan.com/topic/2356748910

        ViewDataBinding binding = DataBindingUtil.inflate(inflater, dataBindingConfig.getLayout(), container, false);
        binding.setLifecycleOwner(this);
        SparseArray bindingParams = dataBindingConfig.getBindingParams();
        for (int i = 0, length = bindingParams.size(); i < length; i++) {
            binding.setVariable(bindingParams.keyAt(i), bindingParams.valueAt(i));
        }
        mBinding = binding;
        loadService = LoadSir.getDefault().register(binding.getRoot());
        loadService.showSuccess();
        baseHttpViewObservable();
        init();
        initViewObservable();

        return loadService.getLoadLayout();
    }

    protected ViewDataBinding getBinding() {
        return mBinding;
    }
    //初始化viewmodel
    protected  void initViewModel(){
        if (viewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            viewModel = (VM) getFragmentViewModel( modelClass);
        }
    }
    //基础Observable监听
    private void baseHttpViewObservable(){
        //空数据
        viewModel.isShowEmpty.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    loadService.showCallback(EmptyCallback.class);
                }
            }
        });
        //加载中
        viewModel.isShowLoading.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    loadService.showCallback(LoadingCallback.class);
                }
            }
        });
        //网络错误
        viewModel.isShowTimeout.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    loadService.showCallback(TimeoutCallback.class);
                }
            }
        });
        //显示内容
        viewModel.isShowSuccess.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    loadService.showSuccess();
                }
            }
        });
    }

    protected <T extends ViewModel> T getFragmentViewModel(@NonNull Class<T> modelClass) {
        if (mFragmentProvider == null) {
            mFragmentProvider = new ViewModelProvider(this);
        }
        return mFragmentProvider.get(modelClass);
    }

    protected <T extends ViewModel> T getActivityViewModel(@NonNull Class<T> modelClass) {
        if (mActivityProvider == null) {
            mActivityProvider = new ViewModelProvider(mActivity);
        }
        return mActivityProvider.get(modelClass);
    }

    protected void showShortToast(String msg) {
        ToastUitl.showShort(msg);
    }
}
