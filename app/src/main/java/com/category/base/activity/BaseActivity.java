package com.category.base.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.category.base.BaseView;
import com.category.base.presenter.BasePresenter;
import com.category.base.util.LogUtil;
import com.category.base.util.Util;


/**
 * Author:fengyin
 * Date: 16-3-18    09:31
 * Email:594601408@qq.com
 * LastUpdateTime: 16-3-18
 * LastUpdateBy:594601408@qq.com
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView, View.OnClickListener {
    protected T mBasePresenter;

    protected Toolbar mToolBar;
    protected FragmentManager mFragmentManager;
    private boolean mCreate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getSupportFragmentManager();
        int layoutId = getLayoutId();
        setContentView(layoutId);
        initView();
        initToolBar();
        registerListener();
        mBasePresenter = createPresenter();
        mCreate = true;
    }

    protected abstract T createPresenter();

    @Override
    protected void onResume() {
        super.onResume();
        if(mBasePresenter != null){
            mBasePresenter.onResume();
        }
        if(mCreate){
            initData();
            mCreate = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mBasePresenter != null){
            mBasePresenter.onDestroy();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initToolBar();

    protected abstract void initData();

    protected abstract void registerListener();

    /**
     * Show message in logcat in info level.
     * @param msg
     */
    public void Logi(String msg){
        LogUtil.Logi(msg);
    }

    /**
     * Show message in logcat.
     * @param msg The showing message.
     * @param level @Constants.DEBUG_LEVEL_INFO,
     *              @Constants.DEBUG_LEVEL_ERROR,
     *              @Constants.DEBUG_LEVEL_WARNING,
     *              @Constants.DEBUG_LEVEL_VERBOSE,
     *              @Constants.DEBUG_LEVEL_DEBUG,
     */
    public void Log(String msg, int level){
        LogUtil.Log(msg, level);
    }

    public void showToast(String msg){
        Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void toast(String msg){
        showToast(msg);
    }

    @Override
    public void showProgress(){

    }

    @Override
    public void hideProgress(){

    }

    @Override
    public void onClick(View v) {

    }
}
