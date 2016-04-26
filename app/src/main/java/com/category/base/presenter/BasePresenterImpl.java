package com.category.base.presenter;

import android.os.Bundle;

import com.category.base.BaseView;

/**
 * Created by fengyin on 16-4-11.
 */
public class BasePresenterImpl<T extends BaseView, V> implements BasePresenter{
    protected T mView;

    public BasePresenterImpl(T view){
        mView = view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }
}
