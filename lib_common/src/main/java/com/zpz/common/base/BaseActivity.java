package com.zpz.common.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.yanzhenjie.sofia.Sofia;
import com.zpz.common.R;
import com.zpz.common.callback.loadsir.EmptyCallback;
import com.zpz.common.callback.loadsir.LoadingCallback;
import com.zpz.common.callback.loadsir.TimeoutCallback;
import com.zpz.common.databinding.ActivityBaseTitleBinding;
import com.zpz.common.utils.AppManager;
import com.zpz.common.utils.CommonUtils;
import com.zpz.common.utils.ToastUitl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseActivity <VM extends BaseViewModel>extends AppCompatActivity {
    protected VM viewModel;
    private CompositeDisposable compositeDisposable ;//统一管理Rx的Disposable
    protected Context mContext;
    private ViewDataBinding mbinding;
    private ViewModelProvider mActivityProvider;
    protected Activity mActivity;
    protected ActivityBaseTitleBinding baseTitleBinding;
    protected LoadService loadService;

    protected abstract DataBindingConfig getDataBindingConfig();
    protected abstract void init();
    protected abstract void initViewObservable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        mContext = this;
        mActivity = this;


        initViewModel();
        initBinding();
        setStatubarColor();
        baseHttpViewObservable();
        init();
        initViewObservable();
    }

    //绑定binding
    protected void initBinding(){
        DataBindingConfig dataBindingConfig = getDataBindingConfig();
        baseTitleBinding = DataBindingUtil.setContentView(this,R.layout.activity_base_title);
        initTitleBar();

        //页面binding
        ViewDataBinding binding = DataBindingUtil.inflate(getLayoutInflater(), dataBindingConfig.getLayout(),baseTitleBinding.baseContent,true);
        binding.setLifecycleOwner(this);
        SparseArray bindingParams = dataBindingConfig.getBindingParams();
        for (int i = 0, length = bindingParams.size(); i < length; i++) {
            binding.setVariable(bindingParams.keyAt(i), bindingParams.valueAt(i));
        }
        mbinding = binding;
        //页面状态
        loadService = LoadSir.getDefault().register(mbinding.getRoot());
        loadService.showSuccess();
    }
    //设置标题
    public void setTitle(String title){
        baseTitleBinding.tvTitle.setText(title);
    }
    //隐藏标题栏
    public void hideTitleBar(){
        baseTitleBinding.toolbar.setVisibility(View.GONE);
    }
    //初始化标题栏
    protected void initTitleBar(){
        setSupportActionBar(baseTitleBinding.toolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            //隐藏原本的标题
            actionBar.setDisplayShowTitleEnabled(false);
        }
        baseTitleBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    //设置状态栏
    protected void setStatubarColor(){
        Sofia.with(this).statusBarDarkFont().statusBarBackground(CommonUtils.getColor(R.color.white));
    }
    //
    protected void showShortToast(String msg) {
        ToastUitl.showShort(msg);
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
            viewModel = (VM) getActivityViewModel( modelClass);
        }
    }
    //基础Observable监听
    private void baseHttpViewObservable(){
        //空数据
        viewModel.isShowEmpty.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    loadService.showCallback(EmptyCallback.class);
                }
            }
        });
        //加载中
        viewModel.isShowLoading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    loadService.showCallback(LoadingCallback.class);
                    Log.e("dfdf", "showCallback: " );
                }
            }
        });
        //网络错误
        viewModel.isShowTimeout.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    loadService.showCallback(TimeoutCallback.class);
                }
            }
        });
        //显示内容
        viewModel.isShowSuccess.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    loadService.showSuccess();
                }
            }
        });
    }

    /**
     * TODO tip: 警惕使用。非必要情况下，尽可能不在子类中拿到 binding 实例乃至获取 view 实例。使用即埋下隐患。
     * <p>
     * @return
     */
    protected ViewDataBinding getBinding() {
        return mbinding;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销viewModel里面的异步
        viewModel.onCleared();
        AppManager.getAppManager().removeActivity(this);
        //注销
        if (compositeDisposable!=null&&!compositeDisposable.isDisposed()){
            compositeDisposable.dispose();
        }
        //解绑
        if (mbinding !=null){
            mbinding.unbind();
        }
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left) || !(event.getX() < right)
                    || !(event.getY() > top) || !(event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
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

    protected <T extends ViewModel> T getActivityViewModel(@NonNull Class<T> modelClass) {
        if (mActivityProvider == null) {
            mActivityProvider = new ViewModelProvider(this);
        }
        return mActivityProvider.get(modelClass);
    }
}
