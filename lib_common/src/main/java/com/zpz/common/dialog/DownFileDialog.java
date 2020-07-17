package com.zpz.common.dialog;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.download.library.DownloadImpl;
import com.download.library.DownloadListenerAdapter;
import com.download.library.Extra;
import com.zpz.common.base.BaseApplication;
import com.zpz.common.databinding.DialogDownFileBinding;
import com.zpz.common.utils.ToastUitl;

import java.io.File;

/**
 * 初审分享
 */
public class DownFileDialog extends DialogFragment {
    private DialogDownFileBinding downFileBinding;
    private String url;
    public static DownFileDialog newInstance(String url) {
        DownFileDialog downFileDialog = new DownFileDialog();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        downFileDialog.setArguments(bundle);
        return downFileDialog;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getArguments().getString("url");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        downFileBinding = DialogDownFileBinding.inflate(inflater,container,false);
        init();
        down();
        setCancelable(false);
        return downFileBinding.getRoot();
    }
    private void init(){
        downFileBinding.tvQx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadImpl.getInstance().cancel(url);
                dismiss();
            }
        });
    }
    private void down(){
        DownloadImpl.getInstance()
                .with(BaseApplication.getInstance())
//                .target(new File(this.getExternalCacheDir(), "com.ss.android.article.news_636.apk"))
                .setUniquePath(false)
                .setForceDownload(true)
                .url(url)
                .enqueue(new DownloadListenerAdapter() {
                    @Override
                    public void onStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength, Extra extra) {
                        super.onStart(url, userAgent, contentDisposition, mimetype, contentLength, extra);
                    }

                    @Override
                    public void onProgress(String url, long downloaded, long length, long usedTime) {
                        super.onProgress(url, downloaded, length, usedTime);
                       setProgress((int) (((float)downloaded/(float)length)*100));
                    }

                    @Override
                    public boolean onResult(Throwable throwable, Uri path, String url, Extra extra) {
                        Log.i("dfdf", " path:" + path + " url:" + url + " length:" + new File(path.getPath()).length());
                        ToastUitl.showShort("文件已经下载到："+path);
                        return super.onResult(throwable, path, url, extra);
                    }
                });
    }

    private void setProgress(int progress){
        downFileBinding.progressBar.setProgress(progress);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        super.show(manager, tag);
    }


}
