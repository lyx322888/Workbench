package com.zpz.main.ui;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yanzhenjie.sofia.Sofia;
import com.zpz.common.base.BaseActivity;
import com.zpz.common.base.DataBindingConfig;
import com.zpz.common.base.MyARouter;
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

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.activity_main);
    }
    @Override
    protected void setStatubarColor() {
        if (((ActivityMainBinding)getBinding())!=null){
            Sofia.with(this).invasionStatusBar().statusBarBackgroundAlpha(0).statusBarDarkFont();
        }
    }
    @Override
    protected void initViewObservable() {

    }
    @Override
    protected void init() {
        mHomeFragment = (Fragment) ARouter.getInstance().build(MyARouter.HomeFragment).navigation();
        commissionFragment = (Fragment) ARouter.getInstance().build(MyARouter.CommissionFragment).navigation();
        mMeFragment = (Fragment) ARouter.getInstance().build(MyARouter.HomeFragment).navigation();
        ( (ActivityMainBinding)getBinding()).navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
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
                    return true;
                } else if (i == R.id.navigation_me) {
                    ARouter.getInstance().build(MyARouter.SetingActivity).navigation();
                    return true;
                }
                return false;
            }
        });

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