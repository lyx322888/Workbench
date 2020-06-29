package com.zpz.home.ui;


import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;

import com.zpz.common.base.BaseActivity;
import com.zpz.common.base.DataBindingConfig;
import com.zpz.common.base.MyARouter;
import com.zpz.common.base.adapter.BaseBindingAdapter;
import com.zpz.common.utils.ChoosePictureUtils;
import com.zpz.common.utils.CommonUtils;
import com.zpz.common.utils.UpLoadPicUtils;
import com.zpz.common.view.dalog.LoadingDialog;
import com.zpz.home.BR;
import com.zpz.home.R;
import com.zpz.home.adapter.HyPhotoAdapter;
import com.zpz.home.adapter.JDPhotoAdapter;
import com.zpz.home.vm.CreateProcedureViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//建档
@Route(path = MyARouter.CreateProcedureActivity)
public class CreateProcedureActivity extends BaseActivity<CreateProcedureViewModel> implements AMapLocationListener {
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
    private JDPhotoAdapter workPhotoAdapter;
    private JDPhotoAdapter ryPhotoAdapter;
    private HyPhotoAdapter hyPhotoAdapter;
    //定位
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明AMapLocationClient类对象
    public AMapLocationClient mlocationClient = null;

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        workPhotoAdapter = new JDPhotoAdapter(mActivity);
        ryPhotoAdapter = new JDPhotoAdapter(mActivity);
        hyPhotoAdapter = new HyPhotoAdapter(mActivity);
        return new DataBindingConfig(R.layout.activity_create_procedure)
                .addBindingParam(BR.vm, viewModel)
                .addBindingParam(BR.workAdapter, workPhotoAdapter)
                .addBindingParam(BR.ryAdapter, ryPhotoAdapter)
                .addBindingParam(BR.hyAdapter, hyPhotoAdapter)
                .addBindingParam(BR.click, new ClickProxy());
    }

    @Override
    protected void init() {
        ARouter.getInstance().inject(this);
        adapterListener();


    }

    private void reLocation() {
        LoadingDialog.showDialogForLoading(mActivity);
        mlocationClient = new AMapLocationClient(getApplicationContext());
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//        设置定位间隔,单位毫秒,默认为2000ms
//        mLocationOption.setInterval(2000);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。
        //如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会。
        mLocationOption.setOnceLocationLatest(true);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
    }

    private void adapterListener() {
        workPhotoAdapter.setOnItemClickListener(new BaseBindingAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                chooseImage(REQUESTCODE_WORK_ENVIRONMENT,13, (ArrayList<String>) viewModel.getCreteProceddure().getValue().getData().getWork_environment_img());
            }
        });
        ryPhotoAdapter.setOnItemClickListener(new BaseBindingAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                chooseImage(REQUESTCODE_HONOR,13, (ArrayList<String>) viewModel.getCreteProceddure().getValue().getData().getEnterprise_honor_img());
            }
        });
        hyPhotoAdapter.setOnItemClickListener(new BaseBindingAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                chooseImage(REQUESTCODE_CONTRACT,13, (ArrayList<String>) viewModel.getCreteProceddure().getValue().getData().getKeep_faith_contract_img());
            }
        });
    }

    @Override
    protected void initViewObservable() {
       LoadingDialog.showDialogForLoading(mActivity);
       viewModel.requesCreteProceddure(company_id,first_assess_id);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        LoadingDialog.cancelDialogForLoading();
        if (aMapLocation.getErrorCode() != AMapLocation.LOCATION_SUCCESS) {
            showShortToast("请开启定位");
        } else {
            viewModel.getCreteProceddure().getValue().getData().setAddress(aMapLocation.getAddress());
            viewModel.getCreteProceddure().getValue().getData().setLat(Double.toString(aMapLocation.getLatitude()));
            viewModel.getCreteProceddure().getValue().getData().setLng(Double.toString(aMapLocation.getLongitude()));
          viewModel.getCreteProceddure().setValue(viewModel.getCreteProceddure().getValue());
        }
    }

    public class ClickProxy{
        //提交
        public void submit(View view){

        }
        //上传视频
        public void uploadAVideo(View view){
            chooseVideo( );
        }
        //上传logo
        public void uploadLogo(final View view){
            ChoosePictureUtils.choosePictureCommon(mContext, 1, new ChoosePictureUtils.Action<ArrayList<String>>() {
                @Override
                public void onAction(@NonNull ArrayList<String> result) {
                    viewModel.getCreteProceddure().getValue().getData().setLogo(result.get(0));
                    viewModel.getCreteProceddure().setValue(viewModel.getCreteProceddure().getValue());
                }
            });
        }
        //选择时间
        public void selectTime (View view){
            //时间选择器
            TimePickerView pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    viewModel.getCreteProceddure().getValue().getData().setEstablish_date(CommonUtils.getTime(date,"yyyy-MM-dd"));
                    viewModel.getCreteProceddure().setValue( viewModel.getCreteProceddure().getValue());
                }
            }).setType(new boolean[]{true, true, true, false, false, false}).build();
            pvTime.show();
        }
        //定位
        public void location (View view){
            reLocation();
        }
    }

    //选择图片
    private void chooseImage(final int requestCode, int maxSelectable, ArrayList<String> list){
        ChoosePictureUtils.choosePictureChecked(mActivity, maxSelectable,list, new ChoosePictureUtils.Action<ArrayList<String>>() {
            @Override
            public void onAction(@NonNull ArrayList<String> result) {
                switch (requestCode){
                    case REQUESTCODE_WORK_ENVIRONMENT:
                        //工作环境
                        LoadingDialog.showDialogForLoading(mActivity);
                        UpLoadPicUtils.batchUpload(result, new UpLoadPicUtils.BatchUpLoadPicListener() {
                            @Override
                            public void success(List<String> qiNiuPath) {
                                Log.e("dfdf", "success: "+qiNiuPath.get(0) );
                                viewModel.getCreteProceddure().getValue().getData().setWork_environment_img(qiNiuPath);
                                viewModel.getCreteProceddure().setValue(viewModel.getCreteProceddure().getValue());
                                LoadingDialog.cancelDialogForLoading();
                            }

                            @Override
                            public void error() {
                                LoadingDialog.cancelDialogForLoading();
                            }
                        });

                        break;
                    case REQUESTCODE_HONOR:
                        //荣誉
                        viewModel.getCreteProceddure().getValue().getData().setEnterprise_honor_img(result);
                        viewModel.getCreteProceddure().setValue(viewModel.getCreteProceddure().getValue());
                        break;
                    case REQUESTCODE_CONTRACT:
                        //合约
                        viewModel.getCreteProceddure().getValue().getData().setKeep_faith_contract_img(result);
                        viewModel.getCreteProceddure().setValue(viewModel.getCreteProceddure().getValue());
                        break;
                }

            }
        });

    }

    //视频
    private void chooseVideo( ){
        Album.camera(this)
                .video() // Record Video.
                .quality(1) // Video quality, [0, 1].
                .limitDuration(Long.MAX_VALUE) // The longest duration of the video is in milliseconds.
                .limitBytes(Long.MAX_VALUE) // Maximum size of the video, in bytes.
                .onResult(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                        Log.e("dfdf", "onAction: "+result );
                        LoadingDialog.showDialogForLoading(mActivity);
                        UpLoadPicUtils.upOnePic(result, new UpLoadPicUtils.UpLoadPicListener() {
                            @Override
                            public void success(String qiNiuPath) {
                                Log.e("dfdf", "success: "+qiNiuPath );
                                LoadingDialog.cancelDialogForLoading();
                            }

                            @Override
                            public void error() {
                                LoadingDialog.cancelDialogForLoading();
                            }
                        });
                    }
                })
                .start();
    }

}