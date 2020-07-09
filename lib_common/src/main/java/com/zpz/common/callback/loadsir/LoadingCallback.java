package com.zpz.common.callback.loadsir;

import com.kingja.loadsir.callback.Callback;
import com.zpz.common.R;

public class LoadingCallback extends Callback{
    @Override
    protected int onCreateView() {
        return R.layout.loadsir_loading;
    }
}
