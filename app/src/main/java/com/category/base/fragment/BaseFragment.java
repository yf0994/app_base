package com.category.base.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.category.base.BaseView;
import com.category.base.presenter.BasePresenter;
import com.category.base.util.Logger;

/**
 * Created by fengyin on 16-4-11.
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements View.OnClickListener, BaseView {
    protected Activity mActivity;
    protected View mRootView;
    protected T mBasePresenter;

    private boolean mVisible;
    private boolean mCreated = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()){
            mVisible = true;
            onVisible();
        } else {
            mVisible = false;
            onInVisible();
        }
    }

    protected void onVisible(){
        lazyLoad();
    }

    protected abstract void lazyLoad();

    protected void onInVisible(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        mCreated = true;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int layoutId = getLayoutId();
        mRootView = inflater.inflate(layoutId, container, false);
        initView(mRootView);
        registerListener();
        return mRootView;
    }


    protected abstract int getLayoutId();

    protected abstract void initView(View view);

    protected void registerListener(){

    }

    protected void initData(){

    }

    @Override
    public void onResume() {
        super.onResume();
        if(mBasePresenter != null){
            mBasePresenter.onResume();
        }
        if(mCreated){
            initData();
            mCreated = false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mBasePresenter != null){
            mBasePresenter.onDestroy();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ViewGroup parent = (ViewGroup)mRootView.getParent();
        if(parent != null){
            parent.removeView(mRootView);
        }
    }

    /**
     * Show message in logcat in info level.
     * @param msg
     */
    public void Logi(String msg){
        Logger.getInstance().Logi(msg);
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
        Logger.getInstance().Log(msg, level);
    }

    public void showToast(String msg){
        Toast.makeText(mActivity, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void toast(String msg) {
        showToast(msg);
    }
}
