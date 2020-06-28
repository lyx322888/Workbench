package com.zpz.common.base;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

public abstract class ToolBarViewModel extends BaseViewModel{

    //返回键监听
    private MutableLiveData<Boolean> onBackPressedEvent;
    //右键
    private MutableLiveData<Boolean> onRightEvent;
    public ObservableField<String> title  = new ObservableField<>();
    public ObservableField<String> rightTv  = new ObservableField<>();
    public ObservableBoolean rightTvVisible  = new ObservableBoolean();

    public MutableLiveData<Boolean> getOnBackPressedEvent() {
        if (onBackPressedEvent==null){
            onBackPressedEvent = new MutableLiveData<>();
        }
        return onBackPressedEvent;

    } public MutableLiveData<Boolean> getOnRightEvent() {
        if (onRightEvent==null){
            onRightEvent = new MutableLiveData<>();
        }
        return onRightEvent;
    }
    {
        setTitle();
    }
    protected abstract void setTitle();
}
