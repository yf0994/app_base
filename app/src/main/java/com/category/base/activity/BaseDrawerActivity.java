package com.category.base.activity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.category.base.R;
import com.category.base.presenter.BasePresenter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fengyin on 16-4-26.
 */
public abstract class BaseDrawerActivity<T extends BasePresenter> extends BaseActivity<T>{
    protected DrawerLayout mDrawerLayout;
//    protected Toolbar mToolbar;
    protected NavigationView mNavigationView;

    private Fragment mContent;
    protected View mHeaderView;

    private Map<String, Fragment> mFragmentTable = new HashMap<>();

    protected abstract int getMemuId();
    protected abstract Fragment getDefaultFragment();
    protected abstract String getDefaultKey();
    protected abstract int getItemTextColor();
    protected abstract int getHeaderLayout();
    protected abstract void initHeaderView();
    protected abstract NavigationView.OnNavigationItemSelectedListener
                                        getOnNavigationItemSelectedListener();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_basedrawer;
    }

    @Override
    protected void initView() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mToolBar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolBar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                mToolBar,
                R.string.open,
                R.string.close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mNavigationView.inflateMenu(getMemuId());
        int headerViewResId = getHeaderLayout();
        if (headerViewResId != 0) {
            mHeaderView = mNavigationView.inflateHeaderView(headerViewResId);
        }
        int itemTextColor = getItemTextColor();
        if (itemTextColor != 0) {
            mNavigationView.setItemTextColor(ColorStateList.valueOf(itemTextColor));
        }
        NavigationView.OnNavigationItemSelectedListener listener = getOnNavigationItemSelectedListener();
        if (listener != null) {
            mNavigationView.setNavigationItemSelectedListener(listener);
        }
        setDefaultFragment();
        initHeaderView();
    }


    private void setDefaultFragment() {
        Fragment fragment = getDefaultFragment();
        String key = getDefaultKey();
        mFragmentTable.put(key, fragment);
        mContent = fragment;
        getSupportFragmentManager().beginTransaction().add(R.id.frame_content, fragment).commit();
    }

    protected void switchFragment(String key, Class<? extends Fragment> clazz) {
        mToolBar.setTitle(key);
        Fragment fragment = mFragmentTable.get(key);
        if(fragment == null){
            try {
                fragment = clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            mFragmentTable.put(key, fragment);

        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(mContent != fragment){
            if(!fragment.isAdded()){
                transaction.hide(mContent).add(R.id.frame_content, fragment).commit();
            } else {
                transaction.hide(mContent).show(fragment).commit();
            }
        }

        mContent = fragment;
    }

    class NavigationViewBuilder{
        private int mMenuId;
        private int mItemTextColor;
        private int mHeaderLayout;
        private NavigationView.OnNavigationItemSelectedListener mListener;
        private int mItemBackgroundRes;
        private Drawable mItemBackground;
        private int mItemIconTintColor;
        private NavigationView mNavigationView;

        public NavigationViewBuilder(NavigationView view){
            mNavigationView = view;
        }

        public NavigationViewBuilder setMenuId(@NonNull int menuId){
            mNavigationView.inflateMenu(menuId);
            return this;
        }

        public NavigationViewBuilder setItemTextColor(@NonNull int itemTextColor){
            mNavigationView.setItemTextColor(ColorStateList.valueOf(itemTextColor));
            return this;
        }

//        public NavigationViewBuilder setHeaderViewLayout(@NonNull int headerViewLayout){
//            mNavigationView.inflateHeaderView()
//        }

        public NavigationViewBuilder setOnNavigationItemSelectedListener(@NonNull NavigationView.OnNavigationItemSelectedListener listener){
            mNavigationView.setNavigationItemSelectedListener(listener);
            return this;
        }

        public NavigationViewBuilder setItemBackgroundRes(@NonNull int itemBackgroundRes){
            mNavigationView.setItemBackgroundResource(itemBackgroundRes);
            return this;
        }

        public NavigationViewBuilder setItemBackground(@NonNull Drawable itemBackground){
            mNavigationView.setItemBackground(itemBackground);
            return this;
        }

        public NavigationViewBuilder setItemIconTintColor(@NonNull int itemIconTintColor){
            mNavigationView.setItemIconTintList(ColorStateList.valueOf(itemIconTintColor));
            return this;
        }

        public NavigationView build(){
            return mNavigationView;
        }
    }
}
