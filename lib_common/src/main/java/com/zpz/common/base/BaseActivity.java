package com.zpz.common.base;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.yanzhenjie.sofia.Sofia;
import com.zpz.common.R;
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
    public Context mContext;
    private ViewDataBinding mbinding;
    private ViewModelProvider mActivityProvider;
    protected Activity mActivity;
    protected abstract void init();
    protected abstract void initViewObservable();
    protected abstract DataBindingConfig getDataBindingConfig();
    protected void settingTitle(){};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mActivity = this;
        initViewModel();
        initBinding();
        setStatubarColor();
        AppManager.getAppManager().addActivity(this);
        settingTitle();
        init();
        initViewObservable();
    }
    //绑定binding
    private void initBinding(){
        DataBindingConfig dataBindingConfig = getDataBindingConfig();
        ViewDataBinding binding = DataBindingUtil.setContentView(this, dataBindingConfig.getLayout());
        binding.setLifecycleOwner(this);
        SparseArray bindingParams = dataBindingConfig.getBindingParams();
        for (int i = 0, length = bindingParams.size(); i < length; i++) {
            binding.setVariable(bindingParams.keyAt(i), bindingParams.valueAt(i));
        }
        mbinding = binding;
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
        //标题栏
        if ((viewModel instanceof ToolBarViewModel)){
            ((ToolBarViewModel)viewModel).getOnBackPressedEvent().observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean b) {
                    mActivity.onBackPressed();
                }
            });
        }
    }

    /**
     * TODO tip: 警惕使用。非必要情况下，尽可能不在子类中拿到 binding 实例乃至获取 view 实例。使用即埋下隐患。
     * 目前的方案是在 debug 模式下，对获取实例的情况给予提示。
     * <p>
     * @return
     */
    protected ViewDataBinding getBinding() {
        return mbinding;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
