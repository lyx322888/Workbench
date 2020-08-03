package com.zpz.common.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.zxing.WriterException;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.zpz.common.R;
import com.zpz.common.bean.FirsTrialShareBean;
import com.zpz.common.bean.UserInfoBean;
import com.zpz.common.databinding.DialogCsfxBinding;
import com.zpz.common.utils.BitmapUtils;
import com.zpz.common.utils.EncodingHandler;
import com.zpz.common.utils.GlideUtils;
import com.zpz.common.utils.ScannerUtils;
import com.zpz.common.utils.ToastUitl;
import com.zpz.common.vm.FirsTrialViewShareModel;
import com.zpz.common.vm.UserViewModel;

/**
 * 初审分享
 */
public class FirstTrialShareDialog extends DialogFragment {
    private DialogCsfxBinding csfxBinding;
    private UserViewModel viewModel;
    private FirsTrialViewShareModel firsTrialViewShareModel;

    public static FirstTrialShareDialog newInstance() {
        FirstTrialShareDialog uploadAccreditationDialog = new FirstTrialShareDialog();
        return uploadAccreditationDialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window win = getDialog().getWindow();
        // 一定要设置Background，如果不设置，window属性设置无效
        win.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

        WindowManager.LayoutParams params = win.getAttributes();
        params.gravity = Gravity.BOTTOM;
        // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        win.setAttributes(params);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        firsTrialViewShareModel = new ViewModelProvider(this).get(FirsTrialViewShareModel.class);
        csfxBinding = DialogCsfxBinding.inflate(inflater,container,false);
        csfxBinding.setVm(viewModel);
        csfxBinding.setSharevm(firsTrialViewShareModel);
        loaddata();
        init();
        return csfxBinding.getRoot();
    }

    private void init(){
        //保存图片
        csfxBinding.shareBctp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScannerUtils.saveImageToGallery(getContext(), BitmapUtils.viewToBitmap(csfxBinding.content), ScannerUtils.ScannerType.RECEIVER);
                dismiss();
            }
        });
        csfxBinding.shareWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firsTrialViewShareModel.data.getValue()!=null){
                    UMWeb web = new UMWeb(firsTrialViewShareModel.data.getValue().getShare_url());
                    web.setTitle("全国企业信用公示平台【企业初审】");//标题
                    web.setThumb(new UMImage(getContext(),R.mipmap.ic_app));  //缩略图
                    web.setDescription("填写相关企业信息进行企业初审，通过初审可建立企业信用档案");//描述
                    new ShareAction(getActivity())
                            .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
                            .withMedia(web)
                            .share();
                    dismiss();
                }else {
                    ToastUitl.showShort("网络走丢啦，请检查网络并重新进入");
                }

            }
        });
        csfxBinding.cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void loaddata() {
        viewModel.getUserInfo().observe(this, new Observer<UserInfoBean.DataBean>() {
            @Override
            public void onChanged(UserInfoBean.DataBean dataBean) {
                GlideUtils.showSmallPic(csfxBinding.civHeard,dataBean.getFace());
                csfxBinding.tvName.setText( dataBean.getNickname());
                csfxBinding.tvWorkNo.setText("工号："+dataBean.getWork_no());
            }
        });
        firsTrialViewShareModel.data.observe(this, new Observer<FirsTrialShareBean.DataBean>() {
            @Override
            public void onChanged(FirsTrialShareBean.DataBean dataBean) {
                try {
                    csfxBinding.ivEwm.setImageBitmap(EncodingHandler.createQRCode(dataBean.getShare_url(),300));
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
        viewModel.requesUserInfo();
        firsTrialViewShareModel.requesShare();

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
