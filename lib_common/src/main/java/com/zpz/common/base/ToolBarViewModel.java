package com.zpz.common.base;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

public class ToolBarViewModel extends BaseViewModel{
    //返回键监听
    private MutableLiveData<Boolean> onBackPressedEvent;
    public ObservableField<String> title  = new ObservableField<>();

    public MutableLiveData<Boolean> getOnBackPressedEvent() {
        if (onBackPressedEvent==null){
            onBackPressedEvent = new MutableLiveData<>();
        }
        return onBackPressedEvent;
    }
}
