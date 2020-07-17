package com.zpz.common.base;

import android.view.View;
import android.webkit.JavascriptInterface;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.download.library.DownloadImpl;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.zpz.common.R;
import com.zpz.common.databinding.ActivityBaseWebBinding;
import com.zpz.common.dialog.DownFileDialog;

@Route(path = MyARouter.BaseWebActivity)
public class BaseWebActivity extends BaseActivity<BaseViewModel> {
    ActivityBaseWebBinding baseWebBinding;
    @Autowired(name = "url")
    String url;
    @Autowired(name = "title")
    String title;
    private WebView webView;

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_base_web);
    }

    @Override
    protected void init() {
        ARouter.getInstance().inject(this);
        setTitle(title);
        baseWebBinding = (ActivityBaseWebBinding) getBinding();
        baseTitleBinding.ivClose.setVisibility(View.VISIBLE);
        baseTitleBinding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        webView = baseWebBinding.web;
        webView.getSettings().setBlockNetworkImage(false);//解决图片不显示
        webView.getSettings().setLoadsImagesAutomatically(true); //支持自动加载图片
        webView.getSettings().setDefaultTextEncodingName("utf-8");//设置编码格式

        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setGeolocationEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setUseWideViewPort(true); // 关键点
        webView.getSettings().setAllowFileAccess(true); // 允许访问文件
        webView.getSettings().setSupportZoom(false); // 支持缩放
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);

        // 设置与Js交互的权限
        webView.addJavascriptInterface(new InJavaScriptLocalObj(), "android");

        webView.loadUrl(url);
        webView.setWebViewClient(webViewClient);
        webView.setWebChromeClient(webChromeClient);
    }

    @Override
    protected void initViewObservable() {

    }

    @Override
    protected void onLoadData() {

    }

    private WebViewClient webViewClient = new WebViewClient(){
        //防止调起手机浏览器
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

    };

    private WebChromeClient webChromeClient = new WebChromeClient(){
        @Override
        public void onProgressChanged(WebView webView, int i) {
            if (i==100){
            baseWebBinding.progressBar.setVisibility(View.GONE);
            return;
            }
            baseWebBinding.progressBar.setVisibility(View.VISIBLE);
            baseWebBinding.progressBar.setProgress(i);
        }
    };

    @Override
    protected void onDestroy() {
        webView.stopLoading();
        webView.removeAllViews();
        webView.destroy();
        webView = null;
        DownloadImpl.getInstance().cancelAll();
        super.onDestroy();
    }
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        }else {
            super.onBackPressed();
        }
    }

    final class InJavaScriptLocalObj {
        @JavascriptInterface
        public void downFile(String url){
            final DownFileDialog downFileDialog =DownFileDialog.newInstance(url);
            downFileDialog.show(getSupportFragmentManager(),"");
        }
    }

}

