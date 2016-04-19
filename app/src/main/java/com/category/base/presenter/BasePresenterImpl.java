package com.category.base.presenter;

import com.category.base.BaseView;
import com.category.base.net.RequestCallback;

/**
 * Created by fengyin on 16-4-11.
 */
public class BasePresenterImpl<T extends BaseView, V> implements BasePresenter, RequestCallback<V> {
    protected T mView;

    public BasePresenterImpl(T view){
        mView = view;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestory() {

    }

    @Override
    public void beforeRequest() {
        mView.showProgress();
    }

    @Override
    public void requestError(String msg) {
        mView.hideProgress();
        mView.toast(msg);
    }

    @Override
    public void requestSuccess(V v) {

    }

    @Override
    public void requestComplete() {
        mView.hideProgress();
    }
}
