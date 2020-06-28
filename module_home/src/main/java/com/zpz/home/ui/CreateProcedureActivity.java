package com.zpz.home.ui;


import android.content.Intent;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;

import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.Filter;
import com.zpz.common.base.BaseActivity;
import com.zpz.common.base.DataBindingConfig;
import com.zpz.common.base.MyARouter;
import com.zpz.common.utils.ChoosePictureUtils;
import com.zpz.common.view.dalog.LoadingDialog;
import com.zpz.home.BR;
import com.zpz.home.R;
import com.zpz.home.vm.CreateProcedureViewModel;
import java.util.ArrayList;
import java.util.List;

//建档
@Route(path = MyARouter.CreateProcedureActivity)
public class CreateProcedureActivity extends BaseActivity<CreateProcedureViewModel> {
    //初审id
    @Autowired(name = "first_assess_id")
    public long first_assess_id;
    //公司id
    @Autowired()
    public long company_id;

    private final int REQUESTCODE_LOGO = 1234;
    private final int REQUESTCODE_WORK_ENVIRONMENT = 1235;
    //荣誉
    private final int REQUESTCODE_HONOR = 1236;
    //合约
    private final int REQUESTCODE_CONTRACT = 1237;

    private final int REQUESTCODE_VIDEO = 1238;

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_create_procedure)
                .addBindingParam(BR.vm,viewModel)
                .addBindingParam(BR.click, new ClickProxy());
    }

    @Override
    protected void init() {
        ARouter.getInstance().inject(this);
    }

    @Override
    protected void initViewObservable() {
       LoadingDialog.showDialogForLoading(mActivity);
       viewModel.requesCreteProceddure(company_id,first_assess_id);
    }

    public class ClickProxy{
        //提交
        public void submit(View view){

        }
        //上传视频
        public void uploadAVideo(View view){
            chooseVideo(REQUESTCODE_VIDEO);
        }
        //上传logo
        public void uploadLogo(View view){
            chooseImage(REQUESTCODE_LOGO,1);
        }
        //选择时间
        public void selectTime (View view){

        }
        //定位
        public void location (View view){

        }
    }

    //选择图片
    private void chooseImage(final int requestCode, int maxSelectable){
        ChoosePictureUtils.choosePictureCommon(mActivity, maxSelectable, new ChoosePictureUtils.Action<ArrayList<String>>() {
            @Override
            public void onAction(@NonNull ArrayList<String> result) {
                switch (requestCode){
                    case REQUESTCODE_LOGO:
                        //logo
                        break;
                    case REQUESTCODE_WORK_ENVIRONMENT:
                        //工作环境
                        break;
                    case REQUESTCODE_HONOR:
                        //荣誉
                        break;
                    case REQUESTCODE_CONTRACT:
                        //合约
                        break;
                }

            }
        });

    }

    //视频
    private void chooseVideo(int requestCode){
        Album.camera(this)
                .video() // Record Video.
                .quality(1) // Video quality, [0, 1].
                .limitDuration(Long.MAX_VALUE) // The longest duration of the video is in milliseconds.
                .limitBytes(Long.MAX_VALUE) // Maximum size of the video, in bytes.
                .onResult(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                    }
                })
                .start();
    }


    List<String> list = new ArrayList<>();


}