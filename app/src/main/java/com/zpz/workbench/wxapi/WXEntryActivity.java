package com.zpz.workbench.wxapi;

import android.os.Bundle;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.umeng.socialize.weixin.view.WXCallbackActivity;
import com.zpz.common.R;
import com.zpz.common.utils.ToastUitl;

/**
 * 微信分享回调
 */

public class WXEntryActivity extends WXCallbackActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wx_entry);
    }

    @Override
    public void onReq(BaseReq req) {
        super.onReq(req);
    }

    @Override
    public void onResp(BaseResp resp) {
        super.onResp(resp);
        switch (resp.getType()) {
            case 1:
                //授权

                break;
            case 2:
                //分享 收藏等
                if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
                    ToastUitl.showShort("成功");
                    finish();
                } else {
                    ToastUitl.showShort("失败");
                    finish();
                }
                break;
        }
    }

}