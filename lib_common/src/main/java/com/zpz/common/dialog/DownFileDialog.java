package com.zpz.common.dialog;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

/**
 * 文件下载
 */
public class DownFileDialog extends DialogFragment {
    private DialogDownFileBinding downFileBinding;
    private String url;
    private Uri filePath;
    //下载是否完成
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
//        downFileBinding.tvDk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = OpenFileUtil.openFile(filePath.getPath());
////                if (OpenFileUtil.isIntentAvailable(BaseApplication.getInstance(),intent)){
////                    startActivity(intent);
////                }else {
////                    ToastUitl.showShort("请安装相关软件");
////                }
//                HashMap<String, String> params = new HashMap<String, String>();
//                params.put("style", "1");
//                params.put("local", "true");
//                QbSdk.openFileReader(getContext(), filePath.getPath(), params, new ValueCallback<String>() {
//                    @Override
//                    public void onReceiveValue(String s) {
//                        Log.e("dfdf", "onReceiveValue: "+s );
//                    }
//                });
//                dismiss();
//            }
//        });
    }



    private void down(){
        DownloadImpl.getInstance()
                .with(BaseApplication.getInstance())
//                .target(new File(this.getExternalCacheDir(), "com.ss.android.article.news_636.apk"))
//                .target(getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS))
                .target(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS))
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
                       if (downloaded==length){
                           downFileBinding.tvDk.setText("打开");
                           downFileBinding.tvQx.setText("关闭");
                       }
                    }

                    @Override
                    public boolean onResult(Throwable throwable, Uri path, String url, Extra extra) {
                        ToastUitl.showLong("文件保存在："+path);
                        filePath = path;
                        return super.onResult(throwable, path, url, extra);
                    }
                });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        //暂停下载
        DownloadImpl.getInstance().cancel(url);
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
