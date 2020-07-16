package com.zpz.home.ui;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.api.widget.Widget;
import com.zpz.common.base.BaseActivity;
import com.zpz.common.base.DataBindingConfig;
import com.zpz.common.base.MyARouter;
import com.zpz.common.dialog.ConfirmDialog;
import com.zpz.common.utils.ChoosePictureUtils;
import com.zpz.common.utils.CommonUtils;
import com.zpz.common.utils.FileUtils;
import com.zpz.common.utils.QiniuUtils;
import com.zpz.common.utils.UpLoadPicUtils;
import com.zpz.common.utils.VideoUtils;
import com.zpz.common.view.dalog.LoadingDialog;
import com.zpz.home.BR;
import com.zpz.home.R;
import com.zpz.home.adapter.HyPhotoAdapter;
import com.zpz.home.adapter.JDPhotoAdapter;
import com.zpz.home.databinding.ActivityCreateProcedureBinding;
import com.zpz.home.vm.CreateProcedureViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayer;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
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
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明AMapLocationClient类对象
    public AMapLocationClient mlocationClient = null;

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        workPhotoAdapter = new JDPhotoAdapter();
        ryPhotoAdapter = new JDPhotoAdapter();
        hyPhotoAdapter = new HyPhotoAdapter();
        return new DataBindingConfig(R.layout.activity_create_procedure)
                .addBindingParam(BR.vm, viewModel)
                .addBindingParam(BR.workAdapter, workPhotoAdapter)
                .addBindingParam(BR.ryAdapter, ryPhotoAdapter)
                .addBindingParam(BR.hyAdapter, hyPhotoAdapter)
                .addBindingParam(BR.click, new ClickProxy());
    }

    @Override
    protected void init() {
        setTitle("企业建档");
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销定位
        if (mlocationClient!=null){
            mlocationClient.onDestroy();
        }
        //删除压缩图片缓存目录
        FileUtils.deleteFile(FileUtils.getCacheDir(mActivity,"image").getAbsolutePath());
        FileUtils.deleteFile(FileUtils.getCacheDir(mActivity,"movie").getAbsolutePath());
    }

    //适配器点击事件
    private void adapterListener() {
        workPhotoAdapter.setOnItemClickListener(position -> {
            if (viewModel.getCreteProceddure().getValue().getWork_environment_img().size()>12){
                showShortToast("最多只能传12张");
            }else {
                if (!TextUtils.isEmpty(viewModel.getCreteProceddure().getValue().getAddress())){
                    chooseImage(REQUESTCODE_WORK_ENVIRONMENT,12-viewModel.getCreteProceddure().getValue().getWork_environment_img().size());
                }else {
                    showShortToast("请先定位公司地址");
                }
            }
        });
        ryPhotoAdapter.setOnItemClickListener(position -> {
            if (viewModel.getCreteProceddure().getValue().getEnterprise_honor_img().size()>12){
                showShortToast("最多只能传12张");
            }else {
                if (!TextUtils.isEmpty(viewModel.getCreteProceddure().getValue().getAddress())){
                    chooseImage(REQUESTCODE_HONOR,12-viewModel.getCreteProceddure().getValue().getEnterprise_honor_img().size());
                }else {
                    showShortToast("请先定位地址");
                }
            }
        });
        hyPhotoAdapter.setOnItemClickListener(position -> {
            if (viewModel.getCreteProceddure().getValue().getKeep_faith_contract_img().size()>12){
                showShortToast("最多只能传12张");
            }else {
                if (!TextUtils.isEmpty(viewModel.getCreteProceddure().getValue().getAddress())){
                    chooseImage(REQUESTCODE_CONTRACT,12-viewModel.getCreteProceddure().getValue().getKeep_faith_contract_img().size());
                }else {
                    showShortToast("请先定位公司地址");
                }
            }
        });
    }
    //数据监听
    @Override
    protected void initViewObservable() {

       viewModel.getCreteProceddure().observe(this, dataBean -> {
           if (TextUtils.isEmpty(dataBean.getIntroduce_video())){
               ((ActivityCreateProcedureBinding)getBinding()).jzTinyId.setVisibility(View.GONE);
               ((ActivityCreateProcedureBinding)getBinding()).ivVideo.setVisibility(View.VISIBLE);
               ((ActivityCreateProcedureBinding)getBinding()).ivVideoGb.setVisibility(View.GONE);
           }else {
               ((ActivityCreateProcedureBinding)getBinding()).jzTinyId.setVisibility(View.VISIBLE);
               ((ActivityCreateProcedureBinding)getBinding()).ivVideo.setVisibility(View.GONE);
               ((ActivityCreateProcedureBinding)getBinding()).ivVideoGb.setVisibility(View.VISIBLE);
               ((ActivityCreateProcedureBinding)getBinding()).jzTinyId.setUp(dataBean.getIntroduce_video(), JZVideoPlayer.SCREEN_WINDOW_NORMAL, "企业介绍");
           }
       });

       viewModel.getsubmitsuccess().observe(this, aBoolean -> {
           ConfirmDialog confirmDialog = ConfirmDialog.newInstance("档案已提交，请等待审核");
           confirmDialog.setDialogListener(new ConfirmDialog.DialogListener() {
               @Override
               public void dialogClickListener() {
                   finish();
               }
           });
           confirmDialog.show(getSupportFragmentManager(),"");
       });
    }

    @Override
    protected void onLoadData() {
        viewModel.requesCreteProceddure(company_id,first_assess_id);
    }


    //定位
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        LoadingDialog.cancelDialogForLoading();
        if (aMapLocation.getErrorCode() != AMapLocation.LOCATION_SUCCESS) {
            showShortToast("请开启定位");
        } else {
            viewModel.getCreteProceddure().getValue().setAddress(aMapLocation.getAddress());
            viewModel.getCreteProceddure().getValue().setLat(Double.toString(aMapLocation.getLatitude()));
            viewModel.getCreteProceddure().getValue().setLng(Double.toString(aMapLocation.getLongitude()));
            upData();
        }
    }
    //刷新数据
    private void upData(){
        viewModel.getCreteProceddure().setValue(viewModel.getCreteProceddure().getValue());
    }

    //选择图片
    private void chooseImage(final int requestCode, int maxSelectable){
        ChoosePictureUtils.choosePictureCommon(mActivity, maxSelectable, result -> {
            LoadingDialog.showDialogForLoading(mActivity);
            //图片压缩
            Disposable d = Flowable.just(result)
                    .observeOn(Schedulers.io())
                    .map(list -> {
                        // 同步方法直接返回压缩后的文件
                        ArrayList<String> arrayList = new ArrayList<>();
                        List<File> files=Luban.with(mContext).load(list).setTargetDir(FileUtils.getCacheDir(mContext,"image").getAbsolutePath()).get();
                        for (int i = 0; i < files.size(); i++) {
                            arrayList.add(files.get(i).getPath());
                        }
                        return arrayList;
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(arrayList -> upLoadPic( arrayList,requestCode));
            addDisposable(d);
        });
    }


    //上传图片七牛
    private void upLoadPic(ArrayList<String> arrayList,final int requestCode){
        UpLoadPicUtils.batchUpload(arrayList, new UpLoadPicUtils.BatchUpLoadPicListener() {
            @Override
            public void success(List<String> qiNiuPath) {
                if (requestCode!=REQUESTCODE_LOGO){
                    //排除logo
                    for (int i = 0; i < qiNiuPath.size(); i++) {
                        qiNiuPath.set(i,QiniuUtils.watermark(qiNiuPath.get(i),viewModel.getCreteProceddure().getValue().getAddress()));
                        Log.e("dfdf", "success: "+qiNiuPath.get(i) );
                    }
                }
                switch (requestCode){
                    case REQUESTCODE_LOGO:
                        //logo
                        viewModel.getCreteProceddure().getValue().setLogo(qiNiuPath.get(0));
                        Log.e("dfdf", "success: "+ qiNiuPath.get(0));
                        break;
                    case REQUESTCODE_WORK_ENVIRONMENT:
                        //工作环境
                        viewModel.getCreteProceddure().getValue().getWork_environment_img().addAll(qiNiuPath);
                        break;
                    case REQUESTCODE_HONOR:
                        //荣誉
                        viewModel.getCreteProceddure().getValue().getEnterprise_honor_img().addAll(qiNiuPath);
                        break;
                    case REQUESTCODE_CONTRACT:
                        //合约
                        viewModel.getCreteProceddure().getValue().getKeep_faith_contract_img().addAll(qiNiuPath);
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

    //选择视频
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
                .onResult(result ->{
                    String path = result.get(0).getPath();
                    ConfirmDialog confirmDialog = ConfirmDialog.newInstance("是否压缩视频？");
                    confirmDialog.setDialogListener(new ConfirmDialog.DialogListener() {
                        @Override
                        public void dialogClickListener() {
                            ysVideo(path);
                        }

                        @Override
                        public void canceListener() {
                            upOnevideo(path);
                        }
                    });
                    new Handler().postDelayed(() -> confirmDialog.show(getSupportFragmentManager(),""),500);
                })
                .start();

    }

    //压缩视频
    private void ysVideo(final String filePath){
        LoadingDialog.showDialogForLoading(mActivity,"压缩视频",false);
        Observable.create((ObservableOnSubscribe<String>) emitter -> VideoUtils.videoCompressor(mContext, filePath, (progress, outputfilePath) -> {
            if (progress==1){
                emitter.onNext(outputfilePath);
                emitter.onComplete();
            }
        })).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(final String outputfilePath) {
                        upOnevideo(outputfilePath);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    //上传图片到七牛
    public void upOnevideo(String videoUrl){
        LoadingDialog.showDialogForLoading(mActivity,"上传视频",false);
        UpLoadPicUtils.upOnePic(videoUrl, new UpLoadPicUtils.UpLoadPicListener() {
            @Override
            public void success(String qiNiuPath) {
                viewModel.getCreteProceddure().getValue().setIntroduce_video(qiNiuPath);
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

    public class ClickProxy{
        //删除视频
        public void deleteVideo(View view){
            viewModel.getCreteProceddure().getValue().setIntroduce_video("");
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
            TimePickerView pvTime = new TimePickerBuilder(mContext, (date, v) -> {
                viewModel.getCreteProceddure().getValue().setEstablish_date(CommonUtils.getTime(date,"yyyy-MM-dd"));
                upData();
            }).setType(new boolean[]{true, true, true, false, false, false}).build();
            pvTime.show();
        }
        //定位
        public void location (View view){
            reLocation();
        }
        //提交
        public void submit(View view){
            if (viewModel.getCreteProceddure().getValue()==null){
                showShortToast("请填写公司信息");
                return;
            }else if (TextUtils.isEmpty(viewModel.getCreteProceddure().getValue().getCompany_name())){
                showShortToast("请填写公司名称");
                return;
            }else if (TextUtils.isEmpty(viewModel.getCreteProceddure().getValue().getLogo())){
                showShortToast("请上传logo");
                return;
            }else if (TextUtils.isEmpty(viewModel.getCreteProceddure().getValue().getLegal_person())){
                showShortToast("请填写法定代表人");
                return;
            }else if (TextUtils.isEmpty(viewModel.getCreteProceddure().getValue().getRegistered_capital())){
                showShortToast("请填写注册资本");
                return;
            } else if (TextUtils.isEmpty(viewModel.getCreteProceddure().getValue().getEstablish_date())){
                showShortToast("请填写成立日期");
                return;
            }else if (TextUtils.isEmpty(viewModel.getCreteProceddure().getValue().getAddress())){
                showShortToast("请填写公司地址");
                return;
            } else if (TextUtils.isEmpty(viewModel.getCreteProceddure().getValue().getIntroduce_text())){
                showShortToast("请填写公司介绍");
                return;
            }else if (TextUtils.isEmpty(viewModel.getCreteProceddure().getValue().getIntroduce_video())){
                showShortToast("请上传公司介绍视频");
                return;
            }
            viewModel.requesSubmit(company_id,first_assess_id);
        }
    }


}