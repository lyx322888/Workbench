package com.zpz.home.ui;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

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
import com.iceteck.silicompressorr.SiliCompressor;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;
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
import com.zpz.home.baen.CreteProceddureBean;
import com.zpz.home.databinding.ActivityCreateProcedureBinding;
import com.zpz.home.vm.CreateProcedureViewModel;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import cn.jzvd.JZVideoPlayer;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

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
    //定位
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销定位
        if (mlocationClient!=null){
            mlocationClient.onDestroy();
        }
    }
    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    //适配器点击事件
    private void adapterListener() {
        workPhotoAdapter.setOnItemClickListener(new BaseBindingAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                if (viewModel.getCreteProceddure().getValue().getData().getWork_environment_img().size()>12){
                    showShortToast("最多只能传12张");
                }else {
                    chooseImage(REQUESTCODE_WORK_ENVIRONMENT,12-viewModel.getCreteProceddure().getValue().getData().getWork_environment_img().size());
                }
            }
        });
        ryPhotoAdapter.setOnItemClickListener(new BaseBindingAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                if (viewModel.getCreteProceddure().getValue().getData().getEnterprise_honor_img().size()>12){
                    showShortToast("最多只能传12张");
                }else {
                    chooseImage(REQUESTCODE_HONOR,12-viewModel.getCreteProceddure().getValue().getData().getEnterprise_honor_img().size());
                }
            }
        });
        hyPhotoAdapter.setOnItemClickListener(new BaseBindingAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                if (viewModel.getCreteProceddure().getValue().getData().getKeep_faith_contract_img().size()>12){
                    showShortToast("最多只能传12张");
                }else {
                    chooseImage(REQUESTCODE_CONTRACT,12-viewModel.getCreteProceddure().getValue().getData().getKeep_faith_contract_img().size());
                }
            }
        });
    }
    //数据监听
    @Override
    protected void initViewObservable() {
       LoadingDialog.showDialogForLoading(mActivity);
       viewModel.getCreteProceddure().observe(this, new Observer<CreteProceddureBean>() {
           @Override
           public void onChanged(CreteProceddureBean creteProceddureBean) {
               if (TextUtils.isEmpty(creteProceddureBean.getData().getIntroduce_video())){
                   ((ActivityCreateProcedureBinding)getBinding()).jzTinyId.setVisibility(View.GONE);
                   ((ActivityCreateProcedureBinding)getBinding()).ivVideo.setVisibility(View.VISIBLE);
                   ((ActivityCreateProcedureBinding)getBinding()).ivVideoGb.setVisibility(View.GONE);
               }else {
                   ((ActivityCreateProcedureBinding)getBinding()).jzTinyId.setVisibility(View.VISIBLE);
                   ((ActivityCreateProcedureBinding)getBinding()).ivVideo.setVisibility(View.GONE);
                   ((ActivityCreateProcedureBinding)getBinding()).ivVideoGb.setVisibility(View.VISIBLE);
                   ((ActivityCreateProcedureBinding)getBinding()).jzTinyId.setUp(creteProceddureBean.getData().getIntroduce_video(), JZVideoPlayer.SCREEN_WINDOW_NORMAL, "企业介绍");
               }
           }
       });
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
            upData();
        }
    }
    //刷新数据
    private void upData(){
        viewModel.getCreteProceddure().setValue(viewModel.getCreteProceddure().getValue());
    }

    public class ClickProxy{
        //提交
        public void submit(View view){

        }
        //删除视频
        public void deleteVideo(View view){
            viewModel.getCreteProceddure().getValue().getData().setIntroduce_video("");
            upData();
        }
        //上传视频
        public void uploadAVideo(View view){
            chooseVideo( );
        }
        //上传logo
        public void uploadLogo(final View view){
           chooseImage(REQUESTCODE_LOGO,1);
        }
        //选择时间
        public void selectTime (View view){
            //时间选择器
            TimePickerView pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    viewModel.getCreteProceddure().getValue().getData().setEstablish_date(CommonUtils.getTime(date,"yyyy-MM-dd"));
                    upData();
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
    private void chooseImage(final int requestCode, int maxSelectable){
        ChoosePictureUtils.choosePictureCommon(mActivity, maxSelectable,new ChoosePictureUtils.Action<ArrayList<String>>() {
            @Override
            public void onAction(@NonNull ArrayList<String> result) {
                LoadingDialog.showDialogForLoading(mActivity);
                //图片压缩
                Disposable d = Flowable.just(result)
                        .observeOn(Schedulers.io())
                        .map(new Function<ArrayList<String>, ArrayList<String>>() {
                            @Override
                            public ArrayList<String> apply(@NonNull ArrayList<String> list) throws Exception {
                                // 同步方法直接返回压缩后的文件
                                ArrayList<String> arrayList = new ArrayList<>();
                                List<File> files=Luban.with(mContext).load(list).get();
                                for (int i = 0; i < files.size(); i++) {
                                    arrayList.add(files.get(i).getPath());
                                }
                                return arrayList;
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<ArrayList<String>>() {
                            @Override
                            public void accept(ArrayList<String> arrayList) throws Exception {
                              upLoadPic( arrayList,requestCode);
                            }
                        });
                addDisposable(d);
            }
        });
    }
    //上传七牛
    private void upLoadPic(ArrayList<String> arrayList,final int requestCode){
        //上传七牛
        UpLoadPicUtils.batchUpload(arrayList, new UpLoadPicUtils.BatchUpLoadPicListener() {
            @Override
            public void success(List<String> qiNiuPath) {
                switch (requestCode){
                    case REQUESTCODE_LOGO:
                        //logo
                        viewModel.getCreteProceddure().getValue().getData().setLogo(qiNiuPath.get(0));
                        break;
                    case REQUESTCODE_WORK_ENVIRONMENT:
                        //工作环境
                        viewModel.getCreteProceddure().getValue().getData().getWork_environment_img().addAll(qiNiuPath);
                        break;
                    case REQUESTCODE_HONOR:
                        //荣誉
                        viewModel.getCreteProceddure().getValue().getData().getEnterprise_honor_img().addAll(qiNiuPath);
                        break;
                    case REQUESTCODE_CONTRACT:
                        //合约
                        viewModel.getCreteProceddure().getValue().getData().getKeep_faith_contract_img().addAll(qiNiuPath);
                        break;
                }
                upData();
                LoadingDialog.cancelDialogForLoading();
            }
            @Override
            public void error() {
                showShortToast("上传失败，请稍后重试");
                LoadingDialog.cancelDialogForLoading();
            }
        });
    }

    //视频
    private void chooseVideo( ){
        Album.video(this) // Video selection.
                .multipleChoice()
                .widget(Widget.newDarkBuilder(mActivity)
                        .title("选择视频") // Title.
                        .mediaItemCheckSelector(ContextCompat.getColor(mActivity, R.color.white), ContextCompat.getColor(mActivity, R.color.appColor))//按钮颜色
                        .statusBarColor(ContextCompat.getColor(mActivity, R.color.appColor))
                        .toolBarColor(ContextCompat.getColor(mActivity, R.color.appColor)).build())// Toolbar color..build())// StatusBar color.)
                .camera(true)
                .columnCount(3)
                .selectCount(1)
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        LoadingDialog.showDialogForLoading(mActivity,"压缩视频",false);
                        ysVideo(result.get(0).getPath());
                    }
                })
                .start();


    }

    //压缩视频
    private void ysVideo(final String filePath){

        Disposable d = Flowable.just(filePath)
                .observeOn(Schedulers.io())
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return SiliCompressor.with(mActivity).compressVideo(filePath,Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String filePath) throws Exception {
                        LoadingDialog.showDialogForLoading(mActivity,"上传视频",false);
                        UpLoadPicUtils.upOnePic(filePath, new UpLoadPicUtils.UpLoadPicListener() {
                            @Override
                            public void success(String qiNiuPath) {
                                viewModel.getCreteProceddure().getValue().getData().setIntroduce_video(qiNiuPath);
                                upData();
                                LoadingDialog.cancelDialogForLoading();
                            }

                            @Override
                            public void error() {
                                showShortToast("上传失败，请稍后重试");
                                LoadingDialog.cancelDialogForLoading();
                            }
                        });
                    }
                });
        addDisposable(d);
    }

}