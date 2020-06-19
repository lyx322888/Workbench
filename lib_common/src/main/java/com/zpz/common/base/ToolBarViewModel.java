package com.zpz.common.base;

import androidx.lifecycle.MutableLiveData;

public class ToolBarViewModel extends BaseViewModel{
    private MutableLiveData<Boolean> onBackPressedEvent;

    public MutableLiveData<Boolean> getOnBackPressedEvent() {
        if (onBackPressedEvent==null){
            onBackPressedEvent = new MutableLiveData<>();
        }
        return onBackPressedEvent;
    }
}
