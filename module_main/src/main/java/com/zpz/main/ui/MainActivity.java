package com.zpz.main.ui;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yanzhenjie.sofia.Sofia;
import com.zpz.common.base.BaseActivity;
import com.zpz.common.base.DataBindingConfig;
import com.zpz.common.base.MyARouter;
import com.zpz.common.bean.CommissionCountBean;
import com.zpz.common.bean.VersionBean;
import com.zpz.common.dialog.UpdateDialogFragment;
import com.zpz.common.utils.APPUtil;
import com.zpz.common.vm.VersionViewModel;
import com.zpz.main.R;
import com.zpz.main.databinding.ActivityMainBinding;
import com.zpz.main.vm.MainViewModel;

@Route(path = MyARouter.MainActivity)
public class MainActivity extends BaseActivity<MainViewModel> {
    @Autowired(name = MyARouter.HomeFragment)
    public Fragment mHomeFragment;
    @Autowired(name = MyARouter.CommissionFragment)
    public Fragment commissionFragment;
    @Autowired(name = MyARouter.HomeFragment)
    public Fragment mMeFragment;
    public Fragment mCurrFragment;
    private ActivityMainBinding activityMainBinding;
    private TextView dbMsgView;
    private long mExitTime;
    private VersionViewModel versionViewModel;
    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_main);
    }

    @Override
    protected void initViewModel() {
        super.initViewModel();
        versionViewModel = getActivityViewModel(VersionViewModel.class);
    }

    @Override
    protected void setStatubarColor() {
        if (((ActivityMainBinding)getBinding())!=null){
            Sofia.with(this).invasionStatusBar().statusBarBackgroundAlpha(0).statusBarDarkFont();
        }
    }
    @Override
    protected void initViewObservable() {
        viewModel.getCountBeanLiveData().observe(this, new Observer<CommissionCountBean>() {
            @Override
            public void onChanged(CommissionCountBean commissionCountBean) {
                CommissionCountBean.DataBean dataBean = commissionCountBean.getData();
                dbMsgView.setText(String.format("%s",dataBean.getCount_type_one()+dataBean.getCount_type_two()+dataBean.getCount_type_three()));
            }
        });
        //版本更新
        versionViewModel.getVersionliveData().observe(this, new Observer<VersionBean.DataBean>() {
            @Override
            public void onChanged(VersionBean.DataBean dataBean) {
                if (TextUtils.equals(dataBean.getIs_update(),"0")){
                    UpdateDialogFragment.newInstent(dataBean).show(getSupportFragmentManager(), "");
                }
            }
        });
        versionViewModel.requesVersionInfo(APPUtil.getVersionCode(mContext));
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.requesBacklogCount();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                showShortToast("再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void init() {
        hideTitleBar();
        activityMainBinding = (ActivityMainBinding) getBinding();
        mHomeFragment = (Fragment) ARouter.getInstance().build(MyARouter.HomeFragment).navigation();
        commissionFragment = (Fragment) ARouter.getInstance().build(MyARouter.CommissionFragment).navigation();
        mMeFragment = (Fragment) ARouter.getInstance().build(MyARouter.HomeFragment).navigation();
        activityMainBinding.navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int i = menuItem.getItemId();
                if (i == R.id.navigation_home) {
                    switchContent(mCurrFragment, mHomeFragment, "首页");
                    mCurrFragment = mHomeFragment;
                    return true;
                } else if (i == R.id.navigation_db) {
                    switchContent(mCurrFragment, commissionFragment, "待办");
                    mCurrFragment = commissionFragment;
                    viewModel.requesBacklogCount();
                    return true;
                } else if (i == R.id.navigation_me) {
                    ARouter.getInstance().build(MyARouter.SetingActivity).navigation();
                    return true;
                }
                return false;
            }
        });

        //添加角标
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) activityMainBinding.navigation.getChildAt(0);
        View tab = menuView.getChildAt(1);
        BottomNavigationItemView itemView = (BottomNavigationItemView) tab;
        //加载我们的角标View，新创建的一个布局
        View badge = LayoutInflater.from(this).inflate(R.layout.menu_badge, menuView, false);
        dbMsgView = badge.findViewById(R.id.tv_msg_count);
        //添加到Tab上
        itemView.addView(badge);

         mCurrFragment = mHomeFragment;
        if(mHomeFragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, mHomeFragment, "首页").commit();
        }
    }

    public void switchContent(Fragment from, Fragment to, String tag) {
        if (from == null || to == null) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!to.isAdded()) {
            transaction.hide(from).add(R.id.frame_content, to, tag).commit();
        } else {
            transaction.hide(from).show(to).commit();
        }
    }
}